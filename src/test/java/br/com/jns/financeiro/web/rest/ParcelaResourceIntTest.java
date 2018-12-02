package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.JnsFinanceiroApp;

import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.repository.ParcelaRepository;
import br.com.jns.financeiro.repository.search.ParcelaSearchRepository;
import br.com.jns.financeiro.service.ParcelaService;
import br.com.jns.financeiro.service.dto.ParcelaDTO;
import br.com.jns.financeiro.service.mapper.ParcelaMapper;
import br.com.jns.financeiro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static br.com.jns.financeiro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jns.financeiro.domain.enumeration.Status;
/**
 * Test class for the ParcelaResource REST controller.
 *
 * @see ParcelaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JnsFinanceiroApp.class)
public class ParcelaResourceIntTest {

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_JUROS = new BigDecimal(1);
    private static final BigDecimal UPDATED_JUROS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final Status DEFAULT_STATUS = Status.PAGO;
    private static final Status UPDATED_STATUS = Status.PENDENTE;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private ParcelaMapper parcelaMapper;

    @Autowired
    private ParcelaService parcelaService;

    /**
     * This repository is mocked in the br.com.jns.financeiro.repository.search test package.
     *
     * @see br.com.jns.financeiro.repository.search.ParcelaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParcelaSearchRepository mockParcelaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParcelaMockMvc;

    private Parcela parcela;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParcelaResource parcelaResource = new ParcelaResource(parcelaService);
        this.restParcelaMockMvc = MockMvcBuilders.standaloneSetup(parcelaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parcela createEntity(EntityManager em) {
        Parcela parcela = new Parcela()
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .numero(DEFAULT_NUMERO)
            .valor(DEFAULT_VALOR)
            .juros(DEFAULT_JUROS)
            .total(DEFAULT_TOTAL)
            .status(DEFAULT_STATUS);
        return parcela;
    }

    @Before
    public void initTest() {
        parcela = createEntity(em);
    }

    @Test
    @Transactional
    public void createParcela() throws Exception {
        int databaseSizeBeforeCreate = parcelaRepository.findAll().size();

        // Create the Parcela
        ParcelaDTO parcelaDTO = parcelaMapper.toDto(parcela);
        restParcelaMockMvc.perform(post("/api/parcelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcelaDTO)))
            .andExpect(status().isCreated());

        // Validate the Parcela in the database
        List<Parcela> parcelaList = parcelaRepository.findAll();
        assertThat(parcelaList).hasSize(databaseSizeBeforeCreate + 1);
        Parcela testParcela = parcelaList.get(parcelaList.size() - 1);
        assertThat(testParcela.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testParcela.getDataPagamento()).isEqualTo(DEFAULT_DATA_PAGAMENTO);
        assertThat(testParcela.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testParcela.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testParcela.getJuros()).isEqualTo(DEFAULT_JUROS);
        assertThat(testParcela.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testParcela.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Parcela in Elasticsearch
        verify(mockParcelaSearchRepository, times(1)).save(testParcela);
    }

    @Test
    @Transactional
    public void createParcelaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parcelaRepository.findAll().size();

        // Create the Parcela with an existing ID
        parcela.setId(1L);
        ParcelaDTO parcelaDTO = parcelaMapper.toDto(parcela);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcelaMockMvc.perform(post("/api/parcelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcelaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parcela in the database
        List<Parcela> parcelaList = parcelaRepository.findAll();
        assertThat(parcelaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Parcela in Elasticsearch
        verify(mockParcelaSearchRepository, times(0)).save(parcela);
    }

    @Test
    @Transactional
    public void getAllParcelas() throws Exception {
        // Initialize the database
        parcelaRepository.saveAndFlush(parcela);

        // Get all the parcelaList
        restParcelaMockMvc.perform(get("/api/parcelas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcela.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].juros").value(hasItem(DEFAULT_JUROS.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getParcela() throws Exception {
        // Initialize the database
        parcelaRepository.saveAndFlush(parcela);

        // Get the parcela
        restParcelaMockMvc.perform(get("/api/parcelas/{id}", parcela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parcela.getId().intValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.juros").value(DEFAULT_JUROS.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParcela() throws Exception {
        // Get the parcela
        restParcelaMockMvc.perform(get("/api/parcelas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParcela() throws Exception {
        // Initialize the database
        parcelaRepository.saveAndFlush(parcela);

        int databaseSizeBeforeUpdate = parcelaRepository.findAll().size();

        // Update the parcela
        Parcela updatedParcela = parcelaRepository.findById(parcela.getId()).get();
        // Disconnect from session so that the updates on updatedParcela are not directly saved in db
        em.detach(updatedParcela);
        updatedParcela
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .numero(UPDATED_NUMERO)
            .valor(UPDATED_VALOR)
            .juros(UPDATED_JUROS)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS);
        ParcelaDTO parcelaDTO = parcelaMapper.toDto(updatedParcela);

        restParcelaMockMvc.perform(put("/api/parcelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcelaDTO)))
            .andExpect(status().isOk());

        // Validate the Parcela in the database
        List<Parcela> parcelaList = parcelaRepository.findAll();
        assertThat(parcelaList).hasSize(databaseSizeBeforeUpdate);
        Parcela testParcela = parcelaList.get(parcelaList.size() - 1);
        assertThat(testParcela.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testParcela.getDataPagamento()).isEqualTo(UPDATED_DATA_PAGAMENTO);
        assertThat(testParcela.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testParcela.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testParcela.getJuros()).isEqualTo(UPDATED_JUROS);
        assertThat(testParcela.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testParcela.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Parcela in Elasticsearch
        verify(mockParcelaSearchRepository, times(1)).save(testParcela);
    }

    @Test
    @Transactional
    public void updateNonExistingParcela() throws Exception {
        int databaseSizeBeforeUpdate = parcelaRepository.findAll().size();

        // Create the Parcela
        ParcelaDTO parcelaDTO = parcelaMapper.toDto(parcela);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcelaMockMvc.perform(put("/api/parcelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcelaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parcela in the database
        List<Parcela> parcelaList = parcelaRepository.findAll();
        assertThat(parcelaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Parcela in Elasticsearch
        verify(mockParcelaSearchRepository, times(0)).save(parcela);
    }

    @Test
    @Transactional
    public void deleteParcela() throws Exception {
        // Initialize the database
        parcelaRepository.saveAndFlush(parcela);

        int databaseSizeBeforeDelete = parcelaRepository.findAll().size();

        // Get the parcela
        restParcelaMockMvc.perform(delete("/api/parcelas/{id}", parcela.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parcela> parcelaList = parcelaRepository.findAll();
        assertThat(parcelaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Parcela in Elasticsearch
        verify(mockParcelaSearchRepository, times(1)).deleteById(parcela.getId());
    }

    @Test
    @Transactional
    public void searchParcela() throws Exception {
        // Initialize the database
        parcelaRepository.saveAndFlush(parcela);
        when(mockParcelaSearchRepository.search(queryStringQuery("id:" + parcela.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(parcela), PageRequest.of(0, 1), 1));
        // Search the parcela
        restParcelaMockMvc.perform(get("/api/_search/parcelas?query=id:" + parcela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcela.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].juros").value(hasItem(DEFAULT_JUROS.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parcela.class);
        Parcela parcela1 = new Parcela();
        parcela1.setId(1L);
        Parcela parcela2 = new Parcela();
        parcela2.setId(parcela1.getId());
        assertThat(parcela1).isEqualTo(parcela2);
        parcela2.setId(2L);
        assertThat(parcela1).isNotEqualTo(parcela2);
        parcela1.setId(null);
        assertThat(parcela1).isNotEqualTo(parcela2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcelaDTO.class);
        ParcelaDTO parcelaDTO1 = new ParcelaDTO();
        parcelaDTO1.setId(1L);
        ParcelaDTO parcelaDTO2 = new ParcelaDTO();
        assertThat(parcelaDTO1).isNotEqualTo(parcelaDTO2);
        parcelaDTO2.setId(parcelaDTO1.getId());
        assertThat(parcelaDTO1).isEqualTo(parcelaDTO2);
        parcelaDTO2.setId(2L);
        assertThat(parcelaDTO1).isNotEqualTo(parcelaDTO2);
        parcelaDTO1.setId(null);
        assertThat(parcelaDTO1).isNotEqualTo(parcelaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parcelaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parcelaMapper.fromId(null)).isNull();
    }
}
