/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thales
 */
@Entity
@Table(name = "RECEITA_TEM_INGREDIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReceitaTemIngredientes.findAll", query = "SELECT r FROM ReceitaTemIngredientes r")
    , @NamedQuery(name = "ReceitaTemIngredientes.findByRctCodigo", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.receitaTemIngredientesPK.rctCodigo = :rctCodigo")
    , @NamedQuery(name = "ReceitaTemIngredientes.findByIngCodigo", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.receitaTemIngredientesPK.ingCodigo = :ingCodigo")
    , @NamedQuery(name = "ReceitaTemIngredientes.findByRtiData", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.rtiData = :rtiData")
    , @NamedQuery(name = "ReceitaTemIngredientes.findByRtiProporcao", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.rtiProporcao = :rtiProporcao")})
public class ReceitaTemIngredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReceitaTemIngredientesPK receitaTemIngredientesPK;
    @Column(name = "RTI_DATA")
    @Temporal(TemporalType.DATE)
    private Date rtiData;
    @Column(name = "RTI_PROPORCAO")
    private Integer rtiProporcao;
    @JoinColumn(name = "ING_CODIGO", referencedColumnName = "ING_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingredientes ingredientes;
    @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Receita receita;

    public ReceitaTemIngredientes() {
    }

    public ReceitaTemIngredientes(ReceitaTemIngredientesPK receitaTemIngredientesPK) {
        this.receitaTemIngredientesPK = receitaTemIngredientesPK;
    }

    public ReceitaTemIngredientes(int rctCodigo, int ingCodigo) {
        this.receitaTemIngredientesPK = new ReceitaTemIngredientesPK(rctCodigo, ingCodigo);
    }

    public ReceitaTemIngredientesPK getReceitaTemIngredientesPK() {
        return receitaTemIngredientesPK;
    }

    public void setReceitaTemIngredientesPK(ReceitaTemIngredientesPK receitaTemIngredientesPK) {
        this.receitaTemIngredientesPK = receitaTemIngredientesPK;
    }

    public Date getRtiData() {
        return rtiData;
    }

    public void setRtiData(Date rtiData) {
        this.rtiData = rtiData;
    }

    public Integer getRtiProporcao() {
        return rtiProporcao;
    }

    public void setRtiProporcao(Integer rtiProporcao) {
        this.rtiProporcao = rtiProporcao;
    }

    public Ingredientes getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Ingredientes ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getIngredienteNome() {
        return ingredientes.getIngNome();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receitaTemIngredientesPK != null ? receitaTemIngredientesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceitaTemIngredientes)) {
            return false;
        }
        ReceitaTemIngredientes other = (ReceitaTemIngredientes) object;
        if ((this.receitaTemIngredientesPK == null && other.receitaTemIngredientesPK != null) || (this.receitaTemIngredientesPK != null && !this.receitaTemIngredientesPK.equals(other.receitaTemIngredientesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ReceitaTemIngredientes[ receitaTemIngredientesPK=" + receitaTemIngredientesPK + " ]";
    }
    
}
