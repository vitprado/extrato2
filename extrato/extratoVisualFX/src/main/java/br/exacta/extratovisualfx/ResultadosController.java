/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.json.resultadoJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Button btnExportarCarregamento;
    @FXML
    private Button btnExportarDescarregamento;
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
                ObjectMapper objectMapper = new ObjectMapper();
                
                try { 
                    resultadoJSON resultado = objectMapper.readValue(arquivo, resultadoJSON.class);
                    
                    
                
                
                } catch (IOException ex) {
                    Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // PARA EXPORTAR PARA CSV   
        
    }

}
