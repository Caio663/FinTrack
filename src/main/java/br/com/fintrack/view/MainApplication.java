package br.com.fintrack.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/br/com/fintrack/view/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1050, 680);
        scene.getStylesheets().add(MainApplication.class.getResource("/br/com/fintrack/view/fintrack.css").toExternalForm());
        stage.setTitle("FinTrack — Finanças Pessoais"); stage.setMinWidth(850); stage.setMinHeight(550); stage.setScene(scene); stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
