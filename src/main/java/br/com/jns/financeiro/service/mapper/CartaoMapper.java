package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.CartaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cartao and its DTO CartaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CartaoMapper extends EntityMapper<CartaoDTO, Cartao> {



    default Cartao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cartao cartao = new Cartao();
        cartao.setId(id);
        return cartao;
    }
}
