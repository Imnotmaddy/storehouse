package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.service.DriverService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.DriverDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Driver.
 */
@RestController
@RequestMapping("/api")
public class DriverResource {

    private final Logger log = LoggerFactory.getLogger(DriverResource.class);

    private static final String ENTITY_NAME = "driver";

    private final DriverService driverService;

    public DriverResource(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * POST  /drivers : Create a new driver.
     *
     * @param driverDTO the driverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driverDTO, or with status 400 (Bad Request) if the driver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drivers")
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driverDTO) throws URISyntaxException {
        log.debug("REST request to save Driver : {}", driverDTO);
        if (driverDTO.getId() != null) {
            throw new BadRequestAlertException("A new driver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DriverDTO result = driverService.save(driverDTO);
        return ResponseEntity.created(new URI("/api/drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drivers : Updates an existing driver.
     *
     * @param driverDTO the driverDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated driverDTO,
     * or with status 400 (Bad Request) if the driverDTO is not valid,
     * or with status 500 (Internal Server Error) if the driverDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drivers")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driverDTO) throws URISyntaxException {
        log.debug("REST request to update Driver : {}", driverDTO);
        if (driverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DriverDTO result = driverService.save(driverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, driverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drivers : get all the drivers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drivers in body
     */
    @GetMapping("/drivers")
    public List<DriverDTO> getAllDrivers() {
        log.debug("REST request to get all Drivers");
        return driverService.findAll();
    }

    /**
     * GET  /drivers/:id : get the "id" driver.
     *
     * @param id the id of the driverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the driverDTO, or with status 404 (Not Found)
     */
    @GetMapping("/drivers/{id}")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Long id) {
        log.debug("REST request to get Driver : {}", id);
        Optional<DriverDTO> driverDTO = driverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(driverDTO);
    }

    /**
     * DELETE  /drivers/:id : delete the "id" driver.
     *
     * @param id the id of the driverDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        log.debug("REST request to delete Driver : {}", id);
        driverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/drivers?query=:query : search for the driver corresponding
     * to the query.
     *
     * @param query the query of the driver search
     * @return the result of the search
     */
    @GetMapping("/_search/drivers")
    public List<DriverDTO> searchDrivers(@RequestParam String query) {
        log.debug("REST request to search Drivers for query {}", query);
        return driverService.search(query);
    }

}
