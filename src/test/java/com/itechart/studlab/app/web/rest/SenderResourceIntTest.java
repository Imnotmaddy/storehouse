package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Sender;
import com.itechart.studlab.app.repository.SenderRepository;
import com.itechart.studlab.app.repository.search.SenderSearchRepository;
import com.itechart.studlab.app.service.SenderService;
import com.itechart.studlab.app.service.dto.SenderDTO;
import com.itechart.studlab.app.service.mapper.SenderMapper;
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
 * Test class for the SenderResource REST controller.
 *
 * @see SenderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class SenderResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private SenderMapper senderMapper;

    @Autowired
    private SenderService senderService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.SenderSearchRepositoryMockConfiguration
     */
    @Autowired
    private SenderSearchRepository mockSenderSearchRepository;

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

    private MockMvc restSenderMockMvc;

    private Sender sender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SenderResource senderResource = new SenderResource(senderService);
        this.restSenderMockMvc = MockMvcBuilders.standaloneSetup(senderResource)
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
    public static Sender createEntity(EntityManager em) {
        Sender sender = new Sender()
            .companyName(DEFAULT_COMPANY_NAME);
        return sender;
    }

    @Before
    public void initTest() {
        sender = createEntity(em);
    }

    @Test
    @Transactional
    public void createSender() throws Exception {
        int databaseSizeBeforeCreate = senderRepository.findAll().size();

        // Create the Sender
        SenderDTO senderDTO = senderMapper.toDto(sender);
        restSenderMockMvc.perform(post("/api/senders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(senderDTO)))
            .andExpect(status().isCreated());

        // Validate the Sender in the database
        List<Sender> senderList = senderRepository.findAll();
        assertThat(senderList).hasSize(databaseSizeBeforeCreate + 1);
        Sender testSender = senderList.get(senderList.size() - 1);
        assertThat(testSender.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);

        // Validate the Sender in Elasticsearch
        verify(mockSenderSearchRepository, times(1)).save(testSender);
    }

    @Test
    @Transactional
    public void createSenderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = senderRepository.findAll().size();

        // Create the Sender with an existing ID
        sender.setId(1L);
        SenderDTO senderDTO = senderMapper.toDto(sender);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSenderMockMvc.perform(post("/api/senders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(senderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sender in the database
        List<Sender> senderList = senderRepository.findAll();
        assertThat(senderList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sender in Elasticsearch
        verify(mockSenderSearchRepository, times(0)).save(sender);
    }

    @Test
    @Transactional
    public void getAllSenders() throws Exception {
        // Initialize the database
        senderRepository.saveAndFlush(sender);

        // Get all the senderList
        restSenderMockMvc.perform(get("/api/senders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sender.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSender() throws Exception {
        // Initialize the database
        senderRepository.saveAndFlush(sender);

        // Get the sender
        restSenderMockMvc.perform(get("/api/senders/{id}", sender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sender.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSender() throws Exception {
        // Get the sender
        restSenderMockMvc.perform(get("/api/senders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSender() throws Exception {
        // Initialize the database
        senderRepository.saveAndFlush(sender);

        int databaseSizeBeforeUpdate = senderRepository.findAll().size();

        // Update the sender
        Sender updatedSender = senderRepository.findById(sender.getId()).get();
        // Disconnect from session so that the updates on updatedSender are not directly saved in db
        em.detach(updatedSender);
        updatedSender
            .companyName(UPDATED_COMPANY_NAME);
        SenderDTO senderDTO = senderMapper.toDto(updatedSender);

        restSenderMockMvc.perform(put("/api/senders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(senderDTO)))
            .andExpect(status().isOk());

        // Validate the Sender in the database
        List<Sender> senderList = senderRepository.findAll();
        assertThat(senderList).hasSize(databaseSizeBeforeUpdate);
        Sender testSender = senderList.get(senderList.size() - 1);
        assertThat(testSender.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);

        // Validate the Sender in Elasticsearch
        verify(mockSenderSearchRepository, times(1)).save(testSender);
    }

    @Test
    @Transactional
    public void updateNonExistingSender() throws Exception {
        int databaseSizeBeforeUpdate = senderRepository.findAll().size();

        // Create the Sender
        SenderDTO senderDTO = senderMapper.toDto(sender);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSenderMockMvc.perform(put("/api/senders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(senderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sender in the database
        List<Sender> senderList = senderRepository.findAll();
        assertThat(senderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sender in Elasticsearch
        verify(mockSenderSearchRepository, times(0)).save(sender);
    }

    @Test
    @Transactional
    public void deleteSender() throws Exception {
        // Initialize the database
        senderRepository.saveAndFlush(sender);

        int databaseSizeBeforeDelete = senderRepository.findAll().size();

        // Delete the sender
        restSenderMockMvc.perform(delete("/api/senders/{id}", sender.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sender> senderList = senderRepository.findAll();
        assertThat(senderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sender in Elasticsearch
        verify(mockSenderSearchRepository, times(1)).deleteById(sender.getId());
    }

    @Test
    @Transactional
    public void searchSender() throws Exception {
        // Initialize the database
        senderRepository.saveAndFlush(sender);
        when(mockSenderSearchRepository.search(queryStringQuery("id:" + sender.getId())))
            .thenReturn(Collections.singletonList(sender));
        // Search the sender
        restSenderMockMvc.perform(get("/api/_search/senders?query=id:" + sender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sender.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sender.class);
        Sender sender1 = new Sender();
        sender1.setId(1L);
        Sender sender2 = new Sender();
        sender2.setId(sender1.getId());
        assertThat(sender1).isEqualTo(sender2);
        sender2.setId(2L);
        assertThat(sender1).isNotEqualTo(sender2);
        sender1.setId(null);
        assertThat(sender1).isNotEqualTo(sender2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SenderDTO.class);
        SenderDTO senderDTO1 = new SenderDTO();
        senderDTO1.setId(1L);
        SenderDTO senderDTO2 = new SenderDTO();
        assertThat(senderDTO1).isNotEqualTo(senderDTO2);
        senderDTO2.setId(senderDTO1.getId());
        assertThat(senderDTO1).isEqualTo(senderDTO2);
        senderDTO2.setId(2L);
        assertThat(senderDTO1).isNotEqualTo(senderDTO2);
        senderDTO1.setId(null);
        assertThat(senderDTO1).isNotEqualTo(senderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(senderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(senderMapper.fromId(null)).isNull();
    }
}
