package br.exacta.dto;

import br.exacta.persistencia.Curral;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class CurralPesoDTO {
    private SimpleStringProperty curral;
    private SimpleStringProperty peso;
    private Integer ittCodigo;
    private Curral curralEntity;

    public CurralPesoDTO(Curral curral, BigDecimal peso) {
        this.curral = new SimpleStringProperty(curral.getCurDescricao());
        this.peso = new SimpleStringProperty(peso.toString());
        this.curralEntity = curral;
    }

    public String getCurral() {
        return curral.get();
    }

    public void setCurral(String curral) {
        this.curral.set(curral);
    }

    public String getPeso() {
        return peso.get();
    }

    public void setPeso(String peso) {
        this.peso.set(peso);
    }

    public Curral getCurralEntity() {
        return curralEntity;
    }

    public Integer getIttCodigo() {
        return ittCodigo;
    }

    public void setIttCodigo(Integer ittCodigo) {
        this.ittCodigo = ittCodigo;
    }
}
