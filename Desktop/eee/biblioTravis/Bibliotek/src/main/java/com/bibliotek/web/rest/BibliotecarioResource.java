package com.bibliotek.web.rest;
import com.bibliotek.domain.Bibliotecario;
import com.bibliotek.service.BibliotecarioService;
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
 * REST controller for managing Bibliotecario.
 */
@RestController
@RequestMapping("/api")
public class BibliotecarioResource {

    private final Logger log = LoggerFactory.getLogger(BibliotecarioResource.class);

    private static final String ENTITY_NAME = "bibliotecario";

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioResource(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    /**
     * POST  /bibliotecarios : Create a new bibliotecario.
     *
     * @param bibliotecario the bibliotecario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bibliotecario, or with status 400 (Bad Request) if the bibliotecario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bibliotecarios")
    public ResponseEntity<Bibliotecario> createBibliotecario(@RequestBody Bibliotecario bibliotecario) throws URISyntaxException {
        log.debug("REST request to save Bibliotecario : {}", bibliotecario);
        if (bibliotecario.getId() != null) {
            throw new BadRequestAlertException("A new bibliotecario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bibliotecario result = bibliotecarioService.save(bibliotecario);
        return ResponseEntity.created(new URI("/api/bibliotecarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bibliotecarios : Updates an existing bibliotecario.
     *
     * @param bibliotecario the bibliotecario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bibliotecario,
     * or with status 400 (Bad Request) if the bibliotecario is not valid,
     * or with status 500 (Internal Server Error) if the bibliotecario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bibliotecarios")
    public ResponseEntity<Bibliotecario> updateBibliotecario(@RequestBody Bibliotecario bibliotecario) throws URISyntaxException {
        log.debug("REST request to update Bibliotecario : {}", bibliotecario);
        if (bibliotecario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bibliotecario result = bibliotecarioService.save(bibliotecario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bibliotecario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bibliotecarios : get all the bibliotecarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bibliotecarios in body
     */
    @GetMapping("/bibliotecarios")
    public List<Bibliotecario> getAllBibliotecarios() {
        log.debug("REST request to get all Bibliotecarios");
        return bibliotecarioService.findAll();
    }

    /**
     * GET  /bibliotecarios/:id : get the "id" bibliotecario.
     *
     * @param id the id of the bibliotecario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bibliotecario, or with status 404 (Not Found)
     */
    @GetMapping("/bibliotecarios/{id}")
    public ResponseEntity<Bibliotecario> getBibliotecario(@PathVariable Long id) {
        log.debug("REST request to get Bibliotecario : {}", id);
        Optional<Bibliotecario> bibliotecario = bibliotecarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bibliotecario);
    }

    /**
     * DELETE  /bibliotecarios/:id : delete the "id" bibliotecario.
     *
     * @param id the id of the bibliotecario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bibliotecarios/{id}")
    public ResponseEntity<Void> deleteBibliotecario(@PathVariable Long id) {
        log.debug("REST request to delete Bibliotecario : {}", id);
        bibliotecarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
