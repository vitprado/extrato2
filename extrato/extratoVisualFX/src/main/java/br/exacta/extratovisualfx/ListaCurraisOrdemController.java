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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
    private ChoiceBox<Curral> cbbCurrais;
    @FXML
    private ListView<Curral> ltvDados;

    private final ObservableList<Curral> observableCurrais = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    private List<Curral> currais;

    public ListaCurraisOrdemController(List<Curral> currais) {
        this.currais = currais;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();

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
            Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                ltvDados.getItems().remove(itemSelecionado);
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "Erro ao remover curral da lista!");
            }
        });

        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enviaDadosGUI();
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void carregaComponentes() {
        List<Curral> currais;
        observableCurrais.addAll(curralDAO.getTodosCurrais());
        cbbCurrais.setItems(observableCurrais);

        cbbCurrais.setConverter(new StringConverter<Curral>() {
            @Override
            public String toString(Curral object) {
                return object.getCurDescricao();
            }

            @Override
            public Curral fromString(String string) {
                return observableCurrais.stream()
                        .filter(curral -> string.equals(curral.getCurDescricao()))
                        .findFirst().get();
            }
        });
    }

    private void enviaDadosGUI() {
        currais.addAll(ltvDados.getItems());
    }
}
