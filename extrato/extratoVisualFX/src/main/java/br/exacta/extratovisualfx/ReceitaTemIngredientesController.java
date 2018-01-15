/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.IngredientesDAO;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
    private Button btnAdicionarLista;
    @FXML
    private Button btnRemoverLista;
    @FXML
    private ListView<ReceitaTemIngredientes> ltvDados;

    Config msgSistema = new Config();

    private final ObservableList<Ingredientes> comboIngredientes = FXCollections.observableArrayList();
    private final IngredientesDAO IngredientesDAO = new IngredientesDAO();
    private final ObservableList<ReceitaTemIngredientes> listaReceitaTemIngredientes = FXCollections.observableArrayList();
    private final ReceitaTemIngredientesDAO ReceitaTemIngredientesDAO = new ReceitaTemIngredientesDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // PARA O COMBOX
        //cbbIngredientes = new ComboBox<Ingredientes>(comboIngredientes);
        comboIngredientes.addAll(IngredientesDAO.getTodosIngredientes());
        cbbIngredientes.setItems(comboIngredientes);

        // PARA O LISTVIEW
        ltvDados.setItems(listaReceitaTemIngredientes);
        listaReceitaTemIngredientes.addAll(ReceitaTemIngredientesDAO.getTodosIngredienteReceita()); // tenho o resultado de todos
        ltvDados.setCellFactory(new Callback<ListView<ReceitaTemIngredientes>, ListCell<ReceitaTemIngredientes>>() {
            @Override
            public ListCell<ReceitaTemIngredientes> call(ListView<ReceitaTemIngredientes> param) {
                ListCell<ReceitaTemIngredientes> listCell;

                listCell = new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            ReceitaTemIngredientes receitaIng = (ReceitaTemIngredientes) item;
                            setText(receitaIng.getReceitaTemIngredientesPK().toString());
                        } else {
                            setText("");
                        }
                    }
                };
                return listCell;
            }
        });
        // ADICIONAR  
        btnAdicionarLista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Calendar d = Calendar.getInstance();

                if (!txtReceitaNome.getText().trim().isEmpty()
                        && !txtProporcao.getText().trim().isEmpty()
                        && cbbIngredientes.getSelectionModel().getSelectedIndex() > -1) {

                    Ingredientes cbbItemSelecionado = cbbIngredientes.getSelectionModel().getSelectedItem();
                    Receita receitaTxt = new Receita();

                    ReceitaTemIngredientes novo = new ReceitaTemIngredientes();
                    //novo.setReceita(Receita.class(txtReceitaNome.getText()));
                    novo.setIngredientes(cbbItemSelecionado);
                    novo.setRtiProporcao(Integer.parseInt(txtProporcao.getText()));
                    novo.setRtiData(d.getTime());

                    try {
                        ReceitaTemIngredientesDAO.adicionarIngredienteReceita(novo);
                        System.out.println("DEU CERTO!");
                        msgSistema.caixaDialogo(Alert.AlertType.INFORMATION, "DADOS GRAVADOS COM SUCESSO!");
                    } catch (Exception ex) {
                        Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("NÃO DEU CERTO!");
                        msgSistema.caixaDialogo(Alert.AlertType.WARNING, "OPSS, PROBLEMAS RELACIONADOS AO BANCO DE DADOS!");
                    }
                    listaReceitaTemIngredientes.add(novo);
                } else {
                    System.out.println("PREENCHA TODOS OS CAMPOS");
                    msgSistema.caixaDialogo(Alert.AlertType.ERROR, "PREENCHA TODOS OS CAMPOS!");
                }
            }
        });

        // REMOVER 
        btnRemoverLista.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ReceitaTemIngredientes itemSelecionado = (ReceitaTemIngredientes) ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    try {
                        ReceitaTemIngredientesDAO.removerIngredienteReceita(itemSelecionado.getReceitaTemIngredientesPK());
                    } catch (Exception ex) {
                        Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaReceitaTemIngredientes.remove(itemSelecionado);
                }
            }
        });

    }

}
