package br.exacta.dto;

import br.exacta.persistencia.Curral;
import br.exacta.persistencia.ItemTrato;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.Trato;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TratoDTO {
    private List<CurralPesoDTO> listCurralPeso;
    private Trato trato;
    private SimpleIntegerProperty numero;
    private SimpleStringProperty receita;
    private SimpleStringProperty pesoTotal;

    public TratoDTO(List<CurralPesoDTO> listCurralPeso, Receita receita, Integer numero) {
        this.listCurralPeso = listCurralPeso;
        this.numero = new SimpleIntegerProperty(numero);
        this.trato = new Trato();
        this.receita = new SimpleStringProperty(receita.getRctNome());
        trato.setReceita(receita);
        trato.setTrtNumero(numero);
        List<ItemTrato> tratos = new ArrayList<>();

        listCurralPeso.forEach(curralPesoDTO -> {
            ItemTrato novoItemTrato = new ItemTrato();
            novoItemTrato.setCurral(curralPesoDTO.getCurralEntity());
            novoItemTrato.setIttPeso(new BigDecimal(curralPesoDTO.getPeso()));
            novoItemTrato.setTrato(this.trato);
            tratos.add(novoItemTrato);
        });

        trato.setItemTratos(tratos);
        this.pesoTotal = new SimpleStringProperty(calculaPesoTotal());
    }

    public String calculaPesoTotal(){
        BigDecimal pesoTotal = listCurralPeso.stream().map(CurralPesoDTO::getPesoBigDecimal).reduce(new BigDecimal("0"),(a, b) -> a.add(b));
        return pesoTotal.toString();
    }

    public CurralPesoDTO getCurralPeso(Curral curral){
        for (CurralPesoDTO curralPeso: listCurralPeso){
            if (curralPeso.getCurralEntity().equals(curral)){
                return curralPeso;
            }
        }
        return null;
    }

    public List<CurralPesoDTO> getListCurralPeso() {
        return listCurralPeso;
    }

    public void setListCurralPeso(ObservableList<CurralPesoDTO> listCurralPeso) {
        this.listCurralPeso = listCurralPeso;
    }

    public Trato getTrato() {
        return trato;
    }

    public void setTrato(Trato trato) {
        this.trato = trato;
    }

    public int getNumero() {
        return numero.get();
    }

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
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

    public String getPesoTotal() {
        return pesoTotal.get();
    }

    public SimpleStringProperty pesoTotalProperty() {
        return pesoTotal;
    }

    public void setPesoTotal(String pesoTotal) {
        this.pesoTotal.set(pesoTotal);
    }
}
