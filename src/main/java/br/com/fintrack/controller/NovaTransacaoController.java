package br.com.fintrack.controller;

import br.com.fintrack.dao.TransacaoDAO;
import br.com.fintrack.model.TipoTransacao;
import br.com.fintrack.model.Transacao;
import br.com.fintrack.service.TransacaoService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class NovaTransacaoController {
    @FXML private TextField descricaoField, valorField;
    @FXML private TextArea observacaoArea;
    @FXML private DatePicker dataPicker;
    @FXML private ComboBox<TipoTransacao> tipoCombo;
    @FXML private CheckBox recorrenteCheck;
    @FXML private Label mensagemLabel;

    @FXML private void initialize() { tipoCombo.getItems().setAll(TipoTransacao.values()); tipoCombo.setValue(TipoTransacao.DESPESA); dataPicker.setValue(LocalDate.now()); }
    @FXML private void salvar() {
        try {
            BigDecimal valor = new BigDecimal(valorField.getText().trim().replace(",", "."));
            new TransacaoService(new TransacaoDAO()).cadastrar(new Transacao(descricaoField.getText(), valor, tipoCombo.getValue(), dataPicker.getValue()));
            mensagemLabel.setText(recorrenteCheck.isSelected() ? "Transação salva (marcada como recorrente para referência)." : "Transação salva com sucesso.");
            descricaoField.clear(); valorField.clear(); observacaoArea.clear(); dataPicker.setValue(LocalDate.now());
        } catch (NumberFormatException e) { mensagemLabel.setText("Informe um valor numérico válido."); }
        catch (IllegalArgumentException | SQLException e) { mensagemLabel.setText(e.getMessage()); }
    }
    @FXML private void cancelar() { ((Stage) descricaoField.getScene().getWindow()).close(); }
}
