/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

//import br.exacta.persistencia.Curral;
import br.exacta.dao.CurralDAO;
import br.exacta.persistencia.Curral;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class CurralController implements Initializable {

    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private ListView<Curral> ltvDados;
    @FXML
    private TextField txtNome;

    private final ObservableList<Curral> listaCurral = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ltvDados.setItems(listaCurral);
        listaCurral.addAll(curralDAO.getTodosCurrais()); // tenho o resultado de todos os currais
        
        ltvDados.setCellFactory((ListView<Curral> param) -> {
            ListCell<Curral> listCell;
            
            listCell = new ListCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        Curral currais = (Curral) item;
                        setText(currais.getCurDescricao());
                    } else {
                        setText("");
                    }
                }
            };
            return listCell;
        });

        // ADICIONAR  
        btnSalvar.setOnAction((ActionEvent event) -> {
            if (!txtNome.getText().trim().isEmpty()) {
                Curral novo = new Curral();
                novo.setCurDescricao(txtNome.getText());
                
                try {
                    curralDAO.adicionarCurral(novo);
                } catch (Exception ex) {
                    Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaCurral.add(novo);
            }
        });

        // REMOVER 
        btnRemover.setOnAction((ActionEvent event) -> {
            Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                try {
                    curralDAO.removerCurral(itemSelecionado.getCurCodigo());
                } catch (Exception ex) {
                    Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaCurral.remove(itemSelecionado);
            }
        });
    }
}
