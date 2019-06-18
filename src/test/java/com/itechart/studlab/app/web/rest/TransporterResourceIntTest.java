package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Transporter;
import com.itechart.studlab.app.repository.TransporterRepository;
import com.itechart.studlab.app.repository.search.TransporterSearchRepository;
import com.itechart.studlab.app.service.TransporterService;
import com.itechart.studlab.app.service.dto.TransporterDTO;
import com.itechart.studlab.app.service.mapper.TransporterMapper;
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
import java.util.Collections;
import java.util.List;


import static com.itechart.studlab.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransporterResource REST controller.
 *
 * @see TransporterResource
 *//*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class TransporterResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private TransporterMapper transporterMapper;

    @Autowired
    private TransporterService transporterService;


    @Autowired
    private TransporterSearchRepository mockTransporterSearchRepository;

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

    private MockMvc restTransporterMockMvc;

    private Transporter transporter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransporterResource transporterResource = new TransporterResource(transporterService);
        this.restTransporterMockMvc = MockMvcBuilders.standaloneSetup(transporterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }


    public static Transporter createEntity(EntityManager em) {
        Transporter transporter = new Transporter()
            .companyName(DEFAULT_COMPANY_NAME);
        return transporter;
    }

    @Before
    public void initTest() {
        transporter = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransporter() throws Exception {
        int databaseSizeBeforeCreate = transporterRepository.findAll().size();

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);
        restTransporterMockMvc.perform(post("/api/transporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isCreated());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeCreate + 1);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);

        // Validate the Transporter in Elasticsearch
        verify(mockTransporterSearchRepository, times(1)).save(testTransporter);
    }

    @Test
    @Transactional
    public void createTransporterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transporterRepository.findAll().size();

        // Create the Transporter with an existing ID
        transporter.setId(1L);
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransporterMockMvc.perform(post("/api/transporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transporter in Elasticsearch
        verify(mockTransporterSearchRepository, times(0)).save(transporter);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporterRepository.findAll().size();
        // set the field null
        transporter.setCompanyName(null);

        // Create the Transporter, which fails.
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        restTransporterMockMvc.perform(post("/api/transporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isBadRequest());

        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransporters() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList
        restTransporterMockMvc.perform(get("/api/transporters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get the transporter
        restTransporterMockMvc.perform(get("/api/transporters/{id}", transporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transporter.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransporter() throws Exception {
        // Get the transporter
        restTransporterMockMvc.perform(get("/api/transporters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Update the transporter
        Transporter updatedTransporter = transporterRepository.findById(transporter.getId()).get();
        // Disconnect from session so that the updates on updatedTransporter are not directly saved in db
        em.detach(updatedTransporter);
        updatedTransporter
            .companyName(UPDATED_COMPANY_NAME);
        TransporterDTO transporterDTO = transporterMapper.toDto(updatedTransporter);

        restTransporterMockMvc.perform(put("/api/transporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);

        // Validate the Transporter in Elasticsearch
        verify(mockTransporterSearchRepository, times(1)).save(testTransporter);
    }

    @Test
    @Transactional
    public void updateNonExistingTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporterMockMvc.perform(put("/api/transporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transporter in Elasticsearch
        verify(mockTransporterSearchRepository, times(0)).save(transporter);
    }

    @Test
    @Transactional
    public void deleteTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeDelete = transporterRepository.findAll().size();

        // Delete the transporter
        restTransporterMockMvc.perform(delete("/api/transporters/{id}", transporter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transporter in Elasticsearch
        verify(mockTransporterSearchRepository, times(1)).deleteById(transporter.getId());
    }

    @Test
    @Transactional
    public void searchTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);
        when(mockTransporterSearchRepository.search(queryStringQuery("id:" + transporter.getId())))
            .thenReturn(Collections.singletonList(transporter));
        // Search the transporter
        restTransporterMockMvc.perform(get("/api/_search/transporters?query=id:" + transporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transporter.class);
        Transporter transporter1 = new Transporter();
        transporter1.setId(1L);
        Transporter transporter2 = new Transporter();
        transporter2.setId(transporter1.getId());
        assertThat(transporter1).isEqualTo(transporter2);
        transporter2.setId(2L);
        assertThat(transporter1).isNotEqualTo(transporter2);
        transporter1.setId(null);
        assertThat(transporter1).isNotEqualTo(transporter2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransporterDTO.class);
        TransporterDTO transporterDTO1 = new TransporterDTO();
        transporterDTO1.setId(1L);
        TransporterDTO transporterDTO2 = new TransporterDTO();
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
        transporterDTO2.setId(transporterDTO1.getId());
        assertThat(transporterDTO1).isEqualTo(transporterDTO2);
        transporterDTO2.setId(2L);
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
        transporterDTO1.setId(null);
        assertThat(transporterDTO1).isNotEqualTo(transporterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transporterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transporterMapper.fromId(null)).isNull();
    }
}
*/
