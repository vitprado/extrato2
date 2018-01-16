/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class MenuController implements Initializable {

    @FXML
    private final ImageView imgLogo = new ImageView();
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
    private Button btnOrdemProducao;
    @FXML
    private Button btnProgramacao;
    @FXML
    private Button btnAmostraResultados;
    @FXML
    private Button btnSobre;
    @FXML
    private VBox vbMenu;
    @FXML
    private AnchorPane apDados;
    
    Config config = new Config();
    @FXML
    private AnchorPane apPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // BOTAO DE USUÁRIOS
        btnUsuarios.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Usuario.fxml");
            }
        });        
        // BOTAO DE CURRAIS
        btnCurrais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Curral.fxml");
            }
        });        
        //BOTAO DE EQUIPAMENTOS
        btnEquipamentos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Equipamentos.fxml");
            }
        });        
        // BOTAO DE INGREDIENTES
        btnIngredientes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Ingredientes.fxml");
            }
        });        
        // BOTAO DE RECEITAS
        btnReceitas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"ReceitaLista.fxml");
            }
        });        
        // BOTAO DE ORDENS DE PRODUCAO 
        btnOrdemProducao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"OrdemProducao.fxml");
            }
        });        
        // BOTAO DE PROGRAMACAO
        btnProgramacao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"ProgramarLista.fxml");
            }
        });        
        // BOTAO DE RESULTADOS
        btnAmostraResultados.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Resultados.fxml");
            }
        });        
        // BOTAO DE SOBRE
        btnSobre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                config.carregarAnchorPane(apDados,"Sobre.fxml");
            }
        });
    }    
}
