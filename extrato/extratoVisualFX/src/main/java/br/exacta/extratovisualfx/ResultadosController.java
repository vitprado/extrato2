/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ResultadosController implements Initializable {

    @FXML
    private Text lblNome;
    @FXML
    private TextField txtNome;
    @FXML
    private AnchorPane apAcoes;
    @FXML
    private Text lblAcoes;
    @FXML
    private VBox vbBotoes;
    @FXML
    private Button btnImportar;
    @FXML
    private Button btnExportar;
    @FXML
    private Text lblLista;
    @FXML
    private Text lblDados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // PARA IMPORTAR ARQUIVO JSON
        btnImportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                // SEQUENCIA DE PASSOS PARA ABRIR UMA CAIXA DE DIALOGO ESCOLHENDO O ARQUIVO DE EXTENSÃO JSON
                FileChooser escolherArquivo = new FileChooser();
                escolherArquivo.setTitle("Selecione o arquivo JSON");
                escolherArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File arquivo = escolherArquivo.showOpenDialog(null);
                
                // MANIPULACAO DO ARQUIVO JSON ESCOLHIDO
                //ObjectMapper objectMapper = new ObjectMapper();
            }
        });

        // PARA EXPORTAR PARA CSV        
    }

}
