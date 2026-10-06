package br.com.fintrack.dao;

import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.model.Transacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/** DAO JDBC com consultas parametrizadas e transações explícitas para escrita. */
public class TransacaoDAO {
    private static final Logger LOG = Logger.getLogger(TransacaoDAO.class.getName());
    private final Connection conexao;
    public TransacaoDAO() throws SQLException { this(Conexao.obterConexao()); }
    public TransacaoDAO(Connection conexao) { this.conexao = conexao; }

    public Transacao inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO transacoes(descricao, valor, tipo, data) VALUES (?, ?, ?, ?)";
        return executarEscrita(() -> {
            try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preencher(ps, transacao); ps.executeUpdate();
                try (ResultSet chaves = ps.getGeneratedKeys()) { if (chaves.next()) transacao.setId(chaves.getLong(1)); }
                return transacao;
            }
        });
    }
    public List<Transacao> listar() throws SQLException {
        List<Transacao> resultado = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM transacoes ORDER BY data DESC, id DESC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultado.add(mapear(rs));
        }
        return resultado;
    }
    public Optional<Transacao> buscarPorId(long id) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM transacoes WHERE id = ?")) {
            ps.setLong(1, id); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(mapear(rs)) : Optional.empty(); }
        }
    }
    public boolean atualizar(Transacao transacao) throws SQLException {
        if (transacao.getId() == null) throw new IllegalArgumentException("ID obrigatório para atualização.");
        return executarEscrita(() -> {
            try (PreparedStatement ps = conexao.prepareStatement("UPDATE transacoes SET descricao=?, valor=?, tipo=?, data=? WHERE id=?")) {
                preencher(ps, transacao); ps.setLong(5, transacao.getId()); return ps.executeUpdate() == 1;
            }
        });
    }
    public boolean excluir(long id) throws SQLException {
        return executarEscrita(() -> { try (PreparedStatement ps = conexao.prepareStatement("DELETE FROM transacoes WHERE id=?")) { ps.setLong(1, id); return ps.executeUpdate() == 1; } });
    }
    private void preencher(PreparedStatement ps, Transacao t) throws SQLException {
        ps.setString(1, t.getDescricao()); ps.setBigDecimal(2, t.getValor()); ps.setString(3, t.getTipo().name()); ps.setDate(4, Date.valueOf(t.getData()));
    }
    private Transacao mapear(ResultSet rs) throws SQLException { return new Transacao(rs.getLong("id"), rs.getString("descricao"), rs.getBigDecimal("valor"), TipoTransacao.valueOf(rs.getString("tipo")), rs.getDate("data").toLocalDate()); }
    private <T> T executarEscrita(SqlOperation<T> operacao) throws SQLException {
        boolean autoCommit = conexao.getAutoCommit(); conexao.setAutoCommit(false);
        try { T resultado = operacao.executar(); conexao.commit(); return resultado; }
        catch (SQLException | RuntimeException e) { conexao.rollback(); LOG.log(Level.SEVERE, "Falha na transação de banco", e); throw e; }
        finally { conexao.setAutoCommit(autoCommit); }
    }
    @FunctionalInterface private interface SqlOperation<T> { T executar() throws SQLException; }
}
