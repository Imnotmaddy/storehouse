package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Transporter;
import com.itechart.studlab.app.repository.TransporterRepository;
import com.itechart.studlab.app.repository.search.TransporterSearchRepository;
import com.itechart.studlab.app.service.dto.TransporterDTO;
import com.itechart.studlab.app.service.mapper.TransporterMapper;
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
 * Service Implementation for managing Transporter.
 */
@Service
@Transactional
public class TransporterService {

    private final Logger log = LoggerFactory.getLogger(TransporterService.class);

    private final TransporterRepository transporterRepository;

    private final TransporterMapper transporterMapper;

    private final TransporterSearchRepository transporterSearchRepository;

    public TransporterService(TransporterRepository transporterRepository, TransporterMapper transporterMapper, TransporterSearchRepository transporterSearchRepository) {
        this.transporterRepository = transporterRepository;
        this.transporterMapper = transporterMapper;
        this.transporterSearchRepository = transporterSearchRepository;
    }

    /**
     * Save a transporter.
     *
     * @param transporterDTO the entity to save
     * @return the persisted entity
     */
    public TransporterDTO save(TransporterDTO transporterDTO) {
        log.debug("Request to save Transporter : {}", transporterDTO);
        Transporter transporter = transporterMapper.toEntity(transporterDTO);
        transporter = transporterRepository.save(transporter);
        TransporterDTO result = transporterMapper.toDto(transporter);
        transporterSearchRepository.save(transporter);
        return result;
    }

    /**
     * Get all the transporters.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TransporterDTO> findAll() {
        log.debug("Request to get all Transporters");
        return transporterRepository.findAll().stream()
            .map(transporterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transporter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TransporterDTO> findOne(Long id) {
        log.debug("Request to get Transporter : {}", id);
        return transporterRepository.findById(id)
            .map(transporterMapper::toDto);
    }

    /**
     * Delete the transporter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transporter : {}", id);
        transporterRepository.deleteById(id);
        transporterSearchRepository.deleteById(id);
    }

    /**
     * Search for the transporter corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TransporterDTO> search(String query) {
        log.debug("Request to search Transporters for query {}", query);
        return StreamSupport
            .stream(transporterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(transporterMapper::toDto)
            .collect(Collectors.toList());
    }
}
