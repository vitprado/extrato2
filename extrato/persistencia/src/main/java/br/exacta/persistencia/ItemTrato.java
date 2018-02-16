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
    @Column(name = "CUR_CODIGO")
    private Integer curralId;
    @ManyToOne
    @JoinColumn(name = "TRT_CODIGO", referencedColumnName = "TRT_CODIGO")
    private Trato trato;

    public ItemTrato() {
    }

    public ItemTrato(Integer ittCodigo, BigDecimal ittPeso, Integer curralId ,Trato trato) {
        this.ittCodigo = ittCodigo;
        this.ittPeso = ittPeso;
        this.curralId = curralId;
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

    public Integer getCurralId() {
        return curralId;
    }

    public void setCurralId(Integer curralId) {
        this.curralId = curralId;
    }

    public Trato getTrato() {
        return trato;
    }

    public void setTrato(Trato trato) {
        this.trato = trato;
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
        return "br.exacta.persistencia.ItemTrato[ ittCodigo=" + ittCodigo +" ]";
    }
}


