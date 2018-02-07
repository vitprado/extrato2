/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json.util;

import java.awt.TextArea;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.json.programacao.Ordem;
import br.exacta.json.programacao.ProgramacaoJson;
import br.exacta.json.resultado.ResultadoJson;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import br.exacta.persistencia.Receita;

/**
 *
 * @author Thales
 */
public class UtilManipulacao {

    public void CarregaResultadoJson(File arquivo) throws Exception {

        // VARIÁVEIS
        String equipamento;
        int nordens = 0;
        int ntratos = 0;
        String ordemproducao = "";
        String data = null;
        int contTrato = 0;
        String requisitado = null, realizado = null;
        String tratorequisitado = null, tratorealizado = null;
        String ingrediente = null, curral = null, receita = null;

        // PARA CARREGAR TODOS OS ARRAYS QUE CONTÊM NO ARQUIVO DE JSON
        JsonArray receitaJ, ingredientesJ, pesosrequisitadosJ, pesosrealizadosJ, curraisJ, tratosrequisitadosJ,
                tratosrealizadosJ;

        Carregamento carreg;
        Descarregamento descarreg;

        InputStream fis;
        try {
            fis = new FileInputStream(arquivo);
            JsonReader reader = Json.createReader(fis);
            JsonObject jsonObject = reader.readObject();

            for (int i = 0; i < jsonObject.size(); i++) {

                // PEGO OS EQUIPAMENTOS
                System.out
                        .println("==================================================================================");
                equipamento = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonString("equipamento")
                        .toString();
                System.out.println("Equipamento: " + equipamento);
                // PEGO O NÚMERO DE ORDENS
                nordens = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonNumber("nordens").intValue();
                System.out.println("Num. Ordens: " + nordens);

                for (int j = 0; j < nordens; j++) { // UTILIZO PARA REPETIR PELA QUANTIDADE DE ORDENS

                    ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                            .getJsonObject(j).getJsonString("ordemproducao").toString();
                    System.out.println("Ordem de Produção: " + ordemproducao);

                    ntratos = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(j)
                            .getJsonNumber("ntratos").intValue();
                    System.out.println("Qtde de Tatros: " + ntratos + "\n");

                    for (int k = 0; k < ntratos; k++) {
                        contTrato = k;
                        System.out.println("Tatro: " + (contTrato + 1));

                        // PEGO A DATA
                        data = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                                .getJsonObject(j).getString("data");
                        System.out.println("Data de Realização: " + data);

                        // PEGO RECEITA
                        receitaJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                                .getJsonObject(j).getJsonArray("receitas");
                        receita = receitaJ.get(i).toString();

                        for (int l = 0; l < receitaJ.size(); l++) {
                            System.out.println("\nReceita: " + receita);

                            // PEGO INGREDIENTES
                            ingredientesJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                                    .getJsonObject(j).getJsonArray("ingredientes");
                            System.out.println("\nCARREGAMENTO");
                            ingrediente = ingredientesJ.get(i).toString();
                            System.out.println("Ingredientes: " + ingrediente);

                            // PESOS REQUISITADOS
                            pesosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i)
                                    .getJsonArray("ordens").getJsonObject(j).getJsonArray("pesosrequisitados");
                            requisitado = pesosrequisitadosJ.get(i).toString();
                            System.out.println("Pesos requisitados: " + requisitado);

                            // PESOS REALIZADOS
                            pesosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                                    .getJsonObject(j).getJsonArray("pesosrealizados");
                            realizado = pesosrealizadosJ.get(i).toString();
                            System.out.println("Pesos realizados: " + realizado + "\n");

                            // PEGO OS CURRAIS
                            System.out.println("DESCARREGAMENTO");
                            curraisJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens")
                                    .getJsonObject(j).getJsonArray("currais");
                            curral = curraisJ.get(i).toString();
                            System.out.println("Currais: " + curral);

                            // PESOS DOS TRATOS REQUISITADOS DO DESCARREGAMENTO
                            tratosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i)
                                    .getJsonArray("ordens").getJsonObject(j).getJsonArray("tratos");
                            tratorequisitado = tratosrequisitadosJ.get(i).toString();
                            System.out.println("Tratos Requisitados: " + tratorequisitado);

                            // PESOS DOS TRATOS REALIZADOS DO DESCARREGAMENTO
                            tratosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i)
                                    .getJsonArray("ordens").getJsonObject(j).getJsonArray("tratosrealizados");
                            tratorealizado = tratosrealizadosJ.get(i).toString();
                            System.out.println("Tratos Realizados: " + tratorealizado);
                        }

                        System.out.println(
                                "------------------------------------------------------------------------------------");
                    }
                }
                // // PARA O CARREGAMENTO
                // carreg = new Carregamento();
                // carreg.setRdcOrdem(ordemproducao);
                // carreg.setRdcEquipamento(equipamento);
                // carreg.setRdcNumtrato(ntratos);
                // carreg.setRdcIngrediente(ingrediente);
                // carreg.setRdcPesorequisitado(requisitado);
                // carreg.setRdcPesorealizado(realizado);
                // //carreg.setR(receita);
                // carreg.setRdcDatajson(data);
                //
                // // PARA O DESCARREGAMENTO
                // descarreg = new Descarregamento();
                // descarreg.setRdgOrdem(ordemproducao);
                // descarreg.setRdgEquipamento(equipamento);
                // descarreg.setRdgNumtrato(ntratos);
                // descarreg.setRdgCurral(curral);
                // descarreg.setRdgTratorequisitado(tratorequisitado);
                // descarreg.setRdgTratorealizado(tratorealizado);
                // descarreg.setRdgDatajson(data);
                // // GRAVA
                // //GravaJsonResultado(carreg, descarreg);
            }
            reader.close();
            fis.close();

        } catch (IOException ex) {
            Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String VisualizaJsonSimplificado(File file) throws Exception {

        FileInputStream fis = new FileInputStream(file);
        JsonReader reader = Json.createReader(fis);
        JsonObject jsonObject = reader.readObject();

        return jsonObject.toString();
    }

    public String VisualizaJsonCompleto(File file) {
        FileInputStream fis = null;
        String texto = "Arquivo JSON de Resultados: \n";
        try {
            fis = new FileInputStream(file);
            int conteudo;
            while ((conteudo = fis.read()) != -1) {
                texto += (char) conteudo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return texto;
    }

    // MÉTODO QUE GRAVA OS RESULTADOS DO JSON NO BANCO
    private void GravaJsonResultado(Carregamento C, Descarregamento D) throws Exception {

        // Classes de Carregamento e Descarregamento
        CarregamentoDAO carDAO = new CarregamentoDAO();
        DescarregamentoDAO desDAO = new DescarregamentoDAO();

        carDAO.adicionarCarregamento(C);
        desDAO.adicionarDescarregamento(D);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MÉTODO QUE CRIA DIRETÓRIO DO EQUIPAMENTO NO CAMINHO SOLICITADO
    private File CriaDiretorioDoEquipamento(String CAMINHO, String EQUIPAMENTO, String NOME_ARQ) {
        File diretorio;
        diretorio = new File(CAMINHO + "\\" + EQUIPAMENTO + "\\" + NOME_ARQ);
        diretorio.mkdir();

        return diretorio;
    }

    // MÉTODO PARA CRIAR O ARQUIVO JSON
    private void CriaProgramacaoJson(ProgramacaoJson ObjProg, Ordem ObjOrdem) {

        String NOME_ARQ = "programacao.json";
        JsonObject ObjJson;
        ObjJson = Json.createObjectBuilder().add("equipamento", ObjProg.getEquipamento()) // Variável/Objeto
                // correspondente ao
                // equipamento
                .add("nordens", ObjProg.getNordens()) // Variável/Objeto correspondente ao contador da quantidade de
                // ordens
                .add("ordens", // Variável/Objeto correspondente ao array de ordens
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder().add("ordemproducao", ObjOrdem.getOrdemproducao()) // Variável/Objeto
                                        // correspondente
                                        // a
                                        // string
                                        // da
                                        // ordem
                                        // de
                                        // produção
                                        .add("ntratos", ObjOrdem.getNtratos()) // Variável/Objeto correspondente ao
                                        // contador da quantidade de tratos
                                        .add("receitas", // Variável/Objeto correspondente ao array de receitas
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getReceitas().toArray().toString()).build())
                                        .add("ingredientes", // Variável/Objeto correspondente ao array de ingredientes
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getIngredientes().toArray().toString()).build())
                                        .add("pesosrequisitados", // Variável/Objeto correspondente ao array de pesos
                                                // requisitados
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getPesosrequisitados().toArray().toString())
                                                        .build())
                                        .add("tolerancias", // Variável/Objeto correspondente ao array de tolerâncias
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getTolerancias().toArray().toString()).build())
                                        .add("ncurrais", ObjOrdem.getNcurrais()) // Variável/Objeto correspondente ao
                                        // contador de quantidade de currais
                                        .add("currais", // Variável/Objeto correspondente ao array de currais
                                                Json.createArrayBuilder()
                                                        .add(ObjOrdem.getCurrais().toArray().toString()).build())
                                        .add("tratos", // Variável/Objeto correspondente ao array de tratos
                                                Json.createArrayBuilder().add(ObjOrdem.getTratos().toArray().toString())
                                                        .build())))
                .build();

        StringWriter stringGravar = new StringWriter();
        try (JsonWriter objGravar = Json.createWriter(stringGravar)) {
            objGravar.writeObject(ObjJson);
        }
        CriaDiretorioDoEquipamento("C:\\Documents\"", ObjProg.getEquipamento(), NOME_ARQ);
        System.out.println(stringGravar.getBuffer().toString());
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

        // Método que faz com que crie o json, em caminho já definido e com o nome do
        // equipamento
        CriaProgramacaoJson(objProg, objOrdem);

        // Grava Tudo no Banco
    }
}
