package br.com.fintrack.dao;

import static org.junit.jupiter.api.Assertions.*;
import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.model.Transacao;
import br.com.fintrack.service.TransacaoService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransacaoDAOTest {
    private Connection conexao;
    private TransacaoDAO dao;
    @BeforeEach void prepararBancoEmMemoria() throws Exception {
        conexao = DriverManager.getConnection("jdbc:sqlite::memory:");
        Conexao.criarTabelaSeNecessario(conexao); dao = new TransacaoDAO(conexao);
    }
    @AfterEach void fecharBanco() throws Exception { conexao.close(); }
    @Test void deveExecutarCrudCompletoECalcularSaldo() throws Exception {
        Transacao criada = dao.inserir(new Transacao("Salário", new BigDecimal("3000.00"), TipoTransacao.RECEITA, LocalDate.of(2026, 10, 1)));
        assertNotNull(criada.getId()); assertEquals(1, dao.listar().size());
        criada.setDescricao("Salário líquido"); assertTrue(dao.atualizar(criada));
        assertEquals("Salário líquido", dao.buscarPorId(criada.getId()).orElseThrow().getDescricao());
        dao.inserir(new Transacao("Mercado", new BigDecimal("250.00"), TipoTransacao.DESPESA, LocalDate.now()));
        assertEquals(new BigDecimal("2750.00"), new TransacaoService(dao).calcularSaldo(dao.listar()));
        assertTrue(dao.excluir(criada.getId())); assertTrue(dao.buscarPorId(criada.getId()).isEmpty());
    }
}
