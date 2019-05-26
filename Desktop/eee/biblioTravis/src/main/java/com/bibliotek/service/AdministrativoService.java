package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Administrativo;

/**
 * Service Interface for managing Administrativo.
 */
public interface AdministrativoService {

    /**
     * Save a administrativo.
     *
     * @param administrativo the entity to save
     * @return the persisted entity
     */
    Administrativo save(Administrativo administrativo);

    /**
     * Get all the administrativos.
     *
     * @return the list of entities
     */
    List<Administrativo> findAll();


    /**
     * Get the "id" administrativo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Administrativo> findOne(Long id);

    /**
     * Delete the "id" administrativo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
