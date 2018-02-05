package br.exacta.json.resultado;

import java.util.ArrayList;
import java.util.List;

public class Equip {

    private String equipamento;
    private Integer nordens;
    private List<Ordem> ordens = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Equip() {
    }

    /**
     *
     * @param nordens
     * @param equipamento
     * @param ordens
     */
    public Equip(String equipamento, Integer nordens, List<Ordem> ordens) {
        super();
        this.equipamento = equipamento;
        this.nordens = nordens;
        this.ordens = ordens;
    }

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

}
