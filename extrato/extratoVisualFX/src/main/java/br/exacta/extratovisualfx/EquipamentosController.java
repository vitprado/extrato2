/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.EquipamentoDAO;
import br.exacta.dto.EquipamentoDTO;
import br.exacta.persistencia.Equipamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

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
public class EquipamentosController implements Initializable {

    @FXML
    private Button btnRemover;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtCapacidade;
    @FXML
    private TableView<EquipamentoDTO> tvEquipamentos;
    @FXML
    private TableColumn<EquipamentoDTO, String> colDescricao;
    @FXML
    private TableColumn<EquipamentoDTO, Integer> colCapacidade;

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();

        btnSalvar.setOnAction((ActionEvent event) -> {
            btnSalvarAction();
        });

        btnRemover.setOnAction((ActionEvent event) -> {
            btnRemoverAction();
        });
    }

    private void carregaComponentes() {
        List<Equipamento> listEquipamentos = equipamentoDAO.getTodosEquipamentos();
        listEquipamentos.forEach(equipamento -> tvEquipamentos.getItems().add(new EquipamentoDTO(equipamento)));
        configuracaoTabela();
    }

    private void configuracaoTabela() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<EquipamentoDTO, String>("descricao"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<EquipamentoDTO, Integer>("capacidade"));
    }

    private void btnSalvarAction() {
        Calendar d = Calendar.getInstance();

        if (!txtDescricao.getText().trim().isEmpty()
                && !txtCapacidade.getText().trim().isEmpty()) {
            Equipamento novo = new Equipamento();
            novo.setEqpDescricao(txtDescricao.getText());
            novo.setEqpCapacidade(Integer.parseInt(txtCapacidade.getText()));
            novo.setEqpDataCadastro(d.getTime());

            try {
                equipamentoDAO.adicionarEquipamento(novo);
                tvEquipamentos.getItems().add(new EquipamentoDTO(novo));
                txtDescricao.setText("");
                txtCapacidade.setText("");
            } catch (Exception ex) {
                Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void btnRemoverAction() {
        EquipamentoDTO itemSelecionado = tvEquipamentos.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            try {
                equipamentoDAO.removerEquipamento(itemSelecionado.getEquipamento().getEqpCodigo());
                tvEquipamentos.getItems().remove(itemSelecionado);
            } catch (Exception ex) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR, "Este equipamento contem ordem de produção registrata! \n" +
                        "Por favor, remova todas as ordens de produção deste equipamento antes.");
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("MENSAGEM DO SISTEMA");
                alert.showAndWait();
                Logger.getLogger(NivelAcessoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
