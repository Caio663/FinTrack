package br.com.fintrack.controller;

import br.com.fintrack.dao.TransacaoDAO;
import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.service.TransacaoService;
import java.math.BigDecimal;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RelatorioController {
    @FXML private Label receitasLabel, despesasLabel, saldoLabel, quantidadeLabel;
    @FXML private void initialize() { atualizar(); }
    @FXML private void atualizar() {
        try {
            var service = new TransacaoService(new TransacaoDAO()); var itens = service.listar();
            BigDecimal receitas = service.calcularTotalPorTipo(itens, TipoTransacao.RECEITA); BigDecimal despesas = service.calcularTotalPorTipo(itens, TipoTransacao.DESPESA);
            receitasLabel.setText("R$ " + receitas.setScale(2)); despesasLabel.setText("R$ " + despesas.setScale(2)); saldoLabel.setText("R$ " + service.calcularSaldo(itens).setScale(2)); quantidadeLabel.setText(itens.size() + " lançamento(s) considerado(s)");
        } catch (SQLException e) { quantidadeLabel.setText("Erro: " + e.getMessage()); }
    }
}
