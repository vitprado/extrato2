/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.dto.ListaResultadosDTO;
import br.exacta.json.util.UtilManipulacao;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ResultadosController implements Initializable {

	@FXML
	private Button btnImportar;
	@FXML
	private Button btnSalvar;
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

	// PARA OS RESULTADOS
	@FXML
	private TableView<ListaResultadosDTO> tbvDados = new TableView<ListaResultadosDTO>();
	@FXML
	private TableColumn<ListaResultadosDTO, String> colOrdem = new TableColumn<ListaResultadosDTO, String>();
	@FXML
	private TableColumn<ListaResultadosDTO, String> colEquipamento = new TableColumn<ListaResultadosDTO, String>();
	@FXML
	private TableColumn<ListaResultadosDTO, String> colData = new TableColumn<ListaResultadosDTO, String>();
	@FXML
	private TableColumn colAcao = new TableColumn();

	private final ObservableList<Carregamento> listaC = FXCollections.observableArrayList();
	private final CarregamentoDAO cDAO = new CarregamentoDAO();
	private final ObservableList<Descarregamento> listaD = FXCollections.observableArrayList();
	private final DescarregamentoDAO dDAO = new DescarregamentoDAO();

	Config config = new Config();

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(final URL url, final ResourceBundle rb) {
		
		listaC.addAll(cDAO.getTodosCarregamentos());
		carregaTabela();

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
				
				if(manipula.CarregaResultadoJson(arquivo)) {
					Config.caixaDialogo(Alert.AlertType.INFORMATION, "Resultados importados com sucesso!");
				} else {
					Config.caixaDialogo(Alert.AlertType.ERROR, "Não foi possível importar o arquivo");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// PARA EXPORTAR PARA XLS e PDF
		btnRelatorioResumoCarregamentoPDF.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DataEquipamentoEscolhaModalController controller = new DataEquipamentoEscolhaModalController(
						DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_PDF);
				Config config = new Config();
				config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
			}
		});

		btnRelatorioCarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DataEquipamentoEscolhaModalController controller = new DataEquipamentoEscolhaModalController(
						DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_XLS);
				Config config = new Config();
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

	private void carregaTabela() {
		
		// COLUNAS DA TABLEVIEW
		colOrdem.setCellValueFactory(new PropertyValueFactory<ListaResultadosDTO, String>("Ordem"));
		colEquipamento.setCellValueFactory(new PropertyValueFactory<ListaResultadosDTO, String>("Equipamento"));
		colData.setCellValueFactory(new PropertyValueFactory<ListaResultadosDTO, String>("Data"));        
	}
}
