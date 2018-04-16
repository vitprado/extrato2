/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.ReceitaJpaController;
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

    public ReceitaDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        receitaController = new ReceitaJpaController(emf);
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
    public void adicionarReceita(Receita receita) throws Exception {
        receitaController.create(receita);
    }

    // EDITAR 
    /**
     *
     * @param receita
     * @throws Exception
     */
    public void editarReceita(Receita receita) throws Exception {
        receitaController.edit(receita);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws java.lang.Exception
     */
    public void removerReceita(int id) throws Exception {
        receitaController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Receita> getTodoReceitas() {
        return receitaController.findReceitaEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Receita getReceitaId(int id) {
        return receitaController.findReceita(id);
    }

    public List<String> getNomesEquipamentosDistinct() {
        return receitaController.findNameReceitasDistinct();
    }

    public List<String> getNomesReceitasDistinct() {
        return receitaController.findNameReceitasDistinct();
    }

    public List<Receita> getTodaReceitaAtiva() { return receitaController.findReceitaEntitiesAtiva(); }
}
