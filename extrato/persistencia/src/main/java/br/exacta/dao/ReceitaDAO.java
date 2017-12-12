/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.ReceitaJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Receita;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class ReceitaDAO {
    
    private final ReceitaJpaController receitaController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public ReceitaDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        receitaController = new ReceitaJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR RECEITA

    /**
     *
     * @param receita
     * @throws Exception
     */
    public void adicionarReceita(Receita receita) throws Exception{
        receitaController.create(receita);
    }
    
    // EDITAR RECEITA

    /**
     *
     * @param receita
     * @throws Exception
     */
    public void editarReceita(Receita receita) throws Exception{
        receitaController.edit(receita);
    }
    
    // REMOVER RECEITA

    /**
     *
     * @param receitaID
     * @throws NonexistentEntityException
     */
    public void removerReceita(int receitaID) throws NonexistentEntityException{
        receitaController.destroy(receitaID);
    }
    
    // LISTAR TODOS RECEITAS

    /**
     *
     * @return
     */
    public List<Receita> getTodosReceitas(){
        return receitaController.findReceitaEntities();
    }
    
    // LISTAR RECEITA POR ID

    /**
     *
     * @param receitaID
     * @return
     */
        public Receita getReceitaId(int receitaID){
        return receitaController.findReceita(receitaID);
    }
}
