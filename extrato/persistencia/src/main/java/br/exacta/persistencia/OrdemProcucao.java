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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ORDEM_PROCUCAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdemProcucao.findAll", query = "SELECT o FROM OrdemProcucao o")
    , @NamedQuery(name = "OrdemProcucao.findByOrdCodigo", query = "SELECT o FROM OrdemProcucao o WHERE o.ordCodigo = :ordCodigo")
    , @NamedQuery(name = "OrdemProcucao.findByOrdDataCadastro", query = "SELECT o FROM OrdemProcucao o WHERE o.ordDataCadastro = :ordDataCadastro")})
public class OrdemProcucao implements Serializable {

    @JoinColumn(name = "CUR_CODIGO", referencedColumnName = "CUR_CODIGO")
    @ManyToOne
    private Curral curCodigo;
    @JoinColumn(name = "EQP_CODIGO", referencedColumnName = "EQP_CODIGO")
    @ManyToOne
    private Equipamento eqpCodigo;
    @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO")
    @ManyToOne
    private Receita rctCodigo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORD_CODIGO")
    private Integer ordCodigo;
    @Basic(optional = false)
    @Column(name = "ORD_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date ordDataCadastro;

    /**
     *
     */
    public OrdemProcucao() {
    }

    /**
     *
     * @param ordCodigo
     */
    public OrdemProcucao(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    /**
     *
     * @param ordCodigo
     * @param ordDataCadastro
     */
    public OrdemProcucao(Integer ordCodigo, Date ordDataCadastro) {
        this.ordCodigo = ordCodigo;
        this.ordDataCadastro = ordDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    /**
     *
     * @param ordCodigo
     */
    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    /**
     *
     * @return
     */
    public Date getOrdDataCadastro() {
        return ordDataCadastro;
    }

    /**
     *
     * @param ordDataCadastro
     */
    public void setOrdDataCadastro(Date ordDataCadastro) {
        this.ordDataCadastro = ordDataCadastro;
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
        if (!(object instanceof OrdemProcucao)) {
            return false;
        }
        OrdemProcucao other = (OrdemProcucao) object;
        return !((this.ordCodigo == null && other.ordCodigo != null) || (this.ordCodigo != null && !this.ordCodigo.equals(other.ordCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.OrdemProcucao[ ordCodigo=" + ordCodigo + " ]";
    }

    public Curral getCurCodigo() {
        return curCodigo;
    }

    public void setCurCodigo(Curral curCodigo) {
        this.curCodigo = curCodigo;
    }

    public Equipamento getEqpCodigo() {
        return eqpCodigo;
    }

    public void setEqpCodigo(Equipamento eqpCodigo) {
        this.eqpCodigo = eqpCodigo;
    }

    public Receita getRctCodigo() {
        return rctCodigo;
    }

    public void setRctCodigo(Receita rctCodigo) {
        this.rctCodigo = rctCodigo;
    }
    
}
