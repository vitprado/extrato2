//package br.exacta.persistencia;
//
//import javax.persistence.*;
//import javax.xml.bind.annotation.XmlRootElement;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@Table(name = "ORDEM_CURRAL")
//@XmlRootElement
//@NamedQueries({
//        @NamedQuery(name = "OrdemCurral.findAll", query = "SELECT oc FROM OrdemCurral oc")
//        , @NamedQuery(name = "OrdemCurral.findByOrcCodigo", query = "SELECT oc FROM OrdemCurral oc WHERE oc.orcCodigo = :orcCodigo")
//        , @NamedQuery(name = "OrdemCurral.findByOrdCodigo", query = "SELECT oc FROM OrdemCurral oc WHERE oc.ordCodigo = :ordCodigo")
//        , @NamedQuery(name = "OrdemCurral.findByCurCodigo", query = "SELECT oc FROM OrdemCurral oc WHERE oc.curCodigo = :curCodigo")
//})
//public class OrdemCurral implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
//    @Column(name = "ORC_CODIGO")
//    private Integer orcCodigo;
//    @JoinColumn(name = "ORD_CODIGO", referencedColumnName = "ORD_CODIGO")
//    @ManyToOne(optional = false)
//    private OrdemProducao ordCodigo;
//    @JoinColumn(name = "CUR_CODIGO", referencedColumnName = "CUR_CODIGO")
//    @ManyToOne(optional = false)
//    private Curral curCodigo;
//
//    public OrdemCurral(OrdemProducao ordCodigo, Curral curCodigo) {
//        this.ordCodigo = ordCodigo;
//        this.curCodigo = curCodigo;
//    }
//
//    public Integer getOrcCodigo() {
//        return orcCodigo;
//    }
//
//    public void setOrcCodigo(Integer orcCodigo) {
//        this.orcCodigo = orcCodigo;
//    }
//
//    public OrdemProducao getOrdCodigo() {
//        return ordCodigo;
//    }
//
//    public void setOrdCodigo(OrdemProducao ordCodigo) {
//        this.ordCodigo = ordCodigo;
//    }
//
//    public Curral getCurCodigo() {
//        return curCodigo;
//    }
//
//    public void setCurCodigo(Curral curCodigo) {
//        this.curCodigo = curCodigo;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof OrdemCurral)) return false;
//        OrdemCurral that = (OrdemCurral) o;
//        return Objects.equals(orcCodigo, that.orcCodigo);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(orcCodigo);
//    }
//
//    @Override
//    public String toString() {
//        return "br.exacta.persistencia.OrdemCurral[ orcCodigo=" + orcCodigo + " ]";
//    }
//}
//
////    CREATE TABLE "EXTRATO"."ORDEM_CURRAL"
////        (
////        ORC_CODIGO int PRIMARY KEY NOT NULL,
////        ORD_CODIGO int,
////        CUR_CODIGO int,
////        CONSTRAINT ORDEM_CURRAL_ORD_CODIGO FOREIGN KEY (ORD_CODIGO) REFERENCES ORDEM_PRODUCAO(ORD_CODIGO),
////        CONSTRAINT ORDEM_CURRAL_CUR_CODIGO FOREIGN KEY (CUR_CODIGO) REFERENCES CURRAL(CUR_CODIGO)
////        );
////        CREATE UNIQUE INDEX AUTO_ORC_CODIGO ON "EXTRATO"."ORDEM_CURRAL"(ORC_CODIGO)
////        ;
