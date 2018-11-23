package br.com.jns.financeiro.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.jns.financeiro.domain.enumeration.FormaPagamento;
import br.com.jns.financeiro.domain.enumeration.Status;

/**
 * A DTO for the Parcela entity.
 */
public class ParcelaDTO implements Serializable {

    private Long id;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private Long numero;

    private BigDecimal valor;

    private BigDecimal juros;

    private BigDecimal total;

    private FormaPagamento forma;

    private Status status;

    private Long pagamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public FormaPagamento getForma() {
        return forma;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(Long pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParcelaDTO parcelaDTO = (ParcelaDTO) o;
        if (parcelaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parcelaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParcelaDTO{" +
            "id=" + getId() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", numero=" + getNumero() +
            ", valor=" + getValor() +
            ", juros=" + getJuros() +
            ", total=" + getTotal() +
            ", forma='" + getForma() + "'" +
            ", status='" + getStatus() + "'" +
            ", pagamento=" + getPagamentoId() +
            "}";
    }
}
