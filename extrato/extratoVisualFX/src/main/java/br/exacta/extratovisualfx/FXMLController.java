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
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.Global;

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
                ListCell<Equipamento> listCell;
                listCell = new ListCell(){
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty); 
                        if(item != null){
                            Equipamento equipamento = (Equipamento) item;
                            setText(equipamento.getEpqPlaca());
                            setText(equipamento.getEqpDescricao());  
                            setText(equipamento.getEqpDataCadastro().toString());
                        }
                        else{
                            setText("");
                        }
                    }
                    
                };
                
                return listCell;
            }
        });
        
        // ADICIONAR EQUIPAMENTO 
        btnAdicionar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(!txtPlaca.getText().trim().isEmpty() && !txtDesc.getText().trim().isEmpty()){
                    Equipamento novoEquipamento = new Equipamento(getID());
                    novoEquipamento.setEpqPlaca(txtPlaca.getText());
                    novoEquipamento.setEqpDescricao(txtDesc.getText());
                    novoEquipamento.setEqpDataCadastro(java.util.Date.from(Instant.now()));
                                        
                    try {
                        equipamentoDAO.adicionarEquipamento(novoEquipamento);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaEquipamentos.add(novoEquipamento);
                }
            }
        });
        
        // REMOVER EQUIPAMENTO
        btnRemover.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Equipamento itemSelecionado = lstEquipamentos.getSelectionModel().getSelectedItem();
                if(itemSelecionado != null){
                    try {
                        equipamentoDAO.removerEquipamento(itemSelecionado.getEqpCodigo());
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listaEquipamentos.remove(itemSelecionado);
                }
            }
        });
        
    }   
    // RETORNO DA ID
    public int getID(){
        List<Equipamento> todosEquipamentos = equipamentoDAO.getTodosEquipamentos();
        int maxID = 0;
        
        if(!todosEquipamentos.isEmpty()){
            for(Equipamento equipamento:todosEquipamentos){
                if(equipamento.getEqpCodigo()>maxID)
                    maxID = equipamento.getEqpCodigo();
            }
        }
        
        return ++maxID;
    }
}