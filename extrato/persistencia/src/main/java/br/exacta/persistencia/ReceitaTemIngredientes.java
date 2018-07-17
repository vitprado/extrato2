/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Thales
 */
@Entity
@Table(name = "RECEITA_TEM_INGREDIENTES")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ReceitaTemIngredientes.findAll", query = "SELECT r FROM ReceitaTemIngredientes r")
        , @NamedQuery(name = "ReceitaTemIngredientes.findByRtiCodigo", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.rtiCodigo = :rtiCodigo")
        , @NamedQuery(name = "ReceitaTemIngredientes.findByRctCodigo", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.receita.rctCodigo = :rctCodigo")
        , @NamedQuery(name = "ReceitaTemIngredientes.findByIngCodigo", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.ingredientes.ingCodigo = :ingCodigo")
        , @NamedQuery(name = "ReceitaTemIngredientes.findByRtiData", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.rtiData = :rtiData")
        , @NamedQuery(name = "ReceitaTemIngredientes.findByRtiProporcao", query = "SELECT r FROM ReceitaTemIngredientes r WHERE r.rtiProporcao = :rtiProporcao")})
public class ReceitaTemIngredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "RTI_CODIGO")
    private Integer rtiCodigo;
    @Column(name = "RTI_DATA")
    @Temporal(TemporalType.DATE)
    private Date rtiData;
    @Column(name = "RTI_PROPORCAO")
    private Double rtiProporcao;
    @JoinColumn(name = "ING_CODIGO", referencedColumnName = "ING_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingredientes ingredientes;
    @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Receita receita;

    public ReceitaTemIngredientes() {
    }

    public Integer getRtiCodigo() {
        return rtiCodigo;
    }

    public void setRtiCodigo(Integer rtiCodigo) {
        this.rtiCodigo = rtiCodigo;
    }

    public Date getRtiData() {
        return rtiData;
    }

    public void setRtiData(Date rtiData) {
        this.rtiData = rtiData;
    }

    public Double getRtiProporcao() {
        return rtiProporcao;
    }

    public void setRtiProporcao(Double rtiProporcao) {
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
        hash += (rtiCodigo != null ? rtiCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceitaTemIngredientes)) {
            return false;
        }
        ReceitaTemIngredientes other = (ReceitaTemIngredientes) object;
        if ((this.rtiCodigo == null && other.rtiCodigo != null) || (this.rtiCodigo != null && !this.rtiCodigo.equals(other.rtiCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ReceitaTemIngredientes[ rtiCodigo=" + rtiCodigo + " ]";
    }

}
