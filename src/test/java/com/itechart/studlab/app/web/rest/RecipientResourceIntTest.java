package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.StoreHouseApp;

import com.itechart.studlab.app.domain.Recipient;
import com.itechart.studlab.app.repository.RecipientRepository;
import com.itechart.studlab.app.repository.search.RecipientSearchRepository;
import com.itechart.studlab.app.service.RecipientService;
import com.itechart.studlab.app.service.dto.RecipientDTO;
import com.itechart.studlab.app.service.mapper.RecipientMapper;
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
 * Test class for the RecipientResource REST controller.
 *
 * @see RecipientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreHouseApp.class)
public class RecipientResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private RecipientMapper recipientMapper;

    @Autowired
    private RecipientService recipientService;

    /**
     * This repository is mocked in the com.itechart.studlab.app.repository.search test package.
     *
     * @see com.itechart.studlab.app.repository.search.RecipientSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecipientSearchRepository mockRecipientSearchRepository;

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

    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientResource recipientResource = new RecipientResource(recipientService);
        this.restRecipientMockMvc = MockMvcBuilders.standaloneSetup(recipientResource)
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
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .companyName(DEFAULT_COMPANY_NAME);
        return recipient;
    }

    @Before
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).save(testRecipient);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(0)).save(recipient);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .companyName(UPDATED_COMPANY_NAME);
        RecipientDTO recipientDTO = recipientMapper.toDto(updatedRecipient);

        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).save(testRecipient);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Create the Recipient
        RecipientDTO recipientDTO = recipientMapper.toDto(recipient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(0)).save(recipient);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Delete the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).deleteById(recipient.getId());
    }

    @Test
    @Transactional
    public void searchRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);
        when(mockRecipientSearchRepository.search(queryStringQuery("id:" + recipient.getId())))
            .thenReturn(Collections.singletonList(recipient));
        // Search the recipient
        restRecipientMockMvc.perform(get("/api/_search/recipients?query=id:" + recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipient.class);
        Recipient recipient1 = new Recipient();
        recipient1.setId(1L);
        Recipient recipient2 = new Recipient();
        recipient2.setId(recipient1.getId());
        assertThat(recipient1).isEqualTo(recipient2);
        recipient2.setId(2L);
        assertThat(recipient1).isNotEqualTo(recipient2);
        recipient1.setId(null);
        assertThat(recipient1).isNotEqualTo(recipient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipientDTO.class);
        RecipientDTO recipientDTO1 = new RecipientDTO();
        recipientDTO1.setId(1L);
        RecipientDTO recipientDTO2 = new RecipientDTO();
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO2.setId(recipientDTO1.getId());
        assertThat(recipientDTO1).isEqualTo(recipientDTO2);
        recipientDTO2.setId(2L);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO1.setId(null);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipientMapper.fromId(null)).isNull();
    }
}
