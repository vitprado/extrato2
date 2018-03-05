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
@Table(name = "CARREGAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carregamento.findAll", query = "SELECT c FROM Carregamento c")
    , @NamedQuery(name = "Carregamento.findByRdcCodigo", query = "SELECT c FROM Carregamento c WHERE c.rdcCodigo = :rdcCodigo")
    , @NamedQuery(name = "Carregamento.findByRdcOrdem", query = "SELECT c FROM Carregamento c WHERE c.rdcOrdem = :rdcOrdem")
    , @NamedQuery(name = "Carregamento.findByRdcEquipamento", query = "SELECT c FROM Carregamento c WHERE c.rdcEquipamento = :rdcEquipamento")
    , @NamedQuery(name = "Carregamento.findByRdcNumtrato", query = "SELECT c FROM Carregamento c WHERE c.rdcNumtrato = :rdcNumtrato")
    , @NamedQuery(name = "Carregamento.findByRdcIngrediente", query = "SELECT c FROM Carregamento c WHERE c.rdcIngrediente = :rdcIngrediente")
    , @NamedQuery(name = "Carregamento.findByRdcPesorequisitado", query = "SELECT c FROM Carregamento c WHERE c.rdcPesorequisitado = :rdcPesorequisitado")
    , @NamedQuery(name = "Carregamento.findByRdcPesorealizado", query = "SELECT c FROM Carregamento c WHERE c.rdcPesorealizado = :rdcPesorealizado")
    , @NamedQuery(name = "Carregamento.findByRdcDatajson", query = "SELECT c FROM Carregamento c WHERE c.rdcDataJson = :rdcDataJson")
    , @NamedQuery(name = "Carregamento.findByRdcReceita", query = "SELECT c FROM Carregamento c WHERE c.rdcReceita = :rdcReceita")})
public class Carregamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RDC_CODIGO")
    private Integer rdcCodigo;
    @Column(name = "RDC_ORDEM")
    private String rdcOrdem;
    @Column(name = "RDC_EQUIPAMENTO")
    private String rdcEquipamento;
    @Column(name = "RDC_NUMTRATO")
    private Integer rdcNumtrato;
    @Column(name = "RDC_INGREDIENTE")
    private String rdcIngrediente;
    @Column(name = "RDC_PESOREQUISITADO")
    private String rdcPesorequisitado;
    @Column(name = "RDC_PESOREALIZADO")
    private String rdcPesorealizado;
    @Column(name = "RDC_DATA_JSON")
    private String rdcDataJson;
    @Column(name = "RDC_RECEITA")
    private String rdcReceita;

    public Carregamento() {
    }

    public Carregamento(Integer rdcCodigo) {
        this.rdcCodigo = rdcCodigo;
    }

    public Integer getRdcCodigo() {
        return rdcCodigo;
    }

    public void setRdcCodigo(Integer rdcCodigo) {
        this.rdcCodigo = rdcCodigo;
    }

    public String getRdcOrdem() {
        return rdcOrdem;
    }

    public void setRdcOrdem(String rdcOrdem) {
        this.rdcOrdem = rdcOrdem;
    }

    public String getRdcEquipamento() {
        return rdcEquipamento;
    }

    public void setRdcEquipamento(String rdcEquipamento) {
        this.rdcEquipamento = rdcEquipamento;
    }

    public Integer getRdcNumtrato() {
        return rdcNumtrato;
    }

    public void setRdcNumtrato(Integer rdcNumtrato) {
        this.rdcNumtrato = rdcNumtrato;
    }

    public String getRdcIngrediente() {
        return rdcIngrediente;
    }

    public void setRdcIngrediente(String rdcIngrediente) {
        this.rdcIngrediente = rdcIngrediente;
    }

    public String getRdcPesorequisitado() {
        return rdcPesorequisitado;
    }

    public void setRdcPesorequisitado(String rdcPesorequisitado) {
        this.rdcPesorequisitado = rdcPesorequisitado;
    }

    public String getRdcPesorealizado() {
        return rdcPesorealizado;
    }

    public void setRdcPesorealizado(String rdcPesorealizado) {
        this.rdcPesorealizado = rdcPesorealizado;
    }

    public String getRdcDataJson() {
        return rdcDataJson;
    }

    public void setRdcDatajson(String rdcDataJson) {
        this.rdcDataJson = rdcDataJson;
    }

    public String getRdcReceita() {
        return rdcReceita;
    }

    public void setRdcReceita(String rdcReceita) {
        this.rdcReceita = rdcReceita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rdcCodigo != null ? rdcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carregamento)) {
            return false;
        }
        Carregamento other = (Carregamento) object;
        if ((this.rdcCodigo == null && other.rdcCodigo != null) || (this.rdcCodigo != null && !this.rdcCodigo.equals(other.rdcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.Carregamento[ rdcCodigo=" + rdcCodigo + " ]";
    }
    
}
