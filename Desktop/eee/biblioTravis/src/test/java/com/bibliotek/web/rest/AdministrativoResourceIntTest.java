package com.bibliotek.web.rest;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.Administrativo;
import com.bibliotek.repository.AdministrativoRepository;
import com.bibliotek.service.AdministrativoService;
import com.bibliotek.web.rest.AdministrativoResource;
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
 * Test class for the AdministrativoResource REST controller.
 *
 * @see AdministrativoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class AdministrativoResourceIntTest {

    @Autowired
    private AdministrativoRepository administrativoRepository;

    @Autowired
    private AdministrativoService administrativoService;

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

    private MockMvc restAdministrativoMockMvc;

    private Administrativo administrativo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdministrativoResource administrativoResource = new AdministrativoResource(administrativoService);
        this.restAdministrativoMockMvc = MockMvcBuilders.standaloneSetup(administrativoResource)
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
    public static Administrativo createEntity(EntityManager em) {
        Administrativo administrativo = new Administrativo();
        return administrativo;
    }

    @Before
    public void initTest() {
        administrativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdministrativo() throws Exception {
        int databaseSizeBeforeCreate = administrativoRepository.findAll().size();

        // Create the Administrativo
        restAdministrativoMockMvc.perform(post("/api/administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administrativo)))
            .andExpect(status().isCreated());

        // Validate the Administrativo in the database
        List<Administrativo> administrativoList = administrativoRepository.findAll();
        assertThat(administrativoList).hasSize(databaseSizeBeforeCreate + 1);
        Administrativo testAdministrativo = administrativoList.get(administrativoList.size() - 1);
    }

    @Test
    @Transactional
    public void createAdministrativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = administrativoRepository.findAll().size();

        // Create the Administrativo with an existing ID
        administrativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrativoMockMvc.perform(post("/api/administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administrativo)))
            .andExpect(status().isBadRequest());

        // Validate the Administrativo in the database
        List<Administrativo> administrativoList = administrativoRepository.findAll();
        assertThat(administrativoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdministrativos() throws Exception {
        // Initialize the database
        administrativoRepository.saveAndFlush(administrativo);

        // Get all the administrativoList
        restAdministrativoMockMvc.perform(get("/api/administrativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativo.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAdministrativo() throws Exception {
        // Initialize the database
        administrativoRepository.saveAndFlush(administrativo);

        // Get the administrativo
        restAdministrativoMockMvc.perform(get("/api/administrativos/{id}", administrativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(administrativo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdministrativo() throws Exception {
        // Get the administrativo
        restAdministrativoMockMvc.perform(get("/api/administrativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdministrativo() throws Exception {
        // Initialize the database
        administrativoService.save(administrativo);

        int databaseSizeBeforeUpdate = administrativoRepository.findAll().size();

        // Update the administrativo
        Administrativo updatedAdministrativo = administrativoRepository.findById(administrativo.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrativo are not directly saved in db
        em.detach(updatedAdministrativo);

        restAdministrativoMockMvc.perform(put("/api/administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdministrativo)))
            .andExpect(status().isOk());

        // Validate the Administrativo in the database
        List<Administrativo> administrativoList = administrativoRepository.findAll();
        assertThat(administrativoList).hasSize(databaseSizeBeforeUpdate);
        Administrativo testAdministrativo = administrativoList.get(administrativoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAdministrativo() throws Exception {
        int databaseSizeBeforeUpdate = administrativoRepository.findAll().size();

        // Create the Administrativo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativoMockMvc.perform(put("/api/administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administrativo)))
            .andExpect(status().isBadRequest());

        // Validate the Administrativo in the database
        List<Administrativo> administrativoList = administrativoRepository.findAll();
        assertThat(administrativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdministrativo() throws Exception {
        // Initialize the database
        administrativoService.save(administrativo);

        int databaseSizeBeforeDelete = administrativoRepository.findAll().size();

        // Delete the administrativo
        restAdministrativoMockMvc.perform(delete("/api/administrativos/{id}", administrativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Administrativo> administrativoList = administrativoRepository.findAll();
        assertThat(administrativoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Administrativo.class);
        Administrativo administrativo1 = new Administrativo();
        administrativo1.setId(1L);
        Administrativo administrativo2 = new Administrativo();
        administrativo2.setId(administrativo1.getId());
        assertThat(administrativo1).isEqualTo(administrativo2);
        administrativo2.setId(2L);
        assertThat(administrativo1).isNotEqualTo(administrativo2);
        administrativo1.setId(null);
        assertThat(administrativo1).isNotEqualTo(administrativo2);
    }
}
