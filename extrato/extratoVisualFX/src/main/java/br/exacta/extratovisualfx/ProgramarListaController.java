/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.EquipamentoDAO;
import br.exacta.dto.*;
import br.exacta.persistencia.Equipamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ProgramarListaController implements Initializable {

    @FXML
    private Button btnProgramar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnSelecionarTudo;
    @FXML
    private ChoiceBox<Equipamento> cbbEquipamento;
    @FXML
    private TableView<ProgramarListaDTO> tvProgramarLista;
    @FXML
    private TableColumn<ProgramarListaDTO, String> colOrdens;
    @FXML
    private TableColumn<ProgramarListaDTO, String> colReceitas;
    @FXML
    private TableColumn<ProgramarListaDTO, String> colTratos;
    @FXML
    private TableColumn<ProgramarListaDTO, String> colCurrais;
    @FXML
    private TableColumn<ProgramarListaDTO, Boolean> colSelect;

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final ObservableList<Equipamento> observableEquipamentos = observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        componentes();
        configurarTabela();
        pesquisarAction();

        btnPesquisar.setOnAction((ActionEvent event) -> {
            pesquisarAction();
        });

        btnProgramar.setOnAction((ActionEvent event) -> {
            programarAction();
        });
    }

    private void pesquisarAction() {
        ConsultaOrdemFilter consultaOrdemFilter = new ConsultaOrdemFilter();
        consultaOrdemFilter.setEquipamento(cbbEquipamento.getValue());
        List<ProgramarListaDTO> listProgramarListaDTO = new ArrayList<>();
        List<ConsultaOrdemDTO> listConsultaOrdemDTO = new ArrayList<>();

        if (cbbEquipamento.getValue() == null) {
            listConsultaOrdemDTO = new ConsultaOrdemDTODAO().findAll();
        } else {
            listConsultaOrdemDTO.add(new ConsultaOrdemDTODAO().findByFilter(consultaOrdemFilter));
        }

        for (ConsultaOrdemDTO consultaOrdemDTO : listConsultaOrdemDTO) {
            for (OrdemTratosDTO ordemTratosDTO : consultaOrdemDTO.getOrdens()) {
                listProgramarListaDTO.add(
                        new ProgramarListaDTO(
                                ordemTratosDTO.getOrdemproducao(),
                                ordemTratosDTO.getReceitas(),
                                ordemTratosDTO.getPesos(),
                                ordemTratosDTO.getCurrais(),
                                consultaOrdemDTO.getEquipamento(),
                                ordemTratosDTO));
            }
        }
        tvProgramarLista.setItems(observableArrayList(listProgramarListaDTO));
    }

    public void programarAction() {
        List<ConsultaOrdemDTO> listConsultaOrdemDTO = new ArrayList<>();

        for (ProgramarListaDTO programarListaDTO : tvProgramarLista.getItems()) {
            if (programarListaDTO.isCheck()) {
                Boolean novoEquipamento = true;
                for (ConsultaOrdemDTO consultaOrdemDTO : listConsultaOrdemDTO) {
                    if (consultaOrdemDTO.getEquipamento().equals(programarListaDTO.getEquipamento())) {
                        consultaOrdemDTO.setNordens(consultaOrdemDTO.getNordens() + 1);
                        consultaOrdemDTO.getOrdens().add(programarListaDTO.getOrdemTratosDTO());
                        novoEquipamento = false;
                    }
                }
                if (novoEquipamento) {
                    listConsultaOrdemDTO.add(new ConsultaOrdemDTO(programarListaDTO.getEquipamento(), 1, programarListaDTO.getOrdemTratosDTO()));
                }
            }
        }

        if (!listConsultaOrdemDTO.isEmpty()) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new FileOutputStream(file.getPath() + "/programacao.json"), listConsultaOrdemDTO);
                
    			Alert alert;
    			
    			// TODO: Erro ao imprimir o caminho. Aparece "..."
    			alert = new Alert(Alert.AlertType.INFORMATION, "Arquivo salvo em" + file.getPath()");
    			alert.initStyle(StageStyle.UTILITY);
    			alert.setTitle("MENSAGEM DO SISTEMA");
    			alert.showAndWait();
    			
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
			Alert alert;
			alert = new Alert(Alert.AlertType.ERROR, "Selecione pelo menos uma ordem!");
			alert.initStyle(StageStyle.UTILITY);
			alert.setTitle("MENSAGEM DO SISTEMA");
			alert.showAndWait();
        }
    }

    private void componentes() {
        observableEquipamentos.addAll(equipamentoDAO.getTodosEquipamentos());
        cbbEquipamento.setItems(observableEquipamentos);
        cbbEquipamento.setConverter(new StringConverter<Equipamento>() {
            @Override
            public String toString(Equipamento object) {
                return object.getEqpDescricao();
            }

            @Override
            public Equipamento fromString(String string) {
                return observableEquipamentos.stream()
                        .filter(equipamento -> string.equals(equipamento.getEqpDescricao()))
                        .findFirst().get();
            }
        });
    }

    private void configurarTabela() {
        colOrdens.setCellValueFactory(new PropertyValueFactory<ProgramarListaDTO, String>("ordem"));
        colReceitas.setCellValueFactory(new PropertyValueFactory<ProgramarListaDTO, String>("receita"));
        colTratos.setCellValueFactory(new PropertyValueFactory<ProgramarListaDTO, String>("trato"));
        colCurrais.setCellValueFactory(new PropertyValueFactory<ProgramarListaDTO, String>("curral"));
        colSelect.setCellValueFactory(new PropertyValueFactory<ProgramarListaDTO, Boolean>("check"));
        colSelect.setCellFactory(new Callback<TableColumn<ProgramarListaDTO, Boolean>, TableCell<ProgramarListaDTO, Boolean>>() {
            @Override
            public TableCell<ProgramarListaDTO, Boolean> call(TableColumn<ProgramarListaDTO, Boolean> param) {
                return new BooleanCell();
            }
        });

        // Header CheckBox
        CheckBox cb = new CheckBox();
        cb.setUserData(this.colSelect);
        cb.setOnAction(handleSelectAllCheckbox());
        this.colSelect.setGraphic(cb);
    }

    private EventHandler<ActionEvent> handleSelectAllCheckbox() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckBox cb = (CheckBox) event.getSource();
                TableColumn column = (TableColumn) cb.getUserData();
                if (cb.isSelected()) {
                    for (ProgramarListaDTO programarListaDTO : tvProgramarLista.getItems()) {
                        programarListaDTO.setCheck(Boolean.TRUE);
                    }
                } else {
                    for (ProgramarListaDTO programarListaDTO : tvProgramarLista.getItems()) {
                        programarListaDTO.setCheck(Boolean.FALSE);
                    }
                }


            }
        };
    }

    private class BooleanCell extends TableCell<ProgramarListaDTO, Boolean> {

        private CheckBox checkBox;

        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(false);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    tvProgramarLista.getItems().get(getTableRow().getIndex()).setCheck(newValue == null ? false : newValue);
                }
            });
            this.setEditable(true);
        }

        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                this.setGraphic(checkBox);
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                checkBox.setSelected(item);
            } else {
                setGraphic(null);
            }
        }
    }
}
