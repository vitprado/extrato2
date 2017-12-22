package br.exacta.json;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ordem {

    private String ordemproducao;
    private Integer ntratos;
    private List<String> receitas = null;
    private List<List<String>> ingredientes = null;
    private List<List<String>> pesosrequisitados = null;
    private List<List<String>> tolerancias = null;
    private Integer ncurrais;
    private List<String> currais = null;
    private List<List<String>> tratos = null;
    //private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getOrdemproducao() {
        return ordemproducao;
    }

    public void setOrdemproducao(String ordemproducao) {
        this.ordemproducao = ordemproducao;
    }

    public Integer getNtratos() {
        return ntratos;
    }

    public void setNtratos(Integer ntratos) {
        this.ntratos = ntratos;
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

    public List<List<String>> getTratos() {
        return tratos;
    }

    public void setTratos(List<List<String>> tratos) {
        this.tratos = tratos;
    }

    /*public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }*/
}
