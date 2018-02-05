package br.exacta.json.resultado;

import java.util.ArrayList;
import java.util.List;

public class Ordem {

    private Integer ajuste;
    private List<String> currais = new ArrayList<String>();
    private Integer currenttrato;
    private String data;
    private List<List<String>> diferencacarregamento = new ArrayList<List<String>>();
    private List<List<String>> diferencadescarregamento = new ArrayList<List<String>>();
    private List<List<String>> ingredientes = new ArrayList<List<String>>();
    private Integer ncurrais;
    private Integer ntratos;
    private String ordemproducao;
    private Integer ordstatus;
    private List<List<String>> pesosrealizados = new ArrayList<List<String>>();
    private List<List<String>> pesosrequisitados = new ArrayList<List<String>>();
    private List<String> receitas = new ArrayList<String>();
    private List<List<String>> tolerancias = new ArrayList<List<String>>();
    private List<List<String>> tratos = new ArrayList<List<String>>();
    private List<List<String>> tratosrealizados = new ArrayList<List<String>>();
    private List<String> starttimeload = new ArrayList<String>();
    private List<String> finishtimeload = new ArrayList<String>();
    private List<String> starttimedisc = new ArrayList<String>();
    private List<String> finishtimedisc = new ArrayList<String>();
    private List<String> operatorload = new ArrayList<String>();
    private List<String> operatordisc = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Ordem() {
    }

    /**
     *
     * @param finishtimedisc
     * @param ncurrais
     * @param ordemproducao
     * @param finishtimeload
     * @param receitas
     * @param data
     * @param operatorload
     * @param diferencadescarregamento
     * @param currais
     * @param starttimedisc
     * @param ingredientes
     * @param tratos
     * @param pesosrequisitados
     * @param ntratos
     * @param ordstatus
     * @param tratosrealizados
     * @param diferencacarregamento
     * @param tolerancias
     * @param starttimeload
     * @param ajuste
     * @param operatordisc
     * @param pesosrealizados
     * @param currenttrato
     */
    public Ordem(Integer ajuste, List<String> currais, Integer currenttrato, String data, List<List<String>> diferencacarregamento, List<List<String>> diferencadescarregamento, List<List<String>> ingredientes, Integer ncurrais, Integer ntratos, String ordemproducao, Integer ordstatus, List<List<String>> pesosrealizados, List<List<String>> pesosrequisitados, List<String> receitas, List<List<String>> tolerancias, List<List<String>> tratos, List<List<String>> tratosrealizados, List<String> starttimeload, List<String> finishtimeload, List<String> starttimedisc, List<String> finishtimedisc, List<String> operatorload, List<String> operatordisc) {
        super();
        this.ajuste = ajuste;
        this.currais = currais;
        this.currenttrato = currenttrato;
        this.data = data;
        this.diferencacarregamento = diferencacarregamento;
        this.diferencadescarregamento = diferencadescarregamento;
        this.ingredientes = ingredientes;
        this.ncurrais = ncurrais;
        this.ntratos = ntratos;
        this.ordemproducao = ordemproducao;
        this.ordstatus = ordstatus;
        this.pesosrealizados = pesosrealizados;
        this.pesosrequisitados = pesosrequisitados;
        this.receitas = receitas;
        this.tolerancias = tolerancias;
        this.tratos = tratos;
        this.tratosrealizados = tratosrealizados;
        this.starttimeload = starttimeload;
        this.finishtimeload = finishtimeload;
        this.starttimedisc = starttimedisc;
        this.finishtimedisc = finishtimedisc;
        this.operatorload = operatorload;
        this.operatordisc = operatordisc;
    }

    public Integer getAjuste() {
        return ajuste;
    }

    public void setAjuste(Integer ajuste) {
        this.ajuste = ajuste;
    }

    public List<String> getCurrais() {
        return currais;
    }

    public void setCurrais(List<String> currais) {
        this.currais = currais;
    }

    public Integer getCurrenttrato() {
        return currenttrato;
    }

    public void setCurrenttrato(Integer currenttrato) {
        this.currenttrato = currenttrato;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<List<String>> getDiferencacarregamento() {
        return diferencacarregamento;
    }

    public void setDiferencacarregamento(List<List<String>> diferencacarregamento) {
        this.diferencacarregamento = diferencacarregamento;
    }

    public List<List<String>> getDiferencadescarregamento() {
        return diferencadescarregamento;
    }

    public void setDiferencadescarregamento(List<List<String>> diferencadescarregamento) {
        this.diferencadescarregamento = diferencadescarregamento;
    }

    public List<List<String>> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<List<String>> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Integer getNcurrais() {
        return ncurrais;
    }

    public void setNcurrais(Integer ncurrais) {
        this.ncurrais = ncurrais;
    }

    public Integer getNtratos() {
        return ntratos;
    }

    public void setNtratos(Integer ntratos) {
        this.ntratos = ntratos;
    }

    public String getOrdemproducao() {
        return ordemproducao;
    }

    public void setOrdemproducao(String ordemproducao) {
        this.ordemproducao = ordemproducao;
    }

    public Integer getOrdstatus() {
        return ordstatus;
    }

    public void setOrdstatus(Integer ordstatus) {
        this.ordstatus = ordstatus;
    }

    public List<List<String>> getPesosrealizados() {
        return pesosrealizados;
    }

    public void setPesosrealizados(List<List<String>> pesosrealizados) {
        this.pesosrealizados = pesosrealizados;
    }

    public List<List<String>> getPesosrequisitados() {
        return pesosrequisitados;
    }

    public void setPesosrequisitados(List<List<String>> pesosrequisitados) {
        this.pesosrequisitados = pesosrequisitados;
    }

    public List<String> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<String> receitas) {
        this.receitas = receitas;
    }

    public List<List<String>> getTolerancias() {
        return tolerancias;
    }

    public void setTolerancias(List<List<String>> tolerancias) {
        this.tolerancias = tolerancias;
    }

    public List<List<String>> getTratos() {
        return tratos;
    }

    public void setTratos(List<List<String>> tratos) {
        this.tratos = tratos;
    }

    public List<List<String>> getTratosrealizados() {
        return tratosrealizados;
    }

    public void setTratosrealizados(List<List<String>> tratosrealizados) {
        this.tratosrealizados = tratosrealizados;
    }

    public List<String> getStarttimeload() {
        return starttimeload;
    }

    public void setStarttimeload(List<String> starttimeload) {
        this.starttimeload = starttimeload;
    }

    public List<String> getFinishtimeload() {
        return finishtimeload;
    }

    public void setFinishtimeload(List<String> finishtimeload) {
        this.finishtimeload = finishtimeload;
    }

    public List<String> getStarttimedisc() {
        return starttimedisc;
    }

    public void setStarttimedisc(List<String> starttimedisc) {
        this.starttimedisc = starttimedisc;
    }

    public List<String> getFinishtimedisc() {
        return finishtimedisc;
    }

    public void setFinishtimedisc(List<String> finishtimedisc) {
        this.finishtimedisc = finishtimedisc;
    }

    public List<String> getOperatorload() {
        return operatorload;
    }

    public void setOperatorload(List<String> operatorload) {
        this.operatorload = operatorload;
    }

    public List<String> getOperatordisc() {
        return operatordisc;
    }

    public void setOperatordisc(List<String> operatordisc) {
        this.operatordisc = operatordisc;
    }

}
