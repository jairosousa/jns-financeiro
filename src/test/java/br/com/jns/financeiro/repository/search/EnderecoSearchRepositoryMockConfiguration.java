package br.com.jns.financeiro.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EnderecoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EnderecoSearchRepositoryMockConfiguration {

    @MockBean
    private EnderecoSearchRepository mockEnderecoSearchRepository;

}
