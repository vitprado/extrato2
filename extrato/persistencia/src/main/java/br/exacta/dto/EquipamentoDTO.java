package br.exacta.dto;

import br.exacta.persistencia.Equipamento;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EquipamentoDTO {

    private Equipamento equipamento;
    private SimpleStringProperty descricao;
    private SimpleIntegerProperty capacidade;

    public EquipamentoDTO(Equipamento equipamento) {
        this.equipamento = equipamento;
        this.descricao = new SimpleStringProperty(equipamento.getEqpDescricao());
        this.capacidade = new SimpleIntegerProperty(equipamento.getEqpCapacidade());
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public int getCapacidade() {
        return capacidade.get();
    }

    public SimpleIntegerProperty capacidadeProperty() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade.set(capacidade);
    }
}
