package com.bibliotek.web.rest;
import com.bibliotek.domain.SalaEstudio;
import com.bibliotek.service.SalaEstudioService;
import com.bibliotek.web.rest.errors.BadRequestAlertException;
import com.bibliotek.web.rest.util.HeaderUtil;
import com.bibliotek.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SalaEstudio.
 */
@RestController
@RequestMapping("/api")
public class SalaEstudioResource {

    private final Logger log = LoggerFactory.getLogger(SalaEstudioResource.class);

    private static final String ENTITY_NAME = "salaEstudio";

    private final SalaEstudioService salaEstudioService;

    public SalaEstudioResource(SalaEstudioService salaEstudioService) {
        this.salaEstudioService = salaEstudioService;
    }

    /**
     * POST  /sala-estudios : Create a new salaEstudio.
     *
     * @param salaEstudio the salaEstudio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaEstudio, or with status 400 (Bad Request) if the salaEstudio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sala-estudios")
    public ResponseEntity<SalaEstudio> createSalaEstudio(@RequestBody SalaEstudio salaEstudio) throws URISyntaxException {
        log.debug("REST request to save SalaEstudio : {}", salaEstudio);
        if (salaEstudio.getId() != null) {
            throw new BadRequestAlertException("A new salaEstudio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaEstudio result = salaEstudioService.save(salaEstudio);
        return ResponseEntity.created(new URI("/api/sala-estudios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sala-estudios : Updates an existing salaEstudio.
     *
     * @param salaEstudio the salaEstudio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaEstudio,
     * or with status 400 (Bad Request) if the salaEstudio is not valid,
     * or with status 500 (Internal Server Error) if the salaEstudio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sala-estudios")
    public ResponseEntity<SalaEstudio> updateSalaEstudio(@RequestBody SalaEstudio salaEstudio) throws URISyntaxException {
        log.debug("REST request to update SalaEstudio : {}", salaEstudio);
        if (salaEstudio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaEstudio result = salaEstudioService.save(salaEstudio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaEstudio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sala-estudios : get all the salaEstudios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salaEstudios in body
     */
    @GetMapping("/sala-estudios")
    public ResponseEntity<List<SalaEstudio>> getAllSalaEstudios(Pageable pageable) {
        log.debug("REST request to get a page of SalaEstudios");
        Page<SalaEstudio> page = salaEstudioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sala-estudios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sala-estudios/:id : get the "id" salaEstudio.
     *
     * @param id the id of the salaEstudio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salaEstudio, or with status 404 (Not Found)
     */
    @GetMapping("/sala-estudios/{id}")
    public ResponseEntity<SalaEstudio> getSalaEstudio(@PathVariable Long id) {
        log.debug("REST request to get SalaEstudio : {}", id);
        Optional<SalaEstudio> salaEstudio = salaEstudioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaEstudio);
    }

    /**
     * DELETE  /sala-estudios/:id : delete the "id" salaEstudio.
     *
     * @param id the id of the salaEstudio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sala-estudios/{id}")
    public ResponseEntity<Void> deleteSalaEstudio(@PathVariable Long id) {
        log.debug("REST request to delete SalaEstudio : {}", id);
        salaEstudioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
