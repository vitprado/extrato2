/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ORDEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordem.findAll", query = "SELECT o FROM Ordem o")
    , @NamedQuery(name = "Ordem.findByOrdCodigo", query = "SELECT o FROM Ordem o WHERE o.ordCodigo = :ordCodigo")
    , @NamedQuery(name = "Ordem.findByOrdOrdemprocucao", query = "SELECT o FROM Ordem o WHERE o.ordOrdemprocucao = :ordOrdemprocucao")
    , @NamedQuery(name = "Ordem.findByOrdData", query = "SELECT o FROM Ordem o WHERE o.ordData = :ordData")})
public class Ordem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ORD_CODIGO")
    private Integer ordCodigo;
    @Column(name = "ORD_ORDEMPROCUCAO")
    private String ordOrdemprocucao;
    @Column(name = "ORD_DATA")
    @Temporal(TemporalType.DATE)
    private Date ordData;

    public Ordem() {
    }

    public Ordem(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public String getOrdOrdemprocucao() {
        return ordOrdemprocucao;
    }

    public void setOrdOrdemprocucao(String ordOrdemprocucao) {
        this.ordOrdemprocucao = ordOrdemprocucao;
    }

    public Date getOrdData() {
        return ordData;
    }

    public void setOrdData(Date ordData) {
        this.ordData = ordData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordCodigo != null ? ordCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordem)) {
            return false;
        }
        Ordem other = (Ordem) object;
        if ((this.ordCodigo == null && other.ordCodigo != null) || (this.ordCodigo != null && !this.ordCodigo.equals(other.ordCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Ordem[ ordCodigo=" + ordCodigo + " ]";
    }
    
}
