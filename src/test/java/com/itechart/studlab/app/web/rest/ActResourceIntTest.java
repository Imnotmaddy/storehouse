package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Act;
import com.itechart.studlab.app.repository.ActRepository;
import com.itechart.studlab.app.repository.search.ActSearchRepository;
import com.itechart.studlab.app.service.ActService;
import com.itechart.studlab.app.service.dto.ActDTO;
import com.itechart.studlab.app.service.mapper.ActMapper;
import com.itechart.studlab.app.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.itechart.studlab.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.itechart.studlab.app.domain.enumeration.ActType;
/**
 * Test class for the ActResource REST controller.
 *
 * @see ActResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class ActResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final ActType DEFAULT_TYPE = ActType.THEFT;
    private static final ActType UPDATED_TYPE = ActType.INCONSISTENCE;

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private ActMapper actMapper;

    @Autowired
    private ActService actService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.ActSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActSearchRepository mockActSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restActMockMvc;

    private Act act;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActResource actResource = new ActResource(actService);
        this.restActMockMvc = MockMvcBuilders.standaloneSetup(actResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Act createEntity(EntityManager em) {
        Act act = new Act()
            .date(DEFAULT_DATE)
            .cost(DEFAULT_COST)
            .type(DEFAULT_TYPE);
        return act;
    }

    @Before
    public void initTest() {
        act = createEntity(em);
    }

    @Test
    @Transactional
    public void createAct() throws Exception {
        int databaseSizeBeforeCreate = actRepository.findAll().size();

        // Create the Act
        ActDTO actDTO = actMapper.toDto(act);
        restActMockMvc.perform(post("/api/acts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actDTO)))
            .andExpect(status().isCreated());

        // Validate the Act in the database
        List<Act> actList = actRepository.findAll();
        assertThat(actList).hasSize(databaseSizeBeforeCreate + 1);
        Act testAct = actList.get(actList.size() - 1);
        assertThat(testAct.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAct.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testAct.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Act in Elasticsearch
        verify(mockActSearchRepository, times(1)).save(testAct);
    }

    @Test
    @Transactional
    public void createActWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actRepository.findAll().size();

        // Create the Act with an existing ID
        act.setId(1L);
        ActDTO actDTO = actMapper.toDto(act);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActMockMvc.perform(post("/api/acts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Act in the database
        List<Act> actList = actRepository.findAll();
        assertThat(actList).hasSize(databaseSizeBeforeCreate);

        // Validate the Act in Elasticsearch
        verify(mockActSearchRepository, times(0)).save(act);
    }

    @Test
    @Transactional
    public void getAllActs() throws Exception {
        // Initialize the database
        actRepository.saveAndFlush(act);

        // Get all the actList
        restActMockMvc.perform(get("/api/acts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(act.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAct() throws Exception {
        // Initialize the database
        actRepository.saveAndFlush(act);

        // Get the act
        restActMockMvc.perform(get("/api/acts/{id}", act.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(act.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAct() throws Exception {
        // Get the act
        restActMockMvc.perform(get("/api/acts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAct() throws Exception {
        // Initialize the database
        actRepository.saveAndFlush(act);

        int databaseSizeBeforeUpdate = actRepository.findAll().size();

        // Update the act
        Act updatedAct = actRepository.findById(act.getId()).get();
        // Disconnect from session so that the updates on updatedAct are not directly saved in db
        em.detach(updatedAct);
        updatedAct
            .date(UPDATED_DATE)
            .cost(UPDATED_COST)
            .type(UPDATED_TYPE);
        ActDTO actDTO = actMapper.toDto(updatedAct);

        restActMockMvc.perform(put("/api/acts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actDTO)))
            .andExpect(status().isOk());

        // Validate the Act in the database
        List<Act> actList = actRepository.findAll();
        assertThat(actList).hasSize(databaseSizeBeforeUpdate);
        Act testAct = actList.get(actList.size() - 1);
        assertThat(testAct.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAct.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testAct.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Act in Elasticsearch
        verify(mockActSearchRepository, times(1)).save(testAct);
    }

    @Test
    @Transactional
    public void updateNonExistingAct() throws Exception {
        int databaseSizeBeforeUpdate = actRepository.findAll().size();

        // Create the Act
        ActDTO actDTO = actMapper.toDto(act);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActMockMvc.perform(put("/api/acts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Act in the database
        List<Act> actList = actRepository.findAll();
        assertThat(actList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Act in Elasticsearch
        verify(mockActSearchRepository, times(0)).save(act);
    }

    @Test
    @Transactional
    public void deleteAct() throws Exception {
        // Initialize the database
        actRepository.saveAndFlush(act);

        int databaseSizeBeforeDelete = actRepository.findAll().size();

        // Delete the act
        restActMockMvc.perform(delete("/api/acts/{id}", act.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Act> actList = actRepository.findAll();
        assertThat(actList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Act in Elasticsearch
        verify(mockActSearchRepository, times(1)).deleteById(act.getId());
    }

    @Test
    @Transactional
    public void searchAct() throws Exception {
        // Initialize the database
        actRepository.saveAndFlush(act);
        when(mockActSearchRepository.search(queryStringQuery("id:" + act.getId())))
            .thenReturn(Collections.singletonList(act));
        // Search the act
        restActMockMvc.perform(get("/api/_search/acts?query=id:" + act.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(act.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Act.class);
        Act act1 = new Act();
        act1.setId(1L);
        Act act2 = new Act();
        act2.setId(act1.getId());
        assertThat(act1).isEqualTo(act2);
        act2.setId(2L);
        assertThat(act1).isNotEqualTo(act2);
        act1.setId(null);
        assertThat(act1).isNotEqualTo(act2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActDTO.class);
        ActDTO actDTO1 = new ActDTO();
        actDTO1.setId(1L);
        ActDTO actDTO2 = new ActDTO();
        assertThat(actDTO1).isNotEqualTo(actDTO2);
        actDTO2.setId(actDTO1.getId());
        assertThat(actDTO1).isEqualTo(actDTO2);
        actDTO2.setId(2L);
        assertThat(actDTO1).isNotEqualTo(actDTO2);
        actDTO1.setId(null);
        assertThat(actDTO1).isNotEqualTo(actDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actMapper.fromId(null)).isNull();
    }
}
