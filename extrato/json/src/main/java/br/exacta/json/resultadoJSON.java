package br.exacta.json;

import java.util.List;

public class resultadoJSON {

    private List<Equip> equips = null;
    private Integer nequipamentos;

    /**
     * No args constructor for use in serialization
     *
     */
    public resultadoJSON() {
    }

    /**
     *
     * @param nequipamentos
     * @param equips
     */
    public resultadoJSON(List<Equip> equips, Integer nequipamentos) {
        super();
        this.equips = equips;
        this.nequipamentos = nequipamentos;
    }

    public List<Equip> getEquips() {
        return equips;
    }

    public void setEquips(List<Equip> equips) {
        this.equips = equips;
    }

    public Integer getNequipamentos() {
        return nequipamentos;
    }

    public void setNequipamentos(Integer nequipamentos) {
        this.nequipamentos = nequipamentos;
    }

}
