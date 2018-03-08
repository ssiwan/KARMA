package com.stanfieldsystems.karma.web.rest;

import com.stanfieldsystems.karma.KarmaSpringbootApp;

import com.stanfieldsystems.karma.domain.Space;
import com.stanfieldsystems.karma.repository.SpaceRepository;
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
import java.util.List;

import static com.stanfieldsystems.karma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpaceResource REST controller.
 *
 * @see SpaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarmaSpringbootApp.class)
public class SpaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpaceMockMvc;

    private Space space;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpaceResource spaceResource = new SpaceResource(spaceRepository);
        this.restSpaceMockMvc = MockMvcBuilders.standaloneSetup(spaceResource)
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
    public static Space createEntity(EntityManager em) {
        Space space = new Space()
            .name(DEFAULT_NAME)
            .handle(DEFAULT_HANDLE);
        return space;
    }

    @Before
    public void initTest() {
        space = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpace() throws Exception {
        int databaseSizeBeforeCreate = spaceRepository.findAll().size();

        // Create the Space
        restSpaceMockMvc.perform(post("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(space)))
            .andExpect(status().isCreated());

        // Validate the Space in the database
        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeCreate + 1);
        Space testSpace = spaceList.get(spaceList.size() - 1);
//        assertThat(testSpace.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testSpace.getHandle()).isEqualTo(DEFAULT_HANDLE);
    }

    @Test
    @Transactional
    public void createSpaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spaceRepository.findAll().size();

        // Create the Space with an existing ID
        space.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpaceMockMvc.perform(post("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(space)))
            .andExpect(status().isBadRequest());

        // Validate the Space in the database
        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = spaceRepository.findAll().size();
        // set the field null
        space.setName(null);

        // Create the Space, which fails.

        restSpaceMockMvc.perform(post("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(space)))
            .andExpect(status().isBadRequest());

        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHandleIsRequired() throws Exception {
        int databaseSizeBeforeTest = spaceRepository.findAll().size();
        // set the field null
        space.setHandle(null);

        // Create the Space, which fails.

        restSpaceMockMvc.perform(post("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(space)))
            .andExpect(status().isBadRequest());

        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpaces() throws Exception {
        // Initialize the database
        spaceRepository.saveAndFlush(space);

        // Get all the spaceList
        restSpaceMockMvc.perform(get("/api/spaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(space.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE.toString())));
    }

    @Test
    @Transactional
    public void getSpace() throws Exception {
        // Initialize the database
        spaceRepository.saveAndFlush(space);

        // Get the space
        restSpaceMockMvc.perform(get("/api/spaces/{id}", space.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(space.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpace() throws Exception {
        // Get the space
        restSpaceMockMvc.perform(get("/api/spaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpace() throws Exception {
        // Initialize the database
        spaceRepository.saveAndFlush(space);
        int databaseSizeBeforeUpdate = spaceRepository.findAll().size();

        // Update the space
        Space updatedSpace = spaceRepository.findOne(space.getId());
        // Disconnect from session so that the updates on updatedSpace are not directly saved in db
        em.detach(updatedSpace);
        updatedSpace
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE);

        restSpaceMockMvc.perform(put("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpace)))
            .andExpect(status().isOk());

        // Validate the Space in the database
        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeUpdate);
        Space testSpace = spaceList.get(spaceList.size() - 1);
//        assertThat(testSpace.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testSpace.getHandle()).isEqualTo(UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpace() throws Exception {
        int databaseSizeBeforeUpdate = spaceRepository.findAll().size();

        // Create the Space

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpaceMockMvc.perform(put("/api/spaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(space)))
            .andExpect(status().isCreated());

        // Validate the Space in the database
        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpace() throws Exception {
        // Initialize the database
        spaceRepository.saveAndFlush(space);
        int databaseSizeBeforeDelete = spaceRepository.findAll().size();

        // Get the space
        restSpaceMockMvc.perform(delete("/api/spaces/{id}", space.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Space> spaceList = spaceRepository.findAll();
        assertThat(spaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Space.class);
        Space space1 = new Space();
        space1.setId(1L);
        Space space2 = new Space();
        space2.setId(space1.getId());
        assertThat(space1).isEqualTo(space2);
        space2.setId(2L);
        assertThat(space1).isNotEqualTo(space2);
        space1.setId(null);
        assertThat(space1).isNotEqualTo(space2);
    }
}
