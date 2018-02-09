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

    public NivelAcessoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        nivelAcessoController = new NivelAcessoJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR 
    /**
     *
     * @param nivel
     * @throws Exception
     */
    public void adicionarNivelAcesso(NivelAcesso nivel) throws Exception {
        nivelAcessoController.create(nivel);
    }

    // EDITAR 
    /**
     *
     * @param nivel
     * @throws Exception
     */
    public void editarNivelAcesso(NivelAcesso nivel) throws Exception {
        nivelAcessoController.edit(nivel);
    }

    // REMOVER 
    /**
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void removerNivelAcesso(int id) throws NonexistentEntityException {
        nivelAcessoController.destroy(id);
    }

    // LISTAR TODOS
    /**
     *
     * @return
     */
    public List<NivelAcesso> getTodosNiveis() {
        return nivelAcessoController.findNivelAcessoEntities();
    }

    // LISTAR POR ID
    /**
     *
     * @param id
     * @return
     */
    public NivelAcesso getNivelAcessoId(int id) {
        return nivelAcessoController.findNivelAcesso(id);
    }
}
