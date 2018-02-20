/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dto.ConsultaOrdemDTODAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private TableColumn<?, ?> colOrdens;
    @FXML
    private TableColumn<?, ?> colReceitas;
    @FXML
    private TableColumn<?, ?> colTratos;
    @FXML
    private TableColumn<?, ?> colCurrais;
    @FXML
    private TableColumn<?, ?> colAcao;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        btnProgramar.setOnAction((ActionEvent event) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new FileOutputStream(file.getPath() + "/programacao.json"), new ConsultaOrdemDTODAO().findAll());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (cbbEquipamento.getValue() != null) {

            // VEJO SE NO BANCO EXISTE ESTE EQUIPAMENTO COM ALGUMA ORDEM RELACIONADA
            // SE HOUVER PEGO E LISTO ESTA ORDEM, ONDE APENAS TEMOS AQUELE EQUIPAMENTO SELECIONADO

//            }
//            else
//                Config.caixaDialogo(Alert.AlertType.WARNING, "Certifique-se que seu equipamento esteja selecionado!");
        });
    }

}
