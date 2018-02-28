package br.exacta.dto;

public class ListaOrdemProducaoDTO {

    private String eqpDescricao;
    private Integer ordCodigo;
    private String ordDescricao;
    private String rctNome;

    public ListaOrdemProducaoDTO(){}

    public ListaOrdemProducaoDTO(String eqpDescricao, Integer ordCodigo, String ordDescricao, String rctNome) {
        this();
        this.eqpDescricao = eqpDescricao;
        this.ordCodigo = ordCodigo;
        this.ordDescricao = ordDescricao;
        this.rctNome = rctNome;
    }

    public String getEqpDescricao() {
        return eqpDescricao;
    }

    public void setEqpDescricao(String eqpDescricao) {
        this.eqpDescricao = eqpDescricao;
    }

    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public String getOrdDescricao() {
        return ordDescricao;
    }

    public void setOrdDescricao(String ordDescricao) {
        this.ordDescricao = ordDescricao;
    }

    public String getRctNome() {
        return rctNome;
    }

    public void setRctNome(String rctNome) {
        this.rctNome = rctNome;
    }
}
