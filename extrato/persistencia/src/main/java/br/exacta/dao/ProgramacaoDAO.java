/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.ProgramacaoJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Programacao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class ProgramacaoDAO {
    
    private final ProgramacaoJpaController programacaoController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public ProgramacaoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        programacaoController = new ProgramacaoJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR PROGRAMAÇÃO

    /**
     *
     * @param programacao
     * @throws Exception
     */
    public void adicionarProgramacao(Programacao programacao) throws Exception{
        programacaoController.create(programacao);
    }
    
    // EDITAR PROGRAMAÇÃO

    /**
     *
     * @param programacao
     * @throws Exception
     */
    public void editarProgramacao(Programacao programacao) throws Exception{
        programacaoController.edit(programacao);
    }
    
    // REMOVER PROGRAMAÇÃO

    /**
     *
     * @param programacaoID
     * @throws NonexistentEntityException
     */
    public void removerProgramacao(int programacaoID) throws NonexistentEntityException{
        programacaoController.destroy(programacaoID);
    }
    
    // LISTAR TODAS PROGRAMAÇÕES

    /**
     *
     * @return
     */
    public List<Programacao> getTodasPromacoes(){
        return programacaoController.findProgramacaoEntities();
    }
    
    // LISTAR PROGRAMAÇÃO POR ID

    /**
     *
     * @param programacaoID
     * @return
     */
        public Programacao getProgramacaoId(int programacaoID){
        return programacaoController.findProgramacao(programacaoID);
    }
    
}
