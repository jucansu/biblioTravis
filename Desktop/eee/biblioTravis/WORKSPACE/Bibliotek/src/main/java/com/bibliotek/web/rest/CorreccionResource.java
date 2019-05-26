package com.bibliotek.web.rest;
import com.bibliotek.domain.Correccion;
import com.bibliotek.service.CorreccionService;
import com.bibliotek.web.rest.errors.BadRequestAlertException;
import com.bibliotek.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Correccion.
 */
@RestController
@RequestMapping("/api")
public class CorreccionResource {

    private final Logger log = LoggerFactory.getLogger(CorreccionResource.class);

    private static final String ENTITY_NAME = "correccion";

    private final CorreccionService correccionService;

    public CorreccionResource(CorreccionService correccionService) {
        this.correccionService = correccionService;
    }

    /**
     * POST  /correccions : Create a new correccion.
     *
     * @param correccion the correccion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correccion, or with status 400 (Bad Request) if the correccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correccions")
    public ResponseEntity<Correccion> createCorreccion(@RequestBody Correccion correccion) throws URISyntaxException {
        log.debug("REST request to save Correccion : {}", correccion);
        if (correccion.getId() != null) {
            throw new BadRequestAlertException("A new correccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Correccion result = correccionService.save(correccion);
        return ResponseEntity.created(new URI("/api/correccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correccions : Updates an existing correccion.
     *
     * @param correccion the correccion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correccion,
     * or with status 400 (Bad Request) if the correccion is not valid,
     * or with status 500 (Internal Server Error) if the correccion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correccions")
    public ResponseEntity<Correccion> updateCorreccion(@RequestBody Correccion correccion) throws URISyntaxException {
        log.debug("REST request to update Correccion : {}", correccion);
        if (correccion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Correccion result = correccionService.save(correccion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correccion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correccions : get all the correccions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of correccions in body
     */
    @GetMapping("/correccions")
    public List<Correccion> getAllCorreccions() {
        log.debug("REST request to get all Correccions");
        return correccionService.findAll();
    }

    /**
     * GET  /correccions/:id : get the "id" correccion.
     *
     * @param id the id of the correccion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correccion, or with status 404 (Not Found)
     */
    @GetMapping("/correccions/{id}")
    public ResponseEntity<Correccion> getCorreccion(@PathVariable Long id) {
        log.debug("REST request to get Correccion : {}", id);
        Optional<Correccion> correccion = correccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correccion);
    }

    /**
     * DELETE  /correccions/:id : delete the "id" correccion.
     *
     * @param id the id of the correccion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correccions/{id}")
    public ResponseEntity<Void> deleteCorreccion(@PathVariable Long id) {
        log.debug("REST request to delete Correccion : {}", id);
        correccionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
