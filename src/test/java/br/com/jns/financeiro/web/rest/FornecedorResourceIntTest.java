package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.JnsFinanceiroApp;

import br.com.jns.financeiro.domain.Fornecedor;
import br.com.jns.financeiro.repository.FornecedorRepository;
import br.com.jns.financeiro.repository.search.FornecedorSearchRepository;
import br.com.jns.financeiro.service.FornecedorService;
import br.com.jns.financeiro.service.dto.FornecedorDTO;
import br.com.jns.financeiro.service.mapper.FornecedorMapper;
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
import java.util.Collections;
import java.util.List;


import static br.com.jns.financeiro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jns.financeiro.domain.enumeration.Pessoa;
/**
 * Test class for the FornecedorResource REST controller.
 *
 * @see FornecedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JnsFinanceiroApp.class)
public class FornecedorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_FIXO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_FIXO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_CEL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_CEL = "BBBBBBBBBB";

    private static final Pessoa DEFAULT_PESSOA = Pessoa.FISICA;
    private static final Pessoa UPDATED_PESSOA = Pessoa.JURIDICA;

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private FornecedorService fornecedorService;

    /**
     * This repository is mocked in the br.com.jns.financeiro.repository.search test package.
     *
     * @see br.com.jns.financeiro.repository.search.FornecedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private FornecedorSearchRepository mockFornecedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorService);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(DEFAULT_NOME)
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .telefoneFixo(DEFAULT_TELEFONE_FIXO)
            .telefoneCel(DEFAULT_TELEFONE_CEL)
            .pessoa(DEFAULT_PESSOA)
            .cnpj(DEFAULT_CNPJ)
            .cpf(DEFAULT_CPF);
        return fornecedor;
    }

    @Before
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testFornecedor.getTelefoneFixo()).isEqualTo(DEFAULT_TELEFONE_FIXO);
        assertThat(testFornecedor.getTelefoneCel()).isEqualTo(DEFAULT_TELEFONE_CEL);
        assertThat(testFornecedor.getPessoa()).isEqualTo(DEFAULT_PESSOA);
        assertThat(testFornecedor.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testFornecedor.getCpf()).isEqualTo(DEFAULT_CPF);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).save(testFornecedor);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(0)).save(fornecedor);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNome(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPessoaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setPessoa(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].telefoneFixo").value(hasItem(DEFAULT_TELEFONE_FIXO.toString())))
            .andExpect(jsonPath("$.[*].telefoneCel").value(hasItem(DEFAULT_TELEFONE_CEL.toString())))
            .andExpect(jsonPath("$.[*].pessoa").value(hasItem(DEFAULT_PESSOA.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())));
    }

    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL.toString()))
            .andExpect(jsonPath("$.telefoneFixo").value(DEFAULT_TELEFONE_FIXO.toString()))
            .andExpect(jsonPath("$.telefoneCel").value(DEFAULT_TELEFONE_CEL.toString()))
            .andExpect(jsonPath("$.pessoa").value(DEFAULT_PESSOA.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .nome(UPDATED_NOME)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .telefoneFixo(UPDATED_TELEFONE_FIXO)
            .telefoneCel(UPDATED_TELEFONE_CEL)
            .pessoa(UPDATED_PESSOA)
            .cnpj(UPDATED_CNPJ)
            .cpf(UPDATED_CPF);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testFornecedor.getTelefoneFixo()).isEqualTo(UPDATED_TELEFONE_FIXO);
        assertThat(testFornecedor.getTelefoneCel()).isEqualTo(UPDATED_TELEFONE_CEL);
        assertThat(testFornecedor.getPessoa()).isEqualTo(UPDATED_PESSOA);
        assertThat(testFornecedor.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFornecedor.getCpf()).isEqualTo(UPDATED_CPF);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).save(testFornecedor);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(0)).save(fornecedor);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Get the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).deleteById(fornecedor.getId());
    }

    @Test
    @Transactional
    public void searchFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        when(mockFornecedorSearchRepository.search(queryStringQuery("id:" + fornecedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fornecedor), PageRequest.of(0, 1), 1));
        // Search the fornecedor
        restFornecedorMockMvc.perform(get("/api/_search/fornecedors?query=id:" + fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].telefoneFixo").value(hasItem(DEFAULT_TELEFONE_FIXO)))
            .andExpect(jsonPath("$.[*].telefoneCel").value(hasItem(DEFAULT_TELEFONE_CEL)))
            .andExpect(jsonPath("$.[*].pessoa").value(hasItem(DEFAULT_PESSOA.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1L);
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);
        fornecedor2.setId(2L);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
        fornecedor1.setId(null);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorDTO.class);
        FornecedorDTO fornecedorDTO1 = new FornecedorDTO();
        fornecedorDTO1.setId(1L);
        FornecedorDTO fornecedorDTO2 = new FornecedorDTO();
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
        fornecedorDTO2.setId(fornecedorDTO1.getId());
        assertThat(fornecedorDTO1).isEqualTo(fornecedorDTO2);
        fornecedorDTO2.setId(2L);
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
        fornecedorDTO1.setId(null);
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fornecedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fornecedorMapper.fromId(null)).isNull();
    }
}
