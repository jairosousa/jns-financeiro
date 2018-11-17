package br.com.jns.financeiro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.jns.financeiro.domain.enumeration.FormaPagamento;
import br.com.jns.financeiro.domain.enumeration.Status;

/**
 * A DTO for the Pagamento entity.
 */
public class PagamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate vencimento;

    private LocalDate diaPagamento;

    private FormaPagamento forma;

    private BigDecimal juros;

    @NotNull
    private Long quantidadeParcelas;

    @NotNull
    private Status status;

    private Long lancamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDate getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(LocalDate diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public FormaPagamento getForma() {
        return forma;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public Long getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Long quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Long lancamentoId) {
        this.lancamentoId = lancamentoId;
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
            ", vencimento='" + getVencimento() + "'" +
            ", diaPagamento='" + getDiaPagamento() + "'" +
            ", forma='" + getForma() + "'" +
            ", juros=" + getJuros() +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", status='" + getStatus() + "'" +
            ", lancamento=" + getLancamentoId() +
            "}";
    }
}
