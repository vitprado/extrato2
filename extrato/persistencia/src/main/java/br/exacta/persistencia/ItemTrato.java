package br.exacta.persistencia;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ITEM_TRATO")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ItemTrato.findAll", query = "SELECT it FROM ItemTrato it")
        , @NamedQuery(name = "ItemTrato.findByIttCodigo", query = "SELECT it FROM ItemTrato it WHERE it.ittCodigo = :ittCodigo")
        , @NamedQuery(name = "ItemTrato.findByIttPeso", query = "SELECT it FROM ItemTrato it WHERE it.ittPeso = :ittPeso")
        , @NamedQuery(name = "ItemTrato.findByTrtCodigo", query = "SELECT it FROM ItemTrato it WHERE it.trato = :trato")
        , @NamedQuery(name = "ItemTrato.findByIttSequencia", query = "SELECT it FROM ItemTrato it WHERE it.ittSequencia = :ittSequencia")
})
public class ItemTrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ITT_CODIGO")
    private Integer ittCodigo;
    @Column(name = "ITT_PESO")
    private BigDecimal ittPeso;
    @Column(name = "ITT_SEQUENCIA")
    private Integer ittSequencia;

    @ManyToOne
    @JoinColumn(name = "CUR_CODIGO", referencedColumnName = "CUR_CODIGO")
    private Curral curral;

    @ManyToOne
    @JoinColumn(name = "TRT_CODIGO", referencedColumnName = "TRT_CODIGO")
    private Trato trato;

    public ItemTrato() {
    }

    public ItemTrato(Integer ittCodigo, BigDecimal ittPeso, Curral curral,Integer ittSequencia , Trato trato) {
        this();
        this.ittCodigo = ittCodigo;
        this.ittPeso = ittPeso;
        this.curral = curral;
        this.ittSequencia = ittSequencia;
        this.trato = trato;
    }

    public Integer getIttCodigo() {
        return ittCodigo;
    }

    public void setIttCodigo(Integer ittCodigo) {
        this.ittCodigo = ittCodigo;
    }

    public BigDecimal getIttPeso() {
        return ittPeso;
    }

    public void setIttPeso(BigDecimal ittPeso) {
        this.ittPeso = ittPeso;
    }

    public Curral getCurral() {
        return curral;
    }

    public void setCurral(Curral curral) {
        this.curral = curral;
    }

    public Trato getTrato() {
        return trato;
    }

    public void setTrato(Trato trato) {
        this.trato = trato;
    }

    public Integer getIttSequencia() {
        return ittSequencia;
    }

    public void setIttSequencia(Integer ittSequencia) {
        this.ittSequencia = ittSequencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTrato)) return false;
        ItemTrato itemTrato = (ItemTrato) o;
        return Objects.equals(ittCodigo, itemTrato.ittCodigo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ittCodigo);
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ItemTrato[ ittCodigo=" + ittCodigo + " ]";
    }
}


