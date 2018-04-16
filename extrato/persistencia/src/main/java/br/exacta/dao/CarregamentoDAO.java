/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.CarregamentoJpaController;
import br.exacta.jpacontroller.CarregamentoJpaFilter;
import br.exacta.persistencia.Carregamento;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Thales
 */
public class CarregamentoDAO {

    private final CarregamentoJpaController carregamentoController;
    private final EntityManagerFactory emf;

    public CarregamentoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        carregamentoController = new CarregamentoJpaController(emf);
    }

    // ADICIONAR
    public void adicionarCarregamento(Carregamento carregamento) throws Exception {
        carregamentoController.create(carregamento);
    }

    // EDITAR
    public void editarCarregamento(Carregamento carregamento) throws Exception {
        carregamentoController.edit(carregamento);
    }

    // REMOVER
    public void removerCarregamento(int id) throws Exception {
        carregamentoController.destroy(id);
    }

    public void removerCarregamento(String ordem) throws Exception {
        carregamentoController.destroy(ordem);
    }

    // LISTAR TODOS
    public List<Carregamento> getTodosCarregamentos() {
        return carregamentoController.findCarregamentoEntities();
    }

    // LISTAR POR ID
    public Carregamento getCarregamentoId(int id) {
        return carregamentoController.findCarregamento(id);
    }

    public List<String> getEquipamentosDistinct() {
        return carregamentoController.findEquipamentoDistinct();
    }

    public List<Carregamento> getOrdensDistinct(){
    	return carregamentoController.findOrdemDistinct();
    }
    
    public List<Carregamento> getCarregamentos(CarregamentoJpaFilter filter) {
        return carregamentoController.findCarregamentos(filter);
    }
}
