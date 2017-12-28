/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thales
 */
@Entity
@Table(name = "NIVEL_ACESSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelAcesso.findAll", query = "SELECT n FROM NivelAcesso n")
    , @NamedQuery(name = "NivelAcesso.findByNvaCodigo", query = "SELECT n FROM NivelAcesso n WHERE n.nvaCodigo = :nvaCodigo")
    , @NamedQuery(name = "NivelAcesso.findByNvaDescricao", query = "SELECT n FROM NivelAcesso n WHERE n.nvaDescricao = :nvaDescricao")})
public class NivelAcesso implements Serializable {

    @OneToMany(mappedBy = "nvaCodigo")
    private List<Usuario> usuarioList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NVA_CODIGO")
    private Integer nvaCodigo;
    @Column(name = "NVA_DESCRICAO")
    private String nvaDescricao;

    /**
     *
     */
    public NivelAcesso() {
    }

    /**
     *
     * @param nvaCodigo
     */
    public NivelAcesso(Integer nvaCodigo) {
        this.nvaCodigo = nvaCodigo;
    }

    /**
     *
     * @return
     */
    public Integer getNvaCodigo() {
        return nvaCodigo;
    }

    /**
     *
     * @param nvaCodigo
     */
    public void setNvaCodigo(Integer nvaCodigo) {
        this.nvaCodigo = nvaCodigo;
    }

    /**
     *
     * @return
     */
    public String getNvaDescricao() {
        return nvaDescricao;
    }

    /**
     *
     * @param nvaDescricao
     */
    public void setNvaDescricao(String nvaDescricao) {
        this.nvaDescricao = nvaDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nvaCodigo != null ? nvaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelAcesso)) {
            return false;
        }
        NivelAcesso other = (NivelAcesso) object;
        return !((this.nvaCodigo == null && other.nvaCodigo != null) || (this.nvaCodigo != null && !this.nvaCodigo.equals(other.nvaCodigo)));
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.NivelAcesso[ nvaCodigo=" + nvaCodigo + " ]";
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
    
}
