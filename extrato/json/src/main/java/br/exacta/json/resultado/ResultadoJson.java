package br.exacta.json.resultado;

import java.util.ArrayList;
import java.util.List;

public class ResultadoJson {

    private List<Equip> equips = new ArrayList<Equip>();
    private Integer nequipamentos;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultadoJson() {
    }

    /**
     *
     * @param nequipamentos
     * @param equips
     */
    public ResultadoJson(List<Equip> equips, Integer nequipamentos) {
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
