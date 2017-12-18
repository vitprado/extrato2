/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thales
 */
@Entity
@Table(name = "CURRAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curral.findAll", query = "SELECT c FROM Curral c")
    , @NamedQuery(name = "Curral.findByCurCodigo", query = "SELECT c FROM Curral c WHERE c.curCodigo = :curCodigo")
    , @NamedQuery(name = "Curral.findByCurDescricao", query = "SELECT c FROM Curral c WHERE c.curDescricao = :curDescricao")
    , @NamedQuery(name = "Curral.findByCurRfid", query = "SELECT c FROM Curral c WHERE c.curRfid = :curRfid")
    , @NamedQuery(name = "Curral.findByCurDataCadastro", query = "SELECT c FROM Curral c WHERE c.curDataCadastro = :curDataCadastro")})
public class Curral implements Serializable {

    @OneToMany(mappedBy = "curCodigo")
    private List<OrdemProcucao> ordemProcucaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curral")
    private List<Trato> tratoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CUR_CODIGO")
    private Integer curCodigo;
    @Column(name = "CUR_DESCRICAO")
    private String curDescricao;
    @Column(name = "CUR_RFID")
    private Integer curRfid;
    @Basic(optional = false)
    @Column(name = "CUR_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date curDataCadastro;

    /**
     *
     */
    public Curral() {
    }

    /**
     *
     * @param curCodigo
     */
    public Curral(Integer curCodigo) {
        this.curCodigo = curCodigo;
    }

    /**
     *
     * @param curCodigo
     * @param curDataCadastro
     */
    public Curral(Integer curCodigo, Date curDataCadastro) {
        this.curCodigo = curCodigo;
        this.curDataCadastro = curDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getCurCodigo() {
        return curCodigo;
    }

    /**
     *
     * @param curCodigo
     */
    public void setCurCodigo(Integer curCodigo) {
        this.curCodigo = curCodigo;
    }

    /**
     *
     * @return
     */
    public String getCurDescricao() {
        return curDescricao;
    }

    /**
     *
     * @param curDescricao
     */
    public void setCurDescricao(String curDescricao) {
        this.curDescricao = curDescricao;
    }

    /**
     *
     * @return
     */
    public Integer getCurRfid() {
        return curRfid;
    }

    /**
     *
     * @param curRfid
     */
    public void setCurRfid(Integer curRfid) {
        this.curRfid = curRfid;
    }

    /**
     *
     * @return
     */
    public Date getCurDataCadastro() {
        return curDataCadastro;
    }

    /**
     *
     * @param curDataCadastro
     */
    public void setCurDataCadastro(Date curDataCadastro) {
        this.curDataCadastro = curDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (curCodigo != null ? curCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curral)) {
            return false;
        }
        Curral other = (Curral) object;
        return !((this.curCodigo == null && other.curCodigo != null) || (this.curCodigo != null && !this.curCodigo.equals(other.curCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Curral[ curCodigo=" + curCodigo + " ]";
    }

    @XmlTransient
    public List<OrdemProcucao> getOrdemProcucaoList() {
        return ordemProcucaoList;
    }

    public void setOrdemProcucaoList(List<OrdemProcucao> ordemProcucaoList) {
        this.ordemProcucaoList = ordemProcucaoList;
    }

    @XmlTransient
    public List<Trato> getTratoList() {
        return tratoList;
    }

    public void setTratoList(List<Trato> tratoList) {
        this.tratoList = tratoList;
    }
    
}
