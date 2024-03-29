/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.dto.CarregamentoDTO;
import br.exacta.dto.CarregamentoDTODAO;
import br.exacta.dto.DescarregamentoDTO;
import br.exacta.dto.DescarregamentoDTODAO;
import br.exacta.json.util.UtilManipulacao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ResultadosController implements Initializable {

    @FXML
    private Button btnImportar;
    @FXML
    private MenuItem btnRelatorioCarregamentoXLS;
    @FXML
    private MenuItem btnRelatorioDescarregamentoXLS;
    @FXML
    private MenuItem btnRelatorioResumoCarregamentoPDF;
    @FXML
    private TextField txtCaminhoArquivo;
    @FXML
    private MenuButton brnResultadosRelatorios;

    // TABELA CARREGAMENTO
    @FXML
    private TableView<CarregamentoDTO> tvCarregamento;
    @FXML
    private TableColumn<CarregamentoDTO, String> colCarOrdem;
    @FXML
    private TableColumn<CarregamentoDTO, String> colCarEquipamento;
    @FXML
    private TableColumn<CarregamentoDTO, String> colCarDataOrdem;
    @FXML
    private TableColumn<CarregamentoDTO, CarregamentoDTO> colCarAcao;

    // TABELA DESCARREGAMENTO
    @FXML
    private TableView<DescarregamentoDTO> tvDescarregamento;
    @FXML
    private TableColumn<DescarregamentoDTO, String> colDesOrdem;
    @FXML
    private TableColumn<DescarregamentoDTO, String> colDesEquipamento;
    @FXML
    private TableColumn<DescarregamentoDTO, String> colDesDataOrdem;
    @FXML
    private TableColumn<DescarregamentoDTO, DescarregamentoDTO> colDesAcao;


    private final CarregamentoDTODAO cDTODAO = new CarregamentoDTODAO();
    private final DescarregamentoDTODAO dDTODAO = new DescarregamentoDTODAO();

    Config config = new Config();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {

        configuracaoTabela();
        carregarTabelaCarregamento();
        carregarTabelaDescarregamento();

        // PARA IMPORTAR ARQUIVO JSON
        btnImportar.setOnAction((ActionEvent event) -> {
            // SEQUENCIA DE PASSOS PARA ABRIR UMA CAIXA DE DIALOGO ESCOLHENDO O ARQUIVO DE EXTENSÃO JSON
            FileChooser escolherArquivo = new FileChooser();
            escolherArquivo.setTitle("Selecione o arquivo JSON");
            escolherArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File arquivo = escolherArquivo.showOpenDialog(null);

            // MOSTRO O CAMINHO QUE ARQUIVO ESTÁ NO TEXTFIELD
            txtCaminhoArquivo.setText(arquivo.toString());

            // MANIPULACAO DO ARQUIVO JSON ESCOLHIDO;
            UtilManipulacao manipula = new UtilManipulacao();
            try {

                if (manipula.CarregaResultadoJson(arquivo)) {
                    config.caixaDialogo(Alert.AlertType.INFORMATION, "Resultados importados com sucesso!");
                    carregarTabelaCarregamento();
                    carregarTabelaDescarregamento();
                } else {
                    config.caixaDialogo(Alert.AlertType.ERROR, "Não foi possível importar o arquivo");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // PARA EXPORTAR PARA XLS e PDF
        btnRelatorioResumoCarregamentoPDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller = new DataEquipamentoEscolhaModalController(
                        DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_PDF);
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });

        btnRelatorioCarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller = new DataEquipamentoEscolhaModalController(
                        DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_XLS);
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });

        btnRelatorioDescarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller = new DataEquipamentoEscolhaModalController(
                        DataEquipamentoEscolhaModalController.DESCARREGAMENTO_ORIGEM_XLS);
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });
    }

    private void configuracaoTabela() {
        colCarOrdem.setCellValueFactory(new PropertyValueFactory<CarregamentoDTO, String>("ordem"));
        colCarEquipamento.setCellValueFactory(new PropertyValueFactory<CarregamentoDTO, String>("equipamento"));
        colCarDataOrdem.setCellValueFactory(new PropertyValueFactory<CarregamentoDTO, String>("dataJson"));
        colCarAcao.setCellFactory(new Callback<TableColumn<CarregamentoDTO, CarregamentoDTO>, TableCell<CarregamentoDTO, CarregamentoDTO>>() {
            @Override
            public TableCell<CarregamentoDTO, CarregamentoDTO> call(TableColumn<CarregamentoDTO, CarregamentoDTO> param) {
                return new BotoesCarregamento();
            }
        });

        colDesOrdem.setCellValueFactory(new PropertyValueFactory<DescarregamentoDTO, String>("ordem"));
        colDesEquipamento.setCellValueFactory(new PropertyValueFactory<DescarregamentoDTO, String>("equipamento"));
        colDesDataOrdem.setCellValueFactory(new PropertyValueFactory<DescarregamentoDTO, String>("dataJson"));
        colDesAcao.setCellFactory(new Callback<TableColumn<DescarregamentoDTO, DescarregamentoDTO>, TableCell<DescarregamentoDTO, DescarregamentoDTO>>() {
            @Override
            public TableCell<DescarregamentoDTO, DescarregamentoDTO> call(TableColumn<DescarregamentoDTO, DescarregamentoDTO> param) {
                return new BotoesDescarregamento();
            }
        });

    }

    private void carregarTabelaCarregamento() {
        if (!tvCarregamento.getItems().isEmpty()) {
            tvCarregamento.getItems().clear();
        }
        tvCarregamento.getItems().addAll(cDTODAO.findCarregamentoDTO());
    }

    private void carregarTabelaDescarregamento() {
        if (!tvDescarregamento.getItems().isEmpty()) {
            tvDescarregamento.getItems().clear();
        }
        tvDescarregamento.getItems().addAll(dDTODAO.findDescarregamentoDTO());
    }

    private class BotoesCarregamento extends TableCell<CarregamentoDTO, CarregamentoDTO> {
        private Button btnExcluir;

        public BotoesCarregamento() {
            btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction((ActionEvent event) -> {
                btnExcluirAction();
            });
        }

        private void btnExcluirAction() {
            try {
                new CarregamentoDAO().removerCarregamento(tvCarregamento.getItems().get(getTableRow().getIndex()).getOrdem());
                tvCarregamento.getItems().remove(getTableRow().getIndex());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(final CarregamentoDTO record, boolean empty) {
            super.updateItem(record, empty);
            if (!empty) {
                btnExcluir.setText("Excluir");
                setGraphic(btnExcluir);
            } else {
                setGraphic(null);
            }
        }
    }

    private class BotoesDescarregamento extends TableCell<DescarregamentoDTO, DescarregamentoDTO> {
        private Button btnExcluir;

        public BotoesDescarregamento() {
            btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction((ActionEvent event) -> {
                btnExcluirAction();
            });
        }

        private void btnExcluirAction() {
            try {
                new DescarregamentoDAO().removerDescarregamento(tvDescarregamento.getItems().get(getTableRow().getIndex()).getOrdem());
                tvDescarregamento.getItems().remove(getTableRow().getIndex());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(final DescarregamentoDTO record, boolean empty) {
            super.updateItem(record, empty);
            if (!empty) {
                btnExcluir.setText("Excluir");
                setGraphic(btnExcluir);
            } else {
                setGraphic(null);
            }
        }
    }
}
