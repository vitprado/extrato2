/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.EquipamentoDAO;
import br.exacta.persistencia.Equipamento;
import java.net.URL;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.Calendar;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class EquipamentosController implements Initializable {

    @FXML
    private TextField txtPlaca;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private Text lblPlaca;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Text lblDescricao;
    @FXML
    private ListView<Equipamento> ltvDados;

    private final ObservableList<Equipamento> listaEquipamento = FXCollections.observableArrayList();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ltvDados.setItems(listaEquipamento);
        listaEquipamento.addAll(equipamentoDAO.getTodosEquipamentos());

        ltvDados.setCellFactory(new Callback<ListView<Equipamento>, ListCell<Equipamento>>() {
            @Override
            public ListCell<Equipamento> call(ListView<Equipamento> param) {
                ListCell<Equipamento> listCell;
                listCell = new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            Equipamento equipamento = (Equipamento) item;
                            //setText(equipamento.getEpqPlaca());
                            setText(equipamento.getEqpDescricao());
                        } else {
                            setText("");
                        }
                    }
                };
                return listCell;
            }
        });

        // ADICIONAR  
        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Calendar d = Calendar.getInstance();

                if (!txtDescricao.getText().trim().isEmpty()) {
                    Equipamento novo = new Equipamento();
                    novo.setEpqPlaca(txtPlaca.getText());
                    novo.setEqpDescricao(txtDescricao.getText());
                    novo.setEqpDataCadastro(d.getTime());

                    try {
                        equipamentoDAO.adicionarEquipamento(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaEquipamento.add(novo);
                }
            }
        });

        // REMOVER 
        btnRemover.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Equipamento itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    try {
                        equipamentoDAO.removerEquipamento(itemSelecionado.getEqpCodigo());
                    } catch (Exception ex) {
                        Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaEquipamento.remove(itemSelecionado);
                }
            }
        });
    }
}
