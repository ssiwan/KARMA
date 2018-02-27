package com.stanfieldsystems.karma.web.rest;

import com.stanfieldsystems.karma.KarmaSpringbootApp;

import com.stanfieldsystems.karma.domain.ArticleHistory;
import com.stanfieldsystems.karma.repository.ArticleHistoryRepository;
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
 * Test class for the ArticleHistoryResource REST controller.
 *
 * @see ArticleHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarmaSpringbootApp.class)
public class ArticleHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_ACCESSED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ACCESSED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ArticleHistoryRepository articleHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleHistoryMockMvc;

    private ArticleHistory articleHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleHistoryResource articleHistoryResource = new ArticleHistoryResource(articleHistoryRepository);
        this.restArticleHistoryMockMvc = MockMvcBuilders.standaloneSetup(articleHistoryResource)
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
    public static ArticleHistory createEntity(EntityManager em) {
        ArticleHistory articleHistory = new ArticleHistory()
            .dateAccessed(DEFAULT_DATE_ACCESSED);
        return articleHistory;
    }

    @Before
    public void initTest() {
        articleHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleHistory() throws Exception {
        int databaseSizeBeforeCreate = articleHistoryRepository.findAll().size();

        // Create the ArticleHistory
        restArticleHistoryMockMvc.perform(post("/api/article-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleHistory)))
            .andExpect(status().isCreated());

        // Validate the ArticleHistory in the database
        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleHistory testArticleHistory = articleHistoryList.get(articleHistoryList.size() - 1);
        assertThat(testArticleHistory.getDateAccessed()).isEqualTo(DEFAULT_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void createArticleHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleHistoryRepository.findAll().size();

        // Create the ArticleHistory with an existing ID
        articleHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleHistoryMockMvc.perform(post("/api/article-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleHistory in the database
        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAccessedIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleHistoryRepository.findAll().size();
        // set the field null
        articleHistory.setDateAccessed(null);

        // Create the ArticleHistory, which fails.

        restArticleHistoryMockMvc.perform(post("/api/article-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleHistory)))
            .andExpect(status().isBadRequest());

        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticleHistories() throws Exception {
        // Initialize the database
        articleHistoryRepository.saveAndFlush(articleHistory);

        // Get all the articleHistoryList
        restArticleHistoryMockMvc.perform(get("/api/article-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAccessed").value(hasItem(sameInstant(DEFAULT_DATE_ACCESSED))));
    }

    @Test
    @Transactional
    public void getArticleHistory() throws Exception {
        // Initialize the database
        articleHistoryRepository.saveAndFlush(articleHistory);

        // Get the articleHistory
        restArticleHistoryMockMvc.perform(get("/api/article-histories/{id}", articleHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articleHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateAccessed").value(sameInstant(DEFAULT_DATE_ACCESSED)));
    }

    @Test
    @Transactional
    public void getNonExistingArticleHistory() throws Exception {
        // Get the articleHistory
        restArticleHistoryMockMvc.perform(get("/api/article-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleHistory() throws Exception {
        // Initialize the database
        articleHistoryRepository.saveAndFlush(articleHistory);
        int databaseSizeBeforeUpdate = articleHistoryRepository.findAll().size();

        // Update the articleHistory
        ArticleHistory updatedArticleHistory = articleHistoryRepository.findOne(articleHistory.getId());
        // Disconnect from session so that the updates on updatedArticleHistory are not directly saved in db
        em.detach(updatedArticleHistory);
        updatedArticleHistory
            .dateAccessed(UPDATED_DATE_ACCESSED);

        restArticleHistoryMockMvc.perform(put("/api/article-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticleHistory)))
            .andExpect(status().isOk());

        // Validate the ArticleHistory in the database
        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeUpdate);
        ArticleHistory testArticleHistory = articleHistoryList.get(articleHistoryList.size() - 1);
        assertThat(testArticleHistory.getDateAccessed()).isEqualTo(UPDATED_DATE_ACCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleHistory() throws Exception {
        int databaseSizeBeforeUpdate = articleHistoryRepository.findAll().size();

        // Create the ArticleHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleHistoryMockMvc.perform(put("/api/article-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleHistory)))
            .andExpect(status().isCreated());

        // Validate the ArticleHistory in the database
        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticleHistory() throws Exception {
        // Initialize the database
        articleHistoryRepository.saveAndFlush(articleHistory);
        int databaseSizeBeforeDelete = articleHistoryRepository.findAll().size();

        // Get the articleHistory
        restArticleHistoryMockMvc.perform(delete("/api/article-histories/{id}", articleHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticleHistory> articleHistoryList = articleHistoryRepository.findAll();
        assertThat(articleHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleHistory.class);
        ArticleHistory articleHistory1 = new ArticleHistory();
        articleHistory1.setId(1L);
        ArticleHistory articleHistory2 = new ArticleHistory();
        articleHistory2.setId(articleHistory1.getId());
        assertThat(articleHistory1).isEqualTo(articleHistory2);
        articleHistory2.setId(2L);
        assertThat(articleHistory1).isNotEqualTo(articleHistory2);
        articleHistory1.setId(null);
        assertThat(articleHistory1).isNotEqualTo(articleHistory2);
    }
}
