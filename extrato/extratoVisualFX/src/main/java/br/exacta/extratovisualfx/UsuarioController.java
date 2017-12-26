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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class UsuarioController implements Initializable {

    @FXML
    private Text lblNome;
    @FXML
    private TextField txtNome;
    @FXML
    private Text lblCodigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Text lblLogin;
    @FXML
    private TextField txtLogin;
    @FXML
    private Text lblSenha;
    @FXML
    private PasswordField tpwSenha;
    @FXML
    private Text lblData;
    @FXML
    private DatePicker dtpData;
    @FXML
    private Text lblNivelAcesso;
    @FXML
    private ComboBox<?> cbbNivelAcesso;
    @FXML
    private Text lblEmpresa;
    @FXML
    private ComboBox<?> cbbEmpresa;
    @FXML
    private AnchorPane apAcoes;
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
    private ListView<?> ltvDados;
    @FXML
    private Text lblLista;
    @FXML
    private Text lblDados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
