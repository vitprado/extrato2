/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class SplashController implements Initializable {

    @FXML
    private Text txtDireitos;
    @FXML
    private Text txtSite;
    @FXML
    private ImageView imgExtratoLogo;
    @FXML
    private ImageView imgLoading;
    @FXML
    private ImageView imgExactaLogo;
    @FXML
    private Text lblNomeLogo;
    @FXML
    private Text lblVersao;
    @FXML
    private ImageView imgExtratoLogo2;
    @FXML
    private VBox vbInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
