package com.bibliotek.web.rest;
import com.bibliotek.domain.Administrativo;
import com.bibliotek.service.AdministrativoService;
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
 * REST controller for managing Administrativo.
 */
@RestController
@RequestMapping("/api")
public class AdministrativoResource {

    private final Logger log = LoggerFactory.getLogger(AdministrativoResource.class);

    private static final String ENTITY_NAME = "administrativo";

    private final AdministrativoService administrativoService;

    public AdministrativoResource(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    /**
     * POST  /administrativos : Create a new administrativo.
     *
     * @param administrativo the administrativo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new administrativo, or with status 400 (Bad Request) if the administrativo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/administrativos")
    public ResponseEntity<Administrativo> createAdministrativo(@RequestBody Administrativo administrativo) throws URISyntaxException {
        log.debug("REST request to save Administrativo : {}", administrativo);
        if (administrativo.getId() != null) {
            throw new BadRequestAlertException("A new administrativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Administrativo result = administrativoService.save(administrativo);
        return ResponseEntity.created(new URI("/api/administrativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /administrativos : Updates an existing administrativo.
     *
     * @param administrativo the administrativo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated administrativo,
     * or with status 400 (Bad Request) if the administrativo is not valid,
     * or with status 500 (Internal Server Error) if the administrativo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/administrativos")
    public ResponseEntity<Administrativo> updateAdministrativo(@RequestBody Administrativo administrativo) throws URISyntaxException {
        log.debug("REST request to update Administrativo : {}", administrativo);
        if (administrativo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Administrativo result = administrativoService.save(administrativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, administrativo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /administrativos : get all the administrativos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of administrativos in body
     */
    @GetMapping("/administrativos")
    public List<Administrativo> getAllAdministrativos() {
        log.debug("REST request to get all Administrativos");
        return administrativoService.findAll();
    }

    /**
     * GET  /administrativos/:id : get the "id" administrativo.
     *
     * @param id the id of the administrativo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the administrativo, or with status 404 (Not Found)
     */
    @GetMapping("/administrativos/{id}")
    public ResponseEntity<Administrativo> getAdministrativo(@PathVariable Long id) {
        log.debug("REST request to get Administrativo : {}", id);
        Optional<Administrativo> administrativo = administrativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrativo);
    }

    /**
     * DELETE  /administrativos/:id : delete the "id" administrativo.
     *
     * @param id the id of the administrativo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/administrativos/{id}")
    public ResponseEntity<Void> deleteAdministrativo(@PathVariable Long id) {
        log.debug("REST request to delete Administrativo : {}", id);
        administrativoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
