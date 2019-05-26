package com.bibliotek.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bibliotek.domain.SalaEstudio;

import java.util.Optional;

/**
 * Service Interface for managing SalaEstudio.
 */
public interface SalaEstudioService {

    /**
     * Save a salaEstudio.
     *
     * @param salaEstudio the entity to save
     * @return the persisted entity
     */
    SalaEstudio save(SalaEstudio salaEstudio);

    /**
     * Get all the salaEstudios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SalaEstudio> findAll(Pageable pageable);


    /**
     * Get the "id" salaEstudio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SalaEstudio> findOne(Long id);

    /**
     * Delete the "id" salaEstudio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
