package br.exacta.dto;

import br.exacta.persistencia.Ingredientes;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class IngredienteDTO {

    private Ingredientes ingrediente;
    private SimpleStringProperty descricao;
    private SimpleStringProperty abreviacao;
    private SimpleIntegerProperty tolerancia;

    public IngredienteDTO(Ingredientes ingrediente) {
        this.ingrediente = ingrediente;
        this.descricao = new SimpleStringProperty(ingrediente.getIngNome());
        this.abreviacao = new SimpleStringProperty(ingrediente.getIngAbreviacao());
        this.tolerancia = new SimpleIntegerProperty(ingrediente.getIngTolerancia());
    }

    public Ingredientes getIngrediente() {
        return ingrediente;
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

    public void setIngrediente(Ingredientes ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getAbreviacao() {
        return abreviacao.get();
    }

    public SimpleStringProperty abreviacaoProperty() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao.set(abreviacao);
    }

    public int getTolerancia() {
        return tolerancia.get();
    }

    public SimpleIntegerProperty toleranciaProperty() {
        return tolerancia;
    }

    public void setTolerancia(int tolerancia) {
        this.tolerancia.set(tolerancia);
    }
}

