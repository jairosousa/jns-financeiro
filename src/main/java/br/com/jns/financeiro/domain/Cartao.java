package br.com.jns.financeiro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import br.com.jns.financeiro.domain.enumeration.Bandeira;

/**
 * A Cartao.
 */
@Entity
@Table(name = "cartao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cartao")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bandeira", nullable = false)
    private Bandeira bandeira;

    @Column(name = "numero")
    private Long numero;

    @Lob
    @Column(name = "logotipo")
    private byte[] logotipo;

    @Column(name = "logotipo_content_type")
    private String logotipoContentType;

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

    public Cartao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public Cartao bandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
        return this;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public Long getNumero() {
        return numero;
    }

    public Cartao numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public Cartao logotipo(byte[] logotipo) {
        this.logotipo = logotipo;
        return this;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public Cartao logotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
        return this;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
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
        Cartao cartao = (Cartao) o;
        if (cartao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cartao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", bandeira='" + getBandeira() + "'" +
            ", numero=" + getNumero() +
            ", logotipo='" + getLogotipo() + "'" +
            ", logotipoContentType='" + getLogotipoContentType() + "'" +
            "}";
    }
}
