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
            	txtAreaJsonCompleta.setText(manipula.VisualizaJsonCompleto(arquivo));
            	manipula.CarregaResultadoJson(arquivo);                
                
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
    
    private void carregaTabelaCarregamento(Carregamento objCarregamento) {
        tbvDadosCarregamento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbvDadosCarregamento.setVisible(true);

        // COLUNAS DA TABLEVIEW
        colOrdem.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Ordem"));
        colEquipamento.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Equipamento"));
        colData.setCellValueFactory(new PropertyValueFactory<CarregamentoFX, String>("Data"));        
    }
}
