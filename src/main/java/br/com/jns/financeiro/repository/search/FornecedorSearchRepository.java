package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.Fornecedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fornecedor entity.
 */
public interface FornecedorSearchRepository extends ElasticsearchRepository<Fornecedor, Long> {
}
