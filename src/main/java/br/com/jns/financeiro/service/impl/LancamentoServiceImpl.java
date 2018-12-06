package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.domain.Lancamento;
import br.com.jns.financeiro.domain.Pagamento;
import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.domain.enumeration.Status;
import br.com.jns.financeiro.domain.enumeration.TipoPagamento;
import br.com.jns.financeiro.repository.LancamentoRepository;
import br.com.jns.financeiro.repository.PagamentoRepository;
import br.com.jns.financeiro.repository.ParcelaRepository;
import br.com.jns.financeiro.repository.search.LancamentoSearchRepository;
import br.com.jns.financeiro.repository.search.PagamentoSearchRepository;
import br.com.jns.financeiro.repository.search.ParcelaSearchRepository;
import br.com.jns.financeiro.service.LancamentoService;
import br.com.jns.financeiro.service.dto.LancamentoDTO;
import br.com.jns.financeiro.service.mapper.LancamentoMapper;
import br.com.jns.financeiro.service.mapper.PagamentoMapper;
import br.com.jns.financeiro.service.mapper.ParcelaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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

    private final PagamentoMapper pagamentoMapper;

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoSearchRepository pagamentoSearchRepository;

    private final ParcelaMapper parcelaMapper;

    private final ParcelaRepository parcelaRepository;

    private final ParcelaSearchRepository parcelaSearchRepository;


    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository, LancamentoMapper lancamentoMapper, LancamentoSearchRepository lancamentoSearchRepository, PagamentoMapper pagamentoMapper, PagamentoRepository pagamentoRepository, PagamentoSearchRepository pagamentoSearchRepository, ParcelaMapper parcelaMapper, ParcelaRepository parcelaRepository, ParcelaSearchRepository parcelaSearchRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
        this.lancamentoSearchRepository = lancamentoSearchRepository;
        this.pagamentoMapper = pagamentoMapper;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoSearchRepository = pagamentoSearchRepository;
        this.parcelaMapper = parcelaMapper;
        this.parcelaRepository = parcelaRepository;
        this.parcelaSearchRepository = parcelaSearchRepository;
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
        Pagamento pagamento = pagamentoMapper.toEntity(lancamentoDTO.getPagamento());
        pagamento = pagamentoRepository.save(pagamento);
        pagamentoSearchRepository.save(pagamento);

        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        lancamento.setPagamento(pagamento);
        lancamento = lancamentoRepository.save(lancamento);
        lancamentoSearchRepository.save(lancamento);
        LancamentoDTO result = lancamentoMapper.toDto(lancamento);
        this.gerarParcela(lancamento);
        return result;
    }

    @Override
    public LancamentoDTO update(LancamentoDTO lancamentoDTO) {
        log.debug("Request to save Lancamento : {}", lancamentoDTO);
        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        lancamento = lancamentoRepository.save(lancamento);
        LancamentoDTO result = lancamentoMapper.toDto(lancamento);
        lancamentoSearchRepository.save(lancamento);
        return result;
    }

    private void gerarParcela(Lancamento lancamento) {
        if (lancamento.getPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)) {
            Parcela parcela = new Parcela(
                lancamento.getPagamento().getDataPrimeiroVencimento(),
                lancamento.getPagamento().getQuantidadeParcelas(),
                lancamento.getValor(),
                Status.PENDENTE,
                lancamento.getPagamento()
            );
            parcela = parcelaRepository.save(parcela);
            parcelaSearchRepository.save(parcela);
        } else {
            String numParcela = Long.toString(lancamento.getPagamento().getQuantidadeParcelas());
            BigDecimal valorParcela = lancamento.getValor().divide(new BigDecimal(numParcela), 2 , RoundingMode.UP);
            LocalDate dataInicial = lancamento.getPagamento().getDataPrimeiroVencimento();
            for (int i = 0; i < lancamento.getPagamento().getQuantidadeParcelas(); i++) {
                dataInicial = this.gerarDataVencimento(dataInicial, i);

                Parcela parcela = new Parcela(
                    dataInicial,
                    (long) (i + 1),
                    valorParcela,
                    Status.PENDENTE,
                    lancamento.getPagamento()
                );
                parcela = parcelaRepository.save(parcela);
                parcelaSearchRepository.save(parcela);
            }

        }
    }

    private LocalDate gerarDataVencimento(LocalDate dataInicial, long i) {
        return dataInicial.plusMonths(i);
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
     * @param query    the query of the search
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
