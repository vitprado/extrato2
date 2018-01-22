/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.EquipamentoJpaController;
import br.exacta.jpacontroller.NivelAcessoJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Equipamento;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class EquipamentoDAO {

    private final EquipamentoJpaController equipamentoController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public EquipamentoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        equipamentoController = new EquipamentoJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR 
    /**
     *
     * @param nivel
     * @throws Exception
     */
    public void adicionarEquipamento(Equipamento equipamento) throws Exception {
        equipamentoController.create(equipamento);
    }

    // EDITAR 
    /**
     *
     * @param nivel
     * @throws Exception
     */
    public void editarEquipamento(Equipamento equipamento) throws Exception {
        equipamentoController.edit(equipamento);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void removerEquipamento(int id) throws NonexistentEntityException {
        equipamentoController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Equipamento> getTodosEquipamentos() {
        return equipamentoController.findEquipamentoEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Equipamento getEquipamentoId(int id) {
        return equipamentoController.findEquipamento(id);
    }
    
    public List<String> getNomesEquipamentos(){
        return equipamentoController.findNameEquipamentos();
    }
    
    public List<String> getNomesEquipamentosdIS(){
        return equipamentoController.findNameEquipamentos();
    }

}
