package br.com.jns.financeiro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.jns.financeiro.domain.enumeration.Tipo;
import br.com.jns.financeiro.domain.enumeration.TipoPagamento;

/**
 * A DTO for the Lancamento entity.
 */
public class LancamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate data;

    @NotNull
    @Size(min = 3)
    private String nome;

    private String descricao;

    private BigDecimal valor;

    private Tipo tipo;

    private TipoPagamento tipoPagamento;

    private Long fornecedorId;

    private String fornecedorNome;

    private Long categoriaId;

    private String categoriaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LancamentoDTO lancamentoDTO = (LancamentoDTO) o;
        if (lancamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", tipo='" + getTipo() + "'" +
            ", tipoPagamento='" + getTipoPagamento() + "'" +
            ", fornecedor=" + getFornecedorId() +
            ", fornecedor='" + getFornecedorNome() + "'" +
            ", categoria=" + getCategoriaId() +
            ", categoria='" + getCategoriaNome() + "'" +
            "}";
    }
}
