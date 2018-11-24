package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.service.dto.ParcelaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Parcela and its DTO ParcelaDTO.
 */
@Mapper(componentModel = "spring", uses = {PagamentoMapper.class})
public interface ParcelaMapper extends EntityMapper<ParcelaDTO, Parcela> {

    @Mapping(source = "pagamento.id", target = "pagamentoId")
    ParcelaDTO toDto(Parcela parcela);

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
