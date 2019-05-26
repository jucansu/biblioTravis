package com.bibliotek.service.impl;

import com.bibliotek.domain.Reporte;
import com.bibliotek.repository.ReporteRepository;
import com.bibliotek.service.ReporteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Reporte.
 */
@Service
@Transactional
public class ReporteServiceImpl implements ReporteService {

    private final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * Save a reporte.
     *
     * @param reporte the entity to save
     * @return the persisted entity
     */
    @Override
    public Reporte save(Reporte reporte) {
        log.debug("Request to save Reporte : {}", reporte);
        return reporteRepository.save(reporte);
    }

    /**
     * Get all the reportes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reporte> findAll() {
        log.debug("Request to get all Reportes");
        return reporteRepository.findAll();
    }


    /**
     * Get one reporte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reporte> findOne(Long id) {
        log.debug("Request to get Reporte : {}", id);
        return reporteRepository.findById(id);
    }

    /**
     * Delete the reporte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reporte : {}", id);
        reporteRepository.deleteById(id);
    }
}
