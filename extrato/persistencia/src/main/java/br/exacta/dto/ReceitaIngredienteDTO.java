package br.exacta.dto;

public class ReceitaIngredienteDTO {

    private String receita;

    private String ingrediente;

    private Integer tolerancia;

    private Integer proporcao;

    public ReceitaIngredienteDTO() {
    }

    public ReceitaIngredienteDTO(String receita, String ingrediente, Integer tolerancia, Integer proporcao) {
        this();
        this.receita = receita;
        this.ingrediente = ingrediente;
        this.tolerancia = tolerancia;
        this.proporcao = proporcao;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Integer getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(Integer tolerancia) {
        this.tolerancia = tolerancia;
    }

    public Integer getProporcao() {
        return proporcao;
    }

    public void setProporcao(Integer proporcao) {
        this.proporcao = proporcao;
    }
}
