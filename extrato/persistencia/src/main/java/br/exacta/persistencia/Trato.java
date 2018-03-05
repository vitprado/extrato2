/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Thales
 */
@Entity
@Table(name = "TRATO")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Trato.findAll", query = "SELECT t FROM Trato t")
        , @NamedQuery(name = "Trato.findByTrtCodigo", query = "SELECT t FROM Trato t WHERE t.trtCodigo = :trtCodigo")
        , @NamedQuery(name = "Trato.findByTrtNumero", query = "SELECT t FROM Trato t WHERE t.trtNumero = :trtNumero")
        , @NamedQuery(name = "Trato.findByRctCodigo", query = "SELECT t FROM Trato t WHERE t.receita = :receita")
})
public class Trato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "TRT_CODIGO")
    private Integer trtCodigo;
    @Column(name = "TRT_NUMERO")
    private Integer trtNumero;
    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO", nullable = false)
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "ORD_CODIGO", referencedColumnName = "ORD_CODIGO")
    private OrdemProducao ordemProducao;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "trato")
    private List<ItemTrato> itemTratos;


    public Trato() {
    }

    public Trato(List<ItemTrato> itemTratos, Integer trtNumero, Integer trtCodigo, Receita receita, OrdemProducao ordemProducao) {
        this();
        this.itemTratos = itemTratos;
        this.trtNumero = trtNumero;
        this.trtCodigo = trtCodigo;
        this.receita = receita;
        this.ordemProducao = ordemProducao;
        this.itemTratos = new ArrayList<>();
        for (ItemTrato itemTrato : itemTratos) {
            ItemTrato novoItemTrato = new ItemTrato(
                    itemTrato.getIttCodigo(),
                    itemTrato.getIttPeso(),
                    itemTrato.getCurral(),
                    this);
            this.itemTratos.add(novoItemTrato);
        }
    }

    public Integer getTrtCodigo() {
        return trtCodigo;
    }

    public void setTrtCodigo(Integer trtCodigo) {
        this.trtCodigo = trtCodigo;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Integer getTrtNumero() {
        return trtNumero;
    }

    public void setTrtNumero(Integer trtNumero) {
        this.trtNumero = trtNumero;
    }

    public List<ItemTrato> getItemTratos() {
        return itemTratos;
    }

    public void setItemTratos(List<ItemTrato> itemTratos) {
        this.itemTratos = itemTratos;
    }

    public OrdemProducao getOrdemProducao() {
        return ordemProducao;
    }

    public void setOrdemProducao(OrdemProducao ordemProducao) {
        this.ordemProducao = ordemProducao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trato)) return false;
        Trato trato = (Trato) o;
        return Objects.equals(trtCodigo, trato.trtCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trtCodigo);
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Trato[ trtCodigo=" + trtCodigo + " ]";
    }
}