package br.com.jns.financeiro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.jns.financeiro.service.LancamentoService;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.web.rest.util.PaginationUtil;
import br.com.jns.financeiro.service.dto.LancamentoDTO;
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
 * REST controller for managing Lancamento.
 */
@RestController
@RequestMapping("/api")
public class LancamentoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;

    public LancamentoResource(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    /**
     * POST  /lancamentos : Create a new lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamentoDTO, or with status 400 (Bad Request) if the lancamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> createLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamentos : Updates an existing lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamentoDTO,
     * or with status 400 (Bad Request) if the lancamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the lancamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamentos")
    @Timed
    public ResponseEntity<LancamentoDTO> updateLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoDTO result = lancamentoService.update(lancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamentos : get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentos in body
     */
    @GetMapping("/lancamentos")
    @Timed
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentos(Pageable pageable) {
        log.debug("REST request to get a page of Lancamentos");
        Page<LancamentoDTO> page = lancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lancamentos/:id : get the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lancamentos/{id}")
    @Timed
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        log.debug("REST request to get Lancamento : {}", id);
        Optional<LancamentoDTO> lancamentoDTO = lancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoDTO);
    }

    /**
     * DELETE  /lancamentos/:id : delete the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        log.debug("REST request to delete Lancamento : {}", id);
        lancamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lancamentos?query=:query : search for the lancamento corresponding
     * to the query.
     *
     * @param query the query of the lancamento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lancamentos")
    @Timed
    public ResponseEntity<List<LancamentoDTO>> searchLancamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lancamentos for query {}", query);
        Page<LancamentoDTO> page = lancamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lancamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
