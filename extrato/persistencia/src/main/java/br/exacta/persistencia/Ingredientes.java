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
@Table(name = "INGREDIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingredientes.findAll", query = "SELECT i FROM Ingredientes i")
    , @NamedQuery(name = "Ingredientes.findByIngCodigo", query = "SELECT i FROM Ingredientes i WHERE i.ingCodigo = :ingCodigo")
    , @NamedQuery(name = "Ingredientes.findByIngNome", query = "SELECT i FROM Ingredientes i WHERE i.ingNome = :ingNome")
    , @NamedQuery(name = "Ingredientes.findByIngAbreviacao", query = "SELECT i FROM Ingredientes i WHERE i.ingAbreviacao = :ingAbreviacao")
    , @NamedQuery(name = "Ingredientes.findByIngTolerancia", query = "SELECT i FROM Ingredientes i WHERE i.ingTolerancia = :ingTolerancia")
    , @NamedQuery(name = "Ingredientes.findByIngDataCadastro", query = "SELECT i FROM Ingredientes i WHERE i.ingDataCadastro = :ingDataCadastro")})
public class Ingredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ING_CODIGO")
    private Integer ingCodigo;
    @Column(name = "ING_NOME")
    private String ingNome;
    @Column(name = "ING_ABREVIACAO")
    private String ingAbreviacao;
    @Column(name = "ING_TOLERANCIA")
    private Integer ingTolerancia;
    @Basic(optional = false)
    @Column(name = "ING_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date ingDataCadastro;

    /**
     *
     */
    public Ingredientes() {
    }

    /**
     *
     * @param ingCodigo
     */
    public Ingredientes(Integer ingCodigo) {
        this.ingCodigo = ingCodigo;
    }

    /**
     *
     * @param ingCodigo
     * @param ingDataCadastro
     */
    public Ingredientes(Integer ingCodigo, Date ingDataCadastro) {
        this.ingCodigo = ingCodigo;
        this.ingDataCadastro = ingDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getIngCodigo() {
        return ingCodigo;
    }

    /**
     *
     * @param ingCodigo
     */
    public void setIngCodigo(Integer ingCodigo) {
        this.ingCodigo = ingCodigo;
    }

    /**
     *
     * @return
     */
    public String getIngNome() {
        return ingNome;
    }

    /**
     *
     * @param ingNome
     */
    public void setIngNome(String ingNome) {
        this.ingNome = ingNome;
    }

    /**
     *
     * @return
     */
    public String getIngAbreviacao() {
        return ingAbreviacao;
    }

    /**
     *
     * @param ingAbreviacao
     */
    public void setIngAbreviacao(String ingAbreviacao) {
        this.ingAbreviacao = ingAbreviacao;
    }

    /**
     *
     * @return
     */
    public Integer getIngTolerancia() {
        return ingTolerancia;
    }

    /**
     *
     * @param ingTolerancia
     */
    public void setIngTolerancia(Integer ingTolerancia) {
        this.ingTolerancia = ingTolerancia;
    }

    /**
     *
     * @return
     */
    public Date getIngDataCadastro() {
        return ingDataCadastro;
    }

    /**
     *
     * @param ingDataCadastro
     */
    public void setIngDataCadastro(Date ingDataCadastro) {
        this.ingDataCadastro = ingDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingCodigo != null ? ingCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredientes)) {
            return false;
        }
        Ingredientes other = (Ingredientes) object;
        return !((this.ingCodigo == null && other.ingCodigo != null) || (this.ingCodigo != null && !this.ingCodigo.equals(other.ingCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Ingredientes[ ingCodigo=" + ingCodigo + " ]";
    }
    
}