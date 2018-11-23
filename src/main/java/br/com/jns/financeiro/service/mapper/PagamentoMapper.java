package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.PagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pagamento and its DTO PagamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {


    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "lancamento", ignore = true)
    Pagamento toEntity(PagamentoDTO pagamentoDTO);

    default Pagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        return pagamento;
    }
}
