/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.dto.ResumoCarregamentoDTO;
import br.exacta.dto.ResumoCarregamentoDTODAO;
import br.exacta.json.util.UtilManipulacao;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.CarregamentoFX;
import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Descarregamento;
import br.exacta.relatorio.pdf.RelatorioResumoCarregamentoPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML
    private TextArea txtAreaJsonSimples;
    @FXML
    private TextArea txtAreaJsonCompleta;
    
    // PARA O CARREGAMENTO
    @FXML
    private TableView<CarregamentoFX> tbvDadosCarregamento = new TableView<CarregamentoFX>();
    @FXML
    private TableColumn<CarregamentoFX, Integer> colCodigo = new TableColumn<CarregamentoFX, Integer>();
    @FXML
    private TableColumn<CarregamentoFX, String> colOrdem = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colEquipamento = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colReceita = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, Integer> colNumTrato = new TableColumn<CarregamentoFX, Integer>();
    @FXML
    private TableColumn<CarregamentoFX, String> colIngrediente = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colPesosRequisitados = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colPesosRealizados = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colData = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn colAcao = new TableColumn();

    // PARA O DESCARREGAMENTO
    @FXML
    private TableView<Descarregamento> tbvDadosDescarregamento = new TableView<Descarregamento>();
    @FXML
    private TableColumn<CarregamentoFX, Integer> colCodigoD = new TableColumn<CarregamentoFX, Integer>();
    @FXML
    private TableColumn<CarregamentoFX, String> colOrdemD = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colEquipamentoD = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, Integer> colNumTratoD = new TableColumn<CarregamentoFX, Integer>();
    @FXML
    private TableColumn<CarregamentoFX, String> colCurral = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colTratosRequisitados = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colTratosRealizados = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn<CarregamentoFX, String> colDataD = new TableColumn<CarregamentoFX, String>();
    @FXML
    private TableColumn colAcaoD = new TableColumn();
    
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

        Carregamento C = new Carregamento();
        Descarregamento D = new Descarregamento();
        
        carregaTabelaCarregamento(C);
        carregaTabelaDescarregamento(D);
        //tbvDadosCarregamento.setItems(listaCarregamentosManual());
        
        //carregaCarregamento();
        //carregaDescarregamento();
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
                manipula.CarregaResultadoJson(arquivo);
                //txtAreaJsonSimples.setText(manipula.VisualizaJsonSimplificado(arquivo));
                txtAreaJsonCompleta.setText(manipula.VisualizaJsonCompleto(arquivo));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // GRAVAÇÃO NO BANCO DOS DADOS
//		btnSalvar.setOnAction((ActionEvent event) -> {
//			// ACOES DE BANCO
//			// TESTE AO GRAVAR
//			if (!txtCaminhoArquivo.getText().trim().equals("")) {
//
//				try {
//					// GRAVA CARREGAMENTO DE DESCARREGAMENTO
//					Config.caixaDialogo(Alert.AlertType.CONFIRMATION, "O Resultado foi salvo com sucesso no banco!");
//				} catch (Exception ex) {
//					Config.caixaDialogo(Alert.AlertType.ERROR,
//							"Lamentamos, houve problemas ao gravar em sua base de dados!"
//									+ "Entre em contato com nosso suporte!");
//					Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
//				}
//			} else {
//				Config.caixaDialogo(Alert.AlertType.WARNING,
//						"Por favor, importe primeiro um arquivo de extensão JSON para obter resultados e salvá-lo!");
//			}
//
//		});
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

    private ObservableList<CarregamentoFX> listaCarregamentosManual() {
        return FXCollections.observableArrayList(
                new CarregamentoFX(1, "2018-0001", "ABC1234", 1, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z1"),
                new CarregamentoFX(2, "2018-0002", "ABC1234", 2, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z1"),
                new CarregamentoFX(3, "2018-0003", "ABC1234", 3, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z1"),
                new CarregamentoFX(4, "2018-0004", "DKG3090", 4, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z3"),
                new CarregamentoFX(5, "2018-0005", "DKG3090", 5, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z3"),
                new CarregamentoFX(6, "2018-0006", "DKG3090", 6, "INGREDIENTE C1", "200", "230", "08/02/2018", "RECEITA Z3")
        );
    }
    
    private void carregaTabelaCarregamento(Carregamento objCarregamento) {
        tbvDadosCarregamento.setItems(listaCarregamentosManual());
        tbvDadosCarregamento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbvDadosCarregamento.setVisible(true);

        // COLUNAS DA TABLEVIEW
        colCodigo.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, Integer>("Código"));
        colOrdem.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Ordem"));
        colEquipamento.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Equipamento"));
        colReceita.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Receita"));
        colNumTrato.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, Integer>("Número de Tratos"));
        colIngrediente.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Ingredientes"));
        colPesosRequisitados.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Pesos Requisitados"));
        colPesosRealizados.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Pesos Realizados"));
        colData.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Data"));        
    }
    
    private void carregaTabelaDescarregamento(Descarregamento objCarregamento) {
        //tbvDadosDescarregamento.setItems(listaCarregamentosManual());
        tbvDadosDescarregamento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbvDadosDescarregamento.setVisible(true);

        // COLUNAS DA TABLEVIEW
        colCodigoD.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, Integer>("Código"));
        colOrdemD.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Ordem"));
        colEquipamentoD.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Equipamento"));
        colNumTratoD.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, Integer>("Número de Tratos"));
        colCurral.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Currais"));
        colTratosRequisitados.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Pesos Requisitados"));
        colTratosRealizados.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Pesos Realizados"));
        colDataD.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Data"));        
    }

//    private void carregaCarregamento() {
//
//        // PARA O CRREGAMENTO
//        listaC.addAll((Carregamento) cDAO.getEquipamentosDistinct()); 
//        ltvDadosC.setItems(listaC);
//
//        ltvDadosC.setCellFactory((ListView<Carregamento> param) -> {
//            ListCell<Carregamento> listCell;
//
//            listCell = new ListCell() {
//                @Override
//                protected void updateItem(Object item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item != null) {
//                        Carregamento carr = (Carregamento) item;
//                        setText(carr.getRdcOrdem());
//                    } else {
//                        setText("");
//                    }
//                }
//            };
//            return listCell;
//        });
//    }
//
//    private void carregaDescarregamento() {
//        // PARA O DESCARRAGAMENTO
//        listaD.addAll(dDAO.getTodosDescarregamentos());
//        ltvDadosD.setItems(listaD);
//
//        ltvDadosD.setCellFactory((ListView<Descarregamento> param) -> {
//            ListCell<Descarregamento> listCell;
//
//            listCell = new ListCell() {
//                @Override
//                protected void updateItem(Object item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item != null) {
//                        Descarregamento desc = (Descarregamento) item;
//                        setText(desc.getRdgOrdem());
//                    } else {
//                        setText("");
//                    }
//                }
//            };
//            return listCell;
//        });
//    }
}
