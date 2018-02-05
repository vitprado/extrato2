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
@Table(name = "EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
    , @NamedQuery(name = "Empresa.findByEmpCodigo", query = "SELECT e FROM Empresa e WHERE e.empCodigo = :empCodigo")
    , @NamedQuery(name = "Empresa.findByEmpCnpj", query = "SELECT e FROM Empresa e WHERE e.empCnpj = :empCnpj")
    , @NamedQuery(name = "Empresa.findByEmpDataCadastro", query = "SELECT e FROM Empresa e WHERE e.empDataCadastro = :empDataCadastro")
    , @NamedQuery(name = "Empresa.findByEmpEndereco", query = "SELECT e FROM Empresa e WHERE e.empEndereco = :empEndereco")
    , @NamedQuery(name = "Empresa.findByEmpInscEstadual", query = "SELECT e FROM Empresa e WHERE e.empInscEstadual = :empInscEstadual")
    , @NamedQuery(name = "Empresa.findByEmpNomeFantasia", query = "SELECT e FROM Empresa e WHERE e.empNomeFantasia = :empNomeFantasia")
    , @NamedQuery(name = "Empresa.findByEmpRazaoSocial", query = "SELECT e FROM Empresa e WHERE e.empRazaoSocial = :empRazaoSocial")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMP_CODIGO")
    private Integer empCodigo;
    @Column(name = "EMP_CNPJ")
    private String empCnpj;
    @Column(name = "EMP_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date empDataCadastro;
    @Column(name = "EMP_ENDERECO")
    private String empEndereco;
    @Column(name = "EMP_INSC_ESTADUAL")
    private String empInscEstadual;
    @Column(name = "EMP_NOME_FANTASIA")
    private String empNomeFantasia;
    @Column(name = "EMP_RAZAO_SOCIAL")
    private String empRazaoSocial;
    @OneToMany(mappedBy = "empCodigo")
    private List<Usuario> usuarioList;

    public Empresa() {
    }

    public Empresa(Integer empCodigo) {
        this.empCodigo = empCodigo;
    }

    public Integer getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Integer empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getEmpCnpj() {
        return empCnpj;
    }

    public void setEmpCnpj(String empCnpj) {
        this.empCnpj = empCnpj;
    }

    public Date getEmpDataCadastro() {
        return empDataCadastro;
    }

    public void setEmpDataCadastro(Date empDataCadastro) {
        this.empDataCadastro = empDataCadastro;
    }

    public String getEmpEndereco() {
        return empEndereco;
    }

    public void setEmpEndereco(String empEndereco) {
        this.empEndereco = empEndereco;
    }

    public String getEmpInscEstadual() {
        return empInscEstadual;
    }

    public void setEmpInscEstadual(String empInscEstadual) {
        this.empInscEstadual = empInscEstadual;
    }

    public String getEmpNomeFantasia() {
        return empNomeFantasia;
    }

    public void setEmpNomeFantasia(String empNomeFantasia) {
        this.empNomeFantasia = empNomeFantasia;
    }

    public String getEmpRazaoSocial() {
        return empRazaoSocial;
    }

    public void setEmpRazaoSocial(String empRazaoSocial) {
        this.empRazaoSocial = empRazaoSocial;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        if ((this.empCodigo == null && other.empCodigo != null) || (this.empCodigo != null && !this.empCodigo.equals(other.empCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Empresa[ empCodigo=" + empCodigo + " ]";
    }
    
}
