package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.JnsFinanceiroApp;

import br.com.jns.financeiro.domain.Cartao;
import br.com.jns.financeiro.repository.CartaoRepository;
import br.com.jns.financeiro.repository.search.CartaoSearchRepository;
import br.com.jns.financeiro.service.CartaoService;
import br.com.jns.financeiro.service.dto.CartaoDTO;
import br.com.jns.financeiro.service.mapper.CartaoMapper;
import br.com.jns.financeiro.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

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

import br.com.jns.financeiro.domain.enumeration.Bandeira;
/**
 * Test class for the CartaoResource REST controller.
 *
 * @see CartaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JnsFinanceiroApp.class)
public class CartaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Bandeira DEFAULT_BANDEIRA = Bandeira.AMERICAN;
    private static final Bandeira UPDATED_BANDEIRA = Bandeira.CIELO;

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final byte[] DEFAULT_LOGOTIPO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOTIPO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGOTIPO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOTIPO_CONTENT_TYPE = "image/png";

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoMapper cartaoMapper;

    @Autowired
    private CartaoService cartaoService;

    /**
     * This repository is mocked in the br.com.jns.financeiro.repository.search test package.
     *
     * @see br.com.jns.financeiro.repository.search.CartaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CartaoSearchRepository mockCartaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartaoMockMvc;

    private Cartao cartao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartaoResource cartaoResource = new CartaoResource(cartaoService);
        this.restCartaoMockMvc = MockMvcBuilders.standaloneSetup(cartaoResource)
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
    public static Cartao createEntity(EntityManager em) {
        Cartao cartao = new Cartao()
            .nome(DEFAULT_NOME)
            .bandeira(DEFAULT_BANDEIRA)
            .numero(DEFAULT_NUMERO)
            .logotipo(DEFAULT_LOGOTIPO)
            .logotipoContentType(DEFAULT_LOGOTIPO_CONTENT_TYPE);
        return cartao;
    }

    @Before
    public void initTest() {
        cartao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartao() throws Exception {
        int databaseSizeBeforeCreate = cartaoRepository.findAll().size();

        // Create the Cartao
        CartaoDTO cartaoDTO = cartaoMapper.toDto(cartao);
        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeCreate + 1);
        Cartao testCartao = cartaoList.get(cartaoList.size() - 1);
        assertThat(testCartao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCartao.getBandeira()).isEqualTo(DEFAULT_BANDEIRA);
        assertThat(testCartao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCartao.getLogotipo()).isEqualTo(DEFAULT_LOGOTIPO);
        assertThat(testCartao.getLogotipoContentType()).isEqualTo(DEFAULT_LOGOTIPO_CONTENT_TYPE);

        // Validate the Cartao in Elasticsearch
        verify(mockCartaoSearchRepository, times(1)).save(testCartao);
    }

    @Test
    @Transactional
    public void createCartaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartaoRepository.findAll().size();

        // Create the Cartao with an existing ID
        cartao.setId(1L);
        CartaoDTO cartaoDTO = cartaoMapper.toDto(cartao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cartao in Elasticsearch
        verify(mockCartaoSearchRepository, times(0)).save(cartao);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRepository.findAll().size();
        // set the field null
        cartao.setNome(null);

        // Create the Cartao, which fails.
        CartaoDTO cartaoDTO = cartaoMapper.toDto(cartao);

        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isBadRequest());

        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBandeiraIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRepository.findAll().size();
        // set the field null
        cartao.setBandeira(null);

        // Create the Cartao, which fails.
        CartaoDTO cartaoDTO = cartaoMapper.toDto(cartao);

        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isBadRequest());

        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartaos() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        // Get all the cartaoList
        restCartaoMockMvc.perform(get("/api/cartaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].bandeira").value(hasItem(DEFAULT_BANDEIRA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))));
    }
    
    @Test
    @Transactional
    public void getCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        // Get the cartao
        restCartaoMockMvc.perform(get("/api/cartaos/{id}", cartao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.bandeira").value(DEFAULT_BANDEIRA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.logotipoContentType").value(DEFAULT_LOGOTIPO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logotipo").value(Base64Utils.encodeToString(DEFAULT_LOGOTIPO)));
    }

    @Test
    @Transactional
    public void getNonExistingCartao() throws Exception {
        // Get the cartao
        restCartaoMockMvc.perform(get("/api/cartaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        int databaseSizeBeforeUpdate = cartaoRepository.findAll().size();

        // Update the cartao
        Cartao updatedCartao = cartaoRepository.findById(cartao.getId()).get();
        // Disconnect from session so that the updates on updatedCartao are not directly saved in db
        em.detach(updatedCartao);
        updatedCartao
            .nome(UPDATED_NOME)
            .bandeira(UPDATED_BANDEIRA)
            .numero(UPDATED_NUMERO)
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE);
        CartaoDTO cartaoDTO = cartaoMapper.toDto(updatedCartao);

        restCartaoMockMvc.perform(put("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isOk());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeUpdate);
        Cartao testCartao = cartaoList.get(cartaoList.size() - 1);
        assertThat(testCartao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCartao.getBandeira()).isEqualTo(UPDATED_BANDEIRA);
        assertThat(testCartao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCartao.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testCartao.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);

        // Validate the Cartao in Elasticsearch
        verify(mockCartaoSearchRepository, times(1)).save(testCartao);
    }

    @Test
    @Transactional
    public void updateNonExistingCartao() throws Exception {
        int databaseSizeBeforeUpdate = cartaoRepository.findAll().size();

        // Create the Cartao
        CartaoDTO cartaoDTO = cartaoMapper.toDto(cartao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartaoMockMvc.perform(put("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cartao in Elasticsearch
        verify(mockCartaoSearchRepository, times(0)).save(cartao);
    }

    @Test
    @Transactional
    public void deleteCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        int databaseSizeBeforeDelete = cartaoRepository.findAll().size();

        // Get the cartao
        restCartaoMockMvc.perform(delete("/api/cartaos/{id}", cartao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cartao in Elasticsearch
        verify(mockCartaoSearchRepository, times(1)).deleteById(cartao.getId());
    }

    @Test
    @Transactional
    public void searchCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);
        when(mockCartaoSearchRepository.search(queryStringQuery("id:" + cartao.getId())))
            .thenReturn(Collections.singletonList(cartao));
        // Search the cartao
        restCartaoMockMvc.perform(get("/api/_search/cartaos?query=id:" + cartao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].bandeira").value(hasItem(DEFAULT_BANDEIRA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cartao.class);
        Cartao cartao1 = new Cartao();
        cartao1.setId(1L);
        Cartao cartao2 = new Cartao();
        cartao2.setId(cartao1.getId());
        assertThat(cartao1).isEqualTo(cartao2);
        cartao2.setId(2L);
        assertThat(cartao1).isNotEqualTo(cartao2);
        cartao1.setId(null);
        assertThat(cartao1).isNotEqualTo(cartao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartaoDTO.class);
        CartaoDTO cartaoDTO1 = new CartaoDTO();
        cartaoDTO1.setId(1L);
        CartaoDTO cartaoDTO2 = new CartaoDTO();
        assertThat(cartaoDTO1).isNotEqualTo(cartaoDTO2);
        cartaoDTO2.setId(cartaoDTO1.getId());
        assertThat(cartaoDTO1).isEqualTo(cartaoDTO2);
        cartaoDTO2.setId(2L);
        assertThat(cartaoDTO1).isNotEqualTo(cartaoDTO2);
        cartaoDTO1.setId(null);
        assertThat(cartaoDTO1).isNotEqualTo(cartaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cartaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cartaoMapper.fromId(null)).isNull();
    }
}
