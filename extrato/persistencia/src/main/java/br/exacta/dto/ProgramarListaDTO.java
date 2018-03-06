package br.exacta.dto;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramarListaDTO{

    private SimpleStringProperty ordem;
    private SimpleStringProperty receita;
    private SimpleStringProperty trato;
    private SimpleStringProperty curral;
    private SimpleBooleanProperty check;
    private OrdemTratosDTO ordemTratosDTO;
    private String equipamento;

    public ProgramarListaDTO() {
    }

    public ProgramarListaDTO(String ordem, List<String> receita, List<List<String>> trato, List<String> curral, String equipamento, OrdemTratosDTO ordemTratosDTO) {
        this();
        this.ordem = new SimpleStringProperty(ordem);
        this.receita = new SimpleStringProperty(receita.stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.trato = new SimpleStringProperty(trato.stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.curral = new SimpleStringProperty(curral.stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.check = new SimpleBooleanProperty(false);
        this.equipamento = equipamento;
        this.ordemTratosDTO = ordemTratosDTO;
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

    public String getReceita() {
        return receita.get();
    }

    public SimpleStringProperty receitaProperty() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita.set(receita);
    }

    public String getTrato() {
        return trato.get();
    }

    public SimpleStringProperty tratoProperty() {
        return trato;
    }

    public void setTrato(String trato) {
        this.trato.set(trato);
    }

    public String getCurral() {
        return curral.get();
    }

    public SimpleStringProperty curralProperty() {
        return curral;
    }

    public void setCurral(String curral) {
        this.curral.set(curral);
    }

    public OrdemTratosDTO getOrdemTratosDTO() {
        return ordemTratosDTO;
    }

    public void setOrdemTratosDTO(OrdemTratosDTO ordemTratosDTO) {
        this.ordemTratosDTO = ordemTratosDTO;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public boolean isCheck() {
        return check.get();
    }

    public SimpleBooleanProperty checkProperty() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check.set(check);
    }
}
