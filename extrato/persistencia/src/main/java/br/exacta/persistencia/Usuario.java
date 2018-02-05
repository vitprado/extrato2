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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuCodigo", query = "SELECT u FROM Usuario u WHERE u.usuCodigo = :usuCodigo")
    , @NamedQuery(name = "Usuario.findByUsuDataCadastro", query = "SELECT u FROM Usuario u WHERE u.usuDataCadastro = :usuDataCadastro")
    , @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin")
    , @NamedQuery(name = "Usuario.findByUsuNome", query = "SELECT u FROM Usuario u WHERE u.usuNome = :usuNome")
    , @NamedQuery(name = "Usuario.findByUsuSenha", query = "SELECT u FROM Usuario u WHERE u.usuSenha = :usuSenha")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USU_CODIGO")
    private Integer usuCodigo;
    @Column(name = "USU_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date usuDataCadastro;
    @Column(name = "USU_LOGIN")
    private String usuLogin;
    @Column(name = "USU_NOME")
    private String usuNome;
    @Column(name = "USU_SENHA")
    private String usuSenha;
    @JoinColumn(name = "EMP_CODIGO", referencedColumnName = "EMP_CODIGO")
    @ManyToOne
    private Empresa empCodigo;
    @JoinColumn(name = "NVA_CODIGO", referencedColumnName = "NVA_CODIGO")
    @ManyToOne
    private NivelAcesso nvaCodigo;

    public Usuario() {
    }

    public Usuario(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public Integer getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public Date getUsuDataCadastro() {
        return usuDataCadastro;
    }

    public void setUsuDataCadastro(Date usuDataCadastro) {
        this.usuDataCadastro = usuDataCadastro;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public Empresa getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Empresa empCodigo) {
        this.empCodigo = empCodigo;
    }

    public NivelAcesso getNvaCodigo() {
        return nvaCodigo;
    }

    public void setNvaCodigo(NivelAcesso nvaCodigo) {
        this.nvaCodigo = nvaCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Usuario[ usuCodigo=" + usuCodigo + " ]";
    }
    
}
