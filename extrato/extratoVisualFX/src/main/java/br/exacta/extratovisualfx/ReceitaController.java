/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Receita;
import java.net.URL;
import java.util.Calendar;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ReceitaController implements Initializable {

    private Button btnRemover;
    private Button btnSalvar;
    @FXML
    private Text lblDados;
    @FXML
    private Text lblLista;
    @FXML
    private ListView<Receita> ltvDados;
    private TextField txtNome;

    private final ObservableList<Receita> listaReceita = FXCollections.observableArrayList();
    private final ReceitaDAO receitaDAO = new ReceitaDAO();
    @FXML
    private Color x1;
    @FXML
    private Font x2;
    @FXML
    private Font x3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ltvDados.setItems(listaReceita);
        listaReceita.addAll(receitaDAO.getTodoReceitas());

        ltvDados.setCellFactory(new Callback<ListView<Receita>, ListCell<Receita>>() {
            @Override
            public ListCell<Receita> call(ListView<Receita> param) {
                ListCell<Receita> listCell;
                listCell = new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            Receita receitas = (Receita) item;
                            //setText(receitas.getRctCodigo().toString());
                            setText(receitas.getRctNome());
                            //                           setText(receitas.get); // Proporcao
                            //setText(receitas.getRctDataCadastro().toString());
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

                if (!txtNome.getText().trim().isEmpty()) {
                    Receita novo = new Receita();
                    novo.setRctNome(txtNome.getText());
                    //novo.setReceitaTemIngredientesList(new List<ReceitaTemIngredientes>);
                    novo.setRctDataCadastro(d.getTime());

                    try {
                        receitaDAO.adicionarReceita(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(ReceitaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaReceita.add(novo);
                }
            }
        });

        // REMOVER 
        btnRemover.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Receita itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    try {
                        receitaDAO.removerReceita(itemSelecionado.getRctCodigo());
                    } catch (Exception ex) {
                        Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaReceita.remove(itemSelecionado);
                }
            }
        });
    }
}
