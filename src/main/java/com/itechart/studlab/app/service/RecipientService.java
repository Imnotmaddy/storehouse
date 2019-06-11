package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Recipient;
import com.itechart.studlab.app.repository.RecipientRepository;
import com.itechart.studlab.app.repository.search.RecipientSearchRepository;
import com.itechart.studlab.app.service.dto.RecipientDTO;
import com.itechart.studlab.app.service.mapper.RecipientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Recipient.
 */
@Service
@Transactional
public class RecipientService {

    private final Logger log = LoggerFactory.getLogger(RecipientService.class);

    private final RecipientRepository recipientRepository;

    private final RecipientMapper recipientMapper;

    private final RecipientSearchRepository recipientSearchRepository;

    public RecipientService(RecipientRepository recipientRepository, RecipientMapper recipientMapper, RecipientSearchRepository recipientSearchRepository) {
        this.recipientRepository = recipientRepository;
        this.recipientMapper = recipientMapper;
        this.recipientSearchRepository = recipientSearchRepository;
    }

    /**
     * Save a recipient.
     *
     * @param recipientDTO the entity to save
     * @return the persisted entity
     */
    public RecipientDTO save(RecipientDTO recipientDTO) {
        log.debug("Request to save Recipient : {}", recipientDTO);
        Recipient recipient = recipientMapper.toEntity(recipientDTO);
        recipient = recipientRepository.save(recipient);
        RecipientDTO result = recipientMapper.toDto(recipient);
        recipientSearchRepository.save(recipient);
        return result;
    }

    /**
     * Get all the recipients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RecipientDTO> findAll() {
        log.debug("Request to get all Recipients");
        return recipientRepository.findAll().stream()
            .map(recipientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one recipient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RecipientDTO> findOne(Long id) {
        log.debug("Request to get Recipient : {}", id);
        return recipientRepository.findById(id)
            .map(recipientMapper::toDto);
    }

    /**
     * Delete the recipient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Recipient : {}", id);
        recipientRepository.deleteById(id);
        recipientSearchRepository.deleteById(id);
    }

    /**
     * Search for the recipient corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RecipientDTO> search(String query) {
        log.debug("Request to search Recipients for query {}", query);
        return StreamSupport
            .stream(recipientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(recipientMapper::toDto)
            .collect(Collectors.toList());
    }
}
