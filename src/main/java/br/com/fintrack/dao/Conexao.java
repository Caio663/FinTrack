package br.com.fintrack.dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Centraliza a abertura e a preparação do banco JDBC. */
public final class Conexao {
    private static final String URL = System.getProperty("fintrack.db.url", "jdbc:sqlite:data/fintrack.db");
    private Conexao() { }

    public static Connection obterConexao() throws SQLException {
        try {
            if (URL.startsWith("jdbc:sqlite:data/")) Files.createDirectories(Path.of("data"));
        } catch (Exception e) { throw new SQLException("Não foi possível preparar o diretório do banco.", e); }
        Connection conexao = DriverManager.getConnection(URL,
                System.getProperty("fintrack.db.user", ""), System.getProperty("fintrack.db.password", ""));
        criarTabelaSeNecessario(conexao);
        return conexao;
    }
    public static void criarTabelaSeNecessario(Connection conexao) throws SQLException {
        // SQLite usa INTEGER PRIMARY KEY AUTOINCREMENT; a forma MySQL equivalente é INT PRIMARY KEY AUTO_INCREMENT.
        String sql = "CREATE TABLE IF NOT EXISTS transacoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(100) NOT NULL, " +
                "valor DECIMAL(10,2) NOT NULL, tipo VARCHAR(10) NOT NULL, data DATE NOT NULL)";
        try (Statement statement = conexao.createStatement()) { statement.execute(sql); }
    }
}
