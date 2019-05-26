package com.bibliotek.web.rest;
import com.bibliotek.domain.Reporte;
import com.bibliotek.service.ReporteService;
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
 * REST controller for managing Reporte.
 */
@RestController
@RequestMapping("/api")
public class ReporteResource {

    private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private static final String ENTITY_NAME = "reporte";

    private final ReporteService reporteService;

    public ReporteResource(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * POST  /reportes : Create a new reporte.
     *
     * @param reporte the reporte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reporte, or with status 400 (Bad Request) if the reporte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reportes")
    public ResponseEntity<Reporte> createReporte(@RequestBody Reporte reporte) throws URISyntaxException {
        log.debug("REST request to save Reporte : {}", reporte);
        if (reporte.getId() != null) {
            throw new BadRequestAlertException("A new reporte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reporte result = reporteService.save(reporte);
        return ResponseEntity.created(new URI("/api/reportes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reportes : Updates an existing reporte.
     *
     * @param reporte the reporte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reporte,
     * or with status 400 (Bad Request) if the reporte is not valid,
     * or with status 500 (Internal Server Error) if the reporte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reportes")
    public ResponseEntity<Reporte> updateReporte(@RequestBody Reporte reporte) throws URISyntaxException {
        log.debug("REST request to update Reporte : {}", reporte);
        if (reporte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reporte result = reporteService.save(reporte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reporte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reportes : get all the reportes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reportes in body
     */
    @GetMapping("/reportes")
    public List<Reporte> getAllReportes() {
        log.debug("REST request to get all Reportes");
        return reporteService.findAll();
    }

    /**
     * GET  /reportes/:id : get the "id" reporte.
     *
     * @param id the id of the reporte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reporte, or with status 404 (Not Found)
     */
    @GetMapping("/reportes/{id}")
    public ResponseEntity<Reporte> getReporte(@PathVariable Long id) {
        log.debug("REST request to get Reporte : {}", id);
        Optional<Reporte> reporte = reporteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reporte);
    }

    /**
     * DELETE  /reportes/:id : delete the "id" reporte.
     *
     * @param id the id of the reporte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reportes/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        log.debug("REST request to delete Reporte : {}", id);
        reporteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
