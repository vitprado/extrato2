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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "RECEITA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receita.findAll", query = "SELECT r FROM Receita r")
    , @NamedQuery(name = "Receita.findByRctCodigo", query = "SELECT r FROM Receita r WHERE r.rctCodigo = :rctCodigo")
    , @NamedQuery(name = "Receita.findByRctNome", query = "SELECT r FROM Receita r WHERE r.rctNome = :rctNome")
    , @NamedQuery(name = "Receita.findByRctDataCadastro", query = "SELECT r FROM Receita r WHERE r.rctDataCadastro = :rctDataCadastro")})
public class Receita implements Serializable {

    @JoinTable(name = "RECEITA_TEM_INGREDIENTES", joinColumns = {
        @JoinColumn(name = "RCT_CODIGO", referencedColumnName = "RCT_CODIGO")}, inverseJoinColumns = {
        @JoinColumn(name = "ING_CODIGO", referencedColumnName = "ING_CODIGO")})
    @ManyToMany
    private List<Ingredientes> ingredientesList;
    @OneToMany(mappedBy = "rctCodigo")
    private List<OrdemProcucao> ordemProcucaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receita")
    private List<Trato> tratoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RCT_CODIGO")
    private Integer rctCodigo;
    @Column(name = "RCT_NOME")
    private String rctNome;
    @Basic(optional = false)
    @Column(name = "RCT_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date rctDataCadastro;

    public Receita() {
    }

    /**
     *
     * @param rctCodigo
     */
    public Receita(Integer rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    /**
     *
     * @param rctCodigo
     * @param rctDataCadastro
     */
    public Receita(Integer rctCodigo, Date rctDataCadastro) {
        this.rctCodigo = rctCodigo;
        this.rctDataCadastro = rctDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getRctCodigo() {
        return rctCodigo;
    }

    /**
     *
     * @param rctCodigo
     */
    public void setRctCodigo(Integer rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    /**
     *
     * @return
     */
    public String getRctNome() {
        return rctNome;
    }

    /**
     *
     * @param rctNome
     */
    public void setRctNome(String rctNome) {
        this.rctNome = rctNome;
    }

    /**
     *
     * @return
     */
    public Date getRctDataCadastro() {
        return rctDataCadastro;
    }

    /**
     *
     * @param rctDataCadastro
     */
    public void setRctDataCadastro(Date rctDataCadastro) {
        this.rctDataCadastro = rctDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rctCodigo != null ? rctCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receita)) {
            return false;
        }
        Receita other = (Receita) object;
        return !((this.rctCodigo == null && other.rctCodigo != null) || (this.rctCodigo != null && !this.rctCodigo.equals(other.rctCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Receita[ rctCodigo=" + rctCodigo + " ]";
    }

    @XmlTransient
    public List<Ingredientes> getIngredientesList() {
        return ingredientesList;
    }

    public void setIngredientesList(List<Ingredientes> ingredientesList) {
        this.ingredientesList = ingredientesList;
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
