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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ChoiceBox<String> cbbCurrais;
    @FXML
    private ListView<String> ltvDados;

    private final ObservableList<String> comboCurrais = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();
        //atualizaListaItensCadastrados();

        // ADICIONAR  
        btnInserirCurral.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (cbbCurrais.getSelectionModel().getSelectedIndex() > -1) {
                    ltvDados.setItems(comboCurrais);
                    ltvDados.setCellFactory(ChoiceBoxListCell.forListView(comboCurrais));
                }
            }
        });

        // REMOVER 
//        btnRemoverLista.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
//                if (itemSelecionado != null) {
//                    try {
//                        curralDAO.removerCurral(itemSelecionado.getCurCodigo());
//                    } catch (Exception ex) {
//                        Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    listaCurral.remove(itemSelecionado);
//                }
//            }
//        });
    }

    private void carregaComponentes() {
        List<String> currais;
        currais = curralDAO.getNomesCurraisDistinct();
        comboCurrais.addAll(currais);
        cbbCurrais.setItems(comboCurrais);
    }

//    private void atualizaListaItensCadastrados() {
//
//        ltvDados.setItems(listaCurral);
//        listaCurral.addAll(curralDAO.getTodosCurrais()); // tenho o resultado de todos os currais
//
//        ltvDados.setCellFactory(new Callback<ListView<Curral>, ListCell<Curral>>() {
//            @Override
//            public ListCell<Curral> call(ListView<Curral> param) {
//                ListCell<Curral> listCell;
//
//                listCell = new ListCell() {
//                    @Override
//                    protected void updateItem(Object item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item != null) {
//                            Curral currais = (Curral) item;
//                            setText(currais.getCurDescricao());
//                        } else {
//                            setText("");
//                        }
//                    }
//                };
//                return listCell;
//            }
//        });
}
