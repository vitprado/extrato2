/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.OrdemProcucaoDAO;
import br.exacta.dto.CarregamentoDTO;
import br.exacta.dto.CarregamentoDTODAO;
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

	private final CarregamentoDTODAO cDTODAO = new CarregamentoDTODAO();

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
		carregarTabela();

		// PARA IMPORTAR ARQUIVO JSON
		btnImportar.setOnAction((ActionEvent event) -> {
			// SEQUENCIA DE PASSOS PARA ABRIR UMA CAIXA DE DIALOGO ESCOLHENDO O ARQUIVO DE
			// EXTENSÃO JSON
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
					carregarTabela();
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
//				Config config = new Config();
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
		colCarDataOrdem.setCellValueFactory(new PropertyValueFactory<CarregamentoDTO, String>("dataOrdem"));
		colCarAcao.setCellFactory(new Callback<TableColumn<CarregamentoDTO, CarregamentoDTO>, TableCell<CarregamentoDTO, CarregamentoDTO>>() {
			@Override
			public TableCell<CarregamentoDTO, CarregamentoDTO> call(TableColumn<CarregamentoDTO, CarregamentoDTO> param) {
				return new Botoes();
			}
		});
	}

	private void carregarTabela() {
		if (!tvCarregamento.getItems().isEmpty()) {
			tvCarregamento.getItems().clear();
		}
		tvCarregamento.getItems().addAll(cDTODAO.findCarregamentoDTO());
	}

	private class Botoes extends TableCell<CarregamentoDTO, CarregamentoDTO> {
		private Button btnExcluir;

		public Botoes() {
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
}
