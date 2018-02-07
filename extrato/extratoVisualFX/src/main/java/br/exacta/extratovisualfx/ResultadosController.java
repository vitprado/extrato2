/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.CurralDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.dto.ResumoCarregamentoDTO;
import br.exacta.dto.ResumoCarregamentoDTODAO;
import br.exacta.json.util.UtilManipulacao;
import br.exacta.persistencia.Carregamento;
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
    @FXML
    private TableView<Carregamento> tbvDadosCarregamento;
    @FXML
    private TableColumn<Carregamento, Integer> colCodigo;
    @FXML
    private TableColumn<Carregamento, String> colOrdem;
    @FXML
    private TableColumn<Carregamento, String> colEquipamento;
    @FXML
    private TableColumn<Carregamento, String> colReceita;
    @FXML
    private TableColumn<Carregamento, Integer> colNumTrato;
    @FXML
    private TableColumn<Carregamento, String> colIngrediente;
    @FXML
    private TableColumn<Carregamento, String> colPesosRequisitados;
    @FXML
    private TableColumn<Carregamento, String> colPesosRealizados;
    @FXML
    private TableColumn<Carregamento, String> colData;
    
    @FXML
    private TableView<Descarregamento> tbvDadosDescarregamento;

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

        carregaTabelaCarregamento(C);
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
                txtAreaJsonSimples.setText(manipula.VisualizaJsonSimplificado(arquivo));
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
    
    private void carregaTabelaCarregamento(Carregamento objCarregamento){
        
        
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
