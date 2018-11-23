package br.com.jns.financeiro.repository;

import br.com.jns.financeiro.domain.Parcela;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Parcela entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

}
