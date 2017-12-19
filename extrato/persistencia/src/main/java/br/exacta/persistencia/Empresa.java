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
@Table(name = "EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
    , @NamedQuery(name = "Empresa.findByEmpCodigo", query = "SELECT e FROM Empresa e WHERE e.empCodigo = :empCodigo")
    , @NamedQuery(name = "Empresa.findByEmpRazaoSocial", query = "SELECT e FROM Empresa e WHERE e.empRazaoSocial = :empRazaoSocial")
    , @NamedQuery(name = "Empresa.findByEmpNomeFantasia", query = "SELECT e FROM Empresa e WHERE e.empNomeFantasia = :empNomeFantasia")
    , @NamedQuery(name = "Empresa.findByEmpCnpj", query = "SELECT e FROM Empresa e WHERE e.empCnpj = :empCnpj")
    , @NamedQuery(name = "Empresa.findByEmpInscEstadual", query = "SELECT e FROM Empresa e WHERE e.empInscEstadual = :empInscEstadual")
    , @NamedQuery(name = "Empresa.findByEmpEndereco", query = "SELECT e FROM Empresa e WHERE e.empEndereco = :empEndereco")
    , @NamedQuery(name = "Empresa.findByEmpDataCadastro", query = "SELECT e FROM Empresa e WHERE e.empDataCadastro = :empDataCadastro")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMP_CODIGO")
    private Integer empCodigo;
    @Column(name = "EMP_RAZAO_SOCIAL")
    private String empRazaoSocial;
    @Column(name = "EMP_NOME_FANTASIA")
    private String empNomeFantasia;
    @Column(name = "EMP_CNPJ")
    private String empCnpj;
    @Column(name = "EMP_INSC_ESTADUAL")
    private String empInscEstadual;
    @Column(name = "EMP_ENDERECO")
    private String empEndereco;
    @Basic(optional = false)
    @Column(name = "EMP_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date empDataCadastro;

    /**
     *
     */
    public Empresa() {
    }

    /**
     *
     * @param empCodigo
     */
    public Empresa(Integer empCodigo) {
        this.empCodigo = empCodigo;
    }

    /**
     *
     * @param empCodigo
     * @param empDataCadastro
     */
    public Empresa(Integer empCodigo, Date empDataCadastro) {
        this.empCodigo = empCodigo;
        this.empDataCadastro = empDataCadastro;
    }

    /**
     *
     * @return
     */
    public Integer getEmpCodigo() {
        return empCodigo;
    }

    /**
     *
     * @param empCodigo
     */
    public void setEmpCodigo(Integer empCodigo) {
        this.empCodigo = empCodigo;
    }

    /**
     *
     * @return
     */
    public String getEmpRazaoSocial() {
        return empRazaoSocial;
    }

    /**
     *
     * @param empRazaoSocial
     */
    public void setEmpRazaoSocial(String empRazaoSocial) {
        this.empRazaoSocial = empRazaoSocial;
    }

    /**
     *
     * @return
     */
    public String getEmpNomeFantasia() {
        return empNomeFantasia;
    }

    /**
     *
     * @param empNomeFantasia
     */
    public void setEmpNomeFantasia(String empNomeFantasia) {
        this.empNomeFantasia = empNomeFantasia;
    }

    /**
     *
     * @return
     */
    public String getEmpCnpj() {
        return empCnpj;
    }

    /**
     *
     * @param empCnpj
     */
    public void setEmpCnpj(String empCnpj) {
        this.empCnpj = empCnpj;
    }

    /**
     *
     * @return
     */
    public String getEmpInscEstadual() {
        return empInscEstadual;
    }

    /**
     *
     * @param empInscEstadual
     */
    public void setEmpInscEstadual(String empInscEstadual) {
        this.empInscEstadual = empInscEstadual;
    }

    /**
     *
     * @return
     */
    public String getEmpEndereco() {
        return empEndereco;
    }

    /**
     *
     * @param empEndereco
     */
    public void setEmpEndereco(String empEndereco) {
        this.empEndereco = empEndereco;
    }

    /**
     *
     * @return
     */
    public Date getEmpDataCadastro() {
        return empDataCadastro;
    }

    public void setEmpDataCadastro(Date empDataCadastro) {
        this.empDataCadastro = empDataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empCodigo != null ? empCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        return !((this.empCodigo == null && other.empCodigo != null) || (this.empCodigo != null && !this.empCodigo.equals(other.empCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Empresa[ empCodigo=" + empCodigo + " ]";
    }
    
}
