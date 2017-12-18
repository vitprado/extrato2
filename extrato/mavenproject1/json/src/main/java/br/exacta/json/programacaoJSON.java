/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json;

import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Equipamento;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.OrdemProcucao;
import br.exacta.persistencia.Receita;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Thales
 */
public class programacaoJSON {
    
    private Equipamento equipamento;
    private int nordens;
    private List<OrdemProcucao> listaOrdens; 
    private List<Receita> listaReceitas;
    private List<Ingredientes> listaIngredientes;
    private List<Curral> listaCurrais;
    
    public programacaoJSON(){
        listaOrdens = new ArrayList<OrdemProcucao>();
    }
    
    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public int getNordens() {
        return nordens;
    }

    public void setNordens(int nordens) {
        this.nordens = nordens;
    }

    public List<OrdemProcucao> getListaOrdens() {
        return listaOrdens;
    }

    public void setListaOrdens(List<OrdemProcucao> listaOrdens) {
        this.listaOrdens = listaOrdens;
    }

    public List<Receita> getListaReceitas() {
        return listaReceitas;
    }

    public void setListaReceitas(List<Receita> listaReceitas) {
        this.listaReceitas = listaReceitas;
    }

    public List<Ingredientes> getListaIngredientes() {
        return listaIngredientes;
    }

    public void setListaIngredientes(List<Ingredientes> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    public List<Curral> getListaCurrais() {
        return listaCurrais;
    }

    public void setListaCurrais(List<Curral> listaCurrais) {
        this.listaCurrais = listaCurrais;
    }
    /*

            {
                "equipamento":"DUK7930",
                "nordens":1,
                "ordens":
                [
                        {
                                "ordemproducao":"2017-00002",
                                "ntratos":4,
                                "receitas":["RECEITA AP","RECEITA AP","RECEITA AP","RECEITA AP"],
                                "ingredientes":[["SAL","AGUA","BAGAÇO","SILAGEM"],
                                                ["SAL","AGUA","BAGAÇO","SILAGEM"],
                                                ["SAL","AGUA","BAGAÇO","SILAGEM"],
                                                ["SAL","AGUA","BAGAÇO","SILAGEM"]
                                               ],
                                "pesosrequisitados":[["225","225","225","225"],
                                                     ["225","225","225","225"],
                                                     ["225","225","225","225"],
                                                     ["225","225","225","225"]
                                                    ],
                                "tolerancias":[["23","23","23","23"],
                                               ["23","23","23","23"],
                                               ["23","23","23","23"],
                                               ["23","23","23","23"]
                                              ],
                                "ncurrais":4,
                                "currais":["C01","C02","C03","C04"],
                                "tratos":[["500","100","200","100"],
                                          ["100","500","100","200"],
                                          ["500","100","200","100"],
                                          ["100","500","100","200"]
                                         ]
                        }
            ]
        }
    
    
    
        {
            "equipamento":"DUK7930",
            "nordens":3,
            "ordens":
            [
              {
                "ordemproducao":"2016-00001",
                "ntratos":3,
                "receitas":["RECEPÇÃO_A", "ADAPTAÇÃO 3", "CONFINAMENTO_B"],
                "ingredientes":
                [
                ["BAGAÇO","SILAGEM","MILHO UM.","NUCLEO","ÁGUA","SAL"],
                ["MILHO","NUCLEO 3","BAGAÇO","PREMIX","ÁGUA"],
                ["SILAGEM","PREMIX","NÚCLE","AMYLSTEEP"]
                ],
                "pesosrequisitados":
                [
                ["207","113","113","113","244","150"],
                ["190","240","200","0","250"],
                ["207","113","113","113"]
                ],
                "tolerancias":
                [
                ["62","34","34","34","73","45"],
                ["50","40","30","0","83"],
                ["62","34","34","34"]
                ],
                "ncurrais":6,
                "currais":["A02","A04","A07","B01","C02","C07"],
                "tratos":
                [
                  ["120","135","95","80","140","120"],
                  ["200","300","130","210","100","140"],
                  ["150","250","160","180","140","80"]
                  ]
              },
              {
                "ordemproducao":"2016-00021",
                "ntratos":3,
                "receitas":["RECEPÇÃO_B", "ADAPTAÇÃO 4", "CONFINAMENTO_C"],
                "ingredientes":
                [
                ["BAGAÇO","SILAGEM","MILHO UM.","NUCLEO","ÁGUA","SAL"],
                ["MILHO","NUCLEO 3","BAGAÇO","PREMIX","ÁGUA"],
                ["SILAGEM","PREMIX","NÚCLE","AMYLSTEEP"]
                ],
                "pesosrequisitados":
                [
                ["207","113","113","113","244","150"],
                ["190","240","200","0","250"],
                ["207","113","113","113"]
                ],
                "tolerancias":
                [
                ["62","34","34","34","73","45"],
                ["50","40","30","0","83"],
                ["62","34","34","34"]
                ],
                "ncurrais":6,
                "currais":["A02","A04","A07","B01","C02","C07"],
                "tratos":
                [
                  ["120","135","95","80","140","120"],
                  ["200","300","130","210","100","140"],
                  ["150","250","160","180","140","80"]
                  ]
              },
              {
                "ordemproducao":"2016-00011",
                "ntratos":4,
                "receitas":["RECEPÇÃO_C", "ADAPTAÇÃO 5", "CONFINAMENTO_D", "PASTO_FUNDÃO"],
                "ingredientes":
                [
                ["BAGAÇO","SILAGEM","MILHO UM.","NUCLEO","ÁGUA","SAL"],
                ["MILHO","NUCLEO 3","BAGAÇO","PREMIX","ÁGUA"],
                ["SILAGEM","PREMIX","NÚCLE","AMYLSTEEP"],
                ["NUCLEO","MILHO","NÚCLE"]
                ],
                "pesosrequisitados":
                [
                ["207","113","113","113","244","150"],
                ["190","240","200","0","250"],
                ["207","113","113","113"],
                ["207","113","113"]
                ],
                "tolerancias":
                [
                ["62","34","34","34","73","45"],
                ["50","40","30","0","83"],
                ["62","34","34","34"],
                ["62","34","34"]
                ],
                "ncurrais":6,
                "currais":["A02","A04","A07","B01","C02","C07"],
                "tratos":
                [
                  ["120","135","95","80","140","120"],
                  ["200","300","130","210","100","140"],
                  ["150","250","160","180","140","80"],
                  ["230","120","80","90","120","180"]
                ]
              }
            ]

    
    */
}
