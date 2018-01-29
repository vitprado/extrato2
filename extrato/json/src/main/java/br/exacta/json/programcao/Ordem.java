package br.exacta.json.programcao;

import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.Receita;
import java.util.ArrayList;
import java.util.List;

public class Ordem {

    private String ordemproducao;
    private Integer ntratos;
    private List<Receita> receitas = new ArrayList<>();
    private List<List<Ingredientes>> ingredientes = new ArrayList<>();
    private List<List<String>> pesosrequisitados = new ArrayList<>();
    private List<List<String>> tolerancias = new ArrayList<>();
    private Integer ncurrais;
    private List<Curral> currais = new ArrayList<>();
    private List<List<String>> tratos = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Ordem() {
    }

    /**
     *
     * @param currais
     * @param ncurrais 
     * @param ordemproducao
     * @param ingredientes
     * @param tratos
     * @param pesosrequisitados
     * @param ntratos
     * @param receitas
     * @param tolerancias
     */
    public Ordem(String ordemproducao, Integer ntratos, List<Receita> receitas, List<List<Ingredientes>> ingredientes, List<List<String>> pesosrequisitados, List<List<String>> tolerancias, Integer ncurrais, List<Curral> currais, List<List<String>> tratos) {
        super();
        this.ordemproducao = ordemproducao;
        this.ntratos = ntratos;
        this.receitas = receitas;
        this.ingredientes = ingredientes;
        this.pesosrequisitados = pesosrequisitados;
        this.tolerancias = tolerancias;
        this.ncurrais = ncurrais;
        this.currais = currais;
        this.tratos = tratos;
    }

    public String getOrdemproducao() {
        return ordemproducao;
    }

    public void setOrdemproducao(String ordemproducao) {
        this.ordemproducao = ordemproducao;
    }

    public Integer getNtratos() {
        return ntratos;
    }

    public void setNtratos(Integer ntratos) {
        this.ntratos = ntratos;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<List<Ingredientes>> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<List<Ingredientes>> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<List<String>> getPesosrequisitados() {
        return pesosrequisitados;
    }

    public void setPesosrequisitados(List<List<String>> pesosrequisitados) {
        this.pesosrequisitados = pesosrequisitados;
    }

    public List<List<String>> getTolerancias() {
        return tolerancias;
    }

    public void setTolerancias(List<List<String>> tolerancias) {
        this.tolerancias = tolerancias;
    }

    public Integer getNcurrais() {
        return ncurrais;
    }

    public void setNcurrais(Integer ncurrais) {
        this.ncurrais = ncurrais;
    }

    public List<Curral> getCurrais() {
        return currais;
    }

    public void setCurrais(List<Curral> currais) {
        this.currais = currais;
    }

    public List<List<String>> getTratos() {
        return tratos;
    }

    public void setTratos(List<List<String>> tratos) {
        this.tratos = tratos;
    }
}
