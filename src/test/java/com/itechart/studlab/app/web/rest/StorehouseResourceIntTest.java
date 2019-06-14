package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Storehouse;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.repository.StorehouseRepository;
import com.itechart.studlab.app.repository.search.StorehouseSearchRepository;
import com.itechart.studlab.app.service.StorageRoomService;
import com.itechart.studlab.app.service.StorehouseService;
import com.itechart.studlab.app.service.dto.StorehouseDTO;
import com.itechart.studlab.app.service.mapper.StorehouseMapper;
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
 * Test class for the StorehouseResource REST controller.
 *
 * @see StorehouseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class StorehouseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StorehouseRepository storehouseRepository;

    @Autowired
    private StorehouseMapper storehouseMapper;

    @Autowired
    private StorehouseService storehouseService;

    @Autowired
    private StorageRoomService storageRoomService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.StorehouseSearchRepositoryMockConfiguration
     */
    @Autowired
    private StorehouseSearchRepository mockStorehouseSearchRepository;

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

    private MockMvc restStorehouseMockMvc;

    private Storehouse storehouse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StorehouseResource storehouseResource = new StorehouseResource(storehouseService, storageRoomService);
        this.restStorehouseMockMvc = MockMvcBuilders.standaloneSetup(storehouseResource)
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
    public static Storehouse createEntity(EntityManager em) {
        Storehouse storehouse = new Storehouse()
            .name(DEFAULT_NAME);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        storehouse.setOwner(user);
        // Add required entity
        storehouse.setAdministrator(user);
        // Add required entity
        storehouse.setDispatcher(user);
        // Add required entity
        storehouse.setManager(user);
        // Add required entity
        storehouse.setSupervisor(user);
        return storehouse;
    }

    @Before
    public void initTest() {
        storehouse = createEntity(em);
    }

    @Test
    @Transactional
    public void createStorehouse() throws Exception {
        int databaseSizeBeforeCreate = storehouseRepository.findAll().size();

        // Create the Storehouse
        StorehouseDTO storehouseDTO = storehouseMapper.toDto(storehouse);
        restStorehouseMockMvc.perform(post("/api/storehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storehouseDTO)))
            .andExpect(status().isCreated());

        // Validate the Storehouse in the database
        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeCreate + 1);
        Storehouse testStorehouse = storehouseList.get(storehouseList.size() - 1);
        assertThat(testStorehouse.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Storehouse in Elasticsearch
        verify(mockStorehouseSearchRepository, times(1)).save(testStorehouse);
    }

    @Test
    @Transactional
    public void createStorehouseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storehouseRepository.findAll().size();

        // Create the Storehouse with an existing ID
        storehouse.setId(1L);
        StorehouseDTO storehouseDTO = storehouseMapper.toDto(storehouse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorehouseMockMvc.perform(post("/api/storehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Storehouse in the database
        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Storehouse in Elasticsearch
        verify(mockStorehouseSearchRepository, times(0)).save(storehouse);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storehouseRepository.findAll().size();
        // set the field null
        storehouse.setName(null);

        // Create the Storehouse, which fails.
        StorehouseDTO storehouseDTO = storehouseMapper.toDto(storehouse);

        restStorehouseMockMvc.perform(post("/api/storehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storehouseDTO)))
            .andExpect(status().isBadRequest());

        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStorehouses() throws Exception {
        // Initialize the database
        storehouseRepository.saveAndFlush(storehouse);

        // Get all the storehouseList
        restStorehouseMockMvc.perform(get("/api/storehouses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStorehouse() throws Exception {
        // Initialize the database
        storehouseRepository.saveAndFlush(storehouse);

        // Get the storehouse
        restStorehouseMockMvc.perform(get("/api/storehouses/{id}", storehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storehouse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStorehouse() throws Exception {
        // Get the storehouse
        restStorehouseMockMvc.perform(get("/api/storehouses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStorehouse() throws Exception {
        // Initialize the database
        storehouseRepository.saveAndFlush(storehouse);

        int databaseSizeBeforeUpdate = storehouseRepository.findAll().size();

        // Update the storehouse
        Storehouse updatedStorehouse = storehouseRepository.findById(storehouse.getId()).get();
        // Disconnect from session so that the updates on updatedStorehouse are not directly saved in db
        em.detach(updatedStorehouse);
        updatedStorehouse
            .name(UPDATED_NAME);
        StorehouseDTO storehouseDTO = storehouseMapper.toDto(updatedStorehouse);

        restStorehouseMockMvc.perform(put("/api/storehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storehouseDTO)))
            .andExpect(status().isOk());

        // Validate the Storehouse in the database
        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeUpdate);
        Storehouse testStorehouse = storehouseList.get(storehouseList.size() - 1);
        assertThat(testStorehouse.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Storehouse in Elasticsearch
        verify(mockStorehouseSearchRepository, times(1)).save(testStorehouse);
    }

    @Test
    @Transactional
    public void updateNonExistingStorehouse() throws Exception {
        int databaseSizeBeforeUpdate = storehouseRepository.findAll().size();

        // Create the Storehouse
        StorehouseDTO storehouseDTO = storehouseMapper.toDto(storehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorehouseMockMvc.perform(put("/api/storehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Storehouse in the database
        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Storehouse in Elasticsearch
        verify(mockStorehouseSearchRepository, times(0)).save(storehouse);
    }

    @Test
    @Transactional
    public void deleteStorehouse() throws Exception {
        // Initialize the database
        storehouseRepository.saveAndFlush(storehouse);

        int databaseSizeBeforeDelete = storehouseRepository.findAll().size();

        // Delete the storehouse
        restStorehouseMockMvc.perform(delete("/api/storehouses/{id}", storehouse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Storehouse> storehouseList = storehouseRepository.findAll();
        assertThat(storehouseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Storehouse in Elasticsearch
        verify(mockStorehouseSearchRepository, times(1)).deleteById(storehouse.getId());
    }

    @Test
    @Transactional
    public void searchStorehouse() throws Exception {
        // Initialize the database
        storehouseRepository.saveAndFlush(storehouse);
        when(mockStorehouseSearchRepository.search(queryStringQuery("id:" + storehouse.getId())))
            .thenReturn(Collections.singletonList(storehouse));
        // Search the storehouse
        restStorehouseMockMvc.perform(get("/api/_search/storehouses?query=id:" + storehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Storehouse.class);
        Storehouse storehouse1 = new Storehouse();
        storehouse1.setId(1L);
        Storehouse storehouse2 = new Storehouse();
        storehouse2.setId(storehouse1.getId());
        assertThat(storehouse1).isEqualTo(storehouse2);
        storehouse2.setId(2L);
        assertThat(storehouse1).isNotEqualTo(storehouse2);
        storehouse1.setId(null);
        assertThat(storehouse1).isNotEqualTo(storehouse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorehouseDTO.class);
        StorehouseDTO storehouseDTO1 = new StorehouseDTO();
        storehouseDTO1.setId(1L);
        StorehouseDTO storehouseDTO2 = new StorehouseDTO();
        assertThat(storehouseDTO1).isNotEqualTo(storehouseDTO2);
        storehouseDTO2.setId(storehouseDTO1.getId());
        assertThat(storehouseDTO1).isEqualTo(storehouseDTO2);
        storehouseDTO2.setId(2L);
        assertThat(storehouseDTO1).isNotEqualTo(storehouseDTO2);
        storehouseDTO1.setId(null);
        assertThat(storehouseDTO1).isNotEqualTo(storehouseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storehouseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storehouseMapper.fromId(null)).isNull();
    }
}
