/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import java.net.URL;
import java.util.ResourceBundle;

import br.exacta.config.Config;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class MenuController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnEquipamentos;
    @FXML
    private Button btnCurrais;
    @FXML
    private Button btnIngredientes;
    @FXML
    private Button btnReceitas;
    @FXML
    private Button btnEmpresa;
    @FXML
    private Button btnOrdemProducao;
    @FXML
    private Button btnProgramacao;
    @FXML
    private Button btnAmostraResultados;
    @FXML
    private Button btnSuporte;
    @FXML
    private Button btnSobre;
    @FXML
    private Button btnDashboard;
    @FXML
    private VBox vbMenu;
    @FXML
    private AnchorPane apDados;

    Config config = new Config();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // BOTAO DE USUÁRIOS
        btnUsuarios.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Usuario");
            }
        });
        // BOTAO DE CURRAIS
        btnCurrais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Curral");
            }
        });
        //BOTAO DE EQUIPAMENTOS
        btnEquipamentos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Equipamentos");
            }
        });
        // BOTAO DE INGREDIENTES
        btnIngredientes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Ingredientes");
            }
        });
        // BOTAO DE RECEITAS
        btnReceitas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "ReceitaLista");
            }
        });
        
        // Botao Empresa
        btnEmpresa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Empresa");
            }
        });
        // BOTAO DA LISTA DE ORDENS DE PRODUCAO
        btnOrdemProducao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "ListaOrdem");
            }
        });
        // BOTAO DE PROGRAMACAO
        btnProgramacao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "ProgramarLista");
            }
        });
        // BOTAO DE RESULTADOS
        btnAmostraResultados.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Resultados");
            }
        });
        // BOTAO SUPORTE
        btnSuporte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            	Alert alert = new Alert(Alert.AlertType.WARNING, "Acesse\n Deseja importar novamente?\n");
				alert.initStyle(StageStyle.UTILITY);
				alert.setTitle("MENSAGEM DO SISTEMA");
				alert.showAndWait();
            }
        });
        // BOTAO DE SOBRE
        btnSobre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados, "Sobre");
            }
        });
    }
}
