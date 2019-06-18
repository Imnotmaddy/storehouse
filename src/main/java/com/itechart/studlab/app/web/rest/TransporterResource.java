package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.repository.UserRepository;
import com.itechart.studlab.app.security.SecurityUtils;
import com.itechart.studlab.app.service.TransporterService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.TransporterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Transporter.
 */
@RestController
@RequestMapping("/api")
public class TransporterResource {

    private final Logger log = LoggerFactory.getLogger(TransporterResource.class);

    private static final String ENTITY_NAME = "transporter";

    private final TransporterService transporterService;

    private UserRepository userRepository;

    public TransporterResource(TransporterService transporterService, UserRepository userRepository) {
        this.transporterService = transporterService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /transporters : Create a new transporter.
     *
     * @param transporterDTO the transporterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transporterDTO, or with status 400 (Bad Request) if the transporter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transporters")
    public ResponseEntity<TransporterDTO> createTransporter(@Valid @RequestBody TransporterDTO transporterDTO) throws URISyntaxException {
        log.debug("REST request to save Transporter : {}", transporterDTO);
        if (transporterDTO.getId() != null) {
            throw new BadRequestAlertException("A new transporter cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String dispatcherCompanyName = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getCompany();
        transporterDTO.setDispatcherCompanyName(dispatcherCompanyName);
        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity.created(new URI("/api/transporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transporters : Updates an existing transporter.
     *
     * @param transporterDTO the transporterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transporterDTO,
     * or with status 400 (Bad Request) if the transporterDTO is not valid,
     * or with status 500 (Internal Server Error) if the transporterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transporters")
    public ResponseEntity<TransporterDTO> updateTransporter(@Valid @RequestBody TransporterDTO transporterDTO) throws URISyntaxException {
        log.debug("REST request to update Transporter : {}", transporterDTO);
        if (transporterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transporterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transporters : get all the transporters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transporters in body
     */
    @GetMapping("/transporters")
    public List<TransporterDTO> getAllTransporters() {
        String dispatcherCompanyName = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getCompany();
        log.debug("REST request to get all Transporters");
        return transporterService.findAllByDispatcherCompanyName(dispatcherCompanyName);
    }

    /**
     * GET  /transporters/:id : get the "id" transporter.
     *
     * @param id the id of the transporterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transporterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transporters/{id}")
    public ResponseEntity<TransporterDTO> getTransporter(@PathVariable Long id) {
        log.debug("REST request to get Transporter : {}", id);
        Optional<TransporterDTO> transporterDTO = transporterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transporterDTO);
    }

    /**
     * DELETE  /transporters/:id : delete the "id" transporter.
     *
     * @param id the id of the transporterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transporters/{id}")
    public ResponseEntity<Void> deleteTransporter(@PathVariable Long id) {
        log.debug("REST request to delete Transporter : {}", id);
        transporterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transporters?query=:query : search for the transporter corresponding
     * to the query.
     *
     * @param query the query of the transporter search
     * @return the result of the search
     */
    @GetMapping("/_search/transporters")
    public List<TransporterDTO> searchTransporters(@RequestParam String query) {
        log.debug("REST request to search Transporters for query {}", query);
        return transporterService.search(query);
    }

}
