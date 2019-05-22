package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.StorageRoom;
import com.itechart.studlab.app.repository.StorageRoomRepository;
import com.itechart.studlab.app.repository.search.StorageRoomSearchRepository;
import com.itechart.studlab.app.service.dto.StorageRoomDTO;
import com.itechart.studlab.app.service.mapper.StorageRoomMapper;
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
 * Service Implementation for managing StorageRoom.
 */
@Service
@Transactional
public class StorageRoomService {

    private final Logger log = LoggerFactory.getLogger(StorageRoomService.class);

    private final StorageRoomRepository storageRoomRepository;

    private final StorageRoomMapper storageRoomMapper;

    private final StorageRoomSearchRepository storageRoomSearchRepository;

    public StorageRoomService(StorageRoomRepository storageRoomRepository, StorageRoomMapper storageRoomMapper, StorageRoomSearchRepository storageRoomSearchRepository) {
        this.storageRoomRepository = storageRoomRepository;
        this.storageRoomMapper = storageRoomMapper;
        this.storageRoomSearchRepository = storageRoomSearchRepository;
    }

    /**
     * Save a storageRoom.
     *
     * @param storageRoomDTO the entity to save
     * @return the persisted entity
     */
    public StorageRoomDTO save(StorageRoomDTO storageRoomDTO) {
        log.debug("Request to save StorageRoom : {}", storageRoomDTO);
        StorageRoom storageRoom = storageRoomMapper.toEntity(storageRoomDTO);
        storageRoom = storageRoomRepository.save(storageRoom);
        StorageRoomDTO result = storageRoomMapper.toDto(storageRoom);
        storageRoomSearchRepository.save(storageRoom);
        return result;
    }

    /**
     * Get all the storageRooms.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StorageRoomDTO> findAll() {
        log.debug("Request to get all StorageRooms");
        return storageRoomRepository.findAll().stream()
            .map(storageRoomMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one storageRoom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StorageRoomDTO> findOne(Long id) {
        log.debug("Request to get StorageRoom : {}", id);
        return storageRoomRepository.findById(id)
            .map(storageRoomMapper::toDto);
    }

    /**
     * Delete the storageRoom by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StorageRoom : {}", id);
        storageRoomRepository.deleteById(id);
        storageRoomSearchRepository.deleteById(id);
    }

    /**
     * Search for the storageRoom corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StorageRoomDTO> search(String query) {
        log.debug("Request to search StorageRooms for query {}", query);
        return StreamSupport
            .stream(storageRoomSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(storageRoomMapper::toDto)
            .collect(Collectors.toList());
    }
}
