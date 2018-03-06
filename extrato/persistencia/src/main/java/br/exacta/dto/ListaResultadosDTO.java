package br.exacta.dto;

import javafx.beans.property.SimpleStringProperty;

public class ListaResultadosDTO {
	
	// Campos: Ordem, Equipamento, Data
	private SimpleStringProperty ordem;
	private SimpleStringProperty equipamento;
	private SimpleStringProperty data;
	
	public ListaResultadosDTO() {}
	
	public ListaResultadosDTO(String ordem, String equipamento, String data) {
		super();
		this.ordem = new SimpleStringProperty(ordem);
		this.equipamento = new SimpleStringProperty(equipamento);
		this.data = new SimpleStringProperty(data);
	}

	public String getOrdem() {
		return ordem.getValue();
	}
	
    public SimpleStringProperty ordemProperty() {
        return ordem;
    }

	public void setOrdem(String ordem) {
		this.ordem.set(ordem);
	}

	public String getEquipamento() {
		return equipamento.getValue();
	}
	
    public SimpleStringProperty equipamentoProperty() {
        return equipamento;
    }

	public void setEquipamento(String equipamento) {
		this.equipamento.set(equipamento);
	}

	public String getData() {
		return data.getValue();
	}

    public SimpleStringProperty dataProperty() {
        return equipamento;
    }
	
	public void setData(String data) {
		this.data.set(data);
	}	
}
