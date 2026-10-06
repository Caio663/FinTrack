package br.com.fintrack.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TransacaoTest {
    @Test void despesaDeveGerarValorAssinadoNegativo() {
        Transacao t = new Transacao("Aluguel", new BigDecimal("1500.00"), TipoTransacao.DESPESA, LocalDate.now());
        assertEquals(new BigDecimal("-1500.00"), t.getValorAssinado());
    }
    @Test void deveRejeitarValorNaoPositivo() {
        assertThrows(IllegalArgumentException.class, () -> new Transacao("Teste", BigDecimal.ZERO, TipoTransacao.RECEITA, LocalDate.now()));
    }
}
