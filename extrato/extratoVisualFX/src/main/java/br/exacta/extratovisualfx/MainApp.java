package br.exacta.extratovisualfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
		Parent fxmlMain = FXMLLoader.load(MainApp.class.getClassLoader().getResource("resources/fxml/Menu.fxml"));
        Scene mainScene = new Scene(fxmlMain);
        Image imgExtrato = new Image(MainApp.class.getClassLoader().getResourceAsStream("resources/img/icon/icone-mini.png"));
        
        mainScene.getStylesheets().add("resources/css/login.css");
        primaryStage.getIcons().add(imgExtrato);        

        primaryStage.setTitle("exTrato - Sistema de Trato Bovino");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
