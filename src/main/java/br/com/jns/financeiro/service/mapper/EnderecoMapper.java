package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Endereco and its DTO EnderecoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {



    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
