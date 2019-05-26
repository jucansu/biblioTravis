package com.bibliotek.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotek.domain.Estudiante;
import com.bibliotek.domain.User;
import com.bibliotek.repository.UserRepository;
import com.bibliotek.service.EstudianteService;
import com.bibliotek.service.UserService;
import com.bibliotek.service.impl.EstudianteServiceImpl;
import com.bibliotek.web.rest.errors.BadRequestAlertException;
import com.bibliotek.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Estudiante.
 */
@RestController
@RequestMapping("/api")
public class EstudianteResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteResource.class);

    private static final String ENTITY_NAME = "estudiante";

    private final EstudianteService estudianteService;
    @Autowired
    private final EstudianteServiceImpl estudianteServiceImpl;
    
    private final UserRepository userRepository;

    public EstudianteResource(EstudianteService estudianteService,EstudianteServiceImpl estudianteServiceImpl, UserRepository userRepository) {
        this.estudianteService = estudianteService;
        this.estudianteServiceImpl=estudianteServiceImpl;
        this.userRepository=userRepository;
    }

    /**
     * POST  /estudiantes : Create a new estudiante.
     *
     * @param estudiante the estudiante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estudiante, or with status 400 (Bad Request) if the estudiante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estudiantes")
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
        log.debug("REST request to save Estudiante : {}", estudiante);
        if (estudiante.getId() != null) {
            throw new BadRequestAlertException("A new estudiante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estudiante result = estudianteService.save(estudiante);
        return ResponseEntity.created(new URI("/api/estudiantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estudiantes : Updates an existing estudiante.
     *
     * @param estudiante the estudiante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estudiante,
     * or with status 400 (Bad Request) if the estudiante is not valid,
     * or with status 500 (Internal Server Error) if the estudiante couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estudiantes")
    public ResponseEntity<Estudiante> updateEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
        log.debug("REST request to update Estudiante : {}", estudiante);
        if (estudiante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Estudiante result = estudianteService.save(estudiante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estudiante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estudiantes : get all the estudiantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estudiantes in body
     */
    @GetMapping("/estudiantes")
    public List<Estudiante> getAllEstudiantes() {
        log.debug("REST request to get all Estudiantes");
        return estudianteService.findAll();
    }

    /**
     * GET  /estudiantes/:id : get the "id" estudiante.
     *
     * @param id the id of the estudiante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estudiante, or with status 404 (Not Found)
     */
    @GetMapping("/estudiantes/{id}")
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable Long id) {
        log.debug("REST request to get Estudiante : {}", id);
        Optional<Estudiante> estudiante = estudianteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estudiante);
    }
    
    @GetMapping("/estudiante/logueado/{id}")
    public ResponseEntity<Estudiante> getEstudianteLogueado(@PathVariable Long id) {
        log.debug("REST request to get Estudiante : {}", id);
        User user=this.userRepository.findOneById(id).get();
        Optional<Estudiante> estudiante = this.estudianteServiceImpl.findByUserId(user);
        return ResponseUtil.wrapOrNotFound(estudiante);
    }

    /**
     * DELETE  /estudiantes/:id : delete the "id" estudiante.
     *
     * @param id the id of the estudiante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estudiantes/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        log.debug("REST request to delete Estudiante : {}", id);
        estudianteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/estudiante/pausa/{id}")
    public void getPausaEstudiante(@PathVariable Long id) {
    	Boolean pausa = this.estudianteServiceImpl.findOne(id).get().getPausa();
    	if(pausa) {
		this.estudianteServiceImpl.updatePausa(false, id);
    	}else {
    		this.estudianteServiceImpl.updatePausa(true, id);

    		
    	}
    }
}
