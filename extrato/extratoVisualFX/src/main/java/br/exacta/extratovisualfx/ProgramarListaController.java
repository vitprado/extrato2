/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.persistencia.Equipamento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ProgramarListaController implements Initializable {

    @FXML
    private Button btnProgramar;
    @FXML
    private ChoiceBox<String> cbbEquipamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
