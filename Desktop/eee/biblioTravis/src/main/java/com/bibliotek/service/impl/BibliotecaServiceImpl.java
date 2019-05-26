package com.bibliotek.service.impl;

import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.Estudiante;
import com.bibliotek.repository.BibliotecaRepository;
import com.bibliotek.service.BibliotecaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Biblioteca.
 */
@Service
@Transactional
public class BibliotecaServiceImpl implements BibliotecaService {

	private final Logger log = LoggerFactory.getLogger(BibliotecaServiceImpl.class);

	private final BibliotecaRepository bibliotecaRepository;

	public BibliotecaServiceImpl(BibliotecaRepository bibliotecaRepository) {
		this.bibliotecaRepository = bibliotecaRepository;
	}

	/**
	 * Save a biblioteca.
	 *
	 * @param biblioteca the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Biblioteca save(Biblioteca biblioteca) {
		log.debug("Request to save Biblioteca : {}", biblioteca);
		return bibliotecaRepository.save(biblioteca);
	}

	/**
	 * Get all the bibliotecas.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Biblioteca> findAll(Pageable pageable) {
		log.debug("Request to get all Bibliotecas");
		return bibliotecaRepository.findAll(pageable);
	}

	/**
	 * Get one biblioteca by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Biblioteca> findOne(Long id) {
		log.debug("Request to get Biblioteca : {}", id);
		return bibliotecaRepository.findById(id);
	}

	/**
	 * Delete the biblioteca by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Biblioteca : {}", id);
		bibliotecaRepository.deleteById(id);
	}

	@Override
	public List<Biblioteca> findAll() {
		List<Biblioteca> bibliotecas = bibliotecaRepository.findAll();
		return bibliotecas;
	}

	@Override
	public void updateBibliotecaSuma(Double valoracion, Long id) {
		bibliotecaRepository.updateBibliotecaSuma(valoracion, id);
	}

}
