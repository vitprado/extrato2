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
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class UtilManipulacao {

	public boolean CarregaResultadoJson(File arquivo) throws Exception {

		String equipamento;
		int nordens = 0;
		int ntratos = 0;
		String ordemproducao = "";
		String data = null;

		CarregamentoDAO carDAO = new CarregamentoDAO();
		DescarregamentoDAO desDAO = new DescarregamentoDAO();

		// PARA CARREGAR TODOS OS ARRAYS QUE CONTÊM NO ARQUIVO DE JSON
		JsonArray receitaJ, ingredientesJ, pesosrequisitadosJ, pesosrealizadosJ, curraisJ, tratosrequisitadosJ, tratosrealizadosJ, ingredientes_trato;

		InputStream fis;
		try {
			fis = new FileInputStream(arquivo);
			JsonReader reader = Json.createReader(fis);
			JsonObject jsonObject = reader.readObject();

			// Executa para cada equipamento
			for (int i = 0; i < jsonObject.getInt("nequipamentos") ; i++) {

				JsonObject equipamentoObj = jsonObject.getJsonArray("equips").getJsonObject(i);

				// Nome do equipamento
				equipamento = equipamentoObj.getJsonString("equipamento").toString();
				System.out.println("Equipamento: " + equipamento);

				// Quantidade de ordens de producao
				nordens = equipamentoObj.getJsonNumber("nordens").intValue();
				System.out.println("Qtd. Ordens: " + nordens);

				/*
				 * TODO: Verificar consistencia do arquivo comparando a chave "nordens" com o tamanho dos objetos:
				 * receitaJ, ingredientesJ, pesosrequisitadosJ, pesosrealizadosJ, tratosrealizadosJ e tratos requisitadosJ
				 * Todos os objetos devem ter o mesmo tamanho, que deve ser o valor da chave "nordens"
				*/

				// Le cada ordem
				for (int nord = 0; nord < nordens; nord++) {
					
					JsonObject ordemObj = equipamentoObj.getJsonArray("ordens").getJsonObject(nord);

					ordemproducao = ordemObj.getJsonString("ordemproducao").toString();
					System.out.println("Ordem de Produção: " + ordemproducao);
					

					if(carDAO.getOrdensDistinct().contains(ordemproducao)) {
						Alert alert = new Alert(Alert.AlertType.WARNING, "Ordem duplicada\n Deseja importar novamente?\n" + ordemproducao, 
								ButtonType.YES, ButtonType.NO);
						alert.initStyle(StageStyle.UTILITY);
						alert.setTitle("MENSAGEM DO SISTEMA");

						if (alert.showAndWait().get() == ButtonType.NO){
							System.out.println("Pular ordem duplicada");
							continue;
						}
						
					}

					// Pega a data
					data = ordemObj.getString("data");
					System.out.println("Data de Realização: " + data);

					ntratos = ordemObj.getJsonNumber("ntratos").intValue();
					System.out.println("Qtd. de Tratos: " + ntratos + "\n");

					// Pega a lista de receitas da ordem
					receitaJ = ordemObj.getJsonArray("receitas");

					// Ingredientes dos carregamentos
					ingredientesJ = ordemObj.getJsonArray("ingredientes");

					// Peso requisitado
					pesosrequisitadosJ = ordemObj.getJsonArray("pesosrequisitados");

					// Peso carregado
					pesosrealizadosJ = ordemObj.getJsonArray("pesosrealizados");

					// Le cada trato
					for (int ntrt = 0; ntrt < ntratos; ntrt++) {

						System.out.println("Trato: " + (ntrt+1));

						// Receita do trato
						String receita = receitaJ.getString(ntrt);
						System.out.println("Receita: " + receita);

						// Ingredientes do trato
						ingredientes_trato = ingredientesJ.getJsonArray(ntrt);

						for (int ing = 0; ing < ingredientes_trato.size(); ing++) {

							String ingrediente = ingredientes_trato.getString(ing);
							String car_requisitado = pesosrequisitadosJ.getJsonArray(ntrt).getString(ing);
							String car_realizado = pesosrealizadosJ.getJsonArray(ntrt).getString(ing);

							// DEBUG
							// System.out.println("Ingrediente: " + ingrediente + " - Requisitado: " + car_requisitado + " - Realizados: " + car_realizado);

							// Grava carregamento no banco de dados
							Carregamento car = new Carregamento();
							car.setRdcOrdem(ordemproducao);
							car.setRdcNumtrato(ntrt+1);
							car.setRdcEquipamento(equipamento);
							car.setRdcDataJson(data);
							car.setRdcReceita(receita);
							car.setRdcIngrediente(ingrediente);
							car.setRdcPesorequisitado(car_requisitado == null || car_requisitado.isEmpty() ? "0" : car_requisitado);
							car.setRdcPesorealizado(car_realizado == null || car_realizado.isEmpty() ? "0" : car_realizado);
							car.setRdcCodigo(carDAO.getTodosCarregamentos().size());
							carDAO.adicionarCarregamento(car);

						}

						// Lista de currais da ordem
						curraisJ = ordemObj.getJsonArray("currais");

						// Pesos requisitados para descarregamento
						tratosrequisitadosJ = ordemObj.getJsonArray("tratos");

						// Pesos realizados no descarregamento
						tratosrealizadosJ = ordemObj.getJsonArray("tratosrealizados");

						for (int curr = 0; curr < curraisJ.size(); curr++) {

							String curral = curraisJ.getString(curr);
							String des_requisitado = tratosrequisitadosJ.getJsonArray(ntrt).getString(curr);
							String des_realizado = tratosrealizadosJ.getJsonArray(ntrt).getString(curr);

							// DEBUG
							// System.out.println("Curral: " + curral + " - Requisitado: " + des_requisitado + " - Realizado: " + des_realizado);

							// Grava descarregamento no banco de dados
							Descarregamento des = new Descarregamento();
							des.setRdgOrdem(ordemproducao);
							des.setRdgNumtrato(ntrt+1);
							des.setRdgEquipamento(equipamento);
							des.setRdgDatajson(data);
							des.setRdgCurral(curral);
							des.setRdgTratorequisitado(des_requisitado == null || des_requisitado.isEmpty() ? "0" : des_requisitado);
							des.setRdgTratorealizado(des_realizado == null || des_realizado.isEmpty() ? "0" : des_realizado);
							des.setRdgCodigo(desDAO.getTodosDescarregamentos().size());
							desDAO.adicionarDescarregamento(des);

						}

					}
				}
				reader.close();
				fis.close();
			}
			return true;
			
		} catch (IOException ex) {
			Logger.getLogger(UtilManipulacao.class.getName()).log(Level.SEVERE, null, ex);
			return false;
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
}
