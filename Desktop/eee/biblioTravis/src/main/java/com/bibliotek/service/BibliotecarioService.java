package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Bibliotecario;

/**
 * Service Interface for managing Bibliotecario.
 */
public interface BibliotecarioService {

    /**
     * Save a bibliotecario.
     *
     * @param bibliotecario the entity to save
     * @return the persisted entity
     */
    Bibliotecario save(Bibliotecario bibliotecario);

    /**
     * Get all the bibliotecarios.
     *
     * @return the list of entities
     */
    List<Bibliotecario> findAll();


    /**
     * Get the "id" bibliotecario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Bibliotecario> findOne(Long id);

    /**
     * Delete the "id" bibliotecario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
