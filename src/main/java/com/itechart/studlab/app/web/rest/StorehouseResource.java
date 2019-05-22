package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.service.StorehouseService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.StorehouseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Storehouse.
 */
@RestController
@RequestMapping("/api")
public class StorehouseResource {

    private final Logger log = LoggerFactory.getLogger(StorehouseResource.class);

    private static final String ENTITY_NAME = "storehouse";

    private final StorehouseService storehouseService;

    public StorehouseResource(StorehouseService storehouseService) {
        this.storehouseService = storehouseService;
    }

    /**
     * POST  /storehouses : Create a new storehouse.
     *
     * @param storehouseDTO the storehouseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storehouseDTO, or with status 400 (Bad Request) if the storehouse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/storehouses")
    public ResponseEntity<StorehouseDTO> createStorehouse(@Valid @RequestBody StorehouseDTO storehouseDTO) throws URISyntaxException {
        log.debug("REST request to save Storehouse : {}", storehouseDTO);
        if (storehouseDTO.getId() != null) {
            throw new BadRequestAlertException("A new storehouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorehouseDTO result = storehouseService.save(storehouseDTO);
        return ResponseEntity.created(new URI("/api/storehouses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /storehouses : Updates an existing storehouse.
     *
     * @param storehouseDTO the storehouseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storehouseDTO,
     * or with status 400 (Bad Request) if the storehouseDTO is not valid,
     * or with status 500 (Internal Server Error) if the storehouseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/storehouses")
    public ResponseEntity<StorehouseDTO> updateStorehouse(@Valid @RequestBody StorehouseDTO storehouseDTO) throws URISyntaxException {
        log.debug("REST request to update Storehouse : {}", storehouseDTO);
        if (storehouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StorehouseDTO result = storehouseService.save(storehouseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storehouseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /storehouses : get all the storehouses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storehouses in body
     */
    @GetMapping("/storehouses")
    public List<StorehouseDTO> getAllStorehouses() {
        log.debug("REST request to get all Storehouses");
        return storehouseService.findAll();
    }

    /**
     * GET  /storehouses/:id : get the "id" storehouse.
     *
     * @param id the id of the storehouseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storehouseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/storehouses/{id}")
    public ResponseEntity<StorehouseDTO> getStorehouse(@PathVariable Long id) {
        log.debug("REST request to get Storehouse : {}", id);
        Optional<StorehouseDTO> storehouseDTO = storehouseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storehouseDTO);
    }

    /**
     * DELETE  /storehouses/:id : delete the "id" storehouse.
     *
     * @param id the id of the storehouseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/storehouses/{id}")
    public ResponseEntity<Void> deleteStorehouse(@PathVariable Long id) {
        log.debug("REST request to delete Storehouse : {}", id);
        storehouseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/storehouses?query=:query : search for the storehouse corresponding
     * to the query.
     *
     * @param query the query of the storehouse search
     * @return the result of the search
     */
    @GetMapping("/_search/storehouses")
    public List<StorehouseDTO> searchStorehouses(@RequestParam String query) {
        log.debug("REST request to search Storehouses for query {}", query);
        return storehouseService.search(query);
    }

}
