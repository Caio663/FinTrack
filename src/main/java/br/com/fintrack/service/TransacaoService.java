package br.com.fintrack.service;

import br.com.fintrack.dao.TransacaoDAO;
import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.model.Transacao;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/** Camada de regras de negócio e cálculos financeiros. */
public class TransacaoService {
    private final TransacaoDAO dao;
    public TransacaoService(TransacaoDAO dao) { this.dao = dao; }
    public Transacao cadastrar(Transacao transacao) throws SQLException { return dao.inserir(transacao); }
    public List<Transacao> listar() throws SQLException { return dao.listar(); }
    public boolean excluir(long id) throws SQLException { return dao.excluir(id); }
    public BigDecimal calcularSaldo(List<? extends Transacao> transacoes) {
        return transacoes.stream().map(Transacao::getValorAssinado).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public BigDecimal calcularTotalPorTipo(List<? extends Transacao> transacoes, TipoTransacao tipo) {
        return transacoes.stream().filter(t -> t.getTipo() == tipo).map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
