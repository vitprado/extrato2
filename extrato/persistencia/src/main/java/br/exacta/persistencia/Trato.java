/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thales
 */
@Entity
@Table(name = "TRATO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trato.findAll", query = "SELECT t FROM Trato t")
    , @NamedQuery(name = "Trato.findByRctCodigo", query = "SELECT t FROM Trato t WHERE t.tratoPK.rctCodigo = :rctCodigo")
    , @NamedQuery(name = "Trato.findByCurCodigo", query = "SELECT t FROM Trato t WHERE t.tratoPK.curCodigo = :curCodigo")
    , @NamedQuery(name = "Trato.findByTrtPeso", query = "SELECT t FROM Trato t WHERE t.trtPeso = :trtPeso")
    , @NamedQuery(name = "Trato.findByTrtPesoTotal", query = "SELECT t FROM Trato t WHERE t.trtPesoTotal = :trtPesoTotal")})
public class Trato implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TratoPK tratoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TRT_PESO")
    private BigDecimal trtPeso;
    @Column(name = "TRT_PESO_TOTAL")
    private BigDecimal trtPesoTotal;
    @JoinColumn(name = "CUR_CODIGO", referencedColumnName = "CUR_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curral curral;
    @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Receita receita;

    public Trato() {
    }

    public Trato(TratoPK tratoPK) {
        this.tratoPK = tratoPK;
    }

    public Trato(int rctCodigo, int curCodigo) {
        this.tratoPK = new TratoPK(rctCodigo, curCodigo);
    }

    public TratoPK getTratoPK() {
        return tratoPK;
    }

    public void setTratoPK(TratoPK tratoPK) {
        this.tratoPK = tratoPK;
    }

    public BigDecimal getTrtPeso() {
        return trtPeso;
    }

    public void setTrtPeso(BigDecimal trtPeso) {
        this.trtPeso = trtPeso;
    }

    public BigDecimal getTrtPesoTotal() {
        return trtPesoTotal;
    }

    public void setTrtPesoTotal(BigDecimal trtPesoTotal) {
        this.trtPesoTotal = trtPesoTotal;
    }

    public Curral getCurral() {
        return curral;
    }

    public void setCurral(Curral curral) {
        this.curral = curral;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tratoPK != null ? tratoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trato)) {
            return false;
        }
        Trato other = (Trato) object;
        if ((this.tratoPK == null && other.tratoPK != null) || (this.tratoPK != null && !this.tratoPK.equals(other.tratoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Trato[ tratoPK=" + tratoPK + " ]";
    }
    
}
