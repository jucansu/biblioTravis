package com.bibliotek.service.impl;

import com.bibliotek.domain.Aviso;
import com.bibliotek.repository.AvisoRepository;
import com.bibliotek.service.AvisoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Aviso.
 */
@Service
@Transactional
public class AvisoServiceImpl implements AvisoService {

    private final Logger log = LoggerFactory.getLogger(AvisoServiceImpl.class);

    private final AvisoRepository avisoRepository;

    public AvisoServiceImpl(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    /**
     * Save a aviso.
     *
     * @param aviso the entity to save
     * @return the persisted entity
     */
    @Override
    public Aviso save(Aviso aviso) {
        log.debug("Request to save Aviso : {}", aviso);
        return avisoRepository.save(aviso);
    }

    /**
     * Get all the avisos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Aviso> findAll() {
        log.debug("Request to get all Avisos");
        return avisoRepository.findAll();
    }


    /**
     * Get one aviso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Aviso> findOne(Long id) {
        log.debug("Request to get Aviso : {}", id);
        return avisoRepository.findById(id);
    }

    /**
     * Delete the aviso by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aviso : {}", id);
        avisoRepository.deleteById(id);
    }
}
