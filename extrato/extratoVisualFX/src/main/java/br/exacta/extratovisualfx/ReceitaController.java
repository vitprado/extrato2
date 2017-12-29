/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

//import br.exacta.dao.ReceitaDAO;
import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.ReceitaTemIngredientes;
import java.net.URL;
import java.util.Calendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ReceitaController implements Initializable {

    @FXML
    private AnchorPane apAcoes;
    @FXML
    private Text lblAcoes;
    @FXML
    private VBox vbBotoes;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnRelatorio;
    @FXML
    private Text lblDados;
    @FXML
    private Text lblLista;
    @FXML
    private ListView<Receita> ltvDados;
    @FXML
    private AnchorPane apDados;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Text lblCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private Text lblNome;
    @FXML
    private Text lblProporcao;
    @FXML
    private TextField txtProporcao;
    @FXML
    private Text lblIngrediente;
    @FXML
    private ComboBox<Ingredientes> cbIngredientes;
    @FXML
    private Text lblData;
    @FXML
    private DatePicker dtpData;

    private final ObservableList<Receita> listaReceita = FXCollections.observableArrayList();
    private final ReceitaDAO receitaDAO = new ReceitaDAO();

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
                            setText(receitas.getRctCodigo().toString());
                            setText(receitas.getRctNome());
                            //                           setText(receitas.get); // Proporcao
                            setText(receitas.getRctDataCadastro().toString());
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
