/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json.util;

import br.exacta.json.programcao.ProgramacaoJson;
import br.exacta.json.resultado.Equip;
import br.exacta.json.resultado.ResultadoJson;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Thales
 */
public class UtilManipulacao {

    public void CarregaResultadoJson(File arquivo) {

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
        String data = "";
        int contTrato = 0;

        // SUBSTITUIR POR UMA CLASSE DE RESULTADOS DO JSON
        ResultadoJson resultJson = new ResultadoJson();

        InputStream fis;
        try {
            fis = new FileInputStream(arquivo);
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
                        contTrato = k + 1;
                        System.out.println("Tatro: " + (contTrato));

                        // PEGO A DATA
                        data = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("data").toString();
                        System.out.println("Data de Realização: " + data);

                        // PEGO INGREDIENTES
                        ingredientes = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("ingredientes").toString();
                        System.out.println("Ingredientes: " + ingredientes);

                        // PESOS REQUISITADOS
                        pesosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados").toString();
                        System.out.println("Pesos requisitados: " + pesosrequisitados);

                        // PESOS REALIZADOS
                        pesosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrealizados").toString();
                        System.out.println("Pesos realizados: " + pesosrealizados + "\n");

                        // PEGO OS CURRAIS
                        currais = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("currais").toString();
                        System.out.println("Currais: " + currais);

                        // PESOS DOS TRATOS REQUISITADOS DO DESCARREGAMENTO
                        tratosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratos").toString();
                        System.out.println("Tratos Requisitados: " + tratosrequisitados);

                        // PESOS DOS TRATOS REALIZADOS DO DESCARREGAMENTO
                        tratosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("tratosrealizados").toString();
                        System.out.println("Tratos Realizados: " + tratosrealizados);

                        System.out.println("------------------------------------------------------------------------------------");
                    }
                }
            }
            reader.close();
            fis.close();

            // Classe de Resultado
            //resultJson.setEquips(new Equip(equipamento, nordens, ordens));
            //GravaJsonResultado(resultJson);
            System.out.println("\nCerto até aqui!");

        } catch (IOException ex) {
            Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MÉTODO PARA CRIAR O ARQUIVO JSON
    public void CriaProgramacaoJson() {

    }

    // MÉTODO QUE CRIA DIRETÓRIO DO EQUIPAMENTO NO CAMINHO SOLICITADO
    public File CriaDiretorioDoEquipamento(String caminho, String equipamento) {
        File diretorio;
        diretorio = new File(caminho + "\\" + equipamento);
        diretorio.mkdir();
        
        return diretorio;
    }

    // MÉTODO QUE GRAVA NO BANCO A PROGRAMAÇÃO
    public void GravaProgramacao(ProgramacaoJson obj) {
        
        CriaDiretorioDoEquipamento("caminho", "equipamento");
        CriaProgramacaoJson();
        
        // Grava Tudo no Banco
        
    }

    // MÉTODO QUE GRAVA OS RESULTADOS DO JSON NO BANCO
    public void GravaJsonResultado(ResultadoJson obj) {

        // Classes de Carregamento e Descarregamento
        Carregamento carr = new Carregamento();
        Descarregamento desc = new Descarregamento();

    }

}
