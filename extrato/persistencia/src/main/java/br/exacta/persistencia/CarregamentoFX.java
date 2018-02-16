/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarregamentoFX {

    private static final long serialVersionUID = 1L;
    
    private SimpleIntegerProperty rdcCodigo;
    private SimpleStringProperty rdcOrdem;
    private SimpleStringProperty rdcEquipamento;
    private SimpleIntegerProperty rdcNumtrato;
    private SimpleStringProperty rdcIngrediente;
    private SimpleStringProperty rdcPesorequisitado;
    private SimpleStringProperty rdcPesorealizado;
    private SimpleStringProperty rdcDatajson;
    private SimpleStringProperty rdcReceita;

    public CarregamentoFX() {
    }

    public CarregamentoFX(SimpleIntegerProperty rdcCodigo) {
        this.rdcCodigo = rdcCodigo;
    }
    
    public CarregamentoFX(int codigo, String ordem, String equipamento, int numtrato, String ingrediente, String pesorequisitado, String pesorealizado, String data, String receita){
        this.rdcCodigo =  new SimpleIntegerProperty(codigo);
        this.rdcOrdem = new SimpleStringProperty(ordem);
        this.rdcEquipamento = new SimpleStringProperty(equipamento);
        this.rdcNumtrato = new SimpleIntegerProperty(numtrato);
        this.rdcIngrediente = new SimpleStringProperty(ingrediente);
        this.rdcPesorequisitado = new SimpleStringProperty(pesorequisitado);
        this.rdcPesorealizado = new SimpleStringProperty(pesorealizado);
        this.rdcDatajson = new SimpleStringProperty(data);
        this.rdcReceita = new SimpleStringProperty(receita);
    }

    public SimpleIntegerProperty getRdcCodigoProperty() {
        return rdcCodigo;
    }

    public void setRdcCodigoProperty(SimpleIntegerProperty rdcCodigo) {
        this.rdcCodigo = rdcCodigo;
    }

    public SimpleStringProperty getRdcOrdemProperty() {
        return rdcOrdem;
    }

    public void setRdcOrdemProperty(SimpleStringProperty rdcOrdem) {
        this.rdcOrdem = rdcOrdem;
    }

    public SimpleStringProperty getRdcEquipamentoProperty() {
        return rdcEquipamento;
    }

    public void setRdcEquipamentoProperty(SimpleStringProperty rdcEquipamento) {
        this.rdcEquipamento = rdcEquipamento;
    }

    public SimpleIntegerProperty getRdcNumtratoProperty() {
        return rdcNumtrato;
    }

    public void setRdcNumtratoProperty(SimpleIntegerProperty rdcNumtrato) {
        this.rdcNumtrato = rdcNumtrato;
    }

    public SimpleStringProperty getRdcIngredienteProperty() {
        return rdcIngrediente;
    }

    public void setRdcIngredienteProperty(SimpleStringProperty rdcIngrediente) {
        this.rdcIngrediente = rdcIngrediente;
    }

    public SimpleStringProperty getRdcPesorequisitadoProperty() {
        return rdcPesorequisitado;
    }

    public void setRdcPesorequisitadoProperty(SimpleStringProperty rdcPesorequisitado) {
        this.rdcPesorequisitado = rdcPesorequisitado;
    }

    public SimpleStringProperty getRdcPesorealizadoProperty() {
        return rdcPesorealizado;
    }

    public void setRdcPesorealizadoProperty(SimpleStringProperty rdcPesorealizado) {
        this.rdcPesorealizado = rdcPesorealizado;
    }

    public SimpleStringProperty getRdcDatajsonProperty() {
        return rdcDatajson;
    }

    public void setRdcDatajsonProperty(SimpleStringProperty rdcDatajson) {
        this.rdcDatajson = rdcDatajson;
    }

    public SimpleStringProperty getRdcReceitaProperty() {
        return rdcReceita;
    }

    public void setRdcReceitaProperty(SimpleStringProperty rdcReceita) {
        this.rdcReceita = rdcReceita;
    }    
}
