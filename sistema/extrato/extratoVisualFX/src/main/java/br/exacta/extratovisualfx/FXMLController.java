package br.exacta.extratovisualfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import br.exacta.persistencia.Equipamento;
import br.exacta.dao.EquipamentoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class FXMLController implements Initializable {
    
    private Label label;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtDesc;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnRemover;
    @FXML
    private ListView<Equipamento> lstEquipamentos;
    private final ObservableList<Equipamento> listaEquipamentos = FXCollections.observableArrayList();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // AQUI IRÃO TODOS OS CÓDIGOS DE TESTE INERENTES AS TELAS RELACIONADAS
        
        lstEquipamentos.setItems(listaEquipamentos);
        listaEquipamentos.addAll(equipamentoDAO.getTodosEquipamentos());
        
        lstEquipamentos.setCellFactory(new Callback<ListView<Equipamento>, ListCell<Equipamento>>() {
            @Override
            public ListCell<Equipamento> call(ListView<Equipamento> param) {
                ListCell<Equipamento> listCell = new ListCell();
                return listCell;
            }
        });
    }    
}
