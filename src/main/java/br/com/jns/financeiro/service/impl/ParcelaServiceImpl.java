package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.domain.Pagamento;
import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.domain.enumeration.Status;
import br.com.jns.financeiro.repository.PagamentoRepository;
import br.com.jns.financeiro.repository.ParcelaRepository;
import br.com.jns.financeiro.repository.search.PagamentoSearchRepository;
import br.com.jns.financeiro.repository.search.ParcelaSearchRepository;
import br.com.jns.financeiro.service.ParcelaService;
import br.com.jns.financeiro.service.dto.ParcelaDTO;
import br.com.jns.financeiro.service.mapper.ParcelaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Parcela.
 */
@Service
@Transactional
public class ParcelaServiceImpl implements ParcelaService {

    private final Logger log = LoggerFactory.getLogger(ParcelaServiceImpl.class);

    private final ParcelaRepository parcelaRepository;

    private final ParcelaMapper parcelaMapper;

    private final ParcelaSearchRepository parcelaSearchRepository;

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoSearchRepository pagamentoSearchRepository;

    public ParcelaServiceImpl(ParcelaRepository parcelaRepository, ParcelaMapper parcelaMapper, ParcelaSearchRepository parcelaSearchRepository, PagamentoRepository pagamentoRepository, PagamentoSearchRepository pagamentoSearchRepository) {
        this.parcelaRepository = parcelaRepository;
        this.parcelaMapper = parcelaMapper;
        this.parcelaSearchRepository = parcelaSearchRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoSearchRepository = pagamentoSearchRepository;
    }

    /**
     * Save a parcela.
     *
     * @param parcelaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParcelaDTO save(ParcelaDTO parcelaDTO) {
        log.debug("Request to save Parcela : {}", parcelaDTO);

        Parcela parcela = parcelaMapper.toEntity(parcelaDTO);

        if (!StringUtils.isEmpty(parcelaDTO.getDataPagamento())) {
            parcela.setStatus(Status.PAGO);
        }

        parcela = parcelaRepository.save(parcela);
        ParcelaDTO result = parcelaMapper.toDto(parcela);
        parcelaSearchRepository.save(parcela);
        this.verfificarStatusPagamento(parcelaDTO.getPagamentoId());
        return result;
    }

    private void verfificarStatusPagamento(Long pagamentoId) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(pagamentoId);
        AtomicBoolean status = new AtomicBoolean(false);

        for (Parcela parcela : pagamento.get().getParcelas()) {
            if (parcela.getStatus().equals(Status.PENDENTE)) {
                status.set(true);
                break;
            }
        }

        if (!status.get()) {
            pagamento.get().setStatus(Status.PAGO);
            Pagamento pagamento1 = pagamentoRepository.save(pagamento.get());
            pagamentoSearchRepository.save(pagamento1);
        }
    }

    /**
     * Get all the parcelas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParcelaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parcelas");
        return parcelaRepository.findAll(pageable)
            .map(parcelaMapper::toDto);
    }


    /**
     * Get one parcela by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParcelaDTO> findOne(Long id) {
        log.debug("Request to get Parcela : {}", id);
        return parcelaRepository.findById(id)
            .map(parcelaMapper::toDto);
    }

    /**
     * Delete the parcela by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parcela : {}", id);
        parcelaRepository.deleteById(id);
        parcelaSearchRepository.deleteById(id);
    }

    /**
     * Search for the parcela corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParcelaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Parcelas for query {}", query);
        return parcelaSearchRepository.search(queryStringQuery(query), pageable)
            .map(parcelaMapper::toDto);
    }

    @Override
    public List<ParcelaDTO> findByPagamento(Long id) {
        return parcelaRepository.findAllByPagamento_Id(id).stream()
            .map(parcelaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
