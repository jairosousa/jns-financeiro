package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.JnsFinanceiroApp;

import br.com.jns.financeiro.domain.Pagamento;
import br.com.jns.financeiro.repository.PagamentoRepository;
import br.com.jns.financeiro.repository.search.PagamentoSearchRepository;
import br.com.jns.financeiro.service.PagamentoService;
import br.com.jns.financeiro.service.dto.PagamentoDTO;
import br.com.jns.financeiro.service.mapper.PagamentoMapper;
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
import br.com.jns.financeiro.domain.enumeration.TipoPagamento;
/**
 * Test class for the PagamentoResource REST controller.
 *
 * @see PagamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JnsFinanceiroApp.class)
public class PagamentoResourceIntTest {

    private static final LocalDate DEFAULT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DIA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DIA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QUANTIDADE_PARCELAS = 1L;
    private static final Long UPDATED_QUANTIDADE_PARCELAS = 2L;

    private static final Status DEFAULT_STATUS = Status.PAGO;
    private static final Status UPDATED_STATUS = Status.PENDENTE;

    private static final TipoPagamento DEFAULT_TIPO_PAGAMENTO = TipoPagamento.AVISTA;
    private static final TipoPagamento UPDATED_TIPO_PAGAMENTO = TipoPagamento.PARCELADO;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Autowired
    private PagamentoService pagamentoService;

    /**
     * This repository is mocked in the br.com.jns.financeiro.repository.search test package.
     *
     * @see br.com.jns.financeiro.repository.search.PagamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PagamentoSearchRepository mockPagamentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagamentoResource pagamentoResource = new PagamentoResource(pagamentoService);
        this.restPagamentoMockMvc = MockMvcBuilders.standaloneSetup(pagamentoResource)
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
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .vencimento(DEFAULT_VENCIMENTO)
            .diaPagamento(DEFAULT_DIA_PAGAMENTO)
            .quantidadeParcelas(DEFAULT_QUANTIDADE_PARCELAS)
            .status(DEFAULT_STATUS)
            .tipoPagamento(DEFAULT_TIPO_PAGAMENTO);
        return pagamento;
    }

    @Before
    public void initTest() {
        pagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getVencimento()).isEqualTo(DEFAULT_VENCIMENTO);
        assertThat(testPagamento.getDiaPagamento()).isEqualTo(DEFAULT_DIA_PAGAMENTO);
        assertThat(testPagamento.getQuantidadeParcelas()).isEqualTo(DEFAULT_QUANTIDADE_PARCELAS);
        assertThat(testPagamento.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPagamento.getTipoPagamento()).isEqualTo(DEFAULT_TIPO_PAGAMENTO);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).save(testPagamento);
    }

    @Test
    @Transactional
    public void createPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento with an existing ID
        pagamento.setId(1L);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(0)).save(pagamento);
    }

    @Test
    @Transactional
    public void checkVencimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setVencimento(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeParcelasIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setQuantidadeParcelas(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setStatus(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc.perform(get("/api/pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].diaPagamento").value(hasItem(DEFAULT_DIA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeParcelas").value(hasItem(DEFAULT_QUANTIDADE_PARCELAS.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tipoPagamento").value(hasItem(DEFAULT_TIPO_PAGAMENTO.toString())));
    }

    @Test
    @Transactional
    public void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.vencimento").value(DEFAULT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.diaPagamento").value(DEFAULT_DIA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.quantidadeParcelas").value(DEFAULT_QUANTIDADE_PARCELAS.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tipoPagamento").value(DEFAULT_TIPO_PAGAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .vencimento(UPDATED_VENCIMENTO)
            .diaPagamento(UPDATED_DIA_PAGAMENTO)
            .quantidadeParcelas(UPDATED_QUANTIDADE_PARCELAS)
            .status(UPDATED_STATUS)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(updatedPagamento);

        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getVencimento()).isEqualTo(UPDATED_VENCIMENTO);
        assertThat(testPagamento.getDiaPagamento()).isEqualTo(UPDATED_DIA_PAGAMENTO);
        assertThat(testPagamento.getQuantidadeParcelas()).isEqualTo(UPDATED_QUANTIDADE_PARCELAS);
        assertThat(testPagamento.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPagamento.getTipoPagamento()).isEqualTo(UPDATED_TIPO_PAGAMENTO);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).save(testPagamento);
    }

    @Test
    @Transactional
    public void updateNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(0)).save(pagamento);
    }

    @Test
    @Transactional
    public void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Get the pagamento
        restPagamentoMockMvc.perform(delete("/api/pagamentos/{id}", pagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).deleteById(pagamento.getId());
    }

    @Test
    @Transactional
    public void searchPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        when(mockPagamentoSearchRepository.search(queryStringQuery("id:" + pagamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pagamento), PageRequest.of(0, 1), 1));
        // Search the pagamento
        restPagamentoMockMvc.perform(get("/api/_search/pagamentos?query=id:" + pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].diaPagamento").value(hasItem(DEFAULT_DIA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeParcelas").value(hasItem(DEFAULT_QUANTIDADE_PARCELAS.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tipoPagamento").value(hasItem(DEFAULT_TIPO_PAGAMENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagamento.class);
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(1L);
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(pagamento1.getId());
        assertThat(pagamento1).isEqualTo(pagamento2);
        pagamento2.setId(2L);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
        pagamento1.setId(null);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagamentoDTO.class);
        PagamentoDTO pagamentoDTO1 = new PagamentoDTO();
        pagamentoDTO1.setId(1L);
        PagamentoDTO pagamentoDTO2 = new PagamentoDTO();
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
        pagamentoDTO2.setId(pagamentoDTO1.getId());
        assertThat(pagamentoDTO1).isEqualTo(pagamentoDTO2);
        pagamentoDTO2.setId(2L);
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
        pagamentoDTO1.setId(null);
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pagamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pagamentoMapper.fromId(null)).isNull();
    }
}
