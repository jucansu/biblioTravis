package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Reporte;

/**
 * Service Interface for managing Reporte.
 */
public interface ReporteService {

    /**
     * Save a reporte.
     *
     * @param reporte the entity to save
     * @return the persisted entity
     */
    Reporte save(Reporte reporte);

    /**
     * Get all the reportes.
     *
     * @return the list of entities
     */
    List<Reporte> findAll();


    /**
     * Get the "id" reporte.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Reporte> findOne(Long id);

    /**
     * Delete the "id" reporte.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
