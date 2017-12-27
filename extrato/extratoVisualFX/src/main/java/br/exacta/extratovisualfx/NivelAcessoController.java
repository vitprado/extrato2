/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.NivelAcessoDAO;
import br.exacta.persistencia.NivelAcesso;
import java.net.URL;
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
public class NivelAcessoController implements Initializable {

    @FXML
    private AnchorPane apNivelAcesso;
    @FXML
    private Text lblNivelAcesso;
    @FXML
    private Text lblDescricaoMini;
    @FXML
    private AnchorPane apAcoesSistema;
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
    private AnchorPane apDados;
    @FXML
    private Text lblCodigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Text lblDescricao;
    @FXML
    private TextField txtDescricao;
    @FXML
    private ListView<NivelAcesso> ltvDados;

    private final ObservableList<NivelAcesso> listaNivelAcesso = FXCollections.observableArrayList();
    private final NivelAcessoDAO nivelAcessoDAO = new NivelAcessoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ltvDados.setItems(listaNivelAcesso);
        listaNivelAcesso.addAll(nivelAcessoDAO.getTodosNiveis());

        ltvDados.setCellFactory(new Callback<ListView<NivelAcesso>, ListCell<NivelAcesso>>() {
            @Override
            public ListCell<NivelAcesso> call(ListView<NivelAcesso> param) {
                ListCell<NivelAcesso> listCell;
                listCell = new ListCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            NivelAcesso nivel = (NivelAcesso) item;
                            setText(nivel.getNvaCodigo().toString());
                            setText(nivel.getNvaDescricao());
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
                if (!txtDescricao.getText().trim().isEmpty()) {
                    NivelAcesso novo = new NivelAcesso(getID());
                    novo.setNvaDescricao(txtDescricao.getText());

                    try {
                        nivelAcessoDAO.adicionarNivelAcesso(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaNivelAcesso.add(novo);
                }
            }
        });

        // REMOVER 
        btnRemover.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NivelAcesso itemSelecionado = ltvDados.getSelectionModel().getSelectedItem();
                if (itemSelecionado != null) {
                    try {
                        nivelAcessoDAO.removerNivelAcesso(itemSelecionado.getNvaCodigo());
                    } catch (Exception ex) {
                        Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaNivelAcesso.remove(itemSelecionado);
                }
            }
        });

        // ALTERAR 
        btnAlterar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txtDescricao.getText().trim().isEmpty()) {
                    NivelAcesso novo = new NivelAcesso(getID());
                    novo.setNvaDescricao(txtDescricao.getText());

                    try {
                        nivelAcessoDAO.editarNivelAcesso(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaNivelAcesso.add(novo);
                }
            }
        });
    }

    // RETORNO DO ID
    public int getID() {
        List<NivelAcesso> todosNiveisAcesso = nivelAcessoDAO.getTodosNiveis();
        int maxID = 0;

        if (!todosNiveisAcesso.isEmpty()) {
            for (NivelAcesso equipamento : todosNiveisAcesso) {
                if (equipamento.getNvaCodigo() > maxID) {
                    maxID = equipamento.getNvaCodigo();
                }
            }
        }

        return ++maxID;
    }
}
