/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.CurralJpaController;
import br.exacta.persistencia.Curral;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class CurralDAO {

    private final CurralJpaController curralController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public CurralDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        curralController = new CurralJpaController(emf);
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
    public void adicionarCurral(Curral curral) throws Exception {
        curralController.create(curral);
    }

    // EDITAR 
    /**
     *
     * @param curral
     * @throws Exception
     */
    public void editarCurral(Curral curral) throws Exception {
        curralController.edit(curral);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws java.lang.Exception
     */
    public void removerCurral(int id) throws Exception {
        curralController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Curral> getTodosCurrais() {
        return curralController.findCurralEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Curral getCurralId(int id) {
        return curralController.findCurral(id);
    }

    public List<String> getNomesCurraisDistinct() {
        return curralController.findNameCurralDistinct();
    }
}
