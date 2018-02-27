package com.stanfieldsystems.karma.web.rest;

import com.stanfieldsystems.karma.KarmaSpringbootApp;

import com.stanfieldsystems.karma.domain.SpaceHistory;
import com.stanfieldsystems.karma.repository.SpaceHistoryRepository;
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
 * Test class for the SpaceHistoryResource REST controller.
 *
 * @see SpaceHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarmaSpringbootApp.class)
public class SpaceHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_ACCESSED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ACCESSED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SpaceHistoryRepository spaceHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpaceHistoryMockMvc;

    private SpaceHistory spaceHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpaceHistoryResource spaceHistoryResource = new SpaceHistoryResource(spaceHistoryRepository);
        this.restSpaceHistoryMockMvc = MockMvcBuilders.standaloneSetup(spaceHistoryResource)
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
    public static SpaceHistory createEntity(EntityManager em) {
        SpaceHistory spaceHistory = new SpaceHistory()
            .dateAccessed(DEFAULT_DATE_ACCESSED);
        return spaceHistory;
    }

    @Before
    public void initTest() {
        spaceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpaceHistory() throws Exception {
        int databaseSizeBeforeCreate = spaceHistoryRepository.findAll().size();

        // Create the SpaceHistory
        restSpaceHistoryMockMvc.perform(post("/api/space-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spaceHistory)))
            .andExpect(status().isCreated());

        // Validate the SpaceHistory in the database
        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SpaceHistory testSpaceHistory = spaceHistoryList.get(spaceHistoryList.size() - 1);
        assertThat(testSpaceHistory.getDateAccessed()).isEqualTo(DEFAULT_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void createSpaceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spaceHistoryRepository.findAll().size();

        // Create the SpaceHistory with an existing ID
        spaceHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpaceHistoryMockMvc.perform(post("/api/space-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spaceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the SpaceHistory in the database
        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAccessedIsRequired() throws Exception {
        int databaseSizeBeforeTest = spaceHistoryRepository.findAll().size();
        // set the field null
        spaceHistory.setDateAccessed(null);

        // Create the SpaceHistory, which fails.

        restSpaceHistoryMockMvc.perform(post("/api/space-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spaceHistory)))
            .andExpect(status().isBadRequest());

        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpaceHistories() throws Exception {
        // Initialize the database
        spaceHistoryRepository.saveAndFlush(spaceHistory);

        // Get all the spaceHistoryList
        restSpaceHistoryMockMvc.perform(get("/api/space-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spaceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAccessed").value(hasItem(sameInstant(DEFAULT_DATE_ACCESSED))));
    }

    @Test
    @Transactional
    public void getSpaceHistory() throws Exception {
        // Initialize the database
        spaceHistoryRepository.saveAndFlush(spaceHistory);

        // Get the spaceHistory
        restSpaceHistoryMockMvc.perform(get("/api/space-histories/{id}", spaceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spaceHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateAccessed").value(sameInstant(DEFAULT_DATE_ACCESSED)));
    }

    @Test
    @Transactional
    public void getNonExistingSpaceHistory() throws Exception {
        // Get the spaceHistory
        restSpaceHistoryMockMvc.perform(get("/api/space-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpaceHistory() throws Exception {
        // Initialize the database
        spaceHistoryRepository.saveAndFlush(spaceHistory);
        int databaseSizeBeforeUpdate = spaceHistoryRepository.findAll().size();

        // Update the spaceHistory
        SpaceHistory updatedSpaceHistory = spaceHistoryRepository.findOne(spaceHistory.getId());
        // Disconnect from session so that the updates on updatedSpaceHistory are not directly saved in db
        em.detach(updatedSpaceHistory);
        updatedSpaceHistory
            .dateAccessed(UPDATED_DATE_ACCESSED);

        restSpaceHistoryMockMvc.perform(put("/api/space-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpaceHistory)))
            .andExpect(status().isOk());

        // Validate the SpaceHistory in the database
        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeUpdate);
        SpaceHistory testSpaceHistory = spaceHistoryList.get(spaceHistoryList.size() - 1);
        assertThat(testSpaceHistory.getDateAccessed()).isEqualTo(UPDATED_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingSpaceHistory() throws Exception {
        int databaseSizeBeforeUpdate = spaceHistoryRepository.findAll().size();

        // Create the SpaceHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpaceHistoryMockMvc.perform(put("/api/space-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spaceHistory)))
            .andExpect(status().isCreated());

        // Validate the SpaceHistory in the database
        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpaceHistory() throws Exception {
        // Initialize the database
        spaceHistoryRepository.saveAndFlush(spaceHistory);
        int databaseSizeBeforeDelete = spaceHistoryRepository.findAll().size();

        // Get the spaceHistory
        restSpaceHistoryMockMvc.perform(delete("/api/space-histories/{id}", spaceHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpaceHistory> spaceHistoryList = spaceHistoryRepository.findAll();
        assertThat(spaceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpaceHistory.class);
        SpaceHistory spaceHistory1 = new SpaceHistory();
        spaceHistory1.setId(1L);
        SpaceHistory spaceHistory2 = new SpaceHistory();
        spaceHistory2.setId(spaceHistory1.getId());
        assertThat(spaceHistory1).isEqualTo(spaceHistory2);
        spaceHistory2.setId(2L);
        assertThat(spaceHistory1).isNotEqualTo(spaceHistory2);
        spaceHistory1.setId(null);
        assertThat(spaceHistory1).isNotEqualTo(spaceHistory2);
    }
}
