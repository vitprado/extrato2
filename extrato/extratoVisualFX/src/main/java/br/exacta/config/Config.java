/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.config;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        }
    }
}
