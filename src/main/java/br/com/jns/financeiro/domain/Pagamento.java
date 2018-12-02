package br.com.jns.financeiro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.jns.financeiro.domain.enumeration.FormaPagamento;

import br.com.jns.financeiro.domain.enumeration.Status;

import br.com.jns.financeiro.domain.enumeration.TipoPagamento;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pagamento")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade_parcelas", nullable = false)
    private Long quantidadeParcelas;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pag")
    private FormaPagamento formaPag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @OneToMany(mappedBy = "pagamento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

    private Set<Parcela> parcelas = new HashSet<>();
    @OneToOne(mappedBy = "pagamento")
    @JsonIgnore
    private Lancamento lancamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public Pagamento quantidadeParcelas(Long quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
        return this;
    }

    public void setQuantidadeParcelas(Long quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public FormaPagamento getFormaPag() {
        return formaPag;
    }

    public Pagamento formaPag(FormaPagamento formaPag) {
        this.formaPag = formaPag;
        return this;
    }

    public void setFormaPag(FormaPagamento formaPag) {
        this.formaPag = formaPag;
    }

    public Status getStatus() {
        return status;
    }

    public Pagamento status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public Pagamento tipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Set<Parcela> getParcelas() {
        return parcelas;
    }

    public Pagamento parcelas(Set<Parcela> parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public Pagamento addParcela(Parcela parcela) {
        this.parcelas.add(parcela);
        parcela.setPagamento(this);
        return this;
    }

    public Pagamento removeParcela(Parcela parcela) {
        this.parcelas.remove(parcela);
        parcela.setPagamento(null);
        return this;
    }

    public void setParcelas(Set<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public Pagamento lancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
        return this;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
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
        Pagamento pagamento = (Pagamento) o;
        if (pagamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + getId() +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", formaPag='" + getFormaPag() + "'" +
            ", status='" + getStatus() + "'" +
            ", tipoPagamento='" + getTipoPagamento() + "'" +
            "}";
    }
}
