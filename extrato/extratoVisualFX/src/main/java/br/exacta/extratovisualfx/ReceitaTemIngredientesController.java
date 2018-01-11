/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.ReceitaTemIngredientesDAO;
import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.ReceitaTemIngredientes;
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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ReceitaTemIngredientesController implements Initializable {

    @FXML
    private Insets x1;
    @FXML
    private TextField txtReceitaNome;
    @FXML
    private ComboBox<Ingredientes> cbbIngredientes;
    @FXML
    private TextField txtProporcao;
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnAdicionarLista;
    @FXML
    private Button btnRemoverLista;
    @FXML
    private ListView<?> ltvDados;

    private final ObservableList<Ingredientes> listaIngredientes = FXCollections.observableArrayList();
    private final ObservableList<ReceitaTemIngredientes> listaReceitaTemIngredientes = FXCollections.observableArrayList();
    private final ReceitaTemIngredientesDAO ReceitaTemIngredientesDAO = new ReceitaTemIngredientesDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        ltvDados.setItems(l);
//        listaCurral.addAll(ReceitaTemIngredientesDAO.getTodosCurrais()); // tenho o resultado de todos os currais
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

        // ADICIONAR  
        btnAdicionarLista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Calendar d = Calendar.getInstance();

                if (!txtReceitaNome.getText().trim().isEmpty() &&
                    !txtProporcao.getText().trim().isEmpty() &&
                    cbbIngredientes.getSelectionModel().getSelectedIndex() > -1) {
                    
                    Ingredientes cbbItemSelecionado = cbbIngredientes.getSelectionModel().getSelectedItem();
                    Receita receitaTxt = new Receita();
                    
                    ReceitaTemIngredientes novo = new ReceitaTemIngredientes();
//                    novo.setReceita(receitaTxt.);
                    novo.setIngredientes(cbbItemSelecionado);
                    novo.setRtiProporcao(Integer.parseInt(txtProporcao.getText()));                    
                    novo.setRtiData(d.getTime());
                 
                    try {
                        ReceitaTemIngredientesDAO.adicionarIngredienteReceita(novo);
                        System.out.println("DEU CERTO!");
                    } catch (Exception ex) {
                        Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("NÃO DEU CERTO!");
                    }
                    listaReceitaTemIngredientes.add(novo);
                }
            }
        });

//        // REMOVER 
//        btnRemoverLista.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
//                if (itemSelecionado != null) {
//                    try {
//                        curralDAO.removerCurral(itemSelecionado.getCurCodigo());
//                    } catch (Exception ex) {
//                        Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    listaCurral.remove(itemSelecionado);
//                }
//            }
//        });
        
        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    this.finalize();
                } catch (Throwable ex) {
                    Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
