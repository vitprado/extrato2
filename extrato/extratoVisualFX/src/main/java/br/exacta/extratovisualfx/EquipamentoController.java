/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.EquipamentoDAO;
import br.exacta.persistencia.Equipamento;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class EquipamentoController implements Initializable {

    @FXML
    private TextField txtPlaca;
    @FXML
    private AnchorPane apAcoes;
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
    private Text lblPlaca;
    @FXML
    private Text lblCodigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Text lblDescricao;
    @FXML
    private Text lblData;
    @FXML
    private DatePicker dtpData;
    private ListView<Equipamento> ltvDados;

    private final ObservableList<Equipamento> listaEquipamento = FXCollections.observableArrayList();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    @FXML
    private TableView<?> tbvDados;
    @FXML
    private TableColumn<?, ?> tbcCodigo;
    @FXML
    private TableColumn<?, ?> tbcPlaca;
    @FXML
    private TableColumn<?, ?> tbcDescricao;
    @FXML
    private TableColumn<?, ?> tbcData;
    
    /**
     * Initializes the controller class.
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
                            setText(equipamento.getEqpCodigo().toString());
                            setText(equipamento.getEpqPlaca());
                            setText(equipamento.getEqpDescricao());
                            setText(equipamento.getEqpDataCadastro().toString());
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
                    Equipamento novo = new Equipamento(getID());
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

        // ALTERAR 
        btnAlterar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Calendar d = Calendar.getInstance();

                if (!txtDescricao.getText().trim().isEmpty()) {
                    Equipamento novo = new Equipamento(getID());
                    novo.setEpqPlaca(txtPlaca.getText());//Placa
                    novo.setEqpDescricao(txtDescricao.getText());
                    novo.setEqpDataCadastro(d.getTime());

                    try {
                        equipamentoDAO.editarEquipamento(novo);
                    } catch (Exception ex) {
                        Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaEquipamento.add(novo);
                }
            }
        });
    }

    // RETORNO DO ID
    public int getID() {
        List<Equipamento> todosNiveisAcesso = equipamentoDAO.getTodosEquipamentos();
        int maxID = 0;

        if (!todosNiveisAcesso.isEmpty()) {
            for (Equipamento equipamento : todosNiveisAcesso) {
                if (equipamento.getEqpCodigo() > maxID) {
                    maxID = equipamento.getEqpCodigo();
                }
            }
        }
        return ++maxID;
    }
}
