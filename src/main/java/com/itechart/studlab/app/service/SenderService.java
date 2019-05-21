package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Sender;
import com.itechart.studlab.app.repository.SenderRepository;
import com.itechart.studlab.app.repository.search.SenderSearchRepository;
import com.itechart.studlab.app.service.dto.SenderDTO;
import com.itechart.studlab.app.service.mapper.SenderMapper;
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
 * Service Implementation for managing Sender.
 */
@Service
@Transactional
public class SenderService {

    private final Logger log = LoggerFactory.getLogger(SenderService.class);

    private final SenderRepository senderRepository;

    private final SenderMapper senderMapper;

    private final SenderSearchRepository senderSearchRepository;

    public SenderService(SenderRepository senderRepository, SenderMapper senderMapper, SenderSearchRepository senderSearchRepository) {
        this.senderRepository = senderRepository;
        this.senderMapper = senderMapper;
        this.senderSearchRepository = senderSearchRepository;
    }

    /**
     * Save a sender.
     *
     * @param senderDTO the entity to save
     * @return the persisted entity
     */
    public SenderDTO save(SenderDTO senderDTO) {
        log.debug("Request to save Sender : {}", senderDTO);
        Sender sender = senderMapper.toEntity(senderDTO);
        sender = senderRepository.save(sender);
        SenderDTO result = senderMapper.toDto(sender);
        senderSearchRepository.save(sender);
        return result;
    }

    /**
     * Get all the senders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SenderDTO> findAll() {
        log.debug("Request to get all Senders");
        return senderRepository.findAll().stream()
            .map(senderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sender by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SenderDTO> findOne(Long id) {
        log.debug("Request to get Sender : {}", id);
        return senderRepository.findById(id)
            .map(senderMapper::toDto);
    }

    /**
     * Delete the sender by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sender : {}", id);
        senderRepository.deleteById(id);
        senderSearchRepository.deleteById(id);
    }

    /**
     * Search for the sender corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SenderDTO> search(String query) {
        log.debug("Request to search Senders for query {}", query);
        return StreamSupport
            .stream(senderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(senderMapper::toDto)
            .collect(Collectors.toList());
    }
}
