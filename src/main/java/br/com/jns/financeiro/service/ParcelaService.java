package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.ParcelaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Parcela.
 */
public interface ParcelaService {

    /**
     * Save a parcela.
     *
     * @param parcelaDTO the entity to save
     * @return the persisted entity
     */
    ParcelaDTO save(ParcelaDTO parcelaDTO);

    /**
     * Get all the parcelas.
     *
     * @return the list of entities
     */
    List<ParcelaDTO> findAll();


    /**
     * Get the "id" parcela.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParcelaDTO> findOne(Long id);

    /**
     * Delete the "id" parcela.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the parcela corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<ParcelaDTO> search(String query);
}
