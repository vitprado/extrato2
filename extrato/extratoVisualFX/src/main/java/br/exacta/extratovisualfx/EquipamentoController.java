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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class EquipamentoController implements Initializable {

    @FXML
    private AnchorPane apAcoes;
    @FXML
    private Text lblDados;
    @FXML
    private Text lblLista;
    @FXML
    private Text lblPlaca;
    @FXML
    private TextField txtNome;
    @FXML
    private Text lblCodigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Text lblDescricao;
    @FXML
    private Text lblData;
    @FXML
    private DatePicker dtpData;
    @FXML
    private ListView<?> ltvDados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
