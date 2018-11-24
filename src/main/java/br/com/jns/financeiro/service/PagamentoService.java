package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.PagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Pagamento.
 */
public interface PagamentoService {

    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save
     * @return the persisted entity
     */
    PagamentoDTO save(PagamentoDTO pagamentoDTO);

    /**
     * Get all the pagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PagamentoDTO> findAll(Pageable pageable);

    /**
     * Get all the PagamentoDTO where Lancamento is null.
     *
     * @return the list of entities
     */
    List<PagamentoDTO> findAllWhereLancamentoIsNull();

    /**
     * Get the "id" pagamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PagamentoDTO> findOne(Long id);

    /**
     * Delete the "id" pagamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pagamento corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PagamentoDTO> search(String query, Pageable pageable);
}
