package br.exacta.dto;

import java.io.Serializable;

public class CarregamentoResumoDTO implements Serializable {

    private String receita;
    private String data;
    private Integer trato;
    private String motorista = "Motorista";
    private String equipamento;
    private String ordem;
    private String descricao;
    private String valorPrevisto;
    private String valorRealizado;

    public CarregamentoResumoDTO(String receita, String data, Integer trato, String equipamento, String ordem,
                                 String descricao, String valorPrevisto, String valorRealizado) {
        this.receita = receita;
        this.data = data;
        this.trato = trato;
        this.equipamento = equipamento;
        this.ordem = ordem;
        this.descricao = descricao;
        this.valorPrevisto = valorPrevisto;
        this.valorRealizado = valorRealizado;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getTrato() {
        return trato;
    }

    public void setTrato(Integer trato) {
        this.trato = trato;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValorPrevisto() {
        return valorPrevisto;
    }

    public void setValorPrevisto(String valorPrevisto) {
        this.valorPrevisto = valorPrevisto;
    }

    public String getValorRealizado() {
        return valorRealizado;
    }

    public void setValorRealizado(String valorRealizado) {
        this.valorRealizado = valorRealizado;
    }
}