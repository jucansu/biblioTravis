package com.bibliotek.service.impl;

import com.bibliotek.domain.Administrativo;
import com.bibliotek.repository.AdministrativoRepository;
import com.bibliotek.service.AdministrativoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Administrativo.
 */
@Service
@Transactional
public class AdministrativoServiceImpl implements AdministrativoService {

    private final Logger log = LoggerFactory.getLogger(AdministrativoServiceImpl.class);

    private final AdministrativoRepository administrativoRepository;

    public AdministrativoServiceImpl(AdministrativoRepository administrativoRepository) {
        this.administrativoRepository = administrativoRepository;
    }

    /**
     * Save a administrativo.
     *
     * @param administrativo the entity to save
     * @return the persisted entity
     */
    @Override
    public Administrativo save(Administrativo administrativo) {
        log.debug("Request to save Administrativo : {}", administrativo);
        return administrativoRepository.save(administrativo);
    }

    /**
     * Get all the administrativos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Administrativo> findAll() {
        log.debug("Request to get all Administrativos");
        return administrativoRepository.findAll();
    }


    /**
     * Get one administrativo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Administrativo> findOne(Long id) {
        log.debug("Request to get Administrativo : {}", id);
        return administrativoRepository.findById(id);
    }

    /**
     * Delete the administrativo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Administrativo : {}", id);
        administrativoRepository.deleteById(id);
    }
}
