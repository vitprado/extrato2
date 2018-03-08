/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.IngredientesDAO;
import br.exacta.dao.ReceitaDAO;
import br.exacta.dao.ReceitaTemIngredientesDAO;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.ReceitaTemIngredientes;
import br.exacta.persistencia.ReceitaTemIngredientesPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ChoiceBox<Ingredientes> cbbIngredientes;
    @FXML
    private TextField txtProporcao;
    @FXML
    private Button btnAdicionarLista;
    @FXML
    private Button btnRemoverLista;
    @FXML
    private Button btnSalvarLista;
    @FXML
    private TableView<ReceitaTemIngredientes> ltvDados = new TableView<>();
    @FXML
    private TableColumn<ReceitaTemIngredientes, String> colIngrediente = new TableColumn<>();
    @FXML
    private TableColumn<ReceitaTemIngredientes, Integer> colProporcao = new TableColumn<>();

    private final ObservableList<Ingredientes> comboIngredientes = FXCollections.observableArrayList();
    private final IngredientesDAO ingredientesDAO = new IngredientesDAO();
    private final ObservableList<ReceitaTemIngredientes> listaReceitaTemIngredientes = FXCollections.observableArrayList();
    private final ReceitaTemIngredientesDAO receitaTemIngredientesDAO = new ReceitaTemIngredientesDAO();
    private ReceitaDAO receitaDAO = new ReceitaDAO();

    private final Receita receita;

    public ReceitaTemIngredientesController(Receita receita) {
        this.receita = receita;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtReceitaNome.setText(receita.getRctNome());

        // PARA O COMBOX
        carregaComponentes();

        // PARA O LISTVIEW
        listaReceitaTemIngredientes.addAll(receita.getReceitaTemIngredientesList());
        ltvDados.setItems(listaReceitaTemIngredientes);

        // ADICIONAR  
        btnAdicionarLista.setOnAction(event -> {

            if (!txtReceitaNome.getText().trim().isEmpty()
                    && !txtProporcao.getText().trim().isEmpty()
                    && cbbIngredientes.getSelectionModel().getSelectedIndex() > -1) {

                Ingredientes ingredienteSelecionado = cbbIngredientes.getSelectionModel().getSelectedItem();

                ReceitaTemIngredientes novo = new ReceitaTemIngredientes();
                novo.setReceitaTemIngredientesPK(new ReceitaTemIngredientesPK(receita.getRctCodigo(), ingredienteSelecionado.getIngCodigo()));
                novo.setReceita(receita);
                novo.setIngredientes(ingredienteSelecionado);
                novo.setRtiProporcao(Integer.parseInt(txtProporcao.getText()));
                novo.setRtiData(Calendar.getInstance().getTime());

                listaReceitaTemIngredientes.add(novo);
                txtProporcao.clear();
            } else {
                Config.caixaDialogo(Alert.AlertType.ERROR, "PREENCHA TODOS OS CAMPOS!");
            }
        });

        // REMOVER 
        btnRemoverLista.setOnAction(event -> {
            ReceitaTemIngredientes itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
            if (itemSelecionado != null) {
                listaReceitaTemIngredientes.remove(itemSelecionado);
            }
        });

        btnSalvarLista.setOnAction(event -> {
            try {
                validaProporcao();

                receitaTemIngredientesDAO.removeByReceita(receita.getRctCodigo());
                for (ReceitaTemIngredientes receitaTemIngredientes : listaReceitaTemIngredientes) {
                    receitaTemIngredientesDAO.adicionarIngredienteReceita(receitaTemIngredientes);
                }

                receita.getReceitaTemIngredientesList().clear();
                receita.getReceitaTemIngredientesList().addAll(listaReceitaTemIngredientes);
                receitaDAO.editarReceita(receita);

                Config.caixaDialogo(Alert.AlertType.INFORMATION, "DADOS GRAVADOS COM SUCESSO!");
            } catch (IllegalArgumentException ei) {//TODO criar exception específica
                Config.caixaDialogo(Alert.AlertType.WARNING, "A soma dos valores das proporções precisam ser 100%!");
            } catch (Exception e) {
                Logger.getLogger(ReceitaTemIngredientesController.class.getName()).log(Level.SEVERE, null, e);
                Config.caixaDialogo(Alert.AlertType.WARNING, "OPSS, PROBLEMAS RELACIONADOS AO BANCO DE DADOS!");
            }
        });
    }

    private void validaProporcao() {
        int proporcaoTotal = ltvDados.getItems().stream().mapToInt(ReceitaTemIngredientes::getRtiProporcao).sum();
        if (proporcaoTotal != 100) {
            throw new IllegalArgumentException();
        }
    }

    private void carregaComponentes() {
        cbbIngredientes.requestFocus();

        List<Ingredientes> todosIngredientes = ingredientesDAO.getTodosIngredientes();
        comboIngredientes.addAll(todosIngredientes);
        cbbIngredientes.setItems(comboIngredientes);
        cbbIngredientes.setConverter(new StringConverter<Ingredientes>() {
            @Override
            public String toString(Ingredientes ingredientes) {
                return ingredientes.getIngNome();
            }

            @Override
            public Ingredientes fromString(String string) {
                return comboIngredientes.stream()
                        .filter(ingredientes -> string.equals(ingredientes.getIngNome()))
                        .findFirst().get();
            }
        });

        carregaTabela();
    }

    private void carregaTabela() {
        colIngrediente.setCellValueFactory(new PropertyValueFactory<>("IngredienteNome"));
        colProporcao.setCellValueFactory(new PropertyValueFactory<>("rtiProporcao"));
    }

}
