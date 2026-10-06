package br.com.fintrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/** Entidade de domínio; valores devem ser positivos, e o tipo define o sinal no saldo. */
public class Transacao {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private LocalDate data;

    public Transacao() { }

    public Transacao(String descricao, BigDecimal valor, TipoTransacao tipo, LocalDate data) {
        setDescricao(descricao); setValor(valor); setTipo(tipo); setData(data);
    }
    public Transacao(Long id, String descricao, BigDecimal valor, TipoTransacao tipo, LocalDate data) {
        this(descricao, valor, tipo, data); this.id = id;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank() || descricao.length() > 100) throw new IllegalArgumentException("Descrição deve ter entre 1 e 100 caracteres.");
        this.descricao = descricao.trim();
    }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) throw new IllegalArgumentException("Valor deve ser maior que zero.");
        this.valor = valor;
    }
    public TipoTransacao getTipo() { return tipo; }
    public void setTipo(TipoTransacao tipo) { this.tipo = Objects.requireNonNull(tipo, "Tipo obrigatório."); }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = Objects.requireNonNull(data, "Data obrigatória."); }
    public BigDecimal getValorAssinado() { return tipo == TipoTransacao.RECEITA ? valor : valor.negate(); }
}
