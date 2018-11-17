package br.com.jns.financeiro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.jns.financeiro.service.FornecedorService;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.web.rest.util.PaginationUtil;
import br.com.jns.financeiro.service.dto.FornecedorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Fornecedor.
 */
@RestController
@RequestMapping("/api")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    private final FornecedorService fornecedorService;

    public FornecedorResource(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    /**
     * POST  /fornecedors : Create a new fornecedor.
     *
     * @param fornecedorDTO the fornecedorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fornecedorDTO, or with status 400 (Bad Request) if the fornecedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fornecedors")
    @Timed
    public ResponseEntity<FornecedorDTO> createFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorDTO result = fornecedorService.save(fornecedorDTO);
        return ResponseEntity.created(new URI("/api/fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fornecedors : Updates an existing fornecedor.
     *
     * @param fornecedorDTO the fornecedorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fornecedorDTO,
     * or with status 400 (Bad Request) if the fornecedorDTO is not valid,
     * or with status 500 (Internal Server Error) if the fornecedorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fornecedors")
    @Timed
    public ResponseEntity<FornecedorDTO> updateFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FornecedorDTO result = fornecedorService.save(fornecedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fornecedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fornecedors : get all the fornecedors.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of fornecedors in body
     */
    @GetMapping("/fornecedors")
    @Timed
    public ResponseEntity<List<FornecedorDTO>> getAllFornecedors(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("lancamento-is-null".equals(filter)) {
            log.debug("REST request to get all Fornecedors where lancamento is null");
            return new ResponseEntity<>(fornecedorService.findAllWhereLancamentoIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Fornecedors");
        Page<FornecedorDTO> page = fornecedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fornecedors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fornecedors/:id : get the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fornecedorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fornecedors/{id}")
    @Timed
    public ResponseEntity<FornecedorDTO> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<FornecedorDTO> fornecedorDTO = fornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorDTO);
    }

    /**
     * DELETE  /fornecedors/:id : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fornecedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fornecedors?query=:query : search for the fornecedor corresponding
     * to the query.
     *
     * @param query the query of the fornecedor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fornecedors")
    @Timed
    public ResponseEntity<List<FornecedorDTO>> searchFornecedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fornecedors for query {}", query);
        Page<FornecedorDTO> page = fornecedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fornecedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
