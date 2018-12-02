package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.LancamentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Lancamento.
 */
public interface LancamentoService {

    /**
     * Save a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    LancamentoDTO save(LancamentoDTO lancamentoDTO);

    /**
     * update a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    LancamentoDTO update(LancamentoDTO lancamentoDTO);

    /**
     * Get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LancamentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lancamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LancamentoDTO> findOne(Long id);

    /**
     * Delete the "id" lancamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the lancamento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LancamentoDTO> search(String query, Pageable pageable);
}
