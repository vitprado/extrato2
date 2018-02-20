package br.exacta.dto;

import java.math.BigDecimal;

public class ItemTratoCurralDTO {

    private Integer ncurrais;

    private String curral;

    private BigDecimal peso;

    public ItemTratoCurralDTO() {
    }

    public ItemTratoCurralDTO(Integer ncurrais, String curral, BigDecimal peso) {
        this();
        this.ncurrais = ncurrais;
        this.curral = curral;
        this.peso = peso;
    }

    public Integer getNcurrais() {
        return ncurrais;
    }

    public void setNcurrais(Integer ncurrais) {
        this.ncurrais = ncurrais;
    }

    public String getCurral() {
        return curral;
    }

    public void setCurral(String curral) {
        this.curral = curral;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
}
