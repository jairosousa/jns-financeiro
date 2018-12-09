package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.ParcelaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Parcela and its DTO ParcelaDTO.
 */
@Mapper(componentModel = "spring", uses = {CartaoMapper.class, PagamentoMapper.class})
public interface ParcelaMapper extends EntityMapper<ParcelaDTO, Parcela> {

    @Mapping(source = "cartao.id", target = "cartaoId")
    @Mapping(source = "cartao.nome", target = "cartaoNome")
    @Mapping(source = "pagamento.id", target = "pagamentoId")
    @Mapping(source = "pagamento.formaPag", target = "pagamentoFormaPag")
    @Mapping(source = "pagamento.quantidadeParcelas", target = "totalParc")
    ParcelaDTO toDto(Parcela parcela);

    @Mapping(source = "cartaoId", target = "cartao")
    @Mapping(source = "pagamentoId", target = "pagamento")
    Parcela toEntity(ParcelaDTO parcelaDTO);

    default Parcela fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parcela parcela = new Parcela();
        parcela.setId(id);
        return parcela;
    }
}
