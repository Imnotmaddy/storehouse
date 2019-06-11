package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.service.ActService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.ActDTO;
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
 * REST controller for managing Act.
 */
@RestController
@RequestMapping("/api")
public class ActResource {

    private final Logger log = LoggerFactory.getLogger(ActResource.class);

    private static final String ENTITY_NAME = "act";

    private final ActService actService;

    public ActResource(ActService actService) {
        this.actService = actService;
    }

    /**
     * POST  /acts : Create a new act.
     *
     * @param actDTO the actDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actDTO, or with status 400 (Bad Request) if the act has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acts")
    public ResponseEntity<ActDTO> createAct(@Valid @RequestBody ActDTO actDTO) throws URISyntaxException {
        log.debug("REST request to save Act : {}", actDTO);
        if (actDTO.getId() != null) {
            throw new BadRequestAlertException("A new act cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActDTO result = actService.save(actDTO);
        return ResponseEntity.created(new URI("/api/acts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acts : Updates an existing act.
     *
     * @param actDTO the actDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actDTO,
     * or with status 400 (Bad Request) if the actDTO is not valid,
     * or with status 500 (Internal Server Error) if the actDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acts")
    public ResponseEntity<ActDTO> updateAct(@Valid @RequestBody ActDTO actDTO) throws URISyntaxException {
        log.debug("REST request to update Act : {}", actDTO);
        if (actDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActDTO result = actService.save(actDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acts : get all the acts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acts in body
     */
    @GetMapping("/acts")
    public List<ActDTO> getAllActs() {
        log.debug("REST request to get all Acts");
        return actService.findAll();
    }

    /**
     * GET  /acts/:id : get the "id" act.
     *
     * @param id the id of the actDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actDTO, or with status 404 (Not Found)
     */
    @GetMapping("/acts/{id}")
    public ResponseEntity<ActDTO> getAct(@PathVariable Long id) {
        log.debug("REST request to get Act : {}", id);
        Optional<ActDTO> actDTO = actService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actDTO);
    }

    /**
     * DELETE  /acts/:id : delete the "id" act.
     *
     * @param id the id of the actDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acts/{id}")
    public ResponseEntity<Void> deleteAct(@PathVariable Long id) {
        log.debug("REST request to delete Act : {}", id);
        actService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/acts?query=:query : search for the act corresponding
     * to the query.
     *
     * @param query the query of the act search
     * @return the result of the search
     */
    @GetMapping("/_search/acts")
    public List<ActDTO> searchActs(@RequestParam String query) {
        log.debug("REST request to search Acts for query {}", query);
        return actService.search(query);
    }

}
