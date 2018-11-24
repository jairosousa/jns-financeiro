package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.service.ParcelaService;
import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.repository.ParcelaRepository;
import br.com.jns.financeiro.repository.search.ParcelaSearchRepository;
import br.com.jns.financeiro.service.dto.ParcelaDTO;
import br.com.jns.financeiro.service.mapper.ParcelaMapper;
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
 * Service Implementation for managing Parcela.
 */
@Service
@Transactional
public class ParcelaServiceImpl implements ParcelaService {

    private final Logger log = LoggerFactory.getLogger(ParcelaServiceImpl.class);

    private final ParcelaRepository parcelaRepository;

    private final ParcelaMapper parcelaMapper;

    private final ParcelaSearchRepository parcelaSearchRepository;

    public ParcelaServiceImpl(ParcelaRepository parcelaRepository, ParcelaMapper parcelaMapper, ParcelaSearchRepository parcelaSearchRepository) {
        this.parcelaRepository = parcelaRepository;
        this.parcelaMapper = parcelaMapper;
        this.parcelaSearchRepository = parcelaSearchRepository;
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
        parcela = parcelaRepository.save(parcela);
        ParcelaDTO result = parcelaMapper.toDto(parcela);
        parcelaSearchRepository.save(parcela);
        return result;
    }

    /**
     * Get all the parcelas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParcelaDTO> findAll() {
        log.debug("Request to get all Parcelas");
        return parcelaRepository.findAll().stream()
            .map(parcelaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParcelaDTO> search(String query) {
        log.debug("Request to search Parcelas for query {}", query);
        return StreamSupport
            .stream(parcelaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(parcelaMapper::toDto)
            .collect(Collectors.toList());
    }
}
