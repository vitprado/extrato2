/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.config;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Config {

    public Config() {
    }

    public static void caixaDialogo(Alert.AlertType alertType, String s) {
        Alert alert = new Alert(alertType, s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Informação");
        alert.showAndWait();
    }

    public void carregarAnchorPane(AnchorPane ap, String str) {
        try {
            AnchorPane novaChamdada = FXMLLoader.load(getClass().getResource("/fxml/" + str));
            AnchorPane.setBottomAnchor(novaChamdada, 0.0);
            AnchorPane.setLeftAnchor(novaChamdada, 0.0);
            AnchorPane.setRightAnchor(novaChamdada, 0.0);
            AnchorPane.setTopAnchor(novaChamdada, 0.0);
            ap.getChildren().setAll(novaChamdada);
        } catch (IOException e) {
            System.out.println("PROBLEMAS AO ABRIR TELA INTERNA!");
        }
    }

    public void carregarAnchorPaneDialog(String strTela) {
        try {
            AnchorPane ap;
            Stage stage = new Stage();
            ap = FXMLLoader.load(getClass().getResource("/fxml/" + strTela + ".fxml"));
            AnchorPane.setBottomAnchor(ap, 0.0);
            AnchorPane.setLeftAnchor(ap, 0.0);
            AnchorPane.setRightAnchor(ap, 0.0);
            AnchorPane.setTopAnchor(ap, 0.0);
            Scene caixaDialogo = new Scene(ap);
            stage.setScene(caixaDialogo);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            System.out.println("PROBLEMAS AO ABRIR TELA DE DIALOG!");
        }
    }
}
