/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Thales
 */
@Entity
@Table(name = "ORDEM_PRODUCAO")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "OrdemProducao.findAll", query = "SELECT o FROM OrdemProducao o")
        , @NamedQuery(name = "OrdemProducao.findByOrdCodigo", query = "SELECT o FROM OrdemProducao o WHERE o.ordCodigo = :ordCodigo")
        , @NamedQuery(name = "OrdemProducao.findByOrdDataCadastro", query = "SELECT o FROM OrdemProducao o WHERE o.ordDataCadastro = :ordDataCadastro")
        , @NamedQuery(name = "OrdemProducao.findByEqpCodigo", query = "SELECT o FROM OrdemProducao o WHERE o.equipamento = :equipamento")
        , @NamedQuery(name = "OrdemProducao.findByOrdDescricao", query = "SELECT o FROM OrdemProducao o WHERE o.ordDescricao = :ordDescricao")
})
public class OrdemProducao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORD_CODIGO")
    private Integer ordCodigo;
    @Column(name = "ORD_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date ordDataCadastro;
    @Column(name = "ORD_DESCRICAO")
    private String ordDescricao;
    @JoinColumn(name = "EQP_CODIGO", referencedColumnName = "EQP_CODIGO")
    @ManyToOne(optional = false)
    private Equipamento equipamento;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "ordemProducao")
    private List<Trato> tratos;

    public OrdemProducao() {
        this.ordDataCadastro = new Date();
        this.tratos = new ArrayList<>();
    }

    public OrdemProducao(String ordDescricao, Equipamento equipamento, List<Trato> tratos) {
        this();
        this.ordDescricao = ordDescricao;
        this.equipamento = equipamento;
        for (Trato trato: tratos) {
            Trato novoTrato = new Trato(trato.getItemTratos(), trato.getTrtNumero(),
                    trato.getTrtCodigo(),trato.getReceita(),
                    this);
            this.tratos.add(novoTrato);
        }
    }

    public OrdemProducao(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public Integer getOrdCodigo() {
        return ordCodigo;
    }

    public void setOrdCodigo(Integer ordCodigo) {
        this.ordCodigo = ordCodigo;
    }

    public Date getOrdDataCadastro() {
        return ordDataCadastro;
    }

    public void setOrdDataCadastro(Date ordDataCadastro) {
        this.ordDataCadastro = ordDataCadastro;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getOrdDescricao() {
        return ordDescricao;
    }

    public void setOrdDescricao(String ordDescricao) {
        this.ordDescricao = ordDescricao;
    }

    public List<Trato> getTratos() {
        return tratos;
    }

    public void setTratos(List<Trato> tratos) {
        this.tratos = tratos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordCodigo != null ? ordCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdemProducao)) {
            return false;
        }
        OrdemProducao other = (OrdemProducao) object;
        if ((this.ordCodigo == null && other.ordCodigo != null) || (this.ordCodigo != null && !this.ordCodigo.equals(other.ordCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.OrdemProducao[ ordCodigo=" + ordCodigo + " ]";
    }

}
