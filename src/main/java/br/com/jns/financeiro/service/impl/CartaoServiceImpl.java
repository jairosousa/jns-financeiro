package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.service.CartaoService;
import br.com.jns.financeiro.domain.Cartao;
import br.com.jns.financeiro.repository.CartaoRepository;
import br.com.jns.financeiro.repository.search.CartaoSearchRepository;
import br.com.jns.financeiro.service.dto.CartaoDTO;
import br.com.jns.financeiro.service.mapper.CartaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cartao.
 */
@Service
@Transactional
public class CartaoServiceImpl implements CartaoService {

    private final Logger log = LoggerFactory.getLogger(CartaoServiceImpl.class);

    private final CartaoRepository cartaoRepository;

    private final CartaoMapper cartaoMapper;

    private final CartaoSearchRepository cartaoSearchRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository, CartaoMapper cartaoMapper, CartaoSearchRepository cartaoSearchRepository) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoMapper = cartaoMapper;
        this.cartaoSearchRepository = cartaoSearchRepository;
    }

    /**
     * Save a cartao.
     *
     * @param cartaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CartaoDTO save(CartaoDTO cartaoDTO) {
        log.debug("Request to save Cartao : {}", cartaoDTO);

        Cartao cartao = cartaoMapper.toEntity(cartaoDTO);
        cartao = cartaoRepository.save(cartao);
        CartaoDTO result = cartaoMapper.toDto(cartao);
        cartaoSearchRepository.save(cartao);
        return result;
    }

    /**
     * Get all the cartaos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartaoDTO> findAll() {
        log.debug("Request to get all Cartaos");
        return cartaoRepository.findAll().stream()
            .map(cartaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cartao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartaoDTO> findOne(Long id) {
        log.debug("Request to get Cartao : {}", id);
        return cartaoRepository.findById(id)
            .map(cartaoMapper::toDto);
    }

    /**
     * Delete the cartao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cartao : {}", id);
        cartaoRepository.deleteById(id);
        cartaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the cartao corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartaoDTO> search(String query) {
        log.debug("Request to search Cartaos for query {}", query);
        return StreamSupport
            .stream(cartaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cartaoMapper::toDto)
            .collect(Collectors.toList());
    }
}
