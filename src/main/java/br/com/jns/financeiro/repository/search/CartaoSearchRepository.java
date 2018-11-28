package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.Cartao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cartao entity.
 */
public interface CartaoSearchRepository extends ElasticsearchRepository<Cartao, Long> {
}
