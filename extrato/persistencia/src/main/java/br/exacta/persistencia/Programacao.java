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
@Table(name = "PROGRAMACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programacao.findAll", query = "SELECT p FROM Programacao p")
    , @NamedQuery(name = "Programacao.findByPrgCodigo", query = "SELECT p FROM Programacao p WHERE p.prgCodigo = :prgCodigo")
    , @NamedQuery(name = "Programacao.findByPrgDataCadastro", query = "SELECT p FROM Programacao p WHERE p.prgDataCadastro = :prgDataCadastro")})
public class Programacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRG_CODIGO")
    private Integer prgCodigo;
    @Basic(optional = false)
    @Column(name = "PRG_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date prgDataCadastro;

    /**
     *
     */
    public Programacao() {
    }

    /**
     *
     * @param prgCodigo
     */
    public Programacao(Integer prgCodigo) {
        this.prgCodigo = prgCodigo;
    }

    /**
     *
     * @param prgCodigo
     * @param prgDataCadastro
     */
    public Programacao(Integer prgCodigo, Date prgDataCadastro) {
        this.prgCodigo = prgCodigo;
        this.prgDataCadastro = prgDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getPrgCodigo() {
        return prgCodigo;
    }

    /**
     *
     * @param prgCodigo
     */
    public void setPrgCodigo(Integer prgCodigo) {
        this.prgCodigo = prgCodigo;
    }

    /**
     *
     * @return
     */
    public Date getPrgDataCadastro() {
        return prgDataCadastro;
    }

    /**
     *
     * @param prgDataCadastro
     */
    public void setPrgDataCadastro(Date prgDataCadastro) {
        this.prgDataCadastro = prgDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prgCodigo != null ? prgCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programacao)) {
            return false;
        }
        Programacao other = (Programacao) object;
        return !((this.prgCodigo == null && other.prgCodigo != null) || (this.prgCodigo != null && !this.prgCodigo.equals(other.prgCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Programacao[ prgCodigo=" + prgCodigo + " ]";
    }
    
}
