package com.itechart.studlab.app.service;

import com.itechart.studlab.app.domain.Product;
import com.itechart.studlab.app.domain.Authority;
import com.itechart.studlab.app.domain.TTN;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.domain.enumeration.TtnStatus;
import com.itechart.studlab.app.repository.TTNRepository;
import com.itechart.studlab.app.repository.UserRepository;
import com.itechart.studlab.app.repository.search.TTNSearchRepository;
import com.itechart.studlab.app.security.SecurityUtils;
import com.itechart.studlab.app.service.dto.TTNDTO;
import com.itechart.studlab.app.service.mapper.ProductMapper;
import com.itechart.studlab.app.service.mapper.TTNMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TTN.
 */
@Service
@Transactional
public class TTNService {

    private final Logger log = LoggerFactory.getLogger(TTNService.class);

    private final TTNRepository tTNRepository;

    private final TTNMapper tTNMapper;

    private final TTNSearchRepository tTNSearchRepository;

    @Autowired
    private UserRepository userRepository;

    public TTNService(TTNRepository tTNRepository, TTNMapper tTNMapper, TTNSearchRepository tTNSearchRepository) {
        this.tTNRepository = tTNRepository;
        this.tTNMapper = tTNMapper;
        this.tTNSearchRepository = tTNSearchRepository;
    }

    /**
     * Save a tTN.
     *
     * @param tTNDTO the entity to save
     * @return the persisted entity
     */
    public TTNDTO save(TTNDTO tTNDTO) {
        log.debug("Request to save TTN : {}", tTNDTO);
        tTNDTO = asignUserToDTO(tTNDTO);
        tTNDTO = asignCompanyToDTO(tTNDTO);
        TTN tTN = tTNMapper.toEntity(tTNDTO);
        for (Product product : tTN.getProducts()) {
            product.setTTN(tTN);
        }
        tTN = tTNRepository.save(tTN);
        TTNDTO result = tTNMapper.toDto(tTN);
      //  tTNSearchRepository.save(tTN);
        return result;
    }

    /**
     * Get all the tTNS.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TTNDTO> findAll() {
        log.debug("Request to get all TTNS");
        return getTtnByStatus();
    }


    /**
     * Get one tTN by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TTNDTO> findOne(Long id) {
        log.debug("Request to get TTN : {}", id);
        return tTNRepository.findById(id)
            .map(tTNMapper::toDto);
    }

    /**
     * Delete the tTN by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TTN : {}", id);
        tTNRepository.deleteById(id);
        tTNSearchRepository.deleteById(id);
    }

    /**
     * Search for the tTN corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TTNDTO> search(String query) {
        log.debug("Request to search TTNS for query {}", query);
        return StreamSupport
            .stream(tTNSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(tTNMapper::toDto)
            .collect(Collectors.toList());
    }

    private TTNDTO asignCompanyToDTO(TTNDTO ttndto){
        if(ttndto.getRecipient()==null){
            ttndto.setRecipient(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getCompany());
        }
        if(ttndto.getSender()==null){
            ttndto.setSender(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getCompany());
        }
        return ttndto;
    }

    private TTNDTO asignUserToDTO(TTNDTO ttndto){
        Authority dispatcher = new Authority();
        Authority manager = new Authority();
        dispatcher.setName("ROLE_DISPATCHER");
        manager.setName("ROLE_MANAGER");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if(user.getAuthorities().contains(dispatcher)){
            ttndto.setDispatcherLastName(user.getLastName());
            ttndto.setDispatcherId(user.getId());
        }
        if (user.getAuthorities().contains(manager)){
            ttndto.setManagerLastName(user.getLastName());
            ttndto.setManagerId(user.getId());
        }
        return ttndto;
    }

    private List<TTNDTO> getTtnByStatus(){
        List<TTNDTO> list = new LinkedList<>();
        Authority dispatcher = new Authority();
        Authority manager = new Authority();
        Authority supervisor = new Authority();
        Authority owner = new Authority();
        dispatcher.setName("ROLE_DISPATCHER");
        manager.setName("ROLE_MANAGER");
        supervisor.setName("ROLE_SUPERVISOR");
        owner.setName("ROLE_OWNER");

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if(user.getAuthorities().contains(dispatcher)){
            list.addAll( tTNRepository.findAllByStatus(TtnStatus.DECORATED).stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
            list.addAll( tTNRepository.findAllByStatus(TtnStatus.CHECKED).stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
            list.addAll( tTNRepository.findAllByStatus(TtnStatus.RELEASE_ALLOWED).stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
        }

        if(user.getAuthorities().contains(supervisor)){
            list.addAll( tTNRepository.findAllByStatus(TtnStatus.REGISTERED).stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
        }

        if(user.getAuthorities().contains(manager)){
            list.addAll( tTNRepository.findAllByStatus(TtnStatus.REMOVED_FROM_STORAGE).stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
        }

        if(user.getAuthorities().contains(owner)){
            list.addAll( tTNRepository.findAll().stream()
                .map(tTNMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new)));
        }

        return list;
    }
}
