package com.stanfieldsystems.karma.web.rest;

import com.stanfieldsystems.karma.KarmaSpringbootApp;

import com.stanfieldsystems.karma.domain.TagHistory;
import com.stanfieldsystems.karma.repository.TagHistoryRepository;
import com.stanfieldsystems.karma.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.stanfieldsystems.karma.web.rest.TestUtil.sameInstant;
import static com.stanfieldsystems.karma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TagHistoryResource REST controller.
 *
 * @see TagHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarmaSpringbootApp.class)
public class TagHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_ACCESSED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ACCESSED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TagHistoryRepository tagHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTagHistoryMockMvc;

    private TagHistory tagHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TagHistoryResource tagHistoryResource = new TagHistoryResource(tagHistoryRepository);
        this.restTagHistoryMockMvc = MockMvcBuilders.standaloneSetup(tagHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagHistory createEntity(EntityManager em) {
        TagHistory tagHistory = new TagHistory()
            .dateAccessed(DEFAULT_DATE_ACCESSED);
        return tagHistory;
    }

    @Before
    public void initTest() {
        tagHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagHistory() throws Exception {
        int databaseSizeBeforeCreate = tagHistoryRepository.findAll().size();

        // Create the TagHistory
        restTagHistoryMockMvc.perform(post("/api/tag-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagHistory)))
            .andExpect(status().isCreated());

        // Validate the TagHistory in the database
        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TagHistory testTagHistory = tagHistoryList.get(tagHistoryList.size() - 1);
        assertThat(testTagHistory.getDateAccessed()).isEqualTo(DEFAULT_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void createTagHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagHistoryRepository.findAll().size();

        // Create the TagHistory with an existing ID
        tagHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagHistoryMockMvc.perform(post("/api/tag-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TagHistory in the database
        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAccessedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagHistoryRepository.findAll().size();
        // set the field null
        tagHistory.setDateAccessed(null);

        // Create the TagHistory, which fails.

        restTagHistoryMockMvc.perform(post("/api/tag-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagHistory)))
            .andExpect(status().isBadRequest());

        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagHistories() throws Exception {
        // Initialize the database
        tagHistoryRepository.saveAndFlush(tagHistory);

        // Get all the tagHistoryList
        restTagHistoryMockMvc.perform(get("/api/tag-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAccessed").value(hasItem(sameInstant(DEFAULT_DATE_ACCESSED))));
    }

    @Test
    @Transactional
    public void getTagHistory() throws Exception {
        // Initialize the database
        tagHistoryRepository.saveAndFlush(tagHistory);

        // Get the tagHistory
        restTagHistoryMockMvc.perform(get("/api/tag-histories/{id}", tagHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tagHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateAccessed").value(sameInstant(DEFAULT_DATE_ACCESSED)));
    }

    @Test
    @Transactional
    public void getNonExistingTagHistory() throws Exception {
        // Get the tagHistory
        restTagHistoryMockMvc.perform(get("/api/tag-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagHistory() throws Exception {
        // Initialize the database
        tagHistoryRepository.saveAndFlush(tagHistory);
        int databaseSizeBeforeUpdate = tagHistoryRepository.findAll().size();

        // Update the tagHistory
        TagHistory updatedTagHistory = tagHistoryRepository.findOne(tagHistory.getId());
        // Disconnect from session so that the updates on updatedTagHistory are not directly saved in db
        em.detach(updatedTagHistory);
        updatedTagHistory
            .dateAccessed(UPDATED_DATE_ACCESSED);

        restTagHistoryMockMvc.perform(put("/api/tag-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTagHistory)))
            .andExpect(status().isOk());

        // Validate the TagHistory in the database
        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeUpdate);
        TagHistory testTagHistory = tagHistoryList.get(tagHistoryList.size() - 1);
        assertThat(testTagHistory.getDateAccessed()).isEqualTo(UPDATED_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingTagHistory() throws Exception {
        int databaseSizeBeforeUpdate = tagHistoryRepository.findAll().size();

        // Create the TagHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTagHistoryMockMvc.perform(put("/api/tag-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagHistory)))
            .andExpect(status().isCreated());

        // Validate the TagHistory in the database
        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTagHistory() throws Exception {
        // Initialize the database
        tagHistoryRepository.saveAndFlush(tagHistory);
        int databaseSizeBeforeDelete = tagHistoryRepository.findAll().size();

        // Get the tagHistory
        restTagHistoryMockMvc.perform(delete("/api/tag-histories/{id}", tagHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TagHistory> tagHistoryList = tagHistoryRepository.findAll();
        assertThat(tagHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagHistory.class);
        TagHistory tagHistory1 = new TagHistory();
        tagHistory1.setId(1L);
        TagHistory tagHistory2 = new TagHistory();
        tagHistory2.setId(tagHistory1.getId());
        assertThat(tagHistory1).isEqualTo(tagHistory2);
        tagHistory2.setId(2L);
        assertThat(tagHistory1).isNotEqualTo(tagHistory2);
        tagHistory1.setId(null);
        assertThat(tagHistory1).isNotEqualTo(tagHistory2);
    }
}
