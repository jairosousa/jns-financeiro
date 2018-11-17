package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.service.PagamentoService;
import br.com.jns.financeiro.domain.Pagamento;
import br.com.jns.financeiro.repository.PagamentoRepository;
import br.com.jns.financeiro.repository.search.PagamentoSearchRepository;
import br.com.jns.financeiro.service.dto.PagamentoDTO;
import br.com.jns.financeiro.service.mapper.PagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Pagamento.
 */
@Service
@Transactional
public class PagamentoServiceImpl implements PagamentoService {

    private final Logger log = LoggerFactory.getLogger(PagamentoServiceImpl.class);

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoMapper pagamentoMapper;

    private final PagamentoSearchRepository pagamentoSearchRepository;

    public PagamentoServiceImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper, PagamentoSearchRepository pagamentoSearchRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
        this.pagamentoSearchRepository = pagamentoSearchRepository;
    }

    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);

        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        PagamentoDTO result = pagamentoMapper.toDto(pagamento);
        pagamentoSearchRepository.save(pagamento);
        return result;
    }

    /**
     * Get all the pagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pagamentos");
        return pagamentoRepository.findAll(pageable)
            .map(pagamentoMapper::toDto);
    }


    /**
     * Get one pagamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PagamentoDTO> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findById(id)
            .map(pagamentoMapper::toDto);
    }

    /**
     * Delete the pagamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagamento : {}", id);
        pagamentoRepository.deleteById(id);
        pagamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the pagamento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pagamentos for query {}", query);
        return pagamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(pagamentoMapper::toDto);
    }
}
