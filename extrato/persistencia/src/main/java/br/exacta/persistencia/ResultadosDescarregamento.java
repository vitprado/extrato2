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
@Table(name = "RESULTADOS_DESCARREGAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultadosDescarregamento.findAll", query = "SELECT r FROM ResultadosDescarregamento r")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgCodigoPai", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgCodigoPai = :rdgCodigoPai")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgCodigoFilho", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgCodigoFilho = :rdgCodigoFilho")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgEquipamento", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgEquipamento = :rdgEquipamento")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgOrdem", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgOrdem = :rdgOrdem")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgNumtrato", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgNumtrato = :rdgNumtrato")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgCurral", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgCurral = :rdgCurral")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgPesorequisitado", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgPesorequisitado = :rdgPesorequisitado")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgPesorealizado", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgPesorealizado = :rdgPesorealizado")
    , @NamedQuery(name = "ResultadosDescarregamento.findByRdgDatacriacao", query = "SELECT r FROM ResultadosDescarregamento r WHERE r.rdgDatacriacao = :rdgDatacriacao")})
public class ResultadosDescarregamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RDG_CODIGO_PAI")
    private Integer rdgCodigoPai;
    @Column(name = "RDG_CODIGO_FILHO")
    private Integer rdgCodigoFilho;
    @Column(name = "RDG_EQUIPAMENTO")
    private String rdgEquipamento;
    @Column(name = "RDG_ORDEM")
    private String rdgOrdem;
    @Column(name = "RDG_NUMTRATO")
    private Integer rdgNumtrato;
    @Column(name = "RDG_CURRAL")
    private String rdgCurral;
    @Column(name = "RDG_PESOREQUISITADO")
    private Integer rdgPesorequisitado;
    @Column(name = "RDG_PESOREALIZADO")
    private Integer rdgPesorealizado;
    @Column(name = "RDG_DATACRIACAO")
    @Temporal(TemporalType.DATE)
    private Date rdgDatacriacao;

    public ResultadosDescarregamento() {
    }

    public ResultadosDescarregamento(Integer rdgCodigoPai) {
        this.rdgCodigoPai = rdgCodigoPai;
    }

    public Integer getRdgCodigoPai() {
        return rdgCodigoPai;
    }

    public void setRdgCodigoPai(Integer rdgCodigoPai) {
        this.rdgCodigoPai = rdgCodigoPai;
    }

    public Integer getRdgCodigoFilho() {
        return rdgCodigoFilho;
    }

    public void setRdgCodigoFilho(Integer rdgCodigoFilho) {
        this.rdgCodigoFilho = rdgCodigoFilho;
    }

    public String getRdgEquipamento() {
        return rdgEquipamento;
    }

    public void setRdgEquipamento(String rdgEquipamento) {
        this.rdgEquipamento = rdgEquipamento;
    }

    public String getRdgOrdem() {
        return rdgOrdem;
    }

    public void setRdgOrdem(String rdgOrdem) {
        this.rdgOrdem = rdgOrdem;
    }

    public Integer getRdgNumtrato() {
        return rdgNumtrato;
    }

    public void setRdgNumtrato(Integer rdgNumtrato) {
        this.rdgNumtrato = rdgNumtrato;
    }

    public String getRdgCurral() {
        return rdgCurral;
    }

    public void setRdgCurral(String rdgCurral) {
        this.rdgCurral = rdgCurral;
    }

    public Integer getRdgPesorequisitado() {
        return rdgPesorequisitado;
    }

    public void setRdgPesorequisitado(Integer rdgPesorequisitado) {
        this.rdgPesorequisitado = rdgPesorequisitado;
    }

    public Integer getRdgPesorealizado() {
        return rdgPesorealizado;
    }

    public void setRdgPesorealizado(Integer rdgPesorealizado) {
        this.rdgPesorealizado = rdgPesorealizado;
    }

    public Date getRdgDatacriacao() {
        return rdgDatacriacao;
    }

    public void setRdgDatacriacao(Date rdgDatacriacao) {
        this.rdgDatacriacao = rdgDatacriacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rdgCodigoPai != null ? rdgCodigoPai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadosDescarregamento)) {
            return false;
        }
        ResultadosDescarregamento other = (ResultadosDescarregamento) object;
        if ((this.rdgCodigoPai == null && other.rdgCodigoPai != null) || (this.rdgCodigoPai != null && !this.rdgCodigoPai.equals(other.rdgCodigoPai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ResultadosDescarregamento[ rdgCodigoPai=" + rdgCodigoPai + " ]";
    }
    
}
