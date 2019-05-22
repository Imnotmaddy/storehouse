package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Transport;
import com.itechart.studlab.app.repository.TransportRepository;
import com.itechart.studlab.app.repository.search.TransportSearchRepository;
import com.itechart.studlab.app.service.TransportService;
import com.itechart.studlab.app.service.dto.TransportDTO;
import com.itechart.studlab.app.service.mapper.TransportMapper;
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

import com.itechart.studlab.app.domain.enumeration.DeliveryType;
import com.itechart.studlab.app.domain.enumeration.Facility;
/**
 * Test class for the TransportResource REST controller.
 *
 * @see TransportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class TransportResourceIntTest {

    private static final String DEFAULT_VEHICLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WAGONS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_WAGONS_NUMBER = "BBBBBBBBBB";

    private static final DeliveryType DEFAULT_DELIVERY_TYPE = DeliveryType.Auto;
    private static final DeliveryType UPDATED_DELIVERY_TYPE = DeliveryType.Train;

    private static final Facility DEFAULT_FACILITY = Facility.REFRIGERATOR;
    private static final Facility UPDATED_FACILITY = Facility.OPEN_SPACE;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private TransportService transportService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.TransportSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransportSearchRepository mockTransportSearchRepository;

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

    private MockMvc restTransportMockMvc;

    private Transport transport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportResource transportResource = new TransportResource(transportService);
        this.restTransportMockMvc = MockMvcBuilders.standaloneSetup(transportResource)
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
    public static Transport createEntity(EntityManager em) {
        Transport transport = new Transport()
            .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
            .wagonsNumber(DEFAULT_WAGONS_NUMBER)
            .deliveryType(DEFAULT_DELIVERY_TYPE)
            .facility(DEFAULT_FACILITY);
        return transport;
    }

    @Before
    public void initTest() {
        transport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransport() throws Exception {
        int databaseSizeBeforeCreate = transportRepository.findAll().size();

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);
        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportDTO)))
            .andExpect(status().isCreated());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeCreate + 1);
        Transport testTransport = transportList.get(transportList.size() - 1);
        assertThat(testTransport.getVehicleNumber()).isEqualTo(DEFAULT_VEHICLE_NUMBER);
        assertThat(testTransport.getWagonsNumber()).isEqualTo(DEFAULT_WAGONS_NUMBER);
        assertThat(testTransport.getDeliveryType()).isEqualTo(DEFAULT_DELIVERY_TYPE);
        assertThat(testTransport.getFacility()).isEqualTo(DEFAULT_FACILITY);

        // Validate the Transport in Elasticsearch
        verify(mockTransportSearchRepository, times(1)).save(testTransport);
    }

    @Test
    @Transactional
    public void createTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportRepository.findAll().size();

        // Create the Transport with an existing ID
        transport.setId(1L);
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transport in Elasticsearch
        verify(mockTransportSearchRepository, times(0)).save(transport);
    }

    @Test
    @Transactional
    public void getAllTransports() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get all the transportList
        restTransportMockMvc.perform(get("/api/transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transport.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].wagonsNumber").value(hasItem(DEFAULT_WAGONS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].deliveryType").value(hasItem(DEFAULT_DELIVERY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].facility").value(hasItem(DEFAULT_FACILITY.toString())));
    }
    
    @Test
    @Transactional
    public void getTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get the transport
        restTransportMockMvc.perform(get("/api/transports/{id}", transport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transport.getId().intValue()))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER.toString()))
            .andExpect(jsonPath("$.wagonsNumber").value(DEFAULT_WAGONS_NUMBER.toString()))
            .andExpect(jsonPath("$.deliveryType").value(DEFAULT_DELIVERY_TYPE.toString()))
            .andExpect(jsonPath("$.facility").value(DEFAULT_FACILITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransport() throws Exception {
        // Get the transport
        restTransportMockMvc.perform(get("/api/transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        int databaseSizeBeforeUpdate = transportRepository.findAll().size();

        // Update the transport
        Transport updatedTransport = transportRepository.findById(transport.getId()).get();
        // Disconnect from session so that the updates on updatedTransport are not directly saved in db
        em.detach(updatedTransport);
        updatedTransport
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .wagonsNumber(UPDATED_WAGONS_NUMBER)
            .deliveryType(UPDATED_DELIVERY_TYPE)
            .facility(UPDATED_FACILITY);
        TransportDTO transportDTO = transportMapper.toDto(updatedTransport);

        restTransportMockMvc.perform(put("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportDTO)))
            .andExpect(status().isOk());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeUpdate);
        Transport testTransport = transportList.get(transportList.size() - 1);
        assertThat(testTransport.getVehicleNumber()).isEqualTo(UPDATED_VEHICLE_NUMBER);
        assertThat(testTransport.getWagonsNumber()).isEqualTo(UPDATED_WAGONS_NUMBER);
        assertThat(testTransport.getDeliveryType()).isEqualTo(UPDATED_DELIVERY_TYPE);
        assertThat(testTransport.getFacility()).isEqualTo(UPDATED_FACILITY);

        // Validate the Transport in Elasticsearch
        verify(mockTransportSearchRepository, times(1)).save(testTransport);
    }

    @Test
    @Transactional
    public void updateNonExistingTransport() throws Exception {
        int databaseSizeBeforeUpdate = transportRepository.findAll().size();

        // Create the Transport
        TransportDTO transportDTO = transportMapper.toDto(transport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportMockMvc.perform(put("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transport in Elasticsearch
        verify(mockTransportSearchRepository, times(0)).save(transport);
    }

    @Test
    @Transactional
    public void deleteTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        int databaseSizeBeforeDelete = transportRepository.findAll().size();

        // Delete the transport
        restTransportMockMvc.perform(delete("/api/transports/{id}", transport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transport in Elasticsearch
        verify(mockTransportSearchRepository, times(1)).deleteById(transport.getId());
    }

    @Test
    @Transactional
    public void searchTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);
        when(mockTransportSearchRepository.search(queryStringQuery("id:" + transport.getId())))
            .thenReturn(Collections.singletonList(transport));
        // Search the transport
        restTransportMockMvc.perform(get("/api/_search/transports?query=id:" + transport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transport.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER)))
            .andExpect(jsonPath("$.[*].wagonsNumber").value(hasItem(DEFAULT_WAGONS_NUMBER)))
            .andExpect(jsonPath("$.[*].deliveryType").value(hasItem(DEFAULT_DELIVERY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].facility").value(hasItem(DEFAULT_FACILITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transport.class);
        Transport transport1 = new Transport();
        transport1.setId(1L);
        Transport transport2 = new Transport();
        transport2.setId(transport1.getId());
        assertThat(transport1).isEqualTo(transport2);
        transport2.setId(2L);
        assertThat(transport1).isNotEqualTo(transport2);
        transport1.setId(null);
        assertThat(transport1).isNotEqualTo(transport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportDTO.class);
        TransportDTO transportDTO1 = new TransportDTO();
        transportDTO1.setId(1L);
        TransportDTO transportDTO2 = new TransportDTO();
        assertThat(transportDTO1).isNotEqualTo(transportDTO2);
        transportDTO2.setId(transportDTO1.getId());
        assertThat(transportDTO1).isEqualTo(transportDTO2);
        transportDTO2.setId(2L);
        assertThat(transportDTO1).isNotEqualTo(transportDTO2);
        transportDTO1.setId(null);
        assertThat(transportDTO1).isNotEqualTo(transportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transportMapper.fromId(null)).isNull();
    }
}
