package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.CartaoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Cartao.
 */
public interface CartaoService {

    /**
     * Save a cartao.
     *
     * @param cartaoDTO the entity to save
     * @return the persisted entity
     */
    CartaoDTO save(CartaoDTO cartaoDTO);

    /**
     * Get all the cartaos.
     *
     * @return the list of entities
     */
    List<CartaoDTO> findAll();


    /**
     * Get the "id" cartao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CartaoDTO> findOne(Long id);

    /**
     * Delete the "id" cartao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cartao corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CartaoDTO> search(String query);
}
