/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.ReceitaTemIngredientes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ltvDados.setItems(listaReceita);
        listaReceita.addAll(receitaDAO.getTodoReceitas());

        ltvDados.setCellFactory((ListView<Receita> param) -> {
            ListCell<Receita> listCell;
            listCell = new ListCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        Receita receitas = (Receita) item;

                        StringJoiner joiner = new StringJoiner(", ", "[", "]");
                        List<ReceitaTemIngredientes> receitaTemIngredientesList = receitas.getReceitaTemIngredientesList();
                        for (ReceitaTemIngredientes r : receitaTemIngredientesList) {
                            String s = String.format("%s = %s", r.getIngredienteNome(), isNull(r.getRtiProporcao()) ? "" : r.getRtiProporcao()+"%");

                            joiner.add(s);
                        }
                        String collect = joiner.toString();

                        setText(receitas.getRctNome() + " " + collect);
                    } else {
                        setText("");
                    }
                }
            };
            return listCell;
        });

        // ADICIONAR  
        btnSalvarListar.setOnAction((ActionEvent event) -> {
            Calendar d = Calendar.getInstance();

            if (!txtNome.getText().trim().isEmpty()) {
                Receita novo = new Receita();
                novo.setRctNome(txtNome.getText());
                novo.setRctAtivo(false);
                novo.setRctDataCadastro(d.getTime());
                novo.setReceitaTemIngredientesList(new ArrayList<>());

                try {
                    receitaDAO.adicionarReceita(novo);
                } catch (Exception ex) {
                    Logger.getLogger(ReceitaListaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaReceita.add(novo);
            }
        });

        // CLIQUE DE UM ELEMENTO DA LISTA
        ltvDados.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Receita itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    String strTela = "ReceitaTemIngredientes";
                    Config config = new Config();
                    ReceitaTemIngredientesController receitaTemIngredientesController = new ReceitaTemIngredientesController(itemSelecionado);
                    config.carregarAnchorPaneStage(strTela, receitaTemIngredientesController);

                    listaReceita.clear();
                    listaReceita.addAll(receitaDAO.getTodoReceitas());
                }
            }
        });

        // REMOVER 
        btnRemover.setOnAction((ActionEvent event) -> {
            Receita itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                try {
                    receitaDAO.removerReceita(itemSelecionado.getRctCodigo());
                } catch (Exception ex) {
                    Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaReceita.remove(itemSelecionado);
            }
        });
    }
}
