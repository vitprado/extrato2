package br.exacta.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"equipamento", "nordens", "ordens"})
public class ConsultaOrdemDTO {

    @JsonProperty("equipamento")
    private String equipamento;

    @JsonProperty("nordens")
    private int nordens;

    @JsonProperty("ordens")
    private List<OrdemTratosDTO> ordens;

    @JsonIgnore
    private List<Integer> listOrdCodigo;

    @JsonIgnore
    private Integer ordCodigo;

    public ConsultaOrdemDTO() {
        this.ordens = new ArrayList<>();
        this.listOrdCodigo = new ArrayList<>();
    }

    public ConsultaOrdemDTO(String equipamento, Integer nordens) {
        this();
        this.equipamento = equipamento;
        this.nordens = nordens;
    }

    public ConsultaOrdemDTO(String equipamento, Integer nordens, Integer ordCodigo) {
        this();
        this.equipamento = equipamento;
        this.nordens = nordens;
        this.ordCodigo = ordCodigo;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setNordens(int nordens) {
        this.nordens = nordens;
    }

    public int getNordens() {
        return nordens;
    }

    public void setOrdens(List<OrdemTratosDTO> ordens) {
        this.ordens = ordens;
    }

    public List<OrdemTratosDTO> getOrdens() {
        return ordens;
    }

    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public List<Integer> getListOrdCodigo() {
        return listOrdCodigo;
    }

    public void setListOrdCodigo(List<Integer> listOrdCodigo) {
        this.listOrdCodigo = listOrdCodigo;
    }
}