package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Transport;
import com.itechart.studlab.app.repository.TransportRepository;
import com.itechart.studlab.app.repository.TransporterRepository;
import com.itechart.studlab.app.repository.search.TransportSearchRepository;
import com.itechart.studlab.app.service.dto.TransportDTO;
import com.itechart.studlab.app.service.mapper.TransportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Transport.
 */
@Service
@Transactional
public class TransportService {

    private final Logger log = LoggerFactory.getLogger(TransportService.class);

    private final TransportRepository transportRepository;

    private final TransportMapper transportMapper;

    private final TransportSearchRepository transportSearchRepository;



    public TransportService(TransportRepository transportRepository, TransportMapper transportMapper, TransportSearchRepository transportSearchRepository) {
        this.transportRepository = transportRepository;
        this.transportMapper = transportMapper;
        this.transportSearchRepository = transportSearchRepository;
    }

    /**
     * Save a transport.
     *
     * @param transportDTO the entity to save
     * @return the persisted entity
     */
    public TransportDTO save(TransportDTO transportDTO) {
        log.debug("Request to save Transport : {}", transportDTO);
        Transport transport = transportMapper.toEntity(transportDTO);
        transport = transportRepository.save(transport);
        TransportDTO result = transportMapper.toDto(transport);
        transportSearchRepository.save(transport);
        return result;
    }

    /**
     * Get all the transports.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TransportDTO> findAll() {
        log.debug("Request to get all Transports");
        return transportRepository.findAll().stream()
            .map(transportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<TransportDTO> findAllByTransporterDispatcherCompany(String dispatcherCompanyName){
        log.debug("Request to get transport with companyId:" + dispatcherCompanyName);
        List<TransportDTO> list = transportRepository.findAllByCompany_DispatcherCompanyName(dispatcherCompanyName).stream()
            .map(transportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        return list;
    }

    /**
     * Get one transport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TransportDTO> findOne(Long id) {
        log.debug("Request to get Transport : {}", id);
        return transportRepository.findById(id)
            .map(transportMapper::toDto);
    }

    /**
     * Delete the transport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transport : {}", id);
        transportRepository.deleteById(id);
        transportSearchRepository.deleteById(id);
    }

    /**
     * Search for the transport corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TransportDTO> search(String query) {
        log.debug("Request to search Transports for query {}", query);
        return StreamSupport
            .stream(transportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(transportMapper::toDto)
            .collect(Collectors.toList());
    }
}
