/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.EquipamentoJpaController;
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

    public EquipamentoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        equipamentoController = new EquipamentoJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR EQUIPAMENTO
    public void adicionarEquipamento(Equipamento equipamento) throws Exception{
        equipamentoController.create(equipamento);
    }
    
    // EDITAR EQUIPAMENTO
    public void editarEquipamento(Equipamento equipamento) throws Exception{
        equipamentoController.edit(equipamento);
    }
    
    // REMOVER EQUIPAMENTO
    public void removerEquipamento(int equipamentoID) throws NonexistentEntityException{
        equipamentoController.destroy(equipamentoID);
    }
    
    // LISTAR TODOS EQUIPAMENTOS
    public List<Equipamento> getTodosEquipamentos(){
        return equipamentoController.findEquipamentoEntities();
    }
    
    // LISTAR EQUIPAMENTO POR ID
    public Equipamento getEquipamentoId(int equipamentoID){
        return equipamentoController.findEquipamento(equipamentoID);
    }
}
