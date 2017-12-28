/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class MenuController implements Initializable {

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
    private Button btnOrdemProducao;
    @FXML
    private Button btnProgramacao;
    @FXML
    private Button btnAmostraResultados;
    @FXML
    private Button btnSobre;
    @FXML
    private HBox hbGeral;
    @FXML
    private VBox vbMenu;
    @FXML
    private ScrollPane spConteudo;
    @FXML
    private AnchorPane apDados;
    
    Config config = new Config();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
//    
//    @FXML
//    private void botoesMenu(MouseEvent event) {
//        switch(listMenu.getSelectionModel().getSelectedIndex()){
//            case 0:{
//                con.loadAnchorPane(paneData, "customer.fxml");
//            }break;
//            case 1:{
//                con.loadAnchorPane(paneData, "product.fxml");
//            }break;
//            case 2:{
//                con.loadAnchorPane(paneData, "micro.fxml");
//            }break;
//        }
//    }
//    
//    // BOTOES DO MENU 
//    btnUsuarios.
}
