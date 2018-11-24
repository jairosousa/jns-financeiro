package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.service.PagamentoService;
import br.com.jns.financeiro.service.dto.PagamentoDTO;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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

/**
 * REST controller for managing Pagamento.
 */
@RestController
@RequestMapping("/api")
public class PagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PagamentoResource.class);

    private static final String ENTITY_NAME = "pagamento";

    private final PagamentoService pagamentoService;

    public PagamentoResource(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /**
     * POST  /pagamentos : Create a new pagamento.
     *
     * @param pagamentoDTO the pagamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pagamentoDTO, or with status 400 (Bad Request) if the pagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pagamentos")
    @Timed
    public ResponseEntity<PagamentoDTO> createPagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Pagamento : {}", pagamentoDTO);
        if (pagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagamentoDTO result = pagamentoService.save(pagamentoDTO);
        return ResponseEntity.created(new URI("/api/pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pagamentos : Updates an existing pagamento.
     *
     * @param pagamentoDTO the pagamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pagamentoDTO,
     * or with status 400 (Bad Request) if the pagamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the pagamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pagamentos")
    @Timed
    public ResponseEntity<PagamentoDTO> updatePagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Pagamento : {}", pagamentoDTO);
        if (pagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PagamentoDTO result = pagamentoService.save(pagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pagamentos : get all the pagamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pagamentos in body
     */
    @GetMapping("/pagamentos")
    @Timed
    public ResponseEntity<List<PagamentoDTO>> getAllPagamentos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("lancamento-is-null".equals(filter)) {
            log.debug("REST request to get all Pagamentos where lancamento is null");
            return new ResponseEntity<>(pagamentoService.findAllWhereLancamentoIsNull(),
                HttpStatus.OK);
        }
        log.debug("REST request to get a page of Pagamentos");
        Page<PagamentoDTO> page = pagamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pagamentos/:id : get the "id" pagamento.
     *
     * @param id the id of the pagamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pagamentos/{id}")
    @Timed
    public ResponseEntity<PagamentoDTO> getPagamento(@PathVariable Long id) {
        log.debug("REST request to get Pagamento : {}", id);
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagamentoDTO);
    }

    /**
     * DELETE  /pagamentos/:id : delete the "id" pagamento.
     *
     * @param id the id of the pagamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pagamentos/{id}")
    @Timed
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        log.debug("REST request to delete Pagamento : {}", id);
        pagamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pagamentos?query=:query : search for the pagamento corresponding
     * to the query.
     *
     * @param query    the query of the pagamento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pagamentos")
    @Timed
    public ResponseEntity<List<PagamentoDTO>> searchPagamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pagamentos for query {}", query);
        Page<PagamentoDTO> page = pagamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pagamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
