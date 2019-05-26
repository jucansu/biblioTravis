package com.bibliotek.web.rest;

import static com.bibliotek.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.enumeration.TipoZona;
import com.bibliotek.repository.BibliotecaRepository;
import com.bibliotek.service.BibliotecaService;
import com.bibliotek.service.impl.BibliotecaServiceImpl;
import com.bibliotek.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the BibliotecaResource REST controller.
 *
 * @see BibliotecaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class BibliotecaResourceIntTest {

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

	private static final Double DEFAULT_VALORACION = 1.00;
	private static final Double UPDATED_VALORACION = 2.00;

	private static final Integer DEFAULT_PLAZAS_TOTALES = 1;
	private static final Integer UPDATED_PLAZAS_TOTALES = 2;

	private static final Integer DEFAULT_PLAZAS_DISPONIBLES = 1;
	private static final Integer UPDATED_PLAZAS_DISPONIBLES = 2;

	private static final TipoZona DEFAULT_ZONA = TipoZona.NERVION;
	private static final TipoZona UPDATED_ZONA = TipoZona.HELIOPOLIS;

	private static final Integer DEFAULT_NUM_ENCHUFES = 1;
	private static final Integer UPDATED_NUM_ENCHUFES = 2;

	private static final Integer DEFAULT_SALAS = 1;
	private static final Integer UPDATED_SALAS = 2;

	@Autowired
	private BibliotecaRepository bibliotecaRepository;

	@Autowired
	private BibliotecaService bibliotecaService;

	@Autowired
	private BibliotecaServiceImpl bibliotecaServiceImpl;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	@Autowired
	private Validator validator;

	private MockMvc restBibliotecaMockMvc;

	private Biblioteca biblioteca;
	
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final BibliotecaResource bibliotecaResource = new BibliotecaResource(bibliotecaService, bibliotecaServiceImpl, null, null);
		this.restBibliotecaMockMvc = MockMvcBuilders.standaloneSetup(bibliotecaResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.setValidator(validator).build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Biblioteca createEntity(EntityManager em) {
		Biblioteca biblioteca = new Biblioteca().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION)
				.valoracion(DEFAULT_VALORACION).plazasTotales(DEFAULT_PLAZAS_TOTALES)
				.plazasOcupadas(DEFAULT_PLAZAS_DISPONIBLES).zona(DEFAULT_ZONA).numEnchufes(DEFAULT_NUM_ENCHUFES)
				.salas(DEFAULT_SALAS);
		return biblioteca;
	}

	@Before
	public void initTest() {
		biblioteca = createEntity(em);
	}

	@Test
	@Transactional
	public void createBiblioteca() throws Exception {
		int databaseSizeBeforeCreate = bibliotecaRepository.findAll().size();

		// Create the Biblioteca
		restBibliotecaMockMvc.perform(post("/api/bibliotecas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(biblioteca))).andExpect(status().isCreated());

		// Validate the Biblioteca in the database
		List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
		assertThat(bibliotecaList).hasSize(databaseSizeBeforeCreate + 1);
		Biblioteca testBiblioteca = bibliotecaList.get(bibliotecaList.size() - 1);
		assertThat(testBiblioteca.getNombre()).isEqualTo(DEFAULT_NOMBRE);
		assertThat(testBiblioteca.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
		assertThat(testBiblioteca.getValoracion()).isEqualTo(DEFAULT_VALORACION);
		assertThat(testBiblioteca.getPlazasTotales()).isEqualTo(DEFAULT_PLAZAS_TOTALES);
		assertThat(testBiblioteca.getPlazasOcupadas()).isEqualTo(DEFAULT_PLAZAS_DISPONIBLES);
		assertThat(testBiblioteca.getZona()).isEqualTo(DEFAULT_ZONA);
		assertThat(testBiblioteca.getNumEnchufes()).isEqualTo(DEFAULT_NUM_ENCHUFES);
		assertThat(testBiblioteca.getSalas()).isEqualTo(DEFAULT_SALAS);
	}

	@Test
	@Transactional
	public void createBibliotecaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = bibliotecaRepository.findAll().size();

		// Create the Biblioteca with an existing ID
		biblioteca.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restBibliotecaMockMvc.perform(post("/api/bibliotecas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(biblioteca))).andExpect(status().isBadRequest());

		// Validate the Biblioteca in the database
		List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
		assertThat(bibliotecaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllBibliotecas() throws Exception {
		// Initialize the database
		bibliotecaRepository.saveAndFlush(biblioteca);

		// Get all the bibliotecaList
		restBibliotecaMockMvc.perform(get("/api/bibliotecas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(biblioteca.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
				.andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
				.andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION.toString())))
				.andExpect(jsonPath("$.[*].plazasTotales").value(hasItem(DEFAULT_PLAZAS_TOTALES)))
				.andExpect(jsonPath("$.[*].plazasDisponibles").value(hasItem(DEFAULT_PLAZAS_DISPONIBLES)))
				.andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA.toString())))
				.andExpect(jsonPath("$.[*].numEnchufes").value(hasItem(DEFAULT_NUM_ENCHUFES)))
				.andExpect(jsonPath("$.[*].salas").value(hasItem(DEFAULT_SALAS)));
	}

	@Test
	@Transactional
	public void getBiblioteca() throws Exception {
		// Initialize the database
		bibliotecaRepository.saveAndFlush(biblioteca);

		// Get the biblioteca
		restBibliotecaMockMvc.perform(get("/api/bibliotecas/{id}", biblioteca.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(biblioteca.getId().intValue()))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
				.andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
				.andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION.toString()))
				.andExpect(jsonPath("$.plazasTotales").value(DEFAULT_PLAZAS_TOTALES))
				.andExpect(jsonPath("$.plazasDisponibles").value(DEFAULT_PLAZAS_DISPONIBLES))
				.andExpect(jsonPath("$.zona").value(DEFAULT_ZONA.toString()))
				.andExpect(jsonPath("$.numEnchufes").value(DEFAULT_NUM_ENCHUFES))
				.andExpect(jsonPath("$.salas").value(DEFAULT_SALAS));
	}

	@Test
	@Transactional
	public void getNonExistingBiblioteca() throws Exception {
		// Get the biblioteca
		restBibliotecaMockMvc.perform(get("/api/bibliotecas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateBiblioteca() throws Exception {
		// Initialize the database
		bibliotecaService.save(biblioteca);

		int databaseSizeBeforeUpdate = bibliotecaRepository.findAll().size();

		// Update the biblioteca
		Biblioteca updatedBiblioteca = bibliotecaRepository.findById(biblioteca.getId()).get();
		// Disconnect from session so that the updates on updatedBiblioteca are not
		// directly saved in db
		em.detach(updatedBiblioteca);
		updatedBiblioteca.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).valoracion(UPDATED_VALORACION)
				.plazasTotales(UPDATED_PLAZAS_TOTALES).plazasOcupadas(UPDATED_PLAZAS_DISPONIBLES).zona(UPDATED_ZONA)
				.numEnchufes(UPDATED_NUM_ENCHUFES).salas(UPDATED_SALAS);

		restBibliotecaMockMvc.perform(put("/api/bibliotecas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updatedBiblioteca))).andExpect(status().isOk());

		// Validate the Biblioteca in the database
		List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
		assertThat(bibliotecaList).hasSize(databaseSizeBeforeUpdate);
		Biblioteca testBiblioteca = bibliotecaList.get(bibliotecaList.size() - 1);
		assertThat(testBiblioteca.getNombre()).isEqualTo(UPDATED_NOMBRE);
		assertThat(testBiblioteca.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
		assertThat(testBiblioteca.getValoracion()).isEqualTo(UPDATED_VALORACION);
		assertThat(testBiblioteca.getPlazasTotales()).isEqualTo(UPDATED_PLAZAS_TOTALES);
		assertThat(testBiblioteca.getPlazasOcupadas()).isEqualTo(UPDATED_PLAZAS_DISPONIBLES);
		assertThat(testBiblioteca.getZona()).isEqualTo(UPDATED_ZONA);
		assertThat(testBiblioteca.getNumEnchufes()).isEqualTo(UPDATED_NUM_ENCHUFES);
		assertThat(testBiblioteca.getSalas()).isEqualTo(UPDATED_SALAS);
	}

	@Test
	@Transactional
	public void updateNonExistingBiblioteca() throws Exception {
		int databaseSizeBeforeUpdate = bibliotecaRepository.findAll().size();

		// Create the Biblioteca

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restBibliotecaMockMvc.perform(put("/api/bibliotecas").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(biblioteca))).andExpect(status().isBadRequest());

		// Validate the Biblioteca in the database
		List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
		assertThat(bibliotecaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteBiblioteca() throws Exception {
		// Initialize the database
		bibliotecaService.save(biblioteca);

		int databaseSizeBeforeDelete = bibliotecaRepository.findAll().size();

		// Delete the biblioteca
		restBibliotecaMockMvc
				.perform(delete("/api/bibliotecas/{id}", biblioteca.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Biblioteca> bibliotecaList = bibliotecaRepository.findAll();
		assertThat(bibliotecaList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Biblioteca.class);
		Biblioteca biblioteca1 = new Biblioteca();
		biblioteca1.setId(1L);
		Biblioteca biblioteca2 = new Biblioteca();
		biblioteca2.setId(biblioteca1.getId());
		assertThat(biblioteca1).isEqualTo(biblioteca2);
		biblioteca2.setId(2L);
		assertThat(biblioteca1).isNotEqualTo(biblioteca2);
		biblioteca1.setId(null);
		assertThat(biblioteca1).isNotEqualTo(biblioteca2);
	}
}
