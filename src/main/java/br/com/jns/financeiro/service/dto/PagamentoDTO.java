package br.com.jns.financeiro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import br.com.jns.financeiro.domain.Parcela;
import br.com.jns.financeiro.domain.enumeration.FormaPagamento;
import br.com.jns.financeiro.domain.enumeration.Status;
import br.com.jns.financeiro.domain.enumeration.TipoPagamento;

/**
 * A DTO for the Pagamento entity.
 */
public class PagamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long quantidadeParcelas;

    @NotNull
    private FormaPagamento formaPag;

    @NotNull
    private LocalDate dataPrimeiroVencimento;

    @NotNull
    private Status status;

    @NotNull
    private TipoPagamento tipoPagamento;

    private Long lancamentoId;

    private String lancamentoNome;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Long quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public LocalDate getDataPrimeiroVencimento() {
        return dataPrimeiroVencimento;
    }

    public void setDataPrimeiroVencimento(LocalDate dataPrimeiroVencimento) {
        this.dataPrimeiroVencimento = dataPrimeiroVencimento;
    }

    public FormaPagamento getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(FormaPagamento formaPag) {
        this.formaPag = formaPag;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Long lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public String getLancamentoNome() {
        return lancamentoNome;
    }

    public void setLancamentoNome(String lancamentoNome) {
        this.lancamentoNome = lancamentoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagamentoDTO pagamentoDTO = (PagamentoDTO) o;
        if (pagamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagamentoDTO{" +
            "id=" + getId() +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", dataPrimeiroVencimento='" + getDataPrimeiroVencimento() + "'" +
            ", formaPag='" + getFormaPag() + "'" +
            ", status='" + getStatus() + "'" +
            ", tipoPagamento='" + getTipoPagamento() + "'" +
            "}";
    }
}
