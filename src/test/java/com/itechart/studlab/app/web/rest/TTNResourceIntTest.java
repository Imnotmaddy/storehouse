package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.TTN;
import com.itechart.studlab.app.domain.Transport;
import com.itechart.studlab.app.domain.Transporter;
import com.itechart.studlab.app.repository.TTNRepository;
import com.itechart.studlab.app.repository.search.TTNSearchRepository;
import com.itechart.studlab.app.service.ProductService;
import com.itechart.studlab.app.service.TTNService;
import com.itechart.studlab.app.service.dto.TTNDTO;
import com.itechart.studlab.app.service.mapper.TTNMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Test class for the TTNResource REST controller.
 *
 * @see TTNResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class TTNResourceIntTest {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCTS_AMOUNT = 1;
    private static final Integer UPDATED_PRODUCTS_AMOUNT = 2;

    private static final Integer DEFAULT_NUMBER_OF_PRODUCT_ENTRIES = 1;
    private static final Integer UPDATED_NUMBER_OF_PRODUCT_ENTRIES = 2;

    private static final Instant DEFAULT_DATE_TIME_OF_REGISTRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME_OF_REGISTRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACCEPTED = false;
    private static final Boolean UPDATED_IS_ACCEPTED = true;

    @Autowired
    private TTNRepository tTNRepository;

    @Autowired
    private TTNMapper tTNMapper;

    @Autowired
    private TTNService tTNService;

    @Autowired
    private ProductService productService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.TTNSearchRepositoryMockConfiguration
     */
    @Autowired
    private TTNSearchRepository mockTTNSearchRepository;

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

    private MockMvc restTTNMockMvc;

    private TTN tTN;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TTNResource tTNResource = new TTNResource(tTNService, productService);
        this.restTTNMockMvc = MockMvcBuilders.standaloneSetup(tTNResource)
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
    public static TTN createEntity(EntityManager em) {
        TTN tTN = new TTN()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION)
            .description(DEFAULT_DESCRIPTION)
            .driverName(DEFAULT_DRIVER_NAME)
            .productsAmount(DEFAULT_PRODUCTS_AMOUNT)
            .numberOfProductEntries(DEFAULT_NUMBER_OF_PRODUCT_ENTRIES)
            .dateTimeOfRegistration(DEFAULT_DATE_TIME_OF_REGISTRATION)
            .isAccepted(DEFAULT_IS_ACCEPTED);
        // Add required entity
        Transport transport = TransportResourceIntTest.createEntity(em);
        em.persist(transport);
        em.flush();
        tTN.setTransport(transport);
        // Add required entity
        Transporter transporter = TransporterResourceIntTest.createEntity(em);
        em.persist(transporter);
        em.flush();
        tTN.setTransporter(transporter);
        return tTN;
    }

    @Before
    public void initTest() {
        tTN = createEntity(em);
    }

    @Test
    @Transactional
    public void createTTN() throws Exception {
        int databaseSizeBeforeCreate = tTNRepository.findAll().size();

        // Create the TTN
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);
        restTTNMockMvc.perform(post("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isCreated());

        // Validate the TTN in the database
        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeCreate + 1);
        TTN testTTN = tTNList.get(tTNList.size() - 1);
        assertThat(testTTN.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testTTN.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);
        assertThat(testTTN.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTTN.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testTTN.getProductsAmount()).isEqualTo(DEFAULT_PRODUCTS_AMOUNT);
        assertThat(testTTN.getNumberOfProductEntries()).isEqualTo(DEFAULT_NUMBER_OF_PRODUCT_ENTRIES);
        assertThat(testTTN.getDateTimeOfRegistration()).isEqualTo(DEFAULT_DATE_TIME_OF_REGISTRATION);
        assertThat(testTTN.isIsAccepted()).isEqualTo(DEFAULT_IS_ACCEPTED);

        // Validate the TTN in Elasticsearch
        verify(mockTTNSearchRepository, times(1)).save(testTTN);
    }

    @Test
    @Transactional
    public void createTTNWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tTNRepository.findAll().size();

        // Create the TTN with an existing ID
        tTN.setId(1L);
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTTNMockMvc.perform(post("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TTN in the database
        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeCreate);

        // Validate the TTN in Elasticsearch
        verify(mockTTNSearchRepository, times(0)).save(tTN);
    }

    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tTNRepository.findAll().size();
        // set the field null
        tTN.setSerialNumber(null);

        // Create the TTN, which fails.
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);

        restTTNMockMvc.perform(post("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isBadRequest());

        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tTNRepository.findAll().size();
        // set the field null
        tTN.setDateOfCreation(null);

        // Create the TTN, which fails.
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);

        restTTNMockMvc.perform(post("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isBadRequest());

        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTimeOfRegistrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tTNRepository.findAll().size();
        // set the field null
        tTN.setDateTimeOfRegistration(null);

        // Create the TTN, which fails.
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);

        restTTNMockMvc.perform(post("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isBadRequest());

        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTTNS() throws Exception {
        // Initialize the database
        tTNRepository.saveAndFlush(tTN);

        // Get all the tTNList
        restTTNMockMvc.perform(get("/api/ttns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tTN.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(DEFAULT_DATE_OF_CREATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].productsAmount").value(hasItem(DEFAULT_PRODUCTS_AMOUNT)))
            .andExpect(jsonPath("$.[*].numberOfProductEntries").value(hasItem(DEFAULT_NUMBER_OF_PRODUCT_ENTRIES)))
            .andExpect(jsonPath("$.[*].dateTimeOfRegistration").value(hasItem(DEFAULT_DATE_TIME_OF_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTTN() throws Exception {
        // Initialize the database
        tTNRepository.saveAndFlush(tTN);

        // Get the tTN
        restTTNMockMvc.perform(get("/api/ttns/{id}", tTN.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tTN.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfCreation").value(DEFAULT_DATE_OF_CREATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.productsAmount").value(DEFAULT_PRODUCTS_AMOUNT))
            .andExpect(jsonPath("$.numberOfProductEntries").value(DEFAULT_NUMBER_OF_PRODUCT_ENTRIES))
            .andExpect(jsonPath("$.dateTimeOfRegistration").value(DEFAULT_DATE_TIME_OF_REGISTRATION.toString()))
            .andExpect(jsonPath("$.isAccepted").value(DEFAULT_IS_ACCEPTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTTN() throws Exception {
        // Get the tTN
        restTTNMockMvc.perform(get("/api/ttns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTTN() throws Exception {
        // Initialize the database
        tTNRepository.saveAndFlush(tTN);

        int databaseSizeBeforeUpdate = tTNRepository.findAll().size();

        // Update the tTN
        TTN updatedTTN = tTNRepository.findById(tTN.getId()).get();
        // Disconnect from session so that the updates on updatedTTN are not directly saved in db
        em.detach(updatedTTN);
        updatedTTN
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .description(UPDATED_DESCRIPTION)
            .driverName(UPDATED_DRIVER_NAME)
            .productsAmount(UPDATED_PRODUCTS_AMOUNT)
            .numberOfProductEntries(UPDATED_NUMBER_OF_PRODUCT_ENTRIES)
            .dateTimeOfRegistration(UPDATED_DATE_TIME_OF_REGISTRATION)
            .isAccepted(UPDATED_IS_ACCEPTED);
        TTNDTO tTNDTO = tTNMapper.toDto(updatedTTN);

        restTTNMockMvc.perform(put("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isOk());

        // Validate the TTN in the database
        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeUpdate);
        TTN testTTN = tTNList.get(tTNList.size() - 1);
        assertThat(testTTN.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testTTN.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);
        assertThat(testTTN.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTTN.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testTTN.getProductsAmount()).isEqualTo(UPDATED_PRODUCTS_AMOUNT);
        assertThat(testTTN.getNumberOfProductEntries()).isEqualTo(UPDATED_NUMBER_OF_PRODUCT_ENTRIES);
        assertThat(testTTN.getDateTimeOfRegistration()).isEqualTo(UPDATED_DATE_TIME_OF_REGISTRATION);
        assertThat(testTTN.isIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);

        // Validate the TTN in Elasticsearch
        verify(mockTTNSearchRepository, times(1)).save(testTTN);
    }

    @Test
    @Transactional
    public void updateNonExistingTTN() throws Exception {
        int databaseSizeBeforeUpdate = tTNRepository.findAll().size();

        // Create the TTN
        TTNDTO tTNDTO = tTNMapper.toDto(tTN);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTTNMockMvc.perform(put("/api/ttns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tTNDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TTN in the database
        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TTN in Elasticsearch
        verify(mockTTNSearchRepository, times(0)).save(tTN);
    }

    @Test
    @Transactional
    public void deleteTTN() throws Exception {
        // Initialize the database
        tTNRepository.saveAndFlush(tTN);

        int databaseSizeBeforeDelete = tTNRepository.findAll().size();

        // Delete the tTN
        restTTNMockMvc.perform(delete("/api/ttns/{id}", tTN.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TTN> tTNList = tTNRepository.findAll();
        assertThat(tTNList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TTN in Elasticsearch
        verify(mockTTNSearchRepository, times(1)).deleteById(tTN.getId());
    }

    @Test
    @Transactional
    public void searchTTN() throws Exception {
        // Initialize the database
        tTNRepository.saveAndFlush(tTN);
        when(mockTTNSearchRepository.search(queryStringQuery("id:" + tTN.getId())))
            .thenReturn(Collections.singletonList(tTN));
        // Search the tTN
        restTTNMockMvc.perform(get("/api/_search/ttns?query=id:" + tTN.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tTN.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(DEFAULT_DATE_OF_CREATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME)))
            .andExpect(jsonPath("$.[*].productsAmount").value(hasItem(DEFAULT_PRODUCTS_AMOUNT)))
            .andExpect(jsonPath("$.[*].numberOfProductEntries").value(hasItem(DEFAULT_NUMBER_OF_PRODUCT_ENTRIES)))
            .andExpect(jsonPath("$.[*].dateTimeOfRegistration").value(hasItem(DEFAULT_DATE_TIME_OF_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TTN.class);
        TTN tTN1 = new TTN();
        tTN1.setId(1L);
        TTN tTN2 = new TTN();
        tTN2.setId(tTN1.getId());
        assertThat(tTN1).isEqualTo(tTN2);
        tTN2.setId(2L);
        assertThat(tTN1).isNotEqualTo(tTN2);
        tTN1.setId(null);
        assertThat(tTN1).isNotEqualTo(tTN2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TTNDTO.class);
        TTNDTO tTNDTO1 = new TTNDTO();
        tTNDTO1.setId(1L);
        TTNDTO tTNDTO2 = new TTNDTO();
        assertThat(tTNDTO1).isNotEqualTo(tTNDTO2);
        tTNDTO2.setId(tTNDTO1.getId());
        assertThat(tTNDTO1).isEqualTo(tTNDTO2);
        tTNDTO2.setId(2L);
        assertThat(tTNDTO1).isNotEqualTo(tTNDTO2);
        tTNDTO1.setId(null);
        assertThat(tTNDTO1).isNotEqualTo(tTNDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tTNMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tTNMapper.fromId(null)).isNull();
    }
}
