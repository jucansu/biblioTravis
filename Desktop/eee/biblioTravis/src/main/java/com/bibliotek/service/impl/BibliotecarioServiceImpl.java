package com.bibliotek.service.impl;

import com.bibliotek.domain.Bibliotecario;
import com.bibliotek.repository.BibliotecarioRepository;
import com.bibliotek.service.BibliotecarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Bibliotecario.
 */
@Service
@Transactional
public class BibliotecarioServiceImpl implements BibliotecarioService {

    private final Logger log = LoggerFactory.getLogger(BibliotecarioServiceImpl.class);

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioServiceImpl(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    /**
     * Save a bibliotecario.
     *
     * @param bibliotecario the entity to save
     * @return the persisted entity
     */
    @Override
    public Bibliotecario save(Bibliotecario bibliotecario) {
        log.debug("Request to save Bibliotecario : {}", bibliotecario);
        return bibliotecarioRepository.save(bibliotecario);
    }

    /**
     * Get all the bibliotecarios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Bibliotecario> findAll() {
        log.debug("Request to get all Bibliotecarios");
        return bibliotecarioRepository.findAll();
    }


    /**
     * Get one bibliotecario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bibliotecario> findOne(Long id) {
        log.debug("Request to get Bibliotecario : {}", id);
        return bibliotecarioRepository.findById(id);
    }

    /**
     * Delete the bibliotecario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bibliotecario : {}", id);
        bibliotecarioRepository.deleteById(id);
    }
}
