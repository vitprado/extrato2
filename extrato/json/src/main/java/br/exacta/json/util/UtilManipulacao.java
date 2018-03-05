/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import br.exacta.dao.CarregamentoDAO;
import br.exacta.dao.DescarregamentoDAO;
import br.exacta.json.programacao.Ordem;
import br.exacta.json.resultado.Equip;
import br.exacta.json.resultado.ResultadoJson;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;

/**
 *
 * @author Thales
 */
public class UtilManipulacao {

	public void JsonResultado(File arquivo) {
		// Objeto
		ResultadoJson objResultado = new ResultadoJson();
		Equip objEquip = new Equip();
		Ordem objOrdem = new Ordem();

		// VARIÁVEIS PARA O JSON
		JsonArray receitasJ, ingredientesJ, curraisJ;
		JsonArray pesosRequisitadosJ, pesosRealizadosJ, tratosRequisitadosJ, tratosRealizadosJ;

		// ACESSO AO ARQUIVO SOLICITADO
		InputStream fis;
		JsonReader reader;
		JsonObject jsonObj;

		// VARIÁVEIS TESTE
		String equipamento;
		int nordens = 0;
		int ntratos = 0;
		String ordemproducao = "";
		String data = null;
		int contTrato = 0;
		String requisitado = null, realizado = null;
		String tratorequisitado = null, tratorealizado = null;
		String ingrediente = null, curral = null, receita = null;

		try {
			fis = new FileInputStream(arquivo);
			reader = Json.createReader(fis);
			jsonObj = reader.readObject();

			// MANIPULAÇÃO DOS ÍNDICES DO ARQUIVOS

		} catch (Exception e) {
			Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, e);
		}
	}

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
				System.out.println("==================================================================================");
				equipamento = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonString("equipamento").toString();
				System.out.println("Equipamento: " + equipamento);

				// PEGO O NÚMERO DE ORDENS
				nordens = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonNumber("nordens").intValue();
				System.out.println("Num. Ordens: " + nordens);

				for (int nord = 0; nord < nordens; nord++) { // UTILIZO PARA REPETIR PELA QUANTIDADE DE ORDENS

					ordemproducao = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonString("ordemproducao").toString();
					System.out.println("Ordem de Produção: " + ordemproducao);

					ntratos = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonNumber("ntratos").intValue();
					System.out.println("Qtde de Tatros: " + ntratos + "\n");

					for (int ntrt = 0; ntrt < ntratos; ntrt++) {
						contTrato = ntrt + 1;
						System.out.println("Tatro: " + contTrato);

						// PEGO A DATA
						data = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getString("data");
						System.out.println("Data de Realização: " + data);

						// PEGO RECEITA
						receitaJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("receitas");
						receita = receitaJ.get(ntrt).toString();
						System.out.println("\nReceita: " + receita);

						// PEGO INGREDIENTES
						System.out.println("\nCARREGAMENTO");
						ingredientesJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("ingredientes");

						for (int ing = 0; ing < ingredientesJ.size(); ing++) {
							ingrediente = ingredientesJ.get(ing).toString();

							// PESOS REQUISITADOS
							pesosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("pesosrequisitados");
							requisitado = pesosrequisitadosJ.get(ing).toString();

							// PESOS REALIZADOS
							pesosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("pesosrealizados");
							realizado = pesosrealizadosJ.get(ing).toString();
									
							System.out.println("Ingrediente: " + ingrediente + " - Peso requisitado: " + requisitado + " - Peso realizados: " + realizado + "\n");
						}

						// PEGO OS CURRAIS
						System.out.println("DESCARREGAMENTO");
						curraisJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("currais");

						for (int curr = 0; curr < curraisJ.size(); curr++) {
							curral = curraisJ.get(curr).toString();
							
							// DESCARREGAMENTO PESOS DOS TRATOS REQUISITADOS DO DESCARREGAMENTO
							tratosrequisitadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("tratos");

							// PESOS DOS TRATOS REALIZADOS DO DESCARREGAMENTO
							tratosrealizadosJ = jsonObject.getJsonArray("equips").getJsonObject(i).getJsonArray("ordens").getJsonObject(nord).getJsonArray("tratosrealizados");

							for (int pesos = 0; pesos < tratosrequisitadosJ.size() && pesos < tratosrealizadosJ.size(); pesos++) {
								tratorequisitado = tratosrequisitadosJ.get(pesos).toString();
																
								tratorealizado = tratosrealizadosJ.get(pesos).toString();
								
								System.out.println("Curral: " + curral + " - " + "Trato Requisitado: " + tratorequisitado + " - Trato Realizado: " + tratorealizado);
							}
						}
						System.out.println("------------------------------------------------------------------------------------");
					}
				}
				reader.close();
				fis.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, ex);
		}
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
}
