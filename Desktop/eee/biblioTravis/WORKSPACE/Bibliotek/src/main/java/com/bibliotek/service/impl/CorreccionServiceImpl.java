package com.bibliotek.service.impl;

import com.bibliotek.domain.Correccion;
import com.bibliotek.repository.CorreccionRepository;
import com.bibliotek.service.CorreccionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Correccion.
 */
@Service
@Transactional
public class CorreccionServiceImpl implements CorreccionService {

    private final Logger log = LoggerFactory.getLogger(CorreccionServiceImpl.class);

    private final CorreccionRepository correccionRepository;

    public CorreccionServiceImpl(CorreccionRepository correccionRepository) {
        this.correccionRepository = correccionRepository;
    }

    /**
     * Save a correccion.
     *
     * @param correccion the entity to save
     * @return the persisted entity
     */
    @Override
    public Correccion save(Correccion correccion) {
        log.debug("Request to save Correccion : {}", correccion);
        return correccionRepository.save(correccion);
    }

    /**
     * Get all the correccions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Correccion> findAll() {
        log.debug("Request to get all Correccions");
        return correccionRepository.findAll();
    }


    /**
     * Get one correccion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Correccion> findOne(Long id) {
        log.debug("Request to get Correccion : {}", id);
        return correccionRepository.findById(id);
    }

    /**
     * Delete the correccion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Correccion : {}", id);
        correccionRepository.deleteById(id);
    }
}
