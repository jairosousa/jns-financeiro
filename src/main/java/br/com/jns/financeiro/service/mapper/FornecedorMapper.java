package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.FornecedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fornecedor and its DTO FornecedorDTO.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    FornecedorDTO toDto(Fornecedor fornecedor);

    @Mapping(source = "enderecoId", target = "endereco")
    Fornecedor toEntity(FornecedorDTO fornecedorDTO);

    default Fornecedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
