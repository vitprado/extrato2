<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.config;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Config {

	private static Stage stage;

	private static Scene mainScene;
	private static Scene dashScene;
	private static Scene usuarioScene;
	private static Scene nivelAcessoScene;
	private static Scene empresaScene;
	private static Scene equipamentoScene;
	private static Scene ingredienteScene;
	private static Scene curralScene;
	private static Scene receitaScene;
	private static Scene ordemScene;
	private static Scene programacaoScene;
	private static Scene resultadoScene;
	private static Scene sobreScene;

	public Config() {
	}

	public static void caixaDialogo(Alert.AlertType alertType, String s) {
		Alert alert = new Alert(alertType, s);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("MENSAGEM DO SISTEMA");
		alert.showAndWait();
	}

	public void carregarAnchorPane(AnchorPane ap, String str) {
		try {
			AnchorPane novaChamada = FXMLLoader.load(getClass().getResource("/fxml/" + str + ".fxml"));
			AnchorPane.setBottomAnchor(novaChamada, 0.0);
			AnchorPane.setLeftAnchor(novaChamada, 0.0);
			AnchorPane.setRightAnchor(novaChamada, 0.0);
			AnchorPane.setTopAnchor(novaChamada, 0.0);
			ap.getChildren().setAll(novaChamada);

			Config.changeScreen(ap, str);
			System.out.println("GUI: " + str + ", Dados da GUI: " + ap);

		} catch (IOException e) {
			System.out.println("PROBLEMAS AO ABRIR TELA INTERNA!");
		}
	}

	public void carregarAnchorPaneDialog(String str) {
		try {
			AnchorPane ap;
			ap = FXMLLoader.load(getClass().getResource("/fxml/" + str + ".fxml"));
			AnchorPane.setBottomAnchor(ap, 0.0);
			AnchorPane.setLeftAnchor(ap, 0.0);
			AnchorPane.setRightAnchor(ap, 0.0);
			AnchorPane.setTopAnchor(ap, 0.0);
			showDialog(ap);

			Config.changeScreen(ap, str);
			System.out.println("GUI: " + str + ", Dados da GUI: " + ap);

		} catch (IOException e) {
			System.out.println("PROBLEMAS AO ABRIR TELA DE DIALOG!");
			e.printStackTrace();
		}
	}

	public void carregarAnchorPaneDialog(String str, Object controller) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + str + ".fxml"));
		loader.setController(controller);
		try {
			AnchorPane anchorPane = loader.load();
			showDialog(anchorPane);

			Config.changeScreen(controller, str);
			System.out.println("GUI: " + str + ", Dados da GUI: " + controller);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDialog(AnchorPane anchorPane) {
		Stage stage = new Stage();
		Scene caixaDialogo = new Scene(anchorPane);
		stage.setScene(caixaDialogo);
		stage.setResizable(false);
		stage.show();
	}

	public static void changeScreen(Object userData, String src) {
		switch (src) {
		case "dash":
			stage.setScene(dashScene);
			Config.notifyAllListeners("Dash", userData);
			break;
		case "main":
			stage.setScene(mainScene);
			Config.notifyAllListeners("main", userData);
			break;
		case "usuario":
			stage.setScene(usuarioScene);
			Config.notifyAllListeners("Usuario", userData);
			break;
		case "nivel":
			stage.setScene(nivelAcessoScene);
			Config.notifyAllListeners("Nivel", userData);
			break;
		case "empresa":
			stage.setScene(empresaScene);
			Config.notifyAllListeners("Empresa", userData);
			break;
		case "equipamento":
			stage.setScene(equipamentoScene);
			Config.notifyAllListeners("Equipamentos", userData);
			break;
		case "ingrediente":
			stage.setScene(ingredienteScene);
			Config.notifyAllListeners("Ingredientes", userData);
			break;
		case "curral":
			stage.setScene(curralScene);
			Config.notifyAllListeners("Curral", userData);
			break;
		case "receita":
			stage.setScene(receitaScene);
			Config.notifyAllListeners("ReceitaLista", userData);
			break;
		case "ordem":
			stage.setScene(ordemScene);
			Config.notifyAllListeners("Ordem", userData);
			break;
		case "programacao":
			stage.setScene(programacaoScene);
			Config.notifyAllListeners("ProgramarLista", userData);
			break;
		case "resultado":
			stage.setScene(resultadoScene);
			Config.notifyAllListeners("Resultados", userData);
			break;
		case "sobre":
			stage.setScene(sobreScene);
			Config.notifyAllListeners("Sobre", userData);
			break;
		}
	}

	// PADRÃO OBSERVER PARA TROCA DE TELAS E PASSAGEM POR PARAMETRO DE DADOS VARIADO
	private static final ArrayList<OnChangeScreen> listeners = new ArrayList<>();

	public static interface OnChangeScreen {
		void onScreenChanged(String newScreen, Object userData);
	}

	public static void addOnChangeScreenListener(OnChangeScreen newListener) {
		listeners.add(newListener);
	}

	public static void notifyAllListeners(String newScreen, Object userData) {
		listeners.forEach((list) -> {
			list.onScreenChanged(newScreen, userData);
		});
	}
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.config;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Config {

    private static Stage stage;

    private static Scene mainScene;
    private static Scene usuarioScene;
    private static Scene nivelAcessoScene;
    private static Scene empresaScene;
    private static Scene equipamentoScene;
    private static Scene ingredienteScene;
    private static Scene curralScene;
    private static Scene tratoScene;
    private static Scene receitaScene;
    private static Scene ordemScene;
    private static Scene programacaoScene;
    private static Scene resultadoScene;
    private static Scene sobreScene;

    public Config() {
    }

    public static void caixaDialogo(Alert.AlertType alertType, String s) {
        Alert alert = new Alert(alertType, s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("MENSAGEM DO SISTEMA");
        alert.showAndWait();
    }

    public void carregarAnchorPane(AnchorPane ap, String str) {
        try {
            AnchorPane novaChamdada = FXMLLoader.load(getClass().getResource("/fxml/" + str + ".fxml"));
            AnchorPane.setBottomAnchor(novaChamdada, 0.0);
            AnchorPane.setLeftAnchor(novaChamdada, 0.0);
            AnchorPane.setRightAnchor(novaChamdada, 0.0);
            AnchorPane.setTopAnchor(novaChamdada, 0.0);
            ap.getChildren().setAll(novaChamdada);

            Config.changeScreen(str, ap);
            System.out.println("GUI: " + str + ", Dados da GUI: " + ap);

        } catch (IOException e) {
            System.out.println("PROBLEMAS AO ABRIR TELA INTERNA!");
            e.printStackTrace();
        }
    }

    public void carregarAnchorPaneDialog(String str) {
        try {
            AnchorPane ap;
            ap = FXMLLoader.load(getClass().getResource("/fxml/" + str + ".fxml"));
            AnchorPane.setBottomAnchor(ap, 0.0);
            AnchorPane.setLeftAnchor(ap, 0.0);
            AnchorPane.setRightAnchor(ap, 0.0);
            AnchorPane.setTopAnchor(ap, 0.0);
            showDialog(ap);

            Config.changeScreen(str, ap);
            System.out.println("GUI: " + str + ", Dados da GUI: " + ap);

        } catch (IOException e) {
            System.out.println("PROBLEMAS AO ABRIR TELA DE DIALOG!");
            e.printStackTrace();
        }
    }

    public void carregarAnchorPaneDialog(String str, Object controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + str + ".fxml"));
        loader.setController(controller);
        try {
            AnchorPane anchorPane = loader.load();
            showDialog(anchorPane);

            Config.changeScreen(str, controller);
            System.out.println("GUI: " + str + ", Dados da GUI: " + controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(AnchorPane anchorPane) {
        Stage stage = new Stage();
        Scene caixaDialogo = new Scene(anchorPane);
        stage.setScene(caixaDialogo);
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScreen(String src, Object userData) {
        switch (src) {
            case "main":
                stage.setScene(mainScene);
                Config.notifyAllListeners("main", userData);
                break;
            case "usuario":
                stage.setScene(usuarioScene);
                Config.notifyAllListeners("Usuario", userData);
                break;
            case "nivel":
                stage.setScene(nivelAcessoScene);
                Config.notifyAllListeners("Nivel", userData);
                break;
            case "empresa":
                stage.setScene(empresaScene);
                Config.notifyAllListeners("Empresa", userData);
                break;
            case "equipamento":
                stage.setScene(equipamentoScene);
                Config.notifyAllListeners("Equipamentos", userData);
                break;
            case "ingrediente":
                stage.setScene(ingredienteScene);
                Config.notifyAllListeners("Ingredientes", userData);
                break;
            case "curral":
                stage.setScene(curralScene);
                Config.notifyAllListeners("Curral", userData);
                break;
            case "trato":
                stage.setScene(tratoScene);
                Config.notifyAllListeners("Trato", userData);
                break;
            case "receita":
                stage.setScene(receitaScene);
                Config.notifyAllListeners("ReceitaLista", userData);
                break;
            case "ordem":
                stage.setScene(ordemScene);
                Config.notifyAllListeners("Ordem", userData);
                break;
            case "programacao":
                stage.setScene(programacaoScene);
                Config.notifyAllListeners("ProgramarLista", userData);
                break;
            case "resultado":
                stage.setScene(resultadoScene);
                Config.notifyAllListeners("Resultados", userData);
                break;
            case "sobre":
                stage.setScene(sobreScene);
                Config.notifyAllListeners("Sobre", userData);
                break;
        }
    }

    // PADRÃO OBSERVER PARA TROCA DE TELAS E PASSAGEM POR PARAMETRO DE DADOS VARIADO
    private static final ArrayList<OnChangeScreen> listeners = new ArrayList<>();

    public static interface OnChangeScreen {

        void onScreenChanged(String newScreen, Object userData);
    }

    public static void addOnChangeScreenListener(OnChangeScreen newListener) {
        listeners.add(newListener);
    }

    public static void notifyAllListeners(String newScreen, Object userData) {
        listeners.forEach((list) -> {
            list.onScreenChanged(newScreen, userData);
        });
    }
}
>>>>>>> e3445df0f28d950bc582f1db9bc3d4b5c6a2a0fc
