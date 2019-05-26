package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.NotificacionInfo;

/**
 * Service Interface for managing NotificacionInfo.
 */
public interface NotificacionInfoService {

    /**
     * Save a notificacionInfo.
     *
     * @param notificacionInfo the entity to save
     * @return the persisted entity
     */
    NotificacionInfo save(NotificacionInfo notificacionInfo);

    /**
     * Get all the notificacionInfos.
     *
     * @return the list of entities
     */
    List<NotificacionInfo> findAll();


    /**
     * Get the "id" notificacionInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NotificacionInfo> findOne(Long id);

    /**
     * Delete the "id" notificacionInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
