package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.Pagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pagamento entity.
 */
public interface PagamentoSearchRepository extends ElasticsearchRepository<Pagamento, Long> {
}
