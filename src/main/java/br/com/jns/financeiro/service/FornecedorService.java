package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.FornecedorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Fornecedor.
 */
public interface FornecedorService {

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save
     * @return the persisted entity
     */
    FornecedorDTO save(FornecedorDTO fornecedorDTO);

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FornecedorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fornecedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FornecedorDTO> findOne(Long id);

    /**
     * Delete the "id" fornecedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fornecedor corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FornecedorDTO> search(String query, Pageable pageable);
}
