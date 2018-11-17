package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.Lancamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lancamento entity.
 */
public interface LancamentoSearchRepository extends ElasticsearchRepository<Lancamento, Long> {
}
