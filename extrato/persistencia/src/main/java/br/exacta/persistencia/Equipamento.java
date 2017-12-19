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
@Table(name = "EQUIPAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipamento.findAll", query = "SELECT e FROM Equipamento e")
    , @NamedQuery(name = "Equipamento.findByEqpCodigo", query = "SELECT e FROM Equipamento e WHERE e.eqpCodigo = :eqpCodigo")
    , @NamedQuery(name = "Equipamento.findByEpqPlaca", query = "SELECT e FROM Equipamento e WHERE e.epqPlaca = :epqPlaca")
    , @NamedQuery(name = "Equipamento.findByEqpDescricao", query = "SELECT e FROM Equipamento e WHERE e.eqpDescricao = :eqpDescricao")
    , @NamedQuery(name = "Equipamento.findByEqpDataCadastro", query = "SELECT e FROM Equipamento e WHERE e.eqpDataCadastro = :eqpDataCadastro")})
public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EQP_CODIGO")
    private Integer eqpCodigo;
    @Basic(optional = false)
    @Column(name = "EPQ_PLACA")
    private String epqPlaca;
    @Column(name = "EQP_DESCRICAO")
    private String eqpDescricao;
    @Basic(optional = false)
    @Column(name = "EQP_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date eqpDataCadastro;

    /**
     *
     */
    public Equipamento() {
    }

    /**
     *
     * @param eqpCodigo
     */
    public Equipamento(Integer eqpCodigo) {
        this.eqpCodigo = eqpCodigo;
    }

    /**
     *
     * @param eqpCodigo
     * @param epqPlaca
     * @param eqpDataCadastro
     */
    public Equipamento(Integer eqpCodigo, String epqPlaca, Date eqpDataCadastro) {
        this.eqpCodigo = eqpCodigo;
        this.epqPlaca = epqPlaca;
        this.eqpDataCadastro = eqpDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getEqpCodigo() {
        return eqpCodigo;
    }

    /**
     *
     * @param eqpCodigo
     */
    public void setEqpCodigo(Integer eqpCodigo) {
        this.eqpCodigo = eqpCodigo;
    }

    /**
     *
     * @return
     */
    public String getEpqPlaca() {
        return epqPlaca;
    }

    /**
     *
     * @param epqPlaca
     */
    public void setEpqPlaca(String epqPlaca) {
        this.epqPlaca = epqPlaca;
    }

    /**
     *
     * @return
     */
    public String getEqpDescricao() {
        return eqpDescricao;
    }

    /**
     *
     * @param eqpDescricao
     */
    public void setEqpDescricao(String eqpDescricao) {
        this.eqpDescricao = eqpDescricao;
    }

    /**
     *
     * @return
     */
    public Date getEqpDataCadastro() {
        return eqpDataCadastro;
    }

    /**
     *
     * @param eqpDataCadastro
     */
    public void setEqpDataCadastro(Date eqpDataCadastro) {
        this.eqpDataCadastro = eqpDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eqpCodigo != null ? eqpCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipamento)) {
            return false;
        }
        Equipamento other = (Equipamento) object;
        return !((this.eqpCodigo == null && other.eqpCodigo != null) || (this.eqpCodigo != null && !this.eqpCodigo.equals(other.eqpCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Equipamento[ eqpCodigo=" + eqpCodigo + " ]";
    }
    
}
