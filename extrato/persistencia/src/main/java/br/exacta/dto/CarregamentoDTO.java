package br.exacta.dto;

import javafx.beans.property.SimpleStringProperty;

public class CarregamentoDTO {

    private SimpleStringProperty ordem;
    private SimpleStringProperty equipamento;
    private SimpleStringProperty dataJson;

    public CarregamentoDTO(String ordem, String equipamento, String dataJson) {
        this.ordem = new SimpleStringProperty(ordem);
        this.equipamento = new SimpleStringProperty(equipamento);
        this.dataJson = new SimpleStringProperty(dataJson);
    }

    public String getOrdem() {
        return ordem.get();
    }

    public SimpleStringProperty ordemProperty() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem.set(ordem);
    }

    public String getEquipamento() {
        return equipamento.get();
    }

    public SimpleStringProperty equipamentoProperty() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento.set(equipamento);
    }

    public String getDataJson() {
        return dataJson.get();
    }

    public SimpleStringProperty dataJsonProperty() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson.set(dataJson);
    }

}

