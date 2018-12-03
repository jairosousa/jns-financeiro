package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.service.LancamentoService;
import br.com.jns.financeiro.domain.Lancamento;
import br.com.jns.financeiro.repository.LancamentoRepository;
import br.com.jns.financeiro.repository.search.LancamentoSearchRepository;
import br.com.jns.financeiro.service.dto.LancamentoDTO;
import br.com.jns.financeiro.service.mapper.LancamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Lancamento.
 */
@Service
@Transactional
public class LancamentoServiceImpl implements LancamentoService {

    private final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    private final LancamentoRepository lancamentoRepository;

    private final LancamentoMapper lancamentoMapper;

    private final LancamentoSearchRepository lancamentoSearchRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper, LancamentoSearchRepository lancamentoSearchRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
        this.lancamentoSearchRepository = lancamentoSearchRepository;
    }

    /**
     * Save a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
        log.debug("Request to save Lancamento : {}", lancamentoDTO);

        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        lancamento = lancamentoRepository.save(lancamento);
        LancamentoDTO result = lancamentoMapper.toDto(lancamento);
        lancamentoSearchRepository.save(lancamento);
        return result;
    }

    /**
     * Get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lancamentos");
        return lancamentoRepository.findAll(pageable)
            .map(lancamentoMapper::toDto);
    }


    /**
     * Get one lancamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LancamentoDTO> findOne(Long id) {
        log.debug("Request to get Lancamento : {}", id);
        return lancamentoRepository.findById(id)
            .map(lancamentoMapper::toDto);
    }

    /**
     * Delete the lancamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lancamento : {}", id);
        lancamentoRepository.deleteById(id);
        lancamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the lancamento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lancamentos for query {}", query);
        return lancamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(lancamentoMapper::toDto);
    }
}
