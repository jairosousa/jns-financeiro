package br.com.jns.financeiro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import br.com.jns.financeiro.domain.enumeration.Pessoa;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fornecedor")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "telefone_fixo")
    private String telefoneFixo;

    @Column(name = "telefone_cel")
    private String telefoneCel;

    @Enumerated(EnumType.STRING)
    @Column(name = "pessoa")
    private Pessoa pessoa;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "cpf")
    private String cpf;

    @OneToOne    @JoinColumn(unique = true)
    private Endereco endereco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Fornecedor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public Fornecedor razaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public Fornecedor telefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
        return this;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getTelefoneCel() {
        return telefoneCel;
    }

    public Fornecedor telefoneCel(String telefoneCel) {
        this.telefoneCel = telefoneCel;
        return this;
    }

    public void setTelefoneCel(String telefoneCel) {
        this.telefoneCel = telefoneCel;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Fornecedor pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Fornecedor cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public Fornecedor cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Fornecedor endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fornecedor fornecedor = (Fornecedor) o;
        if (fornecedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fornecedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", telefoneFixo='" + getTelefoneFixo() + "'" +
            ", telefoneCel='" + getTelefoneCel() + "'" +
            ", pessoa='" + getPessoa() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", cpf='" + getCpf() + "'" +
            "}";
    }
}
