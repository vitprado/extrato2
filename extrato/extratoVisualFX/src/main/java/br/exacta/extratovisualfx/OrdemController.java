/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.EquipamentoDAO;
import br.exacta.dao.ReceitaDAO;
import br.exacta.persistencia.Equipamento;
import br.exacta.persistencia.Receita;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class OrdemController implements Initializable {

    @FXML
    private TextField txtOrdem;
    @FXML
    private Button btnCriarListaCurrais;
    @FXML
    private Button btnFinalizarOrdem;
    @FXML
    private ChoiceBox<String> cbbEquipamento;
    @FXML
    private ChoiceBox<String> cbbReceitas;

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final ReceitaDAO receitaDAO = new ReceitaDAO();
    private final ObservableList<String> comboEquipamentos = FXCollections.observableArrayList();
    private final ObservableList<String> comboReceitas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComponentes();
        atualizaListaItensCadastrados();

        btnCriarListaCurrais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String strTela = "ListaCurraisOrdem";
                Config config = new Config();
                config.carregarAnchorPaneDialog(strTela);
            }
        });
    }

    private void carregaComponentes() {

        // CRIA NUMERAÇÃO PARA ORDE
        String ORDEM = criaNroOrdem();
        txtOrdem.setText(ORDEM);

        // CARREGA TODOS OS EQUIPAMENTOS CADASTRADOS
        List<String> equipamentos;
        equipamentos = equipamentoDAO.getNomesEquipamentosDistinct();
        comboEquipamentos.addAll(equipamentos);
        cbbEquipamento.setItems(comboEquipamentos);

        // CARREGA TODAS AS RECEITAS CADASTRADAS
        List<String> receitas;
        receitas = receitaDAO.getNomesEquipamentosDistinct();
        comboReceitas.addAll(receitas);
        cbbReceitas.setItems(comboReceitas);
    }

    private String criaNroOrdem() {
        Calendar c = Calendar.getInstance();
        int ANO = c.get(Calendar.YEAR);
        String NUMERO_ORDEM;
        int i = 0;

        NUMERO_ORDEM = ANO + "-" + (++i);
        // TRATAMENTO PARA INCREMENTAR NO BANCO UMA NOVA ORDEM (UTILZANDO A ANTERIOR)
        return NUMERO_ORDEM;
    }

    private void atualizaListaItensCadastrados() {

    }
}
