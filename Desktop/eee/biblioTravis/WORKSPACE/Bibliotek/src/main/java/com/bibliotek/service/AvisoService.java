package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Aviso;

/**
 * Service Interface for managing Aviso.
 */
public interface AvisoService {

    /**
     * Save a aviso.
     *
     * @param aviso the entity to save
     * @return the persisted entity
     */
    Aviso save(Aviso aviso);

    /**
     * Get all the avisos.
     *
     * @return the list of entities
     */
    List<Aviso> findAll();


    /**
     * Get the "id" aviso.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Aviso> findOne(Long id);

    /**
     * Delete the "id" aviso.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
