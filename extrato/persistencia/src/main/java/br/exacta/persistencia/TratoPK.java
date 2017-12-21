/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Thales
 */
@Embeddable
public class TratoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "RCT_CODIGO")
    private int rctCodigo;
    @Basic(optional = false)
    @Column(name = "CUR_CODIGO")
    private int curCodigo;

    public TratoPK() {
    }

    public TratoPK(int rctCodigo, int curCodigo) {
        this.rctCodigo = rctCodigo;
        this.curCodigo = curCodigo;
    }

    public int getRctCodigo() {
        return rctCodigo;
    }

    public void setRctCodigo(int rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    public int getCurCodigo() {
        return curCodigo;
    }

    public void setCurCodigo(int curCodigo) {
        this.curCodigo = curCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rctCodigo;
        hash += (int) curCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TratoPK)) {
            return false;
        }
        TratoPK other = (TratoPK) object;
        if (this.rctCodigo != other.rctCodigo) {
            return false;
        }
        if (this.curCodigo != other.curCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.TratoPK[ rctCodigo=" + rctCodigo + ", curCodigo=" + curCodigo + " ]";
    }
    
}
