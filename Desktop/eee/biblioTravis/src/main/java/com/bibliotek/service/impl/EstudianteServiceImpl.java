package com.bibliotek.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotek.domain.Estudiante;
import com.bibliotek.domain.User;
import com.bibliotek.repository.EstudianteRepository;
import com.bibliotek.service.EstudianteService;

/**
 * Service Implementation for managing Estudiante.
 */
@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Save a estudiante.
     *
     * @param estudiante the entity to save
     * @return the persisted entity
     */
    @Override
    public Estudiante save(Estudiante estudiante) {
        log.debug("Request to save Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    /**
     * Get all the estudiantes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        log.debug("Request to get all Estudiantes");
        return estudianteRepository.findAll();
    }


    /**
     * Get one estudiante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> findOne(Long id) {
        log.debug("Request to get Estudiante : {}", id);
        return estudianteRepository.findById(id);
    }

    /**
     * Delete the estudiante by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estudiante : {}", id);
        estudianteRepository.deleteById(id);
    }
    
    public void updateEstudiante(String qr,Long id) {
    	Estudiante estudiante= this.estudianteRepository.findById(id).get();
			estudiante.setCodigoQR(qr);
		
	}

	public Optional<Estudiante> findByUserId(User user) {
		   log.debug("Request to get Estudiante : {}", user);
	        return estudianteRepository.findByUser(user);
	}
	@Override
	public void updatePausa(Boolean pausa, Long id) {
		estudianteRepository.updatePausa(pausa, id);
	}
	
    
}
