package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.domain.Endereco;
import br.com.jns.financeiro.repository.EnderecoRepository;
import br.com.jns.financeiro.repository.search.EnderecoSearchRepository;
import br.com.jns.financeiro.service.CepService;
import br.com.jns.financeiro.service.EnderecoService;
import br.com.jns.financeiro.service.dto.EnderecoDTO;
import br.com.jns.financeiro.service.exceptions.ViaCepException;
import br.com.jns.financeiro.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Endereco.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    private final EnderecoSearchRepository enderecoSearchRepository;

    private final CepService cepService;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper, EnderecoSearchRepository enderecoSearchRepository, CepService cepService) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.enderecoSearchRepository = enderecoSearchRepository;
        this.cepService = cepService;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);

        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        EnderecoDTO result = enderecoMapper.toDto(endereco);
        enderecoSearchRepository.save(endereco);
        return result;
    }

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toDto);
    }


    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id)
            .map(enderecoMapper::toDto);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
        enderecoSearchRepository.deleteById(id);
    }

    /**
     * Search for the endereco corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enderecos for query {}", query);
        return enderecoSearchRepository.search(queryStringQuery(query), pageable)
            .map(enderecoMapper::toDto);
    }

    @Override
    public EnderecoDTO findEnderecoByCep(String cep) throws ViaCepException {
        try {
            return cepService.buscarCep(cep);
        } catch (ViaCepException e) {
            e.printStackTrace();
        }
        throw new ViaCepException("Não foi possível encontrar o CEP", cep, ViaCepException.class.getName());
    }
}
