package com.bibliotek.web.rest;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.Bibliotecario;
import com.bibliotek.repository.BibliotecarioRepository;
import com.bibliotek.service.BibliotecarioService;
import com.bibliotek.web.rest.BibliotecarioResource;
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
 * Test class for the BibliotecarioResource REST controller.
 *
 * @see BibliotecarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class BibliotecarioResourceIntTest {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Autowired
    private BibliotecarioService bibliotecarioService;

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

    private MockMvc restBibliotecarioMockMvc;

    private Bibliotecario bibliotecario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BibliotecarioResource bibliotecarioResource = new BibliotecarioResource(bibliotecarioService);
        this.restBibliotecarioMockMvc = MockMvcBuilders.standaloneSetup(bibliotecarioResource)
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
    public static Bibliotecario createEntity(EntityManager em) {
        Bibliotecario bibliotecario = new Bibliotecario();
        return bibliotecario;
    }

    @Before
    public void initTest() {
        bibliotecario = createEntity(em);
    }

    @Test
    @Transactional
    public void createBibliotecario() throws Exception {
        int databaseSizeBeforeCreate = bibliotecarioRepository.findAll().size();

        // Create the Bibliotecario
        restBibliotecarioMockMvc.perform(post("/api/bibliotecarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecario)))
            .andExpect(status().isCreated());

        // Validate the Bibliotecario in the database
        List<Bibliotecario> bibliotecarioList = bibliotecarioRepository.findAll();
        assertThat(bibliotecarioList).hasSize(databaseSizeBeforeCreate + 1);
        Bibliotecario testBibliotecario = bibliotecarioList.get(bibliotecarioList.size() - 1);
    }

    @Test
    @Transactional
    public void createBibliotecarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bibliotecarioRepository.findAll().size();

        // Create the Bibliotecario with an existing ID
        bibliotecario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBibliotecarioMockMvc.perform(post("/api/bibliotecarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecario)))
            .andExpect(status().isBadRequest());

        // Validate the Bibliotecario in the database
        List<Bibliotecario> bibliotecarioList = bibliotecarioRepository.findAll();
        assertThat(bibliotecarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBibliotecarios() throws Exception {
        // Initialize the database
        bibliotecarioRepository.saveAndFlush(bibliotecario);

        // Get all the bibliotecarioList
        restBibliotecarioMockMvc.perform(get("/api/bibliotecarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bibliotecario.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBibliotecario() throws Exception {
        // Initialize the database
        bibliotecarioRepository.saveAndFlush(bibliotecario);

        // Get the bibliotecario
        restBibliotecarioMockMvc.perform(get("/api/bibliotecarios/{id}", bibliotecario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bibliotecario.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBibliotecario() throws Exception {
        // Get the bibliotecario
        restBibliotecarioMockMvc.perform(get("/api/bibliotecarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBibliotecario() throws Exception {
        // Initialize the database
        bibliotecarioService.save(bibliotecario);

        int databaseSizeBeforeUpdate = bibliotecarioRepository.findAll().size();

        // Update the bibliotecario
        Bibliotecario updatedBibliotecario = bibliotecarioRepository.findById(bibliotecario.getId()).get();
        // Disconnect from session so that the updates on updatedBibliotecario are not directly saved in db
        em.detach(updatedBibliotecario);

        restBibliotecarioMockMvc.perform(put("/api/bibliotecarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBibliotecario)))
            .andExpect(status().isOk());

        // Validate the Bibliotecario in the database
        List<Bibliotecario> bibliotecarioList = bibliotecarioRepository.findAll();
        assertThat(bibliotecarioList).hasSize(databaseSizeBeforeUpdate);
        Bibliotecario testBibliotecario = bibliotecarioList.get(bibliotecarioList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBibliotecario() throws Exception {
        int databaseSizeBeforeUpdate = bibliotecarioRepository.findAll().size();

        // Create the Bibliotecario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBibliotecarioMockMvc.perform(put("/api/bibliotecarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bibliotecario)))
            .andExpect(status().isBadRequest());

        // Validate the Bibliotecario in the database
        List<Bibliotecario> bibliotecarioList = bibliotecarioRepository.findAll();
        assertThat(bibliotecarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBibliotecario() throws Exception {
        // Initialize the database
        bibliotecarioService.save(bibliotecario);

        int databaseSizeBeforeDelete = bibliotecarioRepository.findAll().size();

        // Delete the bibliotecario
        restBibliotecarioMockMvc.perform(delete("/api/bibliotecarios/{id}", bibliotecario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bibliotecario> bibliotecarioList = bibliotecarioRepository.findAll();
        assertThat(bibliotecarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bibliotecario.class);
        Bibliotecario bibliotecario1 = new Bibliotecario();
        bibliotecario1.setId(1L);
        Bibliotecario bibliotecario2 = new Bibliotecario();
        bibliotecario2.setId(bibliotecario1.getId());
        assertThat(bibliotecario1).isEqualTo(bibliotecario2);
        bibliotecario2.setId(2L);
        assertThat(bibliotecario1).isNotEqualTo(bibliotecario2);
        bibliotecario1.setId(null);
        assertThat(bibliotecario1).isNotEqualTo(bibliotecario2);
    }
}
