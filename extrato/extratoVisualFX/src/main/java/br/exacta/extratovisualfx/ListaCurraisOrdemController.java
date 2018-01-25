/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CurralDAO;
import br.exacta.persistencia.Curral;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.control.cell.ChoiceBoxListCell;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ListaCurraisOrdemController implements Initializable {

    @FXML
    private Button btnInserirCurral;
    @FXML
    private Button btnRemoverLista;
    @FXML
    private Button btnSalvar;
    @FXML
    private ChoiceBox<String> cbbCurrais;
    @FXML
    private ListView<String> ltvDados;

    private final ObservableList<String> listaCurrais = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    Config msg = new Config();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();
        //comboCurrais.addAll(cbbCurrais.getItems()); // tenho o resultado de todos os currais

        // ADICIONAR  
        btnInserirCurral.setOnAction((ActionEvent event) -> {
            if (cbbCurrais.getValue() != null) {
                ltvDados.getItems().add(cbbCurrais.getValue());
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Erro ao inserir curral na lista!");
            }
        });

        // REMOVER 
        btnRemoverLista.setOnAction((ActionEvent event) -> {
            String itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                ltvDados.getItems().remove(itemSelecionado);
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Erro ao remover curral da lista!");
            }
        });
    }

    private void carregaComponentes() {
        List<String> currais;
        currais = curralDAO.getNomesCurraisDistinct();
        listaCurrais.addAll(currais);
        cbbCurrais.setItems(listaCurrais);
    }
}
