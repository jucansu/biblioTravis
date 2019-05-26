package com.bibliotek.service.impl;

import com.bibliotek.domain.SalaEstudio;
import com.bibliotek.repository.SalaEstudioRepository;
import com.bibliotek.service.SalaEstudioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SalaEstudio.
 */
@Service
@Transactional
public class SalaEstudioServiceImpl implements SalaEstudioService {

    private final Logger log = LoggerFactory.getLogger(SalaEstudioServiceImpl.class);

    private final SalaEstudioRepository salaEstudioRepository;

    public SalaEstudioServiceImpl(SalaEstudioRepository salaEstudioRepository) {
        this.salaEstudioRepository = salaEstudioRepository;
    }

    /**
     * Save a salaEstudio.
     *
     * @param salaEstudio the entity to save
     * @return the persisted entity
     */
    @Override
    public SalaEstudio save(SalaEstudio salaEstudio) {
        log.debug("Request to save SalaEstudio : {}", salaEstudio);
        return salaEstudioRepository.save(salaEstudio);
    }

    /**
     * Get all the salaEstudios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalaEstudio> findAll(Pageable pageable) {
        log.debug("Request to get all SalaEstudios");
        return salaEstudioRepository.findAll(pageable);
    }


    /**
     * Get one salaEstudio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalaEstudio> findOne(Long id) {
        log.debug("Request to get SalaEstudio : {}", id);
        return salaEstudioRepository.findById(id);
    }

    /**
     * Delete the salaEstudio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalaEstudio : {}", id);
        salaEstudioRepository.deleteById(id);
    }
}
