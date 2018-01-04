/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.json.Json;
import javax.json.JsonObject;

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
    private TableView<?> tbvCarregamento;
    @FXML
    private TableColumn<?, ?> idSequencial;
    @FXML
    private TableColumn<?, ?> tbcEquipamento;
    @FXML
    private TableColumn<?, ?> tbcOrdem;
    @FXML
    private TableColumn<?, ?> tbcTrato;
    @FXML
    private TableColumn<?, ?> tbcIngrediente;
    @FXML
    private TableColumn<?, ?> tbcPesoRequisitado;
    @FXML
    private TableColumn<?, ?> tbcPesoRealizado;
    @FXML
    private TableView<?> tbvDescarregamento;
    @FXML
    private TableColumn<?, ?> tbdEquipamento;
    @FXML
    private TableColumn<?, ?> tbdOrdem;
    @FXML
    private TableColumn<?, ?> tbdTrato;
    @FXML
    private TableColumn<?, ?> tbdCurral;
    @FXML
    private TableColumn<?, ?> tbdTratoRequisitado;
    @FXML
    private TableColumn<?, ?> tbcRealizado;

    // VARIÁVEIS 
    String equipamento;
    int nordens = 0;
    int ntratos = 0;
    String currais;
    String ordemproducao = "";
    String pesosrequisitados;
    String pesosrealizados;
    String tratosrequisitados;
    String tratosrealizados;
    String ingredientes;
    int contTrato = 0;

    private final CarregamentoDAO carregamentoDAO = new CarregamentoDAO();
    private final DescarregamentoDAO descarregamentoDAO = new DescarregamentoDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // PARA IMPORTAR ARQUIVO JSON
        btnImportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // SEQUENCIA DE PASSOS PARA ABRIR UMA CAIXA DE DIALOGO ESCOLHENDO O ARQUIVO DE EXTENSÃO JSON
                FileChooser escolherArquivo = new FileChooser();
                escolherArquivo.setTitle("Selecione o arquivo JSON");
                escolherArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File arquivo = escolherArquivo.showOpenDialog(null);

                // MANIPULACAO DO ARQUIVO JSON ESCOLHIDO
                InputStream fis;
                try {
                    fis = new FileInputStream(arquivo);
                    javax.json.JsonReader reader = Json.createReader(fis);
                    JsonObject jsonObject = reader.readObject();
                    //System.out.println(jsonObject);

                    for (int i = 0; i < jsonObject.size(); i++) {

                        // PEGO OS EQUIPAMENTOS
                        //System.out.println("==================================================================================");
                        equipamento = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonString("equipamento").toString();
                        //System.out.println("Equipamento: " + equipamento);
                        // PEGO O NÚMERO DE ORDENS
                        nordens = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonNumber("nordens").intValue();
                        //System.out.println("Num. Ordens: " + nordens);

                        for (int j = 0; j < nordens; j++) { // UTILIZO PARA REPETIR PELA QUANTIDADE DE ORDENS

                            ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonString("ordemproducao").toString();
                            //System.out.println("Ordem de Produção: " + ordemproducao);

                            ntratos = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonNumber("ntratos").intValue();
                            //System.out.println("Qtde de Tatros: " + ntratos + "\n");

                            for (int k = 0; k < ntratos; k++) {
                                contTrato = k + 1;
                                //System.out.println("Tatro: " + (contTrato));

                                // PEGO INGREDIENTES
                                ingredientes = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("ingredientes").toString();
                                //System.out.println("Ingredientes: " + ingredientes);

                                // PESOS REQUISITADOS
                                pesosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados").toString();
                                //System.out.println("Pesos requisitados: " + pesosrequisitados);

                                // PESOS REALIZADOS
                                pesosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrealizados").toString();
                                //System.out.println("Pesos realizados: " + pesosrealizados + "\n");

                                // PEGO OS CURRAIS
                                currais = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("currais").toString();
                                //System.out.println("Currais: " + currais);

                                // PESOS DOS TRATOS REQUISITADOS DO DESCARREGAMENTO
                                tratosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratos").toString();
                                //System.out.println("Tratos Requisitados: " + tratosrequisitados);

                                // PESOS DOS TRATOS REALIZADOS DO DESCARREGAMENTO
                                tratosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratosrealizados").toString();
                                //System.out.println("Tratos Realizados: " + tratosrealizados);

                                //System.out.println("------------------------------------------------------------------------------------");
                            }
                        }
                    }
                    reader.close();
                    fis.close();

                    System.out.println("\nCerto até aqui!");

                } catch (IOException ex) {
                    Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                novoCarregamento.setRdcCodigo(2);
                novoCarregamento.setRdcEquipamento("ABC1234");
                novoCarregamento.setRdcOrdem("2017-0001");
                novoCarregamento.setRdcNumtrato(1);
                novoCarregamento.setRdcIngrediente("MIL");
                novoCarregamento.setRdcPesorequisitado("200");
                novoCarregamento.setRdcPesorealizado("200");
                //novoCarregamento.setRdcDatacriacao(new Timestamp(System.currentTimeMillis()));

                // PARA O DESCARREGAMENTO
                novoDescarregamento.setRdgCodigo(2);
                novoDescarregamento.setRdgEquipamento("ABC1234");
                novoDescarregamento.setRdgOrdem("2017-0001");
                novoDescarregamento.setRdgNumtrato(1);
                novoDescarregamento.setRdgCurral("MIL");
                novoDescarregamento.setRdgTratorequisitado("200");
                novoDescarregamento.setRdgTratorealizado("200");
                //novoDescarregamento.setRdgDatacriacao(new Timestamp(System.currentTimeMillis()));
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
        // CONTINUA, VOU ADICIONANDO MAIS COISAS AINDA
    }
}
