package br.exacta.dto;

import br.exacta.persistencia.Equipamento;

public class ConsultaOrdemFilter {
    private Equipamento equipamento;

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}
