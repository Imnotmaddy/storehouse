package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.service.StorageRoomService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.StorageRoomDTO;
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
 * REST controller for managing StorageRoom.
 */
@RestController
@RequestMapping("/api")
public class StorageRoomResource {

    private final Logger log = LoggerFactory.getLogger(StorageRoomResource.class);

    private static final String ENTITY_NAME = "storageRoom";

    private final StorageRoomService storageRoomService;

    public StorageRoomResource(StorageRoomService storageRoomService) {
        this.storageRoomService = storageRoomService;
    }

    /**
     * POST  /storage-rooms : Create a new storageRoom.
     *
     * @param storageRoomDTO the storageRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storageRoomDTO, or with status 400 (Bad Request) if the storageRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/storage-rooms")
    public ResponseEntity<StorageRoomDTO> createStorageRoom(@RequestBody StorageRoomDTO storageRoomDTO) throws URISyntaxException {
        log.debug("REST request to save StorageRoom : {}", storageRoomDTO);
        if (storageRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new storageRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageRoomDTO result = storageRoomService.save(storageRoomDTO);
        return ResponseEntity.created(new URI("/api/storage-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /storage-rooms : Updates an existing storageRoom.
     *
     * @param storageRoomDTO the storageRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storageRoomDTO,
     * or with status 400 (Bad Request) if the storageRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the storageRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/storage-rooms")
    public ResponseEntity<StorageRoomDTO> updateStorageRoom(@RequestBody StorageRoomDTO storageRoomDTO) throws URISyntaxException {
        log.debug("REST request to update StorageRoom : {}", storageRoomDTO);
        if (storageRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StorageRoomDTO result = storageRoomService.save(storageRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storageRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /storage-rooms : get all the storageRooms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storageRooms in body
     */
    @GetMapping("/storage-rooms")
    public List<StorageRoomDTO> getAllStorageRooms() {
        log.debug("REST request to get all StorageRooms");
        return storageRoomService.findAll();
    }

    /**
     * GET  /storage-rooms/:id : get the "id" storageRoom.
     *
     * @param id the id of the storageRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storageRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/storage-rooms/{id}")
    public ResponseEntity<StorageRoomDTO> getStorageRoom(@PathVariable Long id) {
        log.debug("REST request to get StorageRoom : {}", id);
        Optional<StorageRoomDTO> storageRoomDTO = storageRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storageRoomDTO);
    }

    /**
     * DELETE  /storage-rooms/:id : delete the "id" storageRoom.
     *
     * @param id the id of the storageRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/storage-rooms/{id}")
    public ResponseEntity<Void> deleteStorageRoom(@PathVariable Long id) {
        log.debug("REST request to delete StorageRoom : {}", id);
        storageRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/storage-rooms?query=:query : search for the storageRoom corresponding
     * to the query.
     *
     * @param query the query of the storageRoom search
     * @return the result of the search
     */
    @GetMapping("/_search/storage-rooms")
    public List<StorageRoomDTO> searchStorageRooms(@RequestParam String query) {
        log.debug("REST request to search StorageRooms for query {}", query);
        return storageRoomService.search(query);
    }

}
