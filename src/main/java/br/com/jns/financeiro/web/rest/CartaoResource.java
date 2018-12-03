package br.com.jns.financeiro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.jns.financeiro.service.CartaoService;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.service.dto.CartaoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Cartao.
 */
@RestController
@RequestMapping("/api")
public class CartaoResource {

    private final Logger log = LoggerFactory.getLogger(CartaoResource.class);

    private static final String ENTITY_NAME = "cartao";

    private final CartaoService cartaoService;

    public CartaoResource(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    /**
     * POST  /cartaos : Create a new cartao.
     *
     * @param cartaoDTO the cartaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartaoDTO, or with status 400 (Bad Request) if the cartao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cartaos")
    @Timed
    public ResponseEntity<CartaoDTO> createCartao(@Valid @RequestBody CartaoDTO cartaoDTO) throws URISyntaxException {
        log.debug("REST request to save Cartao : {}", cartaoDTO);
        if (cartaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cartao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartaoDTO result = cartaoService.save(cartaoDTO);
        return ResponseEntity.created(new URI("/api/cartaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cartaos : Updates an existing cartao.
     *
     * @param cartaoDTO the cartaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartaoDTO,
     * or with status 400 (Bad Request) if the cartaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the cartaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cartaos")
    @Timed
    public ResponseEntity<CartaoDTO> updateCartao(@Valid @RequestBody CartaoDTO cartaoDTO) throws URISyntaxException {
        log.debug("REST request to update Cartao : {}", cartaoDTO);
        if (cartaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartaoDTO result = cartaoService.save(cartaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cartaos : get all the cartaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cartaos in body
     */
    @GetMapping("/cartaos")
    @Timed
    public List<CartaoDTO> getAllCartaos() {
        log.debug("REST request to get all Cartaos");
        return cartaoService.findAll();
    }

    /**
     * GET  /cartaos/:id : get the "id" cartao.
     *
     * @param id the id of the cartaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cartaos/{id}")
    @Timed
    public ResponseEntity<CartaoDTO> getCartao(@PathVariable Long id) {
        log.debug("REST request to get Cartao : {}", id);
        Optional<CartaoDTO> cartaoDTO = cartaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartaoDTO);
    }

    /**
     * DELETE  /cartaos/:id : delete the "id" cartao.
     *
     * @param id the id of the cartaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cartaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartao(@PathVariable Long id) {
        log.debug("REST request to delete Cartao : {}", id);
        cartaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cartaos?query=:query : search for the cartao corresponding
     * to the query.
     *
     * @param query the query of the cartao search
     * @return the result of the search
     */
    @GetMapping("/_search/cartaos")
    @Timed
    public List<CartaoDTO> searchCartaos(@RequestParam String query) {
        log.debug("REST request to search Cartaos for query {}", query);
        return cartaoService.search(query);
    }

}
