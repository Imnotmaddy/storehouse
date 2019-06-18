package com.itechart.studlab.app.web.rest;
import com.itechart.studlab.app.domain.TTN;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.repository.TTNRepository;
import com.itechart.studlab.app.repository.UserRepository;
import com.itechart.studlab.app.security.SecurityUtils;
import com.itechart.studlab.app.service.ProductService;
import com.itechart.studlab.app.service.TTNService;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
import com.itechart.studlab.app.web.rest.errors.InternalServerErrorException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.service.dto.TTNDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TTN.
 */
@RestController
@RequestMapping("/api")
public class TTNResource {

    private final Logger log = LoggerFactory.getLogger(TTNResource.class);

    private static final String ENTITY_NAME = "tTN";

    private final TTNService tTNService;

    private final ProductService productService;

    private TTNRepository ttnRepository;

    private UserRepository userRepository;

    public TTNResource(TTNService tTNService, ProductService productService, UserRepository userRepository, TTNRepository ttnRepository) {
        this.tTNService = tTNService;
        this.productService = productService;
        this.userRepository = userRepository;
        this.ttnRepository = ttnRepository;
    }

    /**
     * POST  /ttns : Create a new tTN.
     *
     * @param tTNDTO the tTNDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tTNDTO, or with status 400 (Bad Request) if the tTN has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ttns")
    public ResponseEntity<TTNDTO> createTTN(@Valid @RequestBody TTNDTO tTNDTO) throws URISyntaxException {
        log.debug("REST request to save TTN : {}", tTNDTO);
        if (tTNDTO.getId() != null) {
            throw new BadRequestAlertException("A new tTN cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String serialNumber = tTNDTO.getSerialNumber();

        if (tTNService.checkIfExist(serialNumber)){
            throw new ValidationException("TTN with such serial number already exist in this company");
        }
        TTNDTO result = tTNService.save(tTNDTO);
        return ResponseEntity.created(new URI("/api/ttns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ttns : Updates an existing tTN.
     *
     * @param tTNDTO the tTNDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tTNDTO,
     * or with status 400 (Bad Request) if the tTNDTO is not valid,
     * or with status 500 (Internal Server Error) if the tTNDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ttns")
    public ResponseEntity<TTNDTO> updateTTN(@Valid @RequestBody TTNDTO tTNDTO) throws URISyntaxException {
        log.debug("REST request to update TTN : {}", tTNDTO);
        if (tTNDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TTNDTO result = tTNService.save(tTNDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tTNDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ttns : get all the tTNS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tTNS in body
     */
    @GetMapping("/ttns")
    public List<TTNDTO> getAllTTNS() {
        log.debug("REST request to get all TTNS");
        return tTNService.findAll();
    }

    /**
     * GET  /ttns/:id : get the "id" tTN.
     *
     * @param id the id of the tTNDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tTNDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ttns/{id}")
    public ResponseEntity<TTNDTO> getTTN(@PathVariable Long id) {
        log.debug("REST request to get TTN : {}", id);
        Optional<TTNDTO> tTNDTO = tTNService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tTNDTO);
    }

    /**
     * DELETE  /ttns/:id : delete the "id" tTN.
     *
     * @param id the id of the tTNDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ttns/{id}")
    public ResponseEntity<Void> deleteTTN(@PathVariable Long id) {
        log.debug("REST request to delete TTN : {}", id);
        tTNService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ttns?query=:query : search for the tTN corresponding
     * to the query.
     *
     * @param query the query of the tTN search
     * @return the result of the search
     */
    @GetMapping("/_search/ttns")
    public List<TTNDTO> searchTTNS(@RequestParam String query) {
        log.debug("REST request to search TTNS for query {}", query);
        return tTNService.search(query);
    }

}
