/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json.util;

import br.exacta.json.programacao.Ordem;
import br.exacta.json.programacao.ProgramacaoJson;
import br.exacta.json.resultado.Equip;
import br.exacta.json.resultado.ResultadoJson;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import br.exacta.persistencia.Receita;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

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

        // SUBSTITUIR POR UMA CLASSE DE RESULTADOS DO JSON
        ResultadoJson resultJson = new ResultadoJson();

        InputStream fis;
        try {
            fis = new FileInputStream(arquivo);
            try (JsonReader reader = Json.createReader(fis)) {
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
                            receita = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("receitas").toString();
                            System.out.println("Receita: " + receita);

                            // PEGO INGREDIENTES
                            ingredientes = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("ingredientes").toString();
                            System.out.println("CARREGAMENTO");
                            System.out.println("Ingredientes: " + ingredientes);

                            // PESOS REQUISITADOS
                            pesosrequisitados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados").toString();
                            System.out.println("Pesos requisitados: " + pesosrequisitados);

                            // PESOS REALIZADOS
                            pesosrealizados = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrealizados").toString();
                            System.out.println("Pesos realizados: " + pesosrealizados + "\n");

                            // PEGO OS CURRAIS
                            System.out.println("DESCARREGAMENTO");
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
            }
            //System.out.println(jsonObject);
            fis.close();

            System.out.println("\nCerto até aqui!");

        } catch (IOException ex) {
            Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MÉTODO PARA CRIAR O ARQUIVO JSON
    public void CriaProgramacaoJson(ProgramacaoJson ObjProg, Ordem ObjOrdem) {

        String NOME_ARQ = "programacao.json";
        JsonObject ObjJson;
        ObjJson = Json.createObjectBuilder()
                .add("equipamento", ObjProg.getEquipamento()) // Variável/Objeto correspondente ao equipamento
                .add("nordens", ObjProg.getNordens()) // Variável/Objeto correspondente ao contador da quantidade de ordens
                .add("ordens", // Variável/Objeto correspondente ao array de ordens
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("ordemproducao", ObjOrdem.getOrdemproducao()) // Variável/Objeto correspondente a string da ordem de produção
                                        .add("ntratos", ObjOrdem.getNtratos()) // Variável/Objeto correspondente ao contador da quantidade de tratos
                                        .add("receitas", // Variável/Objeto correspondente ao array de receitas
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getReceitas().toArray().toString())
                                                        .build())
                                        .add("ingredientes", // Variável/Objeto correspondente ao array de ingredientes
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getIngredientes().toArray().toString())
                                                        .build())
                                        .add("pesosrequisitados", // Variável/Objeto correspondente ao array de pesos requisitados
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getPesosrequisitados().toArray().toString())
                                                        .build())
                                        .add("tolerancias", // Variável/Objeto correspondente ao array de tolerâncias
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getTolerancias().toArray().toString())
                                                        .build())
                                        .add("ncurrais", ObjOrdem.getNcurrais()) // Variável/Objeto correspondente ao contador de quantidade de currais
                                        .add("currais", // Variável/Objeto correspondente ao array de currais
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getCurrais().toArray().toString())
                                                        .build())
                                        .add("tratos", // Variável/Objeto correspondente ao array de tratos
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getTratos().toArray().toString())
                                                        .build())
                                ))
                .build();

        StringWriter stringGravar = new StringWriter();
        try (JsonWriter objGravar = Json.createWriter(stringGravar)) {
            objGravar.writeObject(ObjJson);
        }
        CriaDiretorioDoEquipamento("C:\\Documents\"", ObjProg.getEquipamento(), NOME_ARQ);
        System.out.println(stringGravar.getBuffer().toString());
    }

    // MÉTODO QUE CRIA DIRETÓRIO DO EQUIPAMENTO NO CAMINHO SOLICITADO
    public File CriaDiretorioDoEquipamento(String CAMINHO, String EQUIPAMENTO, String NOME_ARQ) {
        File diretorio;
        diretorio = new File(CAMINHO + "\\" + EQUIPAMENTO + "\\" + NOME_ARQ);
        diretorio.mkdir();

        return diretorio;
    }

    // MÉTODO QUE GRAVA NO BANCO A PROGRAMAÇÃO
    public void GravaProgramacao(ProgramacaoJson obj) {

        Ordem objOrdem = new Ordem();
        ProgramacaoJson objProg = new ProgramacaoJson();
        Receita objReceita = new Receita();

        objProg.setEquipamento("XYZ1234");
        objProg.setNordens(2);

        objOrdem.setOrdemproducao("2018-0001");
        objOrdem.setNtratos(2);

        objReceita.setRctCodigo(1);
        objReceita.setRctCodigo(2);
        objOrdem.setReceitas((List<Receita>) objReceita);

        objProg.setOrdens((List<Ordem>) objOrdem);

        // Método que faz com que crie o json, em caminho já definido e com o nome do equipamento
        CriaProgramacaoJson(objProg, objOrdem);

        // Grava Tudo no Banco
    }

    // MÉTODO QUE GRAVA OS RESULTADOS DO JSON NO BANCO
    public void GravaJsonResultado(ResultadoJson obj) {

        // Classes de Carregamento e Descarregamento
        Carregamento carr = new Carregamento();
        Descarregamento desc = new Descarregamento();

    }

}
