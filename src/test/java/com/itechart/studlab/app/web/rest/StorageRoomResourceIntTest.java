package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.StorageRoom;
import com.itechart.studlab.app.repository.StorageRoomRepository;
import com.itechart.studlab.app.repository.search.StorageRoomSearchRepository;
import com.itechart.studlab.app.service.StorageRoomService;
import com.itechart.studlab.app.service.dto.StorageRoomDTO;
import com.itechart.studlab.app.service.mapper.StorageRoomMapper;
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

import com.itechart.studlab.app.domain.enumeration.Facility;
/**
 * Test class for the StorageRoomResource REST controller.
 *
 * @see StorageRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class StorageRoomResourceIntTest {

    private static final String DEFAULT_ROOM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NUMBER = "BBBBBBBBBB";

    private static final Facility DEFAULT_TYPE = Facility.REFRIGERATOR;
    private static final Facility UPDATED_TYPE = Facility.OPEN_SPACE;

    @Autowired
    private StorageRoomRepository storageRoomRepository;

    @Autowired
    private StorageRoomMapper storageRoomMapper;

    @Autowired
    private StorageRoomService storageRoomService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.StorageRoomSearchRepositoryMockConfiguration
     */
    @Autowired
    private StorageRoomSearchRepository mockStorageRoomSearchRepository;

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

    private MockMvc restStorageRoomMockMvc;

    private StorageRoom storageRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StorageRoomResource storageRoomResource = new StorageRoomResource(storageRoomService);
        this.restStorageRoomMockMvc = MockMvcBuilders.standaloneSetup(storageRoomResource)
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
    public static StorageRoom createEntity(EntityManager em) {
        StorageRoom storageRoom = new StorageRoom()
            .roomNumber(DEFAULT_ROOM_NUMBER)
            .type(DEFAULT_TYPE);
        return storageRoom;
    }

    @Before
    public void initTest() {
        storageRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createStorageRoom() throws Exception {
        int databaseSizeBeforeCreate = storageRoomRepository.findAll().size();

        // Create the StorageRoom
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(storageRoom);
        restStorageRoomMockMvc.perform(post("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the StorageRoom in the database
        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeCreate + 1);
        StorageRoom testStorageRoom = storageRoomList.get(storageRoomList.size() - 1);
        assertThat(testStorageRoom.getRoomNumber()).isEqualTo(DEFAULT_ROOM_NUMBER);
        assertThat(testStorageRoom.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the StorageRoom in Elasticsearch
        verify(mockStorageRoomSearchRepository, times(1)).save(testStorageRoom);
    }

    @Test
    @Transactional
    public void createStorageRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storageRoomRepository.findAll().size();

        // Create the StorageRoom with an existing ID
        storageRoom.setId(1L);
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(storageRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageRoomMockMvc.perform(post("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StorageRoom in the database
        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeCreate);

        // Validate the StorageRoom in Elasticsearch
        verify(mockStorageRoomSearchRepository, times(0)).save(storageRoom);
    }

    @Test
    @Transactional
    public void checkRoomNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageRoomRepository.findAll().size();
        // set the field null
        storageRoom.setRoomNumber(null);

        // Create the StorageRoom, which fails.
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(storageRoom);

        restStorageRoomMockMvc.perform(post("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isBadRequest());

        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageRoomRepository.findAll().size();
        // set the field null
        storageRoom.setType(null);

        // Create the StorageRoom, which fails.
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(storageRoom);

        restStorageRoomMockMvc.perform(post("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isBadRequest());

        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStorageRooms() throws Exception {
        // Initialize the database
        storageRoomRepository.saveAndFlush(storageRoom);

        // Get all the storageRoomList
        restStorageRoomMockMvc.perform(get("/api/storage-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getStorageRoom() throws Exception {
        // Initialize the database
        storageRoomRepository.saveAndFlush(storageRoom);

        // Get the storageRoom
        restStorageRoomMockMvc.perform(get("/api/storage-rooms/{id}", storageRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storageRoom.getId().intValue()))
            .andExpect(jsonPath("$.roomNumber").value(DEFAULT_ROOM_NUMBER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStorageRoom() throws Exception {
        // Get the storageRoom
        restStorageRoomMockMvc.perform(get("/api/storage-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStorageRoom() throws Exception {
        // Initialize the database
        storageRoomRepository.saveAndFlush(storageRoom);

        int databaseSizeBeforeUpdate = storageRoomRepository.findAll().size();

        // Update the storageRoom
        StorageRoom updatedStorageRoom = storageRoomRepository.findById(storageRoom.getId()).get();
        // Disconnect from session so that the updates on updatedStorageRoom are not directly saved in db
        em.detach(updatedStorageRoom);
        updatedStorageRoom
            .roomNumber(UPDATED_ROOM_NUMBER)
            .type(UPDATED_TYPE);
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(updatedStorageRoom);

        restStorageRoomMockMvc.perform(put("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isOk());

        // Validate the StorageRoom in the database
        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeUpdate);
        StorageRoom testStorageRoom = storageRoomList.get(storageRoomList.size() - 1);
        assertThat(testStorageRoom.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testStorageRoom.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the StorageRoom in Elasticsearch
        verify(mockStorageRoomSearchRepository, times(1)).save(testStorageRoom);
    }

    @Test
    @Transactional
    public void updateNonExistingStorageRoom() throws Exception {
        int databaseSizeBeforeUpdate = storageRoomRepository.findAll().size();

        // Create the StorageRoom
        StorageRoomDTO storageRoomDTO = storageRoomMapper.toDto(storageRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageRoomMockMvc.perform(put("/api/storage-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StorageRoom in the database
        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StorageRoom in Elasticsearch
        verify(mockStorageRoomSearchRepository, times(0)).save(storageRoom);
    }

    @Test
    @Transactional
    public void deleteStorageRoom() throws Exception {
        // Initialize the database
        storageRoomRepository.saveAndFlush(storageRoom);

        int databaseSizeBeforeDelete = storageRoomRepository.findAll().size();

        // Delete the storageRoom
        restStorageRoomMockMvc.perform(delete("/api/storage-rooms/{id}", storageRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StorageRoom> storageRoomList = storageRoomRepository.findAll();
        assertThat(storageRoomList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StorageRoom in Elasticsearch
        verify(mockStorageRoomSearchRepository, times(1)).deleteById(storageRoom.getId());
    }

    @Test
    @Transactional
    public void searchStorageRoom() throws Exception {
        // Initialize the database
        storageRoomRepository.saveAndFlush(storageRoom);
        when(mockStorageRoomSearchRepository.search(queryStringQuery("id:" + storageRoom.getId())))
            .thenReturn(Collections.singletonList(storageRoom));
        // Search the storageRoom
        restStorageRoomMockMvc.perform(get("/api/_search/storage-rooms?query=id:" + storageRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageRoom.class);
        StorageRoom storageRoom1 = new StorageRoom();
        storageRoom1.setId(1L);
        StorageRoom storageRoom2 = new StorageRoom();
        storageRoom2.setId(storageRoom1.getId());
        assertThat(storageRoom1).isEqualTo(storageRoom2);
        storageRoom2.setId(2L);
        assertThat(storageRoom1).isNotEqualTo(storageRoom2);
        storageRoom1.setId(null);
        assertThat(storageRoom1).isNotEqualTo(storageRoom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageRoomDTO.class);
        StorageRoomDTO storageRoomDTO1 = new StorageRoomDTO();
        storageRoomDTO1.setId(1L);
        StorageRoomDTO storageRoomDTO2 = new StorageRoomDTO();
        assertThat(storageRoomDTO1).isNotEqualTo(storageRoomDTO2);
        storageRoomDTO2.setId(storageRoomDTO1.getId());
        assertThat(storageRoomDTO1).isEqualTo(storageRoomDTO2);
        storageRoomDTO2.setId(2L);
        assertThat(storageRoomDTO1).isNotEqualTo(storageRoomDTO2);
        storageRoomDTO1.setId(null);
        assertThat(storageRoomDTO1).isNotEqualTo(storageRoomDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storageRoomMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storageRoomMapper.fromId(null)).isNull();
    }
}
