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
import br.exacta.persistencia.Descarregamento;
import br.exacta.relatorio.pdf.RelatorioResumoCarregamentoPDF;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
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
    private MenuItem btnRelatorioResumoCarregamentoPDF;
    @FXML
    private TextField txtCaminhoArquivo;
    @FXML
    private ListView<?> ltvDados;
    @FXML
    private MenuButton brnResultadosRelatorios;

    private final CarregamentoDAO carregamentoDAO = new CarregamentoDAO();
    private final DescarregamentoDAO descarregamentoDAO = new DescarregamentoDAO();

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
        });

        // GRAVAÇÃO NO BANCO DOS DADOS
        btnSalvar.setOnAction((ActionEvent event) -> {
            // ACOES DE BANCO
            // TESTE AO GRAVAR
            if (!txtCaminhoArquivo.getText().trim().equals("")) {
                Carregamento novoCarregamento = new Carregamento();
                Descarregamento novoDescarregamento = new Descarregamento();

                //                // PARA O CARREGAMENTO
                //                novoCarregamento.setRdcCodigo(3);
                //                novoCarregamento.setRdcEquipamento("ADS1234");
                //                novoCarregamento.setRdcOrdem("2017-0001");
                //                novoCarregamento.setRdcNumtrato(1);
                //                novoCarregamento.setRdcIngrediente("MIL");
                //                novoCarregamento.setRdcPesorequisitado("200");
                //                novoCarregamento.setRdcPesorealizado("200");
                //                novoCarregamento.setRdcDataJson("07/12/2017");
                //
                //                // PARA O DESCARREGAMENTO
                //                novoDescarregamento.setRdgCodigo(3);
                //                novoDescarregamento.setRdgEquipamento("ZZZ1234");
                //                novoDescarregamento.setRdgOrdem("2017-0001");
                //                novoDescarregamento.setRdgNumtrato(1);
                //                novoDescarregamento.setRdgCurral("C03");
                //                novoDescarregamento.setRdgTratorequisitado("200");
                //                novoDescarregamento.setRdgTratorealizado("200");
                //                novoDescarregamento.setRdgDataJson("07/12/2017");
                try {
                    carregamentoDAO.adicionarCarregamento(novoCarregamento);
                    descarregamentoDAO.adicionarDescarregamento(novoDescarregamento);
                    Config.caixaDialogo(Alert.AlertType.CONFIRMATION, "O Resultado foi salvo com sucesso no banco!");
                } catch (Exception ex) {
                    Config.caixaDialogo(Alert.AlertType.ERROR, "Lamentamos, houve problemas ao gravar em sua base de dados!"
                            + "Entre em contato com nosso suporte!");
                    Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Config.caixaDialogo(Alert.AlertType.WARNING, "Por favor, importe primeiro um arquivo de extensão JSON para obter resultados e salvá-lo!");
            }

        });
        // PARA EXPORTAR PARA CSV
        // PARA EXPORTAR PARA CSV
        // CONTINUA, VOU ADICIONANDO MAIS COISAS AINDA

        btnRelatorioResumoCarregamentoPDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller
                        = new DataEquipamentoEscolhaModalController(DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_PDF);
                Config config = new Config();
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });

        btnRelatorioCarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller
                        = new DataEquipamentoEscolhaModalController(DataEquipamentoEscolhaModalController.CARREGAMENTO_ORIGEM_XLS);
                Config config = new Config();
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });

        btnRelatorioDescarregamentoXLS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataEquipamentoEscolhaModalController controller
                        = new DataEquipamentoEscolhaModalController(DataEquipamentoEscolhaModalController.DESCARREGAMENTO_ORIGEM_XLS);
                config.carregarAnchorPaneDialog("DataEquipamentoEscolhaModal", controller);
            }
        });
    }
}
