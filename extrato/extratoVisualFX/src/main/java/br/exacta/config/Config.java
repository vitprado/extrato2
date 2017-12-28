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

    public void novoEstagio(Stage stage, Label lb, String load, String judul, boolean resize, StageStyle style, boolean maximized) {
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);
            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (IOException e) {
        }
    }

    
    public void novaEstagio2(Stage stage, Button lb, String load, String judul, boolean resize, StageStyle style, boolean maximized) {
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(load));
            Scene scene = new Scene(root);
            st.initStyle(style);
            st.setResizable(resize);
            st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (IOException e) {
        }
    }

    public void carregarAnchorPane(AnchorPane ap, String str) {
        try {
            AnchorPane novaChamdada = FXMLLoader.load(getClass().getResource("/fxml/" + str));
            ap.getChildren().setAll(novaChamdada);
        } catch (IOException e) {
        }
    }
}
