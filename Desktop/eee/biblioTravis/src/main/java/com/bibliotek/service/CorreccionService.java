package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Correccion;

/**
 * Service Interface for managing Correccion.
 */
public interface CorreccionService {

    /**
     * Save a correccion.
     *
     * @param correccion the entity to save
     * @return the persisted entity
     */
    Correccion save(Correccion correccion);

    /**
     * Get all the correccions.
     *
     * @return the list of entities
     */
    List<Correccion> findAll();


    /**
     * Get the "id" correccion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Correccion> findOne(Long id);

    /**
     * Delete the "id" correccion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
