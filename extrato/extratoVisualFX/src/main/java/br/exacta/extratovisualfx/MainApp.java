package br.exacta.extratovisualfx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent fxmlMain = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
        Scene mainScene = new Scene(fxmlMain);

        primaryStage.setTitle("exTrato - Sistema de Trato Bovino");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
