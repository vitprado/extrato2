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
public class CurralController implements Initializable {

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
    private ListView<Curral> ltvDados;
    @FXML
    private AnchorPane apDados;
    @FXML
    private TextField txtNome;
    @FXML
    private Text lblNome;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Text lblCodigo;

    private final ObservableList<Curral> listaCurral = FXCollections.observableArrayList();
    private final CurralDAO curralDAO = new CurralDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ltvDados.setItems(listaCurral);
        listaCurral.addAll(curralDAO.getTodosCurrais()); // tenho o resultado de todos os currais
        
        ltvDados.setCellFactory(new Callback<ListView<Curral>, ListCell<Curral>>() {
            @Override
            public ListCell<Curral> call(ListView<Curral> param) {
                ListCell<Curral> listCell;
                
                
                listCell = new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            Curral currais = (Curral) item;
                            setText(currais.getCurCodigo().toString());
                            setText(currais.getCurDescricao());
                            setText(currais.getCurDataCadastro().toString());
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
                    Curral novo = new Curral(getID());
                    novo.setCurDescricao(txtNome.getText());
                    novo.setCurDataCadastro(d.getTime());

                    try {
                        curralDAO.adicionarCurral(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaCurral.add(novo);
                }
            }
        });

        // REMOVER 
        btnRemover.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Curral itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    try {
                        curralDAO.removerCurral(itemSelecionado.getCurCodigo());
                    } catch (Exception ex) {
                        Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaCurral.remove(itemSelecionado);
                }
            }
        });

        // ALTERAR 
        btnAlterar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Calendar d = Calendar.getInstance();

                if (!txtNome.getText().trim().isEmpty()) {
                    Curral novo = new Curral(getID());
                    novo.setCurDescricao(txtNome.getText());
                    novo.setCurDataCadastro(d.getTime());

                    try {
                        curralDAO.editarCurral(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(CurralController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaCurral.add(novo);
                }
            }
        });
    }

    // RETORNO DO ID
    public int getID() {
        List<Curral> todosCurrais = curralDAO.getTodosCurrais();
        int maxID = 0;

        if (!todosCurrais.isEmpty()) {
            for (Curral curral : todosCurrais) {
                if (curral.getCurCodigo()> maxID) {
                    maxID = curral.getCurCodigo();
                }
            }
        }

        return ++maxID;
    }

}
