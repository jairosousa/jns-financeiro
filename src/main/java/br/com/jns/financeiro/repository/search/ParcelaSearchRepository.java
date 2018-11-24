package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.Parcela;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Parcela entity.
 */
public interface ParcelaSearchRepository extends ElasticsearchRepository<Parcela, Long> {
}
