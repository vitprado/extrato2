/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.NivelAcessoJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.NivelAcesso;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class NivelAcessoDAO {
    
    private final NivelAcessoJpaController nivelAcessoController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public NivelAcessoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        nivelAcessoController = new NivelAcessoJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR NIVEL DE ACESSO

    /**
     *
     * @param ingredientes
     * @throws Exception
     */
    public void adicionarNivelAcesso(NivelAcesso ingredientes) throws Exception{
        nivelAcessoController.create(ingredientes);
    }
    
    // EDITAR NIVEL DE ACESSO

    /**
     *
     * @param ingredientes
     * @throws Exception
     */
    public void editarNivelAcesso(NivelAcesso ingredientes) throws Exception{
        nivelAcessoController.edit(ingredientes);
    }
    
    // REMOVER NIVEL DE ACESSO

    /**
     *
     * @param nivelAcessoID
     * @throws NonexistentEntityException
     */
    public void removerNivelAcesso(int nivelAcessoID) throws NonexistentEntityException{
        nivelAcessoController.destroy(nivelAcessoID);
    }
    
    // LISTAR TODOS NIVEL DE ACESSO

    /**
     *
     * @return
     */
    public List<NivelAcesso> getTodosNivelAcesso(){
        return nivelAcessoController.findNivelAcessoEntities();
    }
    
    // LISTAR NIVEL DE ACESSO POR ID

    /**
     *
     * @param nivelAcessoID
     * @return
     */
        public NivelAcesso getNivelAcessoId(int nivelAcessoID){
        return nivelAcessoController.findNivelAcesso(nivelAcessoID);
    }
}
