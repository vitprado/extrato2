package br.exacta.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStream fis;
        try {
            String equipamento;
            int nordens = 0;
            int ntratos = 0;
            String receita;
            String currais;
            String ordemproducao = "";
            String pesosrequisitados;
            String pesosrealizados;
            String tratosrequisitados;
            String tratosrealizados;
            String ingredientes;
            String data;
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
                // PEGO O NÚMERO DE ORDENS
                nordens = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonNumber("nordens").intValue();
                System.out.println("Num. Ordens: " + nordens);

                for (int j = 0; j < nordens; j++) { // UTILIZO PARA REPETIR PELA QUANTIDADE DE ORDENS

                    ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonString("ordemproducao").toString();
                    System.out.println("Ordem de Produção: " + ordemproducao);

                    ntratos = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonNumber("ntratos").intValue();
                    System.out.println("Qtde de Tatros: " + ntratos + "\n");

                    for (int k = 0; k < ntratos; k++) {
                        contTrato = k;
                        System.out.println("Tatro: " + (contTrato + 1));

                        // PEGO A DATA
                        data = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getString("data");
                        System.out.println("Data de Realização: " + data);

                        // PEGO RECEITA
                        JsonArray receitaJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("receitas");
                        //System.out.println("Receita: " + receita);
                        for(int l=0;l<receitaJ.size();l++) {
                        	System.out.println("Receita: " + receitaJ.get(i).toString());
                        

	                        // PEGO INGREDIENTES
                        	JsonArray ingredientesJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("ingredientes");
	                        System.out.println("CARREGAMENTO");
	                        System.out.println("Ingredientes: " + ingredientesJ.get(i).toString());
	
	                        // PESOS REQUISITADOS
	                        JsonArray pesosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados");
	                        System.out.println("Pesos requisitados: " + pesosrequisitadosJ.get(i).toString());
	
	                        // PESOS REALIZADOS
	                        JsonArray pesosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrealizados");
	                        System.out.println("Pesos realizados: " + pesosrealizadosJ.get(i).toString() + "\n");
	
	                        // PEGO OS CURRAIS
	                        System.out.println("DESCARREGAMENTO");
	                        JsonArray curraisJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("currais");
	                        System.out.println("Currais: " + curraisJ.get(i).toString());
	
	                        // PESOS DOS TRATOS REQUISITADOS DO DESCARREGAMENTO
	                        JsonArray tratosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratos");
	                        System.out.println("Tratos Requisitados: " + tratosrequisitadosJ.get(i).toString());
	
	                        // PESOS DOS TRATOS REALIZADOS DO DESCARREGAMENTO
	                        JsonArray tratosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratosrealizados");
	                        System.out.println("Tratos Realizados: " + tratosrealizadosJ.get(i).toString());
                        }

                        System.out.println("------------------------------------------------------------------------------------");
                    }
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
