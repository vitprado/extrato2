/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.CurralJpaController;
import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Curral;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    // ADICIONAR CURRAL

    /**
     *
     * @param curral
     * @throws Exception
     */
    public void adicionarCurral(Curral curral) throws Exception{
        curralController.create(curral);
    }
    
    // EDITAR CURRAL

    /**
     *
     * @param curral
     * @throws Exception
     */
    public void editarCurral(Curral curral) throws Exception{
        curralController.edit(curral);
    }
    
    // REMOVER CURRAL

    /**
     *
     * @param curralID
     * @throws NonexistentEntityException
     */
    public void removerCurral(int curralID) throws NonexistentEntityException{
        try {
            curralController.destroy(curralID);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CurralDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // LISTAR TODOS CURRAIS

    /**
     *
     * @return
     */
    public List<Curral> getTodosCurrais(){
        return curralController.findCurralEntities();
    }
    
    // LISTAR CURRAL POR ID

    /**
     *
     * @param curralID
     * @return
     */
        public Curral getCurralId(int curralID){
        return curralController.findCurral(curralID);
    }
}
