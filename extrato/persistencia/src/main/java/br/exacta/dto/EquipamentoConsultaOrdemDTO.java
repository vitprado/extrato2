package br.exacta.dto;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.stream.Collectors;

public class EquipamentoConsultaOrdemDTO {

    private SimpleStringProperty equipamento;
    private SimpleStringProperty ordem;
    private SimpleStringProperty receita;
    private Integer ordCodigo;

    public EquipamentoConsultaOrdemDTO(){}

    public EquipamentoConsultaOrdemDTO(String equipamento, String ordem, List<String> receita, Integer ordCodigo) {
        this();
        this.equipamento = new SimpleStringProperty(equipamento);
        this.ordem = new SimpleStringProperty(ordem);
        this.receita = new SimpleStringProperty(receita.stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.ordCodigo = ordCodigo;
    }

    public String getEquipamento() {
        return equipamento.getValue();
    }

    public SimpleStringProperty equipamentoProperty() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento.set(equipamento);
    }

    public String getOrdem() {
        return ordem.getValue();
    }

    public SimpleStringProperty ordemProperty() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem.set(ordem);
    }

    public String getReceita() {
        return receita.getValue();
    }

    public SimpleStringProperty receitaProperty() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita.set(receita);
    }

    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }
}
