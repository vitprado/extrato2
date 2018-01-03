package br.exacta.json;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

public class Main {

    public static void main(String[] args) throws Exception {
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

            fis = new FileInputStream("../json/src/main/resources/resultados.json");
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
            reader.close();
            fis.close();

            System.out.println("\nCerto até aqui!");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
