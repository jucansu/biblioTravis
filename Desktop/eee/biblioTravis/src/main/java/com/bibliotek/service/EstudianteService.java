package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotek.domain.Estudiante;

/**
 * Service Interface for managing Estudiante.
 */
public interface EstudianteService {

    /**
     * Save a estudiante.
     *
     * @param estudiante the entity to save
     * @return the persisted entity
     */
    Estudiante save(Estudiante estudiante);

    /**
     * Get all the estudiantes.
     *
     * @return the list of entities
     */
    List<Estudiante> findAll();


    /**
     * Get the "id" estudiante.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Estudiante> findOne(Long id);
    
   

    /**
     * Delete the "id" estudiante.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    @Transactional
	@Modifying
	@Query("update Estudiante b set b.pausa = ?1 where b.id = ?2")
	void updatePausa(Boolean pausa, Long id);
    
}
