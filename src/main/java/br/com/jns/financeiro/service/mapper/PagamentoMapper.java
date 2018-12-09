package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.PagamentoDTO;

import br.com.jns.financeiro.service.dto.ParcelaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity Pagamento and its DTO PagamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {LancamentoMapper.class, ParcelaMapper.class})
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {

    @Mapping(source = "lancamento.id", target = "lancamentoId")
    @Mapping(source = "lancamento.nome", target = "lancamentoNome")
    PagamentoDTO toDto(Pagamento pagamento);

    @Mapping(target = "parcelas", ignore = false)
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
