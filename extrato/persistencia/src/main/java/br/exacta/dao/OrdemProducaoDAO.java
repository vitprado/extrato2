/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.OrdemProcucaoJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.OrdemProcucao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class OrdemProducaoDAO {
    
    private final OrdemProcucaoJpaController ordemController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public OrdemProducaoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        ordemController = new OrdemProcucaoJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR ORDEM DE PRODUÇÃO

    /**
     *
     * @param ordem
     * @throws Exception
     */
    public void adicionarOrdemProducao(OrdemProcucao ordem) throws Exception{
        ordemController.create(ordem);
    }
    
    // EDITAR ORDEM DE PRODUÇÃO

    /**
     *
     * @param ordem
     * @throws Exception
     */
    public void editarOrdemProducao(OrdemProcucao ordem) throws Exception{
        ordemController.edit(ordem);
    }
    
    // REMOVER ORDEM DE PRODUÇÃO

    /**
     *
     * @param ordemID
     * @throws NonexistentEntityException
     */
    public void removerOrdemProducao(int ordemID) throws NonexistentEntityException{
        ordemController.destroy(ordemID);
    }
    
    // LISTAR TODOS ORDEM DE PRODUÇÕES

    /**
     *
     * @return
     */
    public List<OrdemProcucao> getTodasOrdensProcucao(){
        return ordemController.findOrdemProcucaoEntities();
    }
    
    // LISTAR ORDEM DE PRODUÇÃO POR ID

    /**
     *
     * @param ordemID
     * @return
     */
        public OrdemProcucao getOrdemProgramacaoId(int ordemID){
        return ordemController.findOrdemProcucao(ordemID);
    }
}
