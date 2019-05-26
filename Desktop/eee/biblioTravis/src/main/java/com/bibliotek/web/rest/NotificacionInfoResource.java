package com.bibliotek.web.rest;
import com.bibliotek.domain.NotificacionInfo;
import com.bibliotek.service.NotificacionInfoService;
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
 * REST controller for managing NotificacionInfo.
 */
@RestController
@RequestMapping("/api")
public class NotificacionInfoResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionInfoResource.class);

    private static final String ENTITY_NAME = "notificacionInfo";

    private final NotificacionInfoService notificacionInfoService;

    public NotificacionInfoResource(NotificacionInfoService notificacionInfoService) {
        this.notificacionInfoService = notificacionInfoService;
    }

    /**
     * POST  /notificacion-infos : Create a new notificacionInfo.
     *
     * @param notificacionInfo the notificacionInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notificacionInfo, or with status 400 (Bad Request) if the notificacionInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notificacion-infos")
    public ResponseEntity<NotificacionInfo> createNotificacionInfo(@RequestBody NotificacionInfo notificacionInfo) throws URISyntaxException {
        log.debug("REST request to save NotificacionInfo : {}", notificacionInfo);
        if (notificacionInfo.getId() != null) {
            throw new BadRequestAlertException("A new notificacionInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificacionInfo result = notificacionInfoService.save(notificacionInfo);
        return ResponseEntity.created(new URI("/api/notificacion-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notificacion-infos : Updates an existing notificacionInfo.
     *
     * @param notificacionInfo the notificacionInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notificacionInfo,
     * or with status 400 (Bad Request) if the notificacionInfo is not valid,
     * or with status 500 (Internal Server Error) if the notificacionInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notificacion-infos")
    public ResponseEntity<NotificacionInfo> updateNotificacionInfo(@RequestBody NotificacionInfo notificacionInfo) throws URISyntaxException {
        log.debug("REST request to update NotificacionInfo : {}", notificacionInfo);
        if (notificacionInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotificacionInfo result = notificacionInfoService.save(notificacionInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notificacionInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notificacion-infos : get all the notificacionInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notificacionInfos in body
     */
    @GetMapping("/notificacion-infos")
    public List<NotificacionInfo> getAllNotificacionInfos() {
        log.debug("REST request to get all NotificacionInfos");
        return notificacionInfoService.findAll();
    }

    /**
     * GET  /notificacion-infos/:id : get the "id" notificacionInfo.
     *
     * @param id the id of the notificacionInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notificacionInfo, or with status 404 (Not Found)
     */
    @GetMapping("/notificacion-infos/{id}")
    public ResponseEntity<NotificacionInfo> getNotificacionInfo(@PathVariable Long id) {
        log.debug("REST request to get NotificacionInfo : {}", id);
        Optional<NotificacionInfo> notificacionInfo = notificacionInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificacionInfo);
    }

    /**
     * DELETE  /notificacion-infos/:id : delete the "id" notificacionInfo.
     *
     * @param id the id of the notificacionInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notificacion-infos/{id}")
    public ResponseEntity<Void> deleteNotificacionInfo(@PathVariable Long id) {
        log.debug("REST request to delete NotificacionInfo : {}", id);
        notificacionInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
