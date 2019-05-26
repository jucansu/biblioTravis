package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotek.domain.Biblioteca;

/**
 * Service Interface for managing Biblioteca.
 */
public interface BibliotecaService {

    /**
     * Save a biblioteca.
     *
     * @param biblioteca the entity to save
     * @return the persisted entity
     */
    Biblioteca save(Biblioteca biblioteca);

    /**
     * Get all the bibliotecas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Biblioteca> findAll(Pageable pageable);
    
    List<Biblioteca> findAll();


    /**
     * Get the "id" biblioteca.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Biblioteca> findOne(Long id);

    /**
     * Delete the "id" biblioteca.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
 
    @Transactional
	@Modifying
	@Query("update Biblioteca b set b.valoracion = ?1 where b.id = ?2")
	void updateBibliotecaSuma(Double valoracion,Long id);
	

	
}
