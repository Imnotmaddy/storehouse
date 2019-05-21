package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.service.SenderService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.SenderDTO;
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
 * REST controller for managing Sender.
 */
@RestController
@RequestMapping("/api")
public class SenderResource {

    private final Logger log = LoggerFactory.getLogger(SenderResource.class);

    private static final String ENTITY_NAME = "sender";

    private final SenderService senderService;

    public SenderResource(SenderService senderService) {
        this.senderService = senderService;
    }

    /**
     * POST  /senders : Create a new sender.
     *
     * @param senderDTO the senderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new senderDTO, or with status 400 (Bad Request) if the sender has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/senders")
    public ResponseEntity<SenderDTO> createSender(@RequestBody SenderDTO senderDTO) throws URISyntaxException {
        log.debug("REST request to save Sender : {}", senderDTO);
        if (senderDTO.getId() != null) {
            throw new BadRequestAlertException("A new sender cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SenderDTO result = senderService.save(senderDTO);
        return ResponseEntity.created(new URI("/api/senders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /senders : Updates an existing sender.
     *
     * @param senderDTO the senderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated senderDTO,
     * or with status 400 (Bad Request) if the senderDTO is not valid,
     * or with status 500 (Internal Server Error) if the senderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/senders")
    public ResponseEntity<SenderDTO> updateSender(@RequestBody SenderDTO senderDTO) throws URISyntaxException {
        log.debug("REST request to update Sender : {}", senderDTO);
        if (senderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SenderDTO result = senderService.save(senderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, senderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /senders : get all the senders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of senders in body
     */
    @GetMapping("/senders")
    public List<SenderDTO> getAllSenders() {
        log.debug("REST request to get all Senders");
        return senderService.findAll();
    }

    /**
     * GET  /senders/:id : get the "id" sender.
     *
     * @param id the id of the senderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the senderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/senders/{id}")
    public ResponseEntity<SenderDTO> getSender(@PathVariable Long id) {
        log.debug("REST request to get Sender : {}", id);
        Optional<SenderDTO> senderDTO = senderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(senderDTO);
    }

    /**
     * DELETE  /senders/:id : delete the "id" sender.
     *
     * @param id the id of the senderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/senders/{id}")
    public ResponseEntity<Void> deleteSender(@PathVariable Long id) {
        log.debug("REST request to delete Sender : {}", id);
        senderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/senders?query=:query : search for the sender corresponding
     * to the query.
     *
     * @param query the query of the sender search
     * @return the result of the search
     */
    @GetMapping("/_search/senders")
    public List<SenderDTO> searchSenders(@RequestParam String query) {
        log.debug("REST request to search Senders for query {}", query);
        return senderService.search(query);
    }

}
