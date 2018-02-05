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

    /**
     *
     */
    public CarregamentoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        carregamentoController = new CarregamentoJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR 
    /**
     *
     * @param curral
     * @throws Exception
     */
    public void adicionarCarregamento(Carregamento carregamento) throws Exception {
        carregamentoController.create(carregamento);
    }

    // EDITAR 
    /**
     *
     * @param curral
     * @throws Exception
     */
    public void editarCarregamento(Carregamento carregamento) throws Exception {
        carregamentoController.edit(carregamento);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws java.lang.Exception
     */
    public void removerCarregamento(int id) throws Exception {
        carregamentoController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Carregamento> getTodosCarregamentos() {
        return carregamentoController.findCarregamentoEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Carregamento getCarregamentoId(int id) {
        return carregamentoController.findCarregamento(id);
    }

    public List<String> getEquipamentosDistinct() {
        return carregamentoController.findEquipamentoDistinct();
    }

    public List<Carregamento> getCarregamentos(CarregamentoJpaFilter filter) {
        return carregamentoController.findCarregamentos(filter);
    }
}
