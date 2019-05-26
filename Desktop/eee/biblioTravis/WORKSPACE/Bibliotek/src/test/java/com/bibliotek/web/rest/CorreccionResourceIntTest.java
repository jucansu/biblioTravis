package com.bibliotek.web.rest;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.Correccion;
import com.bibliotek.repository.CorreccionRepository;
import com.bibliotek.service.CorreccionService;
import com.bibliotek.web.rest.CorreccionResource;
import com.bibliotek.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.bibliotek.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorreccionResource REST controller.
 *
 * @see CorreccionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class CorreccionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CorreccionRepository correccionRepository;

    @Autowired
    private CorreccionService correccionService;

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

    private MockMvc restCorreccionMockMvc;

    private Correccion correccion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorreccionResource correccionResource = new CorreccionResource(correccionService);
        this.restCorreccionMockMvc = MockMvcBuilders.standaloneSetup(correccionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Correccion createEntity(EntityManager em) {
        Correccion correccion = new Correccion()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA);
        return correccion;
    }

    @Before
    public void initTest() {
        correccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorreccion() throws Exception {
        int databaseSizeBeforeCreate = correccionRepository.findAll().size();

        // Create the Correccion
        restCorreccionMockMvc.perform(post("/api/correccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correccion)))
            .andExpect(status().isCreated());

        // Validate the Correccion in the database
        List<Correccion> correccionList = correccionRepository.findAll();
        assertThat(correccionList).hasSize(databaseSizeBeforeCreate + 1);
        Correccion testCorreccion = correccionList.get(correccionList.size() - 1);
        assertThat(testCorreccion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCorreccion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCorreccion.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCorreccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correccionRepository.findAll().size();

        // Create the Correccion with an existing ID
        correccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorreccionMockMvc.perform(post("/api/correccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correccion)))
            .andExpect(status().isBadRequest());

        // Validate the Correccion in the database
        List<Correccion> correccionList = correccionRepository.findAll();
        assertThat(correccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorreccions() throws Exception {
        // Initialize the database
        correccionRepository.saveAndFlush(correccion);

        // Get all the correccionList
        restCorreccionMockMvc.perform(get("/api/correccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCorreccion() throws Exception {
        // Initialize the database
        correccionRepository.saveAndFlush(correccion);

        // Get the correccion
        restCorreccionMockMvc.perform(get("/api/correccions/{id}", correccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correccion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorreccion() throws Exception {
        // Get the correccion
        restCorreccionMockMvc.perform(get("/api/correccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorreccion() throws Exception {
        // Initialize the database
        correccionService.save(correccion);

        int databaseSizeBeforeUpdate = correccionRepository.findAll().size();

        // Update the correccion
        Correccion updatedCorreccion = correccionRepository.findById(correccion.getId()).get();
        // Disconnect from session so that the updates on updatedCorreccion are not directly saved in db
        em.detach(updatedCorreccion);
        updatedCorreccion
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA);

        restCorreccionMockMvc.perform(put("/api/correccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorreccion)))
            .andExpect(status().isOk());

        // Validate the Correccion in the database
        List<Correccion> correccionList = correccionRepository.findAll();
        assertThat(correccionList).hasSize(databaseSizeBeforeUpdate);
        Correccion testCorreccion = correccionList.get(correccionList.size() - 1);
        assertThat(testCorreccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCorreccion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCorreccion.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCorreccion() throws Exception {
        int databaseSizeBeforeUpdate = correccionRepository.findAll().size();

        // Create the Correccion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorreccionMockMvc.perform(put("/api/correccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correccion)))
            .andExpect(status().isBadRequest());

        // Validate the Correccion in the database
        List<Correccion> correccionList = correccionRepository.findAll();
        assertThat(correccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorreccion() throws Exception {
        // Initialize the database
        correccionService.save(correccion);

        int databaseSizeBeforeDelete = correccionRepository.findAll().size();

        // Delete the correccion
        restCorreccionMockMvc.perform(delete("/api/correccions/{id}", correccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Correccion> correccionList = correccionRepository.findAll();
        assertThat(correccionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Correccion.class);
        Correccion correccion1 = new Correccion();
        correccion1.setId(1L);
        Correccion correccion2 = new Correccion();
        correccion2.setId(correccion1.getId());
        assertThat(correccion1).isEqualTo(correccion2);
        correccion2.setId(2L);
        assertThat(correccion1).isNotEqualTo(correccion2);
        correccion1.setId(null);
        assertThat(correccion1).isNotEqualTo(correccion2);
    }
}
