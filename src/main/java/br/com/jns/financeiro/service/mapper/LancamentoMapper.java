package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.LancamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lancamento and its DTO LancamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {FornecedorMapper.class, CategoriaMapper.class})
public interface LancamentoMapper extends EntityMapper<LancamentoDTO, Lancamento> {

    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    @Mapping(source = "fornecedor.nome", target = "fornecedorNome")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    LancamentoDTO toDto(Lancamento lancamento);

    @Mapping(source = "fornecedorId", target = "fornecedor")
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(target = "pagamentos", ignore = true)
    Lancamento toEntity(LancamentoDTO lancamentoDTO);

    default Lancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        return lancamento;
    }
}
