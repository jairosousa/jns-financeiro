package br.com.jns.financeiro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import br.com.jns.financeiro.domain.enumeration.Bandeira;

/**
 * A DTO for the Cartao entity.
 */
public class CartaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Bandeira bandeira;

    private Long numero;

    @Lob
    private byte[] logotipo;
    private String logotipoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartaoDTO cartaoDTO = (CartaoDTO) o;
        if (cartaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", bandeira='" + getBandeira() + "'" +
            ", numero=" + getNumero() +
            ", logotipo='" + getLogotipo() + "'" +
            "}";
    }
}
