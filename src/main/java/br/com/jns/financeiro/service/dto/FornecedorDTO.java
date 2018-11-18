package br.com.jns.financeiro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.jns.financeiro.domain.enumeration.Pessoa;

/**
 * A DTO for the Fornecedor entity.
 */
public class FornecedorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String nome;

    private Pessoa pessoa;

    private String cnpj;

    private String cpf;

    private Long enderecoId;

    private EnderecoDTO endereco;

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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FornecedorDTO fornecedorDTO = (FornecedorDTO) o;
        if (fornecedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fornecedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", pessoa='" + getPessoa() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", endereco=" + getEnderecoId() +
            "}";
    }
}
