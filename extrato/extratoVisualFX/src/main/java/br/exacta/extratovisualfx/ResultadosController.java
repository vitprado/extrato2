/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.config.Config;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.json.util.UtilManipulacao;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
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
    private TextField txtCaminhoArquivo;
    @FXML
    private ListView<?> ltvDados;
    @FXML
    private MenuButton brnResultadosRelatorios;

    private final CarregamentoDAO carregamentoDAO = new CarregamentoDAO();
    private final DescarregamentoDAO descarregamentoDAO = new DescarregamentoDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        
        // PARA IMPORTAR ARQUIVO JSON
        btnImportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // SEQUENCIA DE PASSOS PARA ABRIR UMA CAIXA DE DIALOGO ESCOLHENDO O ARQUIVO DE EXTENSÃO JSON
                FileChooser escolherArquivo = new FileChooser();
                escolherArquivo.setTitle("Selecione o arquivo JSON");
                escolherArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File arquivo = escolherArquivo.showOpenDialog(null);

                // MOSTRO O CAMINHO QUE ARQUIVO ESTÁ NO TEXTFIELD
                txtCaminhoArquivo.setText(arquivo.toString());

                // MANIPULACAO DO ARQUIVO JSON ESCOLHIDO;
                UtilManipulacao manipula = new UtilManipulacao();
                manipula.CarregaResultadoJson(arquivo);
                
            }
        });

        // GRAVAÇÃO NO BANCO DOS DADOS
        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // ACOES DE BANCO
                // TESTE AO GRAVAR
                Carregamento novoCarregamento = new Carregamento();
                Descarregamento novoDescarregamento = new Descarregamento();

                // PARA O CARREGAMENTO
                novoCarregamento.setRdcCodigo(3);
                novoCarregamento.setRdcEquipamento("ABC1234");
                novoCarregamento.setRdcOrdem("2017-0001");
                novoCarregamento.setRdcNumtrato(1);
                novoCarregamento.setRdcIngrediente("MIL");
                novoCarregamento.setRdcPesorequisitado("200");
                novoCarregamento.setRdcPesorealizado("200");
                novoCarregamento.setRdcDataJson("07/12/2017");

                // PARA O DESCARREGAMENTO
                novoDescarregamento.setRdgCodigo(3);
                novoDescarregamento.setRdgEquipamento("ZZZ1234");
                novoDescarregamento.setRdgOrdem("2017-0001");
                novoDescarregamento.setRdgNumtrato(1);
                novoDescarregamento.setRdgCurral("C03");
                novoDescarregamento.setRdgTratorequisitado("200");
                novoDescarregamento.setRdgTratorealizado("200");
                novoDescarregamento.setRdgDataJson("07/12/2017");
                try {
                    carregamentoDAO.adicionarCarregamento(novoCarregamento);
                    descarregamentoDAO.adicionarDescarregamento(novoDescarregamento);
                    System.out.println("GRAVOU NO BANCO!");
                } catch (Exception ex) {
                    Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // PARA EXPORTAR PARA CSV
        btnRelatorioCarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller
                        = new DataEquipamentoEscolhaModalController(DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM);
                Config config = new Config();
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });

        btnRelatorioDescarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller
                        = new DataEquipamentoEscolhaModalController(DataEquipamentoEscolhaModalController.DESCARREGAMENTO_ORIGEM);
                Config config = new Config();
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });
    }
}
