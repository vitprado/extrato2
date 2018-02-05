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
public class ReceitaTemIngredientesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "RCT_CODIGO")
    private int rctCodigo;
    @Basic(optional = false)
    @Column(name = "ING_CODIGO")
    private int ingCodigo;

    public ReceitaTemIngredientesPK() {
    }

    public ReceitaTemIngredientesPK(int rctCodigo, int ingCodigo) {
        this.rctCodigo = rctCodigo;
        this.ingCodigo = ingCodigo;
    }

    public int getRctCodigo() {
        return rctCodigo;
    }

    public void setRctCodigo(int rctCodigo) {
        this.rctCodigo = rctCodigo;
    }

    public int getIngCodigo() {
        return ingCodigo;
    }

    public void setIngCodigo(int ingCodigo) {
        this.ingCodigo = ingCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rctCodigo;
        hash += (int) ingCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceitaTemIngredientesPK)) {
            return false;
        }
        ReceitaTemIngredientesPK other = (ReceitaTemIngredientesPK) object;
        if (this.rctCodigo != other.rctCodigo) {
            return false;
        }
        if (this.ingCodigo != other.ingCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.exacta.persistencia.ReceitaTemIngredientesPK[ rctCodigo=" + rctCodigo + ", ingCodigo=" + ingCodigo + " ]";
    }
    
}
