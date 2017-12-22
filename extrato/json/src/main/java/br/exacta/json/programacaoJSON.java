package br.exacta.json;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class programacaoJSON {

    private String equipamento;
    private Integer nordens;
    private List<Ordem> ordens = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public Integer getNordens() {
        return nordens;
    }

    public void setNordens(Integer nordens) {
        this.nordens = nordens;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<Ordem> ordens) {
        this.ordens = ordens;
    }

    /*public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }*/

}
