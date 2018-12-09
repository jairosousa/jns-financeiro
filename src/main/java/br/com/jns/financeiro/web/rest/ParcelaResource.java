package br.com.jns.financeiro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.jns.financeiro.service.ParcelaService;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.web.rest.util.PaginationUtil;
import br.com.jns.financeiro.service.dto.ParcelaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Parcela.
 */
@RestController
@RequestMapping("/api")
public class ParcelaResource {

    private final Logger log = LoggerFactory.getLogger(ParcelaResource.class);

    private static final String ENTITY_NAME = "parcela";

    private final ParcelaService parcelaService;

    public ParcelaResource(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    /**
     * POST  /parcelas : Create a new parcela.
     *
     * @param parcelaDTO the parcelaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parcelaDTO, or with status 400 (Bad Request) if the parcela has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parcelas")
    @Timed
    public ResponseEntity<ParcelaDTO> createParcela(@RequestBody ParcelaDTO parcelaDTO) throws URISyntaxException {
        log.debug("REST request to save Parcela : {}", parcelaDTO);
        if (parcelaDTO.getId() != null) {
            throw new BadRequestAlertException("A new parcela cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParcelaDTO result = parcelaService.save(parcelaDTO);
        return ResponseEntity.created(new URI("/api/parcelas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parcelas : Updates an existing parcela.
     *
     * @param parcelaDTO the parcelaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parcelaDTO,
     * or with status 400 (Bad Request) if the parcelaDTO is not valid,
     * or with status 500 (Internal Server Error) if the parcelaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parcelas")
    @Timed
    public ResponseEntity<ParcelaDTO> updateParcela(@RequestBody ParcelaDTO parcelaDTO) throws URISyntaxException {
        log.debug("REST request to update Parcela : {}", parcelaDTO);
        if (parcelaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParcelaDTO result = parcelaService.save(parcelaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parcelaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parcelas : get all the parcelas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parcelas in body
     */
    @GetMapping("/parcelas")
    @Timed
    public ResponseEntity<List<ParcelaDTO>> getAllParcelas(Pageable pageable) {
        log.debug("REST request to get a page of Parcelas");
        Page<ParcelaDTO> page = parcelaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parcelas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /parcelas/:id : get the "id" parcela.
     *
     * @param id the id of the parcelaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parcelaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parcelas/{id}")
    @Timed
    public ResponseEntity<ParcelaDTO> getParcela(@PathVariable Long id) {
        log.debug("REST request to get Parcela : {}", id);
        Optional<ParcelaDTO> parcelaDTO = parcelaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcelaDTO);
    }

    /**
     * DELETE  /parcelas/:id : delete the "id" parcela.
     *
     * @param id the id of the parcelaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parcelas/{id}")
    @Timed
    public ResponseEntity<Void> deleteParcela(@PathVariable Long id) {
        log.debug("REST request to delete Parcela : {}", id);
        parcelaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parcelas?query=:query : search for the parcela corresponding
     * to the query.
     *
     * @param query the query of the parcela search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/parcelas")
    @Timed
    public ResponseEntity<List<ParcelaDTO>> searchParcelas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Parcelas for query {}", query);
        Page<ParcelaDTO> page = parcelaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/parcelas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/parcelas/pagamento/{id}")
    @Timed
    public List<ParcelaDTO> getAllParcelasByPagamento(@PathVariable Long id) {
        log.debug("REST request to get all Emails");
        return parcelaService.findByPagamento(id);
    }

}
