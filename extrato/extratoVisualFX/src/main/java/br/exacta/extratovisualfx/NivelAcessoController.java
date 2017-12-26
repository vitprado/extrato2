/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class NivelAcessoController implements Initializable {

    @FXML
    private Text lblDados;
    @FXML
    private Text lblLista;
    @FXML
    private ListView<?> ltvDados;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Text lblCodigo;
    @FXML
    private Text lblAcoes;
    @FXML
    private VBox vbBotoes;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnRelatorio;
    @FXML
    private AnchorPane apDados;
    @FXML
    private AnchorPane apNivelAcesso;
    @FXML
    private Text lblNivelAcesso;
    @FXML
    private Text lblDescricaoMini;
    @FXML
    private AnchorPane apAcoesSistema;
    @FXML
    private Text lblDescricao;
    @FXML
    private TextField txtDescricao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
