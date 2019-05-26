package com.bibliotek.web.rest;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.SalaEstudio;
import com.bibliotek.domain.enumeration.TipoZona;
import com.bibliotek.repository.SalaEstudioRepository;
import com.bibliotek.service.SalaEstudioService;
import com.bibliotek.web.rest.SalaEstudioResource;
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
import java.util.List;

import static com.bibliotek.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the SalaEstudioResource REST controller.
 *
 * @see SalaEstudioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class SalaEstudioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_VALORACION = "AAAAAAAAAA";
    private static final String UPDATED_VALORACION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLAZAS_TOTALES = 1;
    private static final Integer UPDATED_PLAZAS_TOTALES = 2;

    private static final TipoZona DEFAULT_ZONA = TipoZona.NERVION;
    private static final TipoZona UPDATED_ZONA = TipoZona.HELIOPOLIS;

    private static final Integer DEFAULT_NUM_ENCHUFES = 1;
    private static final Integer UPDATED_NUM_ENCHUFES = 2;

    private static final Boolean DEFAULT_HABLAR = false;
    private static final Boolean UPDATED_HABLAR = true;

    @Autowired
    private SalaEstudioRepository salaEstudioRepository;

    @Autowired
    private SalaEstudioService salaEstudioService;

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

    private MockMvc restSalaEstudioMockMvc;

    private SalaEstudio salaEstudio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaEstudioResource salaEstudioResource = new SalaEstudioResource(salaEstudioService);
        this.restSalaEstudioMockMvc = MockMvcBuilders.standaloneSetup(salaEstudioResource)
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
    public static SalaEstudio createEntity(EntityManager em) {
        SalaEstudio salaEstudio = new SalaEstudio()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .valoracion(DEFAULT_VALORACION)
            .plazasTotales(DEFAULT_PLAZAS_TOTALES)
            .zona(DEFAULT_ZONA)
            .numEnchufes(DEFAULT_NUM_ENCHUFES)
            .hablar(DEFAULT_HABLAR);
        return salaEstudio;
    }

    @Before
    public void initTest() {
        salaEstudio = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalaEstudio() throws Exception {
        int databaseSizeBeforeCreate = salaEstudioRepository.findAll().size();

        // Create the SalaEstudio
        restSalaEstudioMockMvc.perform(post("/api/sala-estudios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaEstudio)))
            .andExpect(status().isCreated());

        // Validate the SalaEstudio in the database
        List<SalaEstudio> salaEstudioList = salaEstudioRepository.findAll();
        assertThat(salaEstudioList).hasSize(databaseSizeBeforeCreate + 1);
        SalaEstudio testSalaEstudio = salaEstudioList.get(salaEstudioList.size() - 1);
        assertThat(testSalaEstudio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSalaEstudio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSalaEstudio.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testSalaEstudio.getPlazasTotales()).isEqualTo(DEFAULT_PLAZAS_TOTALES);
        assertThat(testSalaEstudio.getZona()).isEqualTo(DEFAULT_ZONA);
        assertThat(testSalaEstudio.getNumEnchufes()).isEqualTo(DEFAULT_NUM_ENCHUFES);
        assertThat(testSalaEstudio.isHablar()).isEqualTo(DEFAULT_HABLAR);
    }

    @Test
    @Transactional
    public void createSalaEstudioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaEstudioRepository.findAll().size();

        // Create the SalaEstudio with an existing ID
        salaEstudio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaEstudioMockMvc.perform(post("/api/sala-estudios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaEstudio)))
            .andExpect(status().isBadRequest());

        // Validate the SalaEstudio in the database
        List<SalaEstudio> salaEstudioList = salaEstudioRepository.findAll();
        assertThat(salaEstudioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalaEstudios() throws Exception {
        // Initialize the database
        salaEstudioRepository.saveAndFlush(salaEstudio);

        // Get all the salaEstudioList
        restSalaEstudioMockMvc.perform(get("/api/sala-estudios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaEstudio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION.toString())))
            .andExpect(jsonPath("$.[*].plazasTotales").value(hasItem(DEFAULT_PLAZAS_TOTALES)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA.toString())))
            .andExpect(jsonPath("$.[*].numEnchufes").value(hasItem(DEFAULT_NUM_ENCHUFES)))
            .andExpect(jsonPath("$.[*].hablar").value(hasItem(DEFAULT_HABLAR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSalaEstudio() throws Exception {
        // Initialize the database
        salaEstudioRepository.saveAndFlush(salaEstudio);

        // Get the salaEstudio
        restSalaEstudioMockMvc.perform(get("/api/sala-estudios/{id}", salaEstudio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salaEstudio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION.toString()))
            .andExpect(jsonPath("$.plazasTotales").value(DEFAULT_PLAZAS_TOTALES))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA.toString()))
            .andExpect(jsonPath("$.numEnchufes").value(DEFAULT_NUM_ENCHUFES))
            .andExpect(jsonPath("$.hablar").value(DEFAULT_HABLAR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalaEstudio() throws Exception {
        // Get the salaEstudio
        restSalaEstudioMockMvc.perform(get("/api/sala-estudios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalaEstudio() throws Exception {
        // Initialize the database
        salaEstudioService.save(salaEstudio);

        int databaseSizeBeforeUpdate = salaEstudioRepository.findAll().size();

        // Update the salaEstudio
        SalaEstudio updatedSalaEstudio = salaEstudioRepository.findById(salaEstudio.getId()).get();
        // Disconnect from session so that the updates on updatedSalaEstudio are not directly saved in db
        em.detach(updatedSalaEstudio);
        updatedSalaEstudio
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .valoracion(UPDATED_VALORACION)
            .plazasTotales(UPDATED_PLAZAS_TOTALES)
            .zona(UPDATED_ZONA)
            .numEnchufes(UPDATED_NUM_ENCHUFES)
            .hablar(UPDATED_HABLAR);

        restSalaEstudioMockMvc.perform(put("/api/sala-estudios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalaEstudio)))
            .andExpect(status().isOk());

        // Validate the SalaEstudio in the database
        List<SalaEstudio> salaEstudioList = salaEstudioRepository.findAll();
        assertThat(salaEstudioList).hasSize(databaseSizeBeforeUpdate);
        SalaEstudio testSalaEstudio = salaEstudioList.get(salaEstudioList.size() - 1);
        assertThat(testSalaEstudio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSalaEstudio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSalaEstudio.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testSalaEstudio.getPlazasTotales()).isEqualTo(UPDATED_PLAZAS_TOTALES);
        assertThat(testSalaEstudio.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testSalaEstudio.getNumEnchufes()).isEqualTo(UPDATED_NUM_ENCHUFES);
        assertThat(testSalaEstudio.isHablar()).isEqualTo(UPDATED_HABLAR);
    }

    @Test
    @Transactional
    public void updateNonExistingSalaEstudio() throws Exception {
        int databaseSizeBeforeUpdate = salaEstudioRepository.findAll().size();

        // Create the SalaEstudio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaEstudioMockMvc.perform(put("/api/sala-estudios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaEstudio)))
            .andExpect(status().isBadRequest());

        // Validate the SalaEstudio in the database
        List<SalaEstudio> salaEstudioList = salaEstudioRepository.findAll();
        assertThat(salaEstudioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalaEstudio() throws Exception {
        // Initialize the database
        salaEstudioService.save(salaEstudio);

        int databaseSizeBeforeDelete = salaEstudioRepository.findAll().size();

        // Delete the salaEstudio
        restSalaEstudioMockMvc.perform(delete("/api/sala-estudios/{id}", salaEstudio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalaEstudio> salaEstudioList = salaEstudioRepository.findAll();
        assertThat(salaEstudioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaEstudio.class);
        SalaEstudio salaEstudio1 = new SalaEstudio();
        salaEstudio1.setId(1L);
        SalaEstudio salaEstudio2 = new SalaEstudio();
        salaEstudio2.setId(salaEstudio1.getId());
        assertThat(salaEstudio1).isEqualTo(salaEstudio2);
        salaEstudio2.setId(2L);
        assertThat(salaEstudio1).isNotEqualTo(salaEstudio2);
        salaEstudio1.setId(null);
        assertThat(salaEstudio1).isNotEqualTo(salaEstudio2);
    }
}
