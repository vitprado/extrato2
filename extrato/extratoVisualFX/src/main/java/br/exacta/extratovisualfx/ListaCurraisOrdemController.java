/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.CurralDAO;
import br.exacta.persistencia.Curral;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ListaCurraisOrdemController implements Initializable {

    @FXML
    private Button btnInserirCurral;
    @FXML
    private ChoiceBox<String> cbbCurrais;
    @FXML
    private ListView<Curral> ltvDados;

    private final ObservableList<String> comboCurrais = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();
    }

    private void carregaComponentes() {
        List<String> currais;
        currais = curralDAO.getNomesCurraisDistinct();
        comboCurrais.addAll(currais);
        cbbCurrais.setItems(comboCurrais);
    }

}
