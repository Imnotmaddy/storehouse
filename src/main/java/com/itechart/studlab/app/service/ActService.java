package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Act;
import com.itechart.studlab.app.repository.ActRepository;
import com.itechart.studlab.app.repository.search.ActSearchRepository;
import com.itechart.studlab.app.service.dto.ActDTO;
import com.itechart.studlab.app.service.mapper.ActMapper;
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
 * Service Implementation for managing Act.
 */
@Service
@Transactional
public class ActService {

    private final Logger log = LoggerFactory.getLogger(ActService.class);

    private final ActRepository actRepository;

    private final ActMapper actMapper;

    private final ActSearchRepository actSearchRepository;

    public ActService(ActRepository actRepository, ActMapper actMapper, ActSearchRepository actSearchRepository) {
        this.actRepository = actRepository;
        this.actMapper = actMapper;
        this.actSearchRepository = actSearchRepository;
    }

    /**
     * Save a act.
     *
     * @param actDTO the entity to save
     * @return the persisted entity
     */
    public ActDTO save(ActDTO actDTO) {
        log.debug("Request to save Act : {}", actDTO);
        Act act = actMapper.toEntity(actDTO);
        act = actRepository.save(act);
        ActDTO result = actMapper.toDto(act);
        actSearchRepository.save(act);
        return result;
    }

    /**
     * Get all the acts.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ActDTO> findAll() {
        log.debug("Request to get all Acts");
        return actRepository.findAll().stream()
            .map(actMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one act by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ActDTO> findOne(Long id) {
        log.debug("Request to get Act : {}", id);
        return actRepository.findById(id)
            .map(actMapper::toDto);
    }

    /**
     * Delete the act by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Act : {}", id);
        actRepository.deleteById(id);
        actSearchRepository.deleteById(id);
    }

    /**
     * Search for the act corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ActDTO> search(String query) {
        log.debug("Request to search Acts for query {}", query);
        return StreamSupport
            .stream(actSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(actMapper::toDto)
            .collect(Collectors.toList());
    }
}
