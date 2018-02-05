package br.exacta.dto;

import java.io.Serializable;

public class ResumoCarregamentoDTO implements Serializable {

    private String receita;
    private String data;
    private String roteiro = "Roteiro";
    private Integer trato;
    private String motorista = "Motorista";
    private String equipamento;
    private Integer codigo;
    private String descricao;
    private String valorPrevisto;
    private String valorRealizado;

    public ResumoCarregamentoDTO(String receita, String data, Integer trato, String equipamento, Integer codigo, String descricao, String valorPrevisto, String valorRealizado) {
        this.receita = receita;
        this.data = data;
        this.trato = trato;
        this.equipamento = equipamento;
        this.codigo = codigo;
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

    public String getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(String roteiro) {
        this.roteiro = roteiro;
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
