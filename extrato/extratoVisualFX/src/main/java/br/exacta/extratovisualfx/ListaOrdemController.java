package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.EquipamentoDAO;
import br.exacta.dao.OrdemProcucaoDAO;
import br.exacta.dto.*;
import br.exacta.persistencia.Equipamento;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ListaOrdemController implements Initializable {

    @FXML
    private ChoiceBox<Equipamento> cbbEquipamento;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnNova;
    @FXML
    private TableView<EquipamentoConsultaOrdemDTO> tvOrdens;
    @FXML
    private TableColumn<EquipamentoConsultaOrdemDTO, String> colEquipamento;
    @FXML
    private TableColumn<EquipamentoConsultaOrdemDTO, String> colOrdem;
    @FXML
    private TableColumn<EquipamentoConsultaOrdemDTO, String> colReceita;
    @FXML
    private TableColumn<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO> colBotoes;
    @FXML
    private AnchorPane apDados;

    private Config config = new Config();

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final ObservableList<Equipamento> observableEquipamentos = observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        componentes();
        configurarTabela();

        btnNova.setOnAction((ActionEvent event) -> {
            OrdemController ordem = new OrdemController();
            Config config = new Config();
            config.carregarAnchorPaneDialog("Ordem", ordem);
        });

        btnPesquisar.setOnAction((ActionEvent event) -> {
            pesquisarAction();
        });
    }

    public void pesquisarAction() {
        ConsultaOrdemFilter consultaOrdemFilter = new ConsultaOrdemFilter();
        consultaOrdemFilter.setEquipamento(cbbEquipamento.getValue());
        ConsultaOrdemDTO consultaOrdemDTO = new ConsultaOrdemDTODAO().findByFilter(consultaOrdemFilter);
        List<EquipamentoConsultaOrdemDTO> listEquipamentoConsultaOrdem = new ArrayList<>();

        Integer countOrdem = 0;
        for (OrdemTratosDTO ordemTratosDTO : consultaOrdemDTO.getOrdens()) {
            listEquipamentoConsultaOrdem.add(
                    new EquipamentoConsultaOrdemDTO(
                            consultaOrdemDTO.getEquipamento(),
                            ordemTratosDTO.getOrdemproducao(),
                            ordemTratosDTO.getReceitas(),
                            consultaOrdemDTO.getListOrdCodigo().get(countOrdem)));
            countOrdem++;
        }

        tvOrdens.setItems(observableArrayList(listEquipamentoConsultaOrdem));
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
        colEquipamento.setCellValueFactory(new PropertyValueFactory<EquipamentoConsultaOrdemDTO, String>("equipamento"));
        colOrdem.setCellValueFactory(new PropertyValueFactory<EquipamentoConsultaOrdemDTO, String>("ordem"));
        colReceita.setCellValueFactory(new PropertyValueFactory<EquipamentoConsultaOrdemDTO, String>("receita"));
        colBotoes.setCellFactory(new Callback<TableColumn<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO>, TableCell<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO>>() {
            @Override
            public TableCell<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO> call(TableColumn<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO> colBotoes) {
                return new Botoes();
            }
        });
    }

    private class Botoes extends TableCell<EquipamentoConsultaOrdemDTO, EquipamentoConsultaOrdemDTO> {
        private Button btnEditar;
        private Button btnExcluir;
        private OrdemController ordem;

        public Botoes() {
            btnEditar = new Button("Editar");
            btnEditar.setOnAction((ActionEvent event) -> {
                btnEditarAction();
            });
            btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction((ActionEvent event) -> {
                btnExcluirAction();
            });
        }

        private void btnEditarAction() {
            ordem = new OrdemController(ListaOrdemController.this, tvOrdens.getItems().get(getTableRow().getIndex()).getOrdCodigo());
            Config config = new Config();
            config.carregarAnchorPaneDialog("Ordem", ordem);
        }

        private void btnExcluirAction() {
            try {
                Integer ordCodigo = tvOrdens.getItems().get(getTableRow().getIndex()).getOrdCodigo();
                new OrdemProcucaoDAO().removerOrdemProcucao(ordCodigo);
                pesquisarAction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(final EquipamentoConsultaOrdemDTO record, boolean empty) {
            super.updateItem(record, empty);
            if (!empty) {
                btnEditar.setText("Editar");
                btnExcluir.setText("Excluir");
                Text espaco = new Text(" ");
                HBox pane = new HBox(btnEditar, espaco, btnExcluir);
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

}
