package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Storehouse;
import com.itechart.studlab.app.repository.StorehouseRepository;
import com.itechart.studlab.app.repository.search.StorehouseSearchRepository;
import com.itechart.studlab.app.service.dto.StorehouseDTO;
import com.itechart.studlab.app.service.mapper.StorageRoomMapper;
import com.itechart.studlab.app.service.mapper.StorehouseMapper;
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
 * Service Implementation for managing Storehouse.
 */
@Service
@Transactional
public class StorehouseService {

    private final Logger log = LoggerFactory.getLogger(StorehouseService.class);

    private final StorehouseRepository storehouseRepository;

    private final StorehouseMapper storehouseMapper;

    private final StorehouseSearchRepository storehouseSearchRepository;

    public StorehouseService(StorehouseRepository storehouseRepository, StorehouseMapper storehouseMapper, StorehouseSearchRepository storehouseSearchRepository) {
        this.storehouseRepository = storehouseRepository;
        this.storehouseMapper = storehouseMapper;
        this.storehouseSearchRepository = storehouseSearchRepository;
    }

    /**
     * Save a storehouse.
     *
     * @param storehouseDTO the entity to save
     * @return the persisted entity
     */
    public StorehouseDTO save(StorehouseDTO storehouseDTO) {
        log.debug("Request to save Storehouse : {}", storehouseDTO);
        Storehouse storehouse = storehouseMapper.toEntity(storehouseDTO);
        storehouse = storehouseRepository.save(storehouse);

        StorehouseDTO result = storehouseMapper.toDto(storehouse);
        storehouseSearchRepository.save(storehouse);
        return result;
    }

    /**
     * Get all the storehouses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StorehouseDTO> findAll() {
        log.debug("Request to get all Storehouses");
        return storehouseRepository.findAll().stream()
            .map(storehouseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<StorehouseDTO> findAllForCompany(String company) {
        log.debug("Request to get all Storehouses for company {}", company);
        return storehouseRepository.findAllByCompanyNameIs(company).stream()
            .map(storehouseMapper::toDto).collect(Collectors.toList());
    }


    /**
     * Get one storehouse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StorehouseDTO> findOne(Long id) {
        log.debug("Request to get Storehouse : {}", id);
        return storehouseRepository.findById(id)
            .map(storehouseMapper::toDto);
    }

    /**
     * Delete the storehouse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Storehouse : {}", id);
        storehouseRepository.deleteById(id);
        storehouseSearchRepository.deleteById(id);
    }

    /**
     * Search for the storehouse corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StorehouseDTO> search(String query) {
        log.debug("Request to search Storehouses for query {}", query);
        return StreamSupport
            .stream(storehouseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(storehouseMapper::toDto)
            .collect(Collectors.toList());
    }
}
