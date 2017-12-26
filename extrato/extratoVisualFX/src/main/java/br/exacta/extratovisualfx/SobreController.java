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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class SobreController implements Initializable {

    @FXML
    private ImageView imgLogoExtrato;
    @FXML
    private Label lblVersao;
    @FXML
    private ImageView imgLogoExacta;
    @FXML
    private Label lblEnderecoSite;
    @FXML
    private Label lblFechar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
