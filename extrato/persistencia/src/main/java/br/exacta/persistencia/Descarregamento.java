/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thales
 */
@Entity
@Table(name = "DESCARREGAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descarregamento.findAll", query = "SELECT d FROM Descarregamento d")
    , @NamedQuery(name = "Descarregamento.findByRdgCodigo", query = "SELECT d FROM Descarregamento d WHERE d.rdgCodigo = :rdgCodigo")
    , @NamedQuery(name = "Descarregamento.findByRdgEquipamento", query = "SELECT d FROM Descarregamento d WHERE d.rdgEquipamento = :rdgEquipamento")
    , @NamedQuery(name = "Descarregamento.findByRdgOrdem", query = "SELECT d FROM Descarregamento d WHERE d.rdgOrdem = :rdgOrdem")
    , @NamedQuery(name = "Descarregamento.findByRdgNumtrato", query = "SELECT d FROM Descarregamento d WHERE d.rdgNumtrato = :rdgNumtrato")
    , @NamedQuery(name = "Descarregamento.findByRdgCurral", query = "SELECT d FROM Descarregamento d WHERE d.rdgCurral = :rdgCurral")
    , @NamedQuery(name = "Descarregamento.findByRdgTratorequisitado", query = "SELECT d FROM Descarregamento d WHERE d.rdgTratorequisitado = :rdgTratorequisitado")
    , @NamedQuery(name = "Descarregamento.findByRdgTratorealizado", query = "SELECT d FROM Descarregamento d WHERE d.rdgTratorealizado = :rdgTratorealizado")})
public class Descarregamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RDG_CODIGO")
    private Integer rdgCodigo;
    @Column(name = "RDG_EQUIPAMENTO")
    private String rdgEquipamento;
    @Column(name = "RDG_ORDEM")
    private String rdgOrdem;
    @Column(name = "RDG_NUMTRATO")
    private Integer rdgNumtrato;
    @Column(name = "RDG_CURRAL")
    private String rdgCurral;
    @Column(name = "RDG_TRATOREQUISITADO")
    private String rdgTratorequisitado;
    @Column(name = "RDG_TRATOREALIZADO")
    private String rdgTratorealizado;

    public Descarregamento() {
    }

    public Descarregamento(Integer rdgCodigo) {
        this.rdgCodigo = rdgCodigo;
    }

    public Integer getRdgCodigo() {
        return rdgCodigo;
    }

    public void setRdgCodigo(Integer rdgCodigo) {
        this.rdgCodigo = rdgCodigo;
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

    public String getRdgTratorequisitado() {
        return rdgTratorequisitado;
    }

    public void setRdgTratorequisitado(String rdgTratorequisitado) {
        this.rdgTratorequisitado = rdgTratorequisitado;
    }

    public String getRdgTratorealizado() {
        return rdgTratorealizado;
    }

    public void setRdgTratorealizado(String rdgTratorealizado) {
        this.rdgTratorealizado = rdgTratorealizado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rdgCodigo != null ? rdgCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descarregamento)) {
            return false;
        }
        Descarregamento other = (Descarregamento) object;
        if ((this.rdgCodigo == null && other.rdgCodigo != null) || (this.rdgCodigo != null && !this.rdgCodigo.equals(other.rdgCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Descarregamento[ rdgCodigo=" + rdgCodigo + " ]";
    }
    
}
