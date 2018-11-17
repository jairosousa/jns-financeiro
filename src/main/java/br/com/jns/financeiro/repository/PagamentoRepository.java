package br.com.jns.financeiro.repository;

import br.com.jns.financeiro.domain.Pagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
