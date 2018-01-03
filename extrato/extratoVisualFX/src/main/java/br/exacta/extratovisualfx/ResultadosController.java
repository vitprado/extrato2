/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.extratovisualfx;

import br.exacta.json.Equip;
import br.exacta.json.Ordem;
import br.exacta.json.resultadoJSON;
import com.google.gson.stream.JsonReader;
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
import java.util.Iterator;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * FXML Controller class
 *
 * @author Thales
 */
public class ResultadosController implements Initializable {

    @FXML
    private Button btnImportar;

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
                    String equipamento;
                    int nordens = 0;
                    String currais;
                    String ordemproducao = "";
                    String pesosrequisitados;
                    String pesosrealizados;
                    String ingredientes;
                    int contTrato = 0;

                    fis = new FileInputStream(arquivo);
                    javax.json.JsonReader reader = Json.createReader(fis);
                    JsonObject jsonObject = reader.readObject();
                    //System.out.println(jsonObject);

                    for (int i = 0; i < jsonObject.size(); i++) {

                        // PEGO OS EQUIPAMENTOS
                        System.out.println("==================================================================================");
                        equipamento = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonString("equipamento").toString();
                        System.out.println("Equipamento: " + equipamento);
                        System.out.println("\n");
                        // PEGO O NÚMERO DE ORDENS
                        nordens = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonNumber("nordens").intValue();
                        System.out.println("Num. Ordens: " + nordens);

                        System.out.println("Tratos: " + (contTrato = i + 1));

                        for (int j = 0; j < nordens; j++) { // UTILIZO PARA REPETIR PELA QUANTIDADE DE ORDENS

                            ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonString("ordemproducao").toString();
                            System.out.println("Ordem de Produção: " + ordemproducao);

                            // PEGO INGREDIENTES
                            ingredientes = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("ingredientes").toString();
                            System.out.println("Ingredientes: " + ingredientes);

                            // PEGO OS CURRAIS
                            currais = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("currais").toString();
                            System.out.println("Currais: " + currais);

                            // PESOS REQUISITADOS
                            pesosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados").toString();
                            System.out.println("Pesos requisitados: " + pesosrequisitados);

                            // PESOS REALIZADOS
                            pesosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").
                                    getJsonObject(j).getJsonArray("pesosrealizados").toString();
                            System.out.println("Pesos realizados: " + pesosrealizados);
                            System.out.println("\n");

                        }
                    }

                    //nordens = jsonObject.getJsonArray("equips").getJsonObject(0).getJsonNumber("nordens").intValue();                    
                    //ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(1).toString();
                    reader.close();
                    fis.close();

                    System.out.println("\nCerto até aqui!");

                } catch (IOException ex) {
                    Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        // PARA EXPORTAR PARA CSV 
        // SELECIONA O TIPO DE TABELA, RETORNANDO SE É VALOR 1 OU 2 (CARREGAMENTO OU DESCARREGAMENTO)
        
        
        // CONTINUA, VOU ADICIONANDO MAIS COISAS AINDA
    }
}
