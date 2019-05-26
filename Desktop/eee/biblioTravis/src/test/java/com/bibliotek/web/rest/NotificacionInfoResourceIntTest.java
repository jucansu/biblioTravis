package com.bibliotek.web.rest;

import com.bibliotek.BibliotekApp;
import com.bibliotek.domain.NotificacionInfo;
import com.bibliotek.repository.NotificacionInfoRepository;
import com.bibliotek.service.NotificacionInfoService;
import com.bibliotek.web.rest.NotificacionInfoResource;
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
 * Test class for the NotificacionInfoResource REST controller.
 *
 * @see NotificacionInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekApp.class)
public class NotificacionInfoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NotificacionInfoRepository notificacionInfoRepository;

    @Autowired
    private NotificacionInfoService notificacionInfoService;

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

    private MockMvc restNotificacionInfoMockMvc;

    private NotificacionInfo notificacionInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificacionInfoResource notificacionInfoResource = new NotificacionInfoResource(notificacionInfoService);
        this.restNotificacionInfoMockMvc = MockMvcBuilders.standaloneSetup(notificacionInfoResource)
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
    public static NotificacionInfo createEntity(EntityManager em) {
        NotificacionInfo notificacionInfo = new NotificacionInfo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        return notificacionInfo;
    }

    @Before
    public void initTest() {
        notificacionInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacionInfo() throws Exception {
        int databaseSizeBeforeCreate = notificacionInfoRepository.findAll().size();

        // Create the NotificacionInfo
        restNotificacionInfoMockMvc.perform(post("/api/notificacion-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacionInfo)))
            .andExpect(status().isCreated());

        // Validate the NotificacionInfo in the database
        List<NotificacionInfo> notificacionInfoList = notificacionInfoRepository.findAll();
        assertThat(notificacionInfoList).hasSize(databaseSizeBeforeCreate + 1);
        NotificacionInfo testNotificacionInfo = notificacionInfoList.get(notificacionInfoList.size() - 1);
        assertThat(testNotificacionInfo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testNotificacionInfo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNotificacionInfo.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testNotificacionInfo.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    public void createNotificacionInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacionInfoRepository.findAll().size();

        // Create the NotificacionInfo with an existing ID
        notificacionInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionInfoMockMvc.perform(post("/api/notificacion-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacionInfo)))
            .andExpect(status().isBadRequest());

        // Validate the NotificacionInfo in the database
        List<NotificacionInfo> notificacionInfoList = notificacionInfoRepository.findAll();
        assertThat(notificacionInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNotificacionInfos() throws Exception {
        // Initialize the database
        notificacionInfoRepository.saveAndFlush(notificacionInfo);

        // Get all the notificacionInfoList
        restNotificacionInfoMockMvc.perform(get("/api/notificacion-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacionInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getNotificacionInfo() throws Exception {
        // Initialize the database
        notificacionInfoRepository.saveAndFlush(notificacionInfo);

        // Get the notificacionInfo
        restNotificacionInfoMockMvc.perform(get("/api/notificacion-infos/{id}", notificacionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notificacionInfo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotificacionInfo() throws Exception {
        // Get the notificacionInfo
        restNotificacionInfoMockMvc.perform(get("/api/notificacion-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacionInfo() throws Exception {
        // Initialize the database
        notificacionInfoService.save(notificacionInfo);

        int databaseSizeBeforeUpdate = notificacionInfoRepository.findAll().size();

        // Update the notificacionInfo
        NotificacionInfo updatedNotificacionInfo = notificacionInfoRepository.findById(notificacionInfo.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacionInfo are not directly saved in db
        em.detach(updatedNotificacionInfo);
        updatedNotificacionInfo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restNotificacionInfoMockMvc.perform(put("/api/notificacion-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificacionInfo)))
            .andExpect(status().isOk());

        // Validate the NotificacionInfo in the database
        List<NotificacionInfo> notificacionInfoList = notificacionInfoRepository.findAll();
        assertThat(notificacionInfoList).hasSize(databaseSizeBeforeUpdate);
        NotificacionInfo testNotificacionInfo = notificacionInfoList.get(notificacionInfoList.size() - 1);
        assertThat(testNotificacionInfo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNotificacionInfo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNotificacionInfo.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testNotificacionInfo.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacionInfo() throws Exception {
        int databaseSizeBeforeUpdate = notificacionInfoRepository.findAll().size();

        // Create the NotificacionInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionInfoMockMvc.perform(put("/api/notificacion-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacionInfo)))
            .andExpect(status().isBadRequest());

        // Validate the NotificacionInfo in the database
        List<NotificacionInfo> notificacionInfoList = notificacionInfoRepository.findAll();
        assertThat(notificacionInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacionInfo() throws Exception {
        // Initialize the database
        notificacionInfoService.save(notificacionInfo);

        int databaseSizeBeforeDelete = notificacionInfoRepository.findAll().size();

        // Delete the notificacionInfo
        restNotificacionInfoMockMvc.perform(delete("/api/notificacion-infos/{id}", notificacionInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NotificacionInfo> notificacionInfoList = notificacionInfoRepository.findAll();
        assertThat(notificacionInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificacionInfo.class);
        NotificacionInfo notificacionInfo1 = new NotificacionInfo();
        notificacionInfo1.setId(1L);
        NotificacionInfo notificacionInfo2 = new NotificacionInfo();
        notificacionInfo2.setId(notificacionInfo1.getId());
        assertThat(notificacionInfo1).isEqualTo(notificacionInfo2);
        notificacionInfo2.setId(2L);
        assertThat(notificacionInfo1).isNotEqualTo(notificacionInfo2);
        notificacionInfo1.setId(null);
        assertThat(notificacionInfo1).isNotEqualTo(notificacionInfo2);
    }
}
