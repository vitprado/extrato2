/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.ReceitaTemIngredientesJpaController;
import br.exacta.persistencia.ReceitaTemIngredientes;
import br.exacta.persistencia.ReceitaTemIngredientesPK;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class ReceitaTemIngredientesDAO {

    private final ReceitaTemIngredientesJpaController receitaTemIngController;
    private final EntityManagerFactory emf;

    public ReceitaTemIngredientesDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        receitaTemIngController = new ReceitaTemIngredientesJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR 
    /**
     *
     * @param receita
     * @throws Exception
     */
    public void adicionarIngredienteReceita(ReceitaTemIngredientes receita) throws Exception {
        receitaTemIngController.create(receita);
    }

    // EDITAR 
    /**
     *
     * @param receita
     * @throws Exception
     */
    public void editarIngredienteReceita(ReceitaTemIngredientes receita) throws Exception {
        receitaTemIngController.edit(receita);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws java.lang.Exception
     */
    public void removerIngredienteReceita(ReceitaTemIngredientesPK id) throws Exception {
        receitaTemIngController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<ReceitaTemIngredientes> getTodosIngredienteReceita() {
        return receitaTemIngController.findReceitaTemIngredientesEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public ReceitaTemIngredientes getIngredienteReceitaId(ReceitaTemIngredientesPK id) {
        return receitaTemIngController.findReceitaTemIngredientes(id);
    }
}
