package br.com.jns.financeiro.repository;

import br.com.jns.financeiro.domain.Lancamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lancamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
