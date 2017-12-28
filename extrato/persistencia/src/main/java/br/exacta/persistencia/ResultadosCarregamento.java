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
@Table(name = "RESULTADOS_CARREGAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultadosCarregamento.findAll", query = "SELECT r FROM ResultadosCarregamento r")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgCodigoPai", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgCodigoPai = :rcgCodigoPai")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgEquipamento", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgEquipamento = :rcgEquipamento")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgOrdem", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgOrdem = :rcgOrdem")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgNumtrato", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgNumtrato = :rcgNumtrato")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgCurral", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgCurral = :rcgCurral")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgPesorequisitado", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgPesorequisitado = :rcgPesorequisitado")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgPesorealizado", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgPesorealizado = :rcgPesorealizado")
    , @NamedQuery(name = "ResultadosCarregamento.findByRcgDatacriacao", query = "SELECT r FROM ResultadosCarregamento r WHERE r.rcgDatacriacao = :rcgDatacriacao")})
public class ResultadosCarregamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RCG_CODIGO_PAI")
    private Integer rcgCodigoPai;
    @Column(name = "RCG_EQUIPAMENTO")
    private String rcgEquipamento;
    @Column(name = "RCG_ORDEM")
    private String rcgOrdem;
    @Column(name = "RCG_NUMTRATO")
    private Integer rcgNumtrato;
    @Column(name = "RCG_CURRAL")
    private String rcgCurral;
    @Column(name = "RCG_PESOREQUISITADO")
    private Integer rcgPesorequisitado;
    @Column(name = "RCG_PESOREALIZADO")
    private Integer rcgPesorealizado;
    @Column(name = "RCG_DATACRIACAO")
    @Temporal(TemporalType.DATE)
    private Date rcgDatacriacao;

    public ResultadosCarregamento() {
    }

    public ResultadosCarregamento(Integer rcgCodigoPai) {
        this.rcgCodigoPai = rcgCodigoPai;
    }

    public Integer getRcgCodigoPai() {
        return rcgCodigoPai;
    }

    public void setRcgCodigoPai(Integer rcgCodigoPai) {
        this.rcgCodigoPai = rcgCodigoPai;
    }

    public String getRcgEquipamento() {
        return rcgEquipamento;
    }

    public void setRcgEquipamento(String rcgEquipamento) {
        this.rcgEquipamento = rcgEquipamento;
    }

    public String getRcgOrdem() {
        return rcgOrdem;
    }

    public void setRcgOrdem(String rcgOrdem) {
        this.rcgOrdem = rcgOrdem;
    }

    public Integer getRcgNumtrato() {
        return rcgNumtrato;
    }

    public void setRcgNumtrato(Integer rcgNumtrato) {
        this.rcgNumtrato = rcgNumtrato;
    }

    public String getRcgCurral() {
        return rcgCurral;
    }

    public void setRcgCurral(String rcgCurral) {
        this.rcgCurral = rcgCurral;
    }

    public Integer getRcgPesorequisitado() {
        return rcgPesorequisitado;
    }

    public void setRcgPesorequisitado(Integer rcgPesorequisitado) {
        this.rcgPesorequisitado = rcgPesorequisitado;
    }

    public Integer getRcgPesorealizado() {
        return rcgPesorealizado;
    }

    public void setRcgPesorealizado(Integer rcgPesorealizado) {
        this.rcgPesorealizado = rcgPesorealizado;
    }

    public Date getRcgDatacriacao() {
        return rcgDatacriacao;
    }

    public void setRcgDatacriacao(Date rcgDatacriacao) {
        this.rcgDatacriacao = rcgDatacriacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcgCodigoPai != null ? rcgCodigoPai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadosCarregamento)) {
            return false;
        }
        ResultadosCarregamento other = (ResultadosCarregamento) object;
        if ((this.rcgCodigoPai == null && other.rcgCodigoPai != null) || (this.rcgCodigoPai != null && !this.rcgCodigoPai.equals(other.rcgCodigoPai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ResultadosCarregamento[ rcgCodigoPai=" + rcgCodigoPai + " ]";
    }
    
}
