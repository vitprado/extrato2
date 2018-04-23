/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.IngredientesJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Ingredientes;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class IngredientesDAO {

    private final IngredientesJpaController ingredientesController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public IngredientesDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        ingredientesController = new IngredientesJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR 
    /**
     *
     * @param equipamento
     * @throws Exception
     */
    public void adicionarIngrediente(Ingredientes ingrediente) throws Exception {
        ingredientesController.create(ingrediente);
    }

    // EDITAR 
    /**
     *
     * @param equipamento
     * @throws Exception
     */
    public void editarIngrediente(Ingredientes equipamento) throws Exception {
        ingredientesController.edit(equipamento);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void removerIngrediente(int id) throws Exception {
        ingredientesController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Ingredientes> getTodosIngredientes() {
        return ingredientesController.findIngredientesEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Ingredientes getIngredienteId(int id) {
        return ingredientesController.findIngredientes(id);
    }

    public List<String> getNomesIngredientes() {
        return ingredientesController.findNameIngredientesDistinct();
    }
    
    public List<String> getAbreviacaoIngredientes() {
        return ingredientesController.findAbrevIngredientesDistinct();
    }
}
