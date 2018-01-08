/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Receita;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ReceitaListaController implements Initializable {

    @FXML
    private Text lblNome;
    @FXML
    private TextField txtNome;
    @FXML
    private Button btnSalvarListar;
    @FXML
    private Button btnRemover;
    @FXML
    private ListView<Receita> ltvDados;

    private final ObservableList<Receita> listaReceita = FXCollections.observableArrayList();
    private final ReceitaDAO receitaDAO = new ReceitaDAO();
    @FXML
    private Button btnAdd;

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
                            setText(receitas.getRctNome());
                        } else {
                            setText("");
                        }
                    }
                };
                return listCell;
            }
        });

        // ADICIONAR  
        btnSalvarListar.setOnAction(new EventHandler<ActionEvent>() {
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

        // CLIQUE DE UM ELEMENTO DA LISTA
        ltvDados.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("Clicou duas vezes!");
                    Receita itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                    if (itemSelecionado != null) {
                        // QUERO ABRIR OUTRA TELA QUANDO CLICAR DUAS VEZES NO ITEM SELECIONADO
                        Stage stage = new Stage();
                        try {
                            AnchorPane tela;
                            tela = FXMLLoader.load(getClass().getResource("/fxml/ReceitaTemIngredientes.fxml"));
                            Scene caixaDialogo = new Scene(tela);
                            stage.setScene(caixaDialogo);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ReceitaListaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
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
