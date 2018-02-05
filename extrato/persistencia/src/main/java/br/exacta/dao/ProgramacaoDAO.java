/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.ProgramacaoJpaController;
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
    // ADICIONAR 
    /**
     *
     * @param curral
     * @throws Exception
     */
    public void adicionarProgramacao(Programacao programacao) throws Exception {
        programacaoController.create(programacao);
    }

    // EDITAR 
    /**
     *
     * @param curral
     * @throws Exception
     */
    public void editarProgramacao(Programacao programacao) throws Exception {
        programacaoController.edit(programacao);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws java.lang.Exception
     */
    public void removerProgramacao(int id) throws Exception {
        programacaoController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<Programacao> getTodasProgramacoes() {
        return programacaoController.findProgramacaoEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public Programacao getProgramacaolId(int id) {
        return programacaoController.findProgramacao(id);
    }

//    public List<String> getEquipamentosDistinct() {
//        return programacaoController.findEquipamentoDistinct();
//    }
    
}
