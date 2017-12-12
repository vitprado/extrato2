/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.UsuarioJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Usuario;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class UsuarioDAO {
    
    private final UsuarioJpaController usuarioController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public UsuarioDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        usuarioController = new UsuarioJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR USUARIO

    /**
     *
     * @param usuario
     * @throws Exception
     */
    public void adicionarUsuario(Usuario usuario) throws Exception{
        usuarioController.create(usuario);
    }
    
    // EDITAR USUARIO

    /**
     *
     * @param usuario
     * @throws Exception
     */
    public void editarUsuario(Usuario usuario) throws Exception{
        usuarioController.edit(usuario);
    }
    
    // REMOVER USUARIO

    /**
     *
     * @param usuarioID
     * @throws NonexistentEntityException
     */
    public void removerUsuario(int usuarioID) throws NonexistentEntityException{
        usuarioController.destroy(usuarioID);
    }
    
    // LISTAR TODOS USUARIOS

    /**
     *
     * @return
     */
    public List<Usuario> getTodosUsuarios(){
        return usuarioController.findUsuarioEntities();
    }
    
    // LISTAR USUARIO POR ID

    /**
     *
     * @param usuarioID
     * @return
     */
        public Usuario getUsuarioId(int usuarioID){
        return usuarioController.findUsuario(usuarioID);
    }
    
}
