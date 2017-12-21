/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.IngredientesJpaController;
import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Ingredientes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class IngredientesDAO {
    
    private final IngredientesJpaController ingredienteController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public IngredientesDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        ingredienteController = new IngredientesJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR INGREDIENTES

    /**
     *
     * @param ingredientes
     * @throws Exception
     */
    public void adicionarIngredientes(Ingredientes ingredientes) throws Exception{
        ingredienteController.create(ingredientes);
    }
    
    // EDITAR INGREDIENTES

    /**
     *
     * @param ingredientes
     * @throws Exception
     */
    public void editarIngredientes(Ingredientes ingredientes) throws Exception{
        ingredienteController.edit(ingredientes);
    }
    
    // REMOVER INGREDIENTES

    /**
     *
     * @param ingredientesID
     * @throws NonexistentEntityException
     */
    public void removerIngredientes(int ingredientesID) throws NonexistentEntityException{
        try {
            ingredienteController.destroy(ingredientesID);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(IngredientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // LISTAR TODOS INGREDIENTES

    /**
     *
     * @return
     */
    public List<Ingredientes> getTodosIngredientes(){
        return ingredienteController.findIngredientesEntities();
    }
    
    // LISTAR INGREDIENTES POR ID

    /**
     *
     * @param ingredientesID
     * @return
     */
    public Ingredientes getIngredientesId(int ingredientesID){
        return ingredienteController.findIngredientes(ingredientesID);
    }
    
}
