package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.domain.Endereco;
import br.com.jns.financeiro.repository.EnderecoRepository;
import br.com.jns.financeiro.service.FornecedorService;
import br.com.jns.financeiro.domain.Fornecedor;
import br.com.jns.financeiro.repository.FornecedorRepository;
import br.com.jns.financeiro.repository.search.FornecedorSearchRepository;
import br.com.jns.financeiro.service.dto.FornecedorDTO;
import br.com.jns.financeiro.service.mapper.EnderecoMapper;
import br.com.jns.financeiro.service.mapper.FornecedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Fornecedor.
 */
@Service
@Transactional
public class FornecedorServiceImpl implements FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorServiceImpl.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    private final FornecedorSearchRepository fornecedorSearchRepository;

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper, FornecedorSearchRepository fornecedorSearchRepository, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
        this.fornecedorSearchRepository = fornecedorSearchRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Endereco endereco = enderecoMapper.toEntity(fornecedorDTO.getEndereco());
        fornecedorDTO.setEnderecoId(enderecoRepository.save(endereco).getId());
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        FornecedorDTO result = fornecedorMapper.toDto(fornecedor);
        fornecedorSearchRepository.save(fornecedor);
        return result;
    }

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAllByOrderByNomeAsc(pageable)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
        fornecedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the fornecedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fornecedors for query {}", query);
        return fornecedorSearchRepository.search(queryStringQuery(query), pageable)
            .map(fornecedorMapper::toDto);
    }
}
