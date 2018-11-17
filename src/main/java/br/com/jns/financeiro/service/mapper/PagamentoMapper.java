package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.PagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pagamento and its DTO PagamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {LancamentoMapper.class})
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {

    @Mapping(source = "lancamento.id", target = "lancamentoId")
    PagamentoDTO toDto(Pagamento pagamento);

    @Mapping(source = "lancamentoId", target = "lancamento")
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
