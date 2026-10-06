package br.com.fintrack.controller;

import br.com.fintrack.dao.TransacaoDAO;
import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.model.Transacao;
import br.com.fintrack.service.TransacaoService;
import br.com.fintrack.view.MainApplication;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML private TableView<Transacao> tabelaTransacoes;
    @FXML private TableColumn<Transacao, String> colunaData, colunaDescricao, colunaValor, colunaTipo;
    @FXML private Label saldoLabel, receitasLabel, despesasLabel, statusLabel;
    private TransacaoService service;

    @FXML private void initialize() {
        try { service = new TransacaoService(new TransacaoDAO()); configurarTabela(); atualizarDados(); }
        catch (SQLException e) { mostrarErro("Banco de dados", e.getMessage()); }
    }
    private void configurarTabela() {
        colunaData.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colunaDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescricao()));
        colunaValor.setCellValueFactory(c -> new SimpleStringProperty("R$ " + c.getValue().getValor().setScale(2)));
        colunaTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo().name()));
    }
    @FXML public void abrirNovaTransacao() { abrirJanela("Nova Transação", "nova-transacao-view.fxml", 500, 530); }
    @FXML public void abrirRelatorio() { abrirJanela("Relatório Financeiro", "relatorio-view.fxml", 480, 350); }
    @FXML public void atualizarDados() {
        if (service == null) return;
        try {
            var transacoes = service.listar(); tabelaTransacoes.setItems(FXCollections.observableArrayList(transacoes));
            BigDecimal receitas = service.calcularTotalPorTipo(transacoes, TipoTransacao.RECEITA);
            BigDecimal despesas = service.calcularTotalPorTipo(transacoes, TipoTransacao.DESPESA);
            saldoLabel.setText("R$ " + service.calcularSaldo(transacoes).setScale(2)); receitasLabel.setText("R$ " + receitas.setScale(2)); despesasLabel.setText("R$ " + despesas.setScale(2));
            statusLabel.setText(transacoes.size() + " transação(ões) carregada(s).");
        } catch (SQLException e) { mostrarErro("Não foi possível carregar", e.getMessage()); }
    }
    @FXML public void excluirSelecionada() {
        Transacao selecionada = tabelaTransacoes.getSelectionModel().getSelectedItem();
        if (selecionada == null) { statusLabel.setText("Selecione uma transação para excluir."); return; }
        if (new Alert(Alert.AlertType.CONFIRMATION, "Excluir '" + selecionada.getDescricao() + "'?", ButtonType.YES, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try { service.excluir(selecionada.getId()); atualizarDados(); } catch (SQLException e) { mostrarErro("Erro ao excluir", e.getMessage()); }
        }
    }
    private void abrirJanela(String titulo, String arquivo, int largura, int altura) {
        try {
            Parent root = FXMLLoader.load(MainApplication.class.getResource("/br/com/fintrack/view/" + arquivo));
            Stage janela = new Stage(); janela.initOwner(tabelaTransacoes.getScene().getWindow()); janela.initModality(Modality.WINDOW_MODAL); janela.setTitle(titulo);
            Scene scene = new Scene(root, largura, altura); scene.getStylesheets().add(MainApplication.class.getResource("/br/com/fintrack/view/fintrack.css").toExternalForm()); janela.setScene(scene); janela.setOnHidden(e -> atualizarDados()); janela.show();
        } catch (IOException e) { mostrarErro("Tela indisponível", e.getMessage()); }
    }
    private void mostrarErro(String titulo, String detalhe) { new Alert(Alert.AlertType.ERROR, detalhe, ButtonType.OK) {{ setTitle(titulo); setHeaderText(titulo); }}.showAndWait(); }
}
