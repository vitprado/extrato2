package br.exacta.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"ordemproducao", "ntratos", "receitaingrediente", "receitas", "ingredientes", "pesosrequisitados", "tolerancias", "ncurrais", "currais", "tratos"})
public class OrdemTratosDTO {

    @JsonProperty("ordemproducao")
    private String ordemproducao;

    @JsonProperty("ntratos")
    private Integer ntratos;

    @JsonIgnore
    private Integer trtCodigo;

    @JsonIgnore
    private Integer rctCodigo;

    @JsonIgnore
    private List<Integer> listTrtCodigo;

    @JsonIgnore
    private List<Integer> listRctCodigo;

    @JsonProperty("ncurrais")
    private Integer ncurrais;

    @JsonProperty("currais")
    private List<String> currais;

    @JsonProperty("tratos")
    private List<List<String>> pesos;

    @JsonProperty("receitas")
    private List<String> receitas;

    @JsonProperty("ingredientes")
    private List<List<String>> ingredientes;

    @JsonProperty("pesosrequisitados")
    private List<List<String>> pesosrequisitados;

    @JsonProperty("tolerancias")
    private List<List<String>> tolerancias;

    public OrdemTratosDTO() {
        this.listTrtCodigo = new ArrayList<>();
        this.listRctCodigo = new ArrayList<>();

        currais = new ArrayList<>();
        pesos = new ArrayList<>(new ArrayList<>());

        this.receitas = new ArrayList<>();
        this.ingredientes = new ArrayList<>(new ArrayList<>());
        this.pesosrequisitados = new ArrayList<>(new ArrayList<>());
        this.tolerancias = new ArrayList<>(new ArrayList<>());
    }

    public OrdemTratosDTO(String ordemproducao, int ntratos, int trtCodigo, int rctCodigo) {
        this();
        this.ordemproducao = ordemproducao;
        this.ntratos = ntratos;
        this.trtCodigo = trtCodigo;
        this.rctCodigo = rctCodigo;
    }

    public void setNtratos(int ntratos) {
        this.ntratos = ntratos;
    }

    public int getNtratos() {
        return ntratos;
    }

    public void setOrdemproducao(String ordemproducao) {
        this.ordemproducao = ordemproducao;
    }

    public String getOrdemproducao() {
        return ordemproducao;
    }

    public int getTrtCodigo() {
        return trtCodigo;
    }

    public void setTrtCodigo(int trtCodigo) {
        this.trtCodigo = trtCodigo;
    }

    public int getRctCodigo() {
        return rctCodigo;
    }

    public void setRctCodigo(int rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    public void setNtratos(Integer ntratos) {
        this.ntratos = ntratos;
    }

    public void setTrtCodigo(Integer trtCodigo) {
        this.trtCodigo = trtCodigo;
    }

    public void setRctCodigo(Integer rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    public List<Integer> getListTrtCodigo() {
        return listTrtCodigo;
    }

    public void setListTrtCodigo(List<Integer> listTrtCodigo) {
        this.listTrtCodigo = listTrtCodigo;
    }

    public List<Integer> getListRctCodigo() {
        return listRctCodigo;
    }

    public void setListRctCodigo(List<Integer> listRctCodigo) {
        this.listRctCodigo = listRctCodigo;
    }

    public Integer getNcurrais() {
        return ncurrais;
    }

    public void setNcurrais(Integer ncurrais) {
        this.ncurrais = ncurrais;
    }

    public List<String> getCurrais() {
        return currais;
    }

    public void setCurrais(List<String> currais) {
        this.currais = currais;
    }

    public List<List<String>> getPesos() {
        return pesos;
    }

    public void setPesos(List<List<String>> pesos) {
        this.pesos = pesos;
    }

    public List<String> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<String> receitas) {
        this.receitas = receitas;
    }

    public List<List<String>> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<List<String>> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<List<String>> getPesosrequisitados() {
        return pesosrequisitados;
    }

    public void setPesosrequisitados(List<List<String>> pesosrequisitados) {
        this.pesosrequisitados = pesosrequisitados;
    }

    public List<List<String>> getTolerancias() {
        return tolerancias;
    }

    public void setTolerancias(List<List<String>> tolerancias) {
        this.tolerancias = tolerancias;
    }
}