package com.bibliotek.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.Estudiante;
import com.bibliotek.domain.User;
import com.bibliotek.repository.UserRepository;
import com.bibliotek.service.BibliotecaService;
import com.bibliotek.service.dto.BibliotecaDAO;
import com.bibliotek.service.impl.BibliotecaServiceImpl;
import com.bibliotek.service.impl.EstudianteServiceImpl;
import com.bibliotek.web.rest.errors.BadRequestAlertException;
import com.bibliotek.web.rest.util.HeaderUtil;
import com.bibliotek.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Biblioteca.
 */
@RestController
@RequestMapping("/api")
public class BibliotecaResource {

	private final Logger log = LoggerFactory.getLogger(BibliotecaResource.class);

	private static final String ENTITY_NAME = "biblioteca";
	@Autowired
	private final BibliotecaService bibliotecaService;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final EstudianteServiceImpl estudianteServiceImpl;

	@Autowired
	private final BibliotecaServiceImpl bibliotecaServiceImpl;

	private BibliotecaDAO bibliotecaDAO;

	public BibliotecaResource(BibliotecaService bibliotecaService, BibliotecaServiceImpl bibliotecaServiceImpl,
			UserRepository userRepository, EstudianteServiceImpl estudianteServiceImpl) {
		this.bibliotecaService = bibliotecaService;
		this.bibliotecaServiceImpl = bibliotecaServiceImpl;
		this.estudianteServiceImpl = estudianteServiceImpl;
		this.userRepository = userRepository;
	}

	/**
	 * POST /bibliotecas : Create a new biblioteca.
	 *
	 * @param biblioteca the biblioteca to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         biblioteca, or with status 400 (Bad Request) if the biblioteca has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/bibliotecas")
	public ResponseEntity<Biblioteca> createBiblioteca(@RequestBody Biblioteca biblioteca) throws URISyntaxException {
		log.debug("REST request to save Biblioteca : {}", biblioteca);
		if (biblioteca.getId() != null) {
			throw new BadRequestAlertException("A new biblioteca cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Biblioteca result = bibliotecaService.save(biblioteca);
		return ResponseEntity.created(new URI("/api/bibliotecas/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /bibliotecas : Updates an existing biblioteca.
	 *
	 * @param biblioteca the biblioteca to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         biblioteca, or with status 400 (Bad Request) if the biblioteca is not
	 *         valid, or with status 500 (Internal Server Error) if the biblioteca
	 *         couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/bibliotecas")
	public ResponseEntity<Biblioteca> updateBiblioteca(@RequestBody Biblioteca biblioteca) throws URISyntaxException {
		log.debug("REST request to update Biblioteca : {}", biblioteca);
		if (biblioteca.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Biblioteca result = bibliotecaService.save(biblioteca);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, biblioteca.getId().toString())).body(result);
	}

	/**
	 * GET /bibliotecas : get all the bibliotecas.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of bibliotecas
	 *         in body
	 */
	@GetMapping("/bibliotecas")
	public ResponseEntity<List<Biblioteca>> getAllBibliotecas(Pageable pageable) {
		log.debug("REST request to get a page of Bibliotecas");
		Page<Biblioteca> page = bibliotecaService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bibliotecas");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/bibliotecas/all")
//  @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<Biblioteca> getBiblioteca() {
		List<Biblioteca> bibliotecas = bibliotecaServiceImpl.findAll();
		return bibliotecas;
	}

	@GetMapping("/bibliotecas/all/order")
	public List<Biblioteca> findAllOrder() {
		List<Biblioteca> bibliotecas = bibliotecaServiceImpl.findAll();

		Collections.sort(bibliotecas, new Comparator<Biblioteca>() {

			@Override
			public int compare(Biblioteca o1, Biblioteca o2) {
				return o2.getValoracion().compareTo(o1.getValoracion());
			}
		});

		return bibliotecas;
	}

	@GetMapping("/bibliotecas/mias")
//  @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<Biblioteca> getMisBibliotecas() {
		return bibliotecaServiceImpl.findAll();
	}

	/**
	 * GET /bibliotecas/:id : get the "id" biblioteca.
	 *
	 * @param id the id of the biblioteca to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the biblioteca,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/bibliotecas/{id}")
	public ResponseEntity<Biblioteca> getBiblioteca(@PathVariable Long id) {
		log.debug("REST request to get Biblioteca : {}", id);
		Optional<Biblioteca> biblioteca = bibliotecaService.findOne(id);
		return ResponseUtil.wrapOrNotFound(biblioteca);
	}

	/**
	 * DELETE /bibliotecas/:id : delete the "id" biblioteca.
	 *
	 * @param id the id of the biblioteca to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/bibliotecas/{id}")
	public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
		log.debug("REST request to delete Biblioteca : {}", id);
		bibliotecaService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@GetMapping("/bibliotecas/mias/{id}")
	public List<Biblioteca> getBibliotecasMias(@PathVariable Long id) {
		User user = this.userRepository.findOneById(id).get();
		Estudiante estudiante = this.estudianteServiceImpl.findByUserId(user).get();
		List<Biblioteca> misBibliotecas = new ArrayList<Biblioteca>(estudiante.getBibliotecas());
		return misBibliotecas;
	}

	@GetMapping("/bibliotecas/positivo/{id}")
	public void getBibliotecasPositivo(@PathVariable Long id) {
		Double valoracionActual = this.bibliotecaService.findOne(id).get().getValoracion();
		Double numeroVotos = 50.0;// this.bibliotecaService.findOne(id).get().getNumVotos();
		Double valoracionMedia = (double) ((numeroVotos + 1) * ((double) (valoracionActual / numeroVotos)));
		this.bibliotecaServiceImpl.updateBibliotecaSuma(valoracionMedia, id);

	}

	@GetMapping("/bibliotecas/negativo/{id}")
	public void getBibliotecasNegativo(@PathVariable Long id) {
		Double valoracionActual = this.bibliotecaService.findOne(id).get().getValoracion();
		Double numeroVotos = 50.0;// this.bibliotecaService.findOne(id).get().getNumVotos();
		Double valoracionMedia = (double) ((numeroVotos - 1) * ((double) (valoracionActual / numeroVotos)));
		this.bibliotecaServiceImpl.updateBibliotecaSuma(valoracionMedia, id);

	}
}
