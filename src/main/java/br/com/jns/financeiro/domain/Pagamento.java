package br.com.jns.financeiro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import br.com.jns.financeiro.domain.enumeration.FormaPagamento;

import br.com.jns.financeiro.domain.enumeration.Status;

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
    @Column(name = "vencimento", nullable = false)
    private LocalDate vencimento;

    @Column(name = "dia_pagamento")
    private LocalDate diaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma")
    private FormaPagamento forma;

    @Column(name = "juros", precision = 10, scale = 2)
    private BigDecimal juros;

    @NotNull
    @Column(name = "quantidade_parcelas", nullable = false)
    private Long quantidadeParcelas;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties("pagamentos")
    private Lancamento lancamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public Pagamento vencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDate getDiaPagamento() {
        return diaPagamento;
    }

    public Pagamento diaPagamento(LocalDate diaPagamento) {
        this.diaPagamento = diaPagamento;
        return this;
    }

    public void setDiaPagamento(LocalDate diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public FormaPagamento getForma() {
        return forma;
    }

    public Pagamento forma(FormaPagamento forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public Pagamento juros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
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
            ", vencimento='" + getVencimento() + "'" +
            ", diaPagamento='" + getDiaPagamento() + "'" +
            ", forma='" + getForma() + "'" +
            ", juros=" + getJuros() +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
