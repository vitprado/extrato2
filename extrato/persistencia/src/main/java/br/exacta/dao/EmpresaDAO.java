/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.EmpresaJpaController;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Empresa;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class EmpresaDAO {
    
    private final EmpresaJpaController empresaController;
    private final EntityManagerFactory emf;
    
    /**
     *
     */
    public EmpresaDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        empresaController = new EmpresaJpaController(emf);
    }
    
    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
    */
    
    // ADICIONAR EMPRESA

    /**
     *
     * @param empresa
     * @throws Exception
     */
    public void adicionarEmpresa(Empresa empresa) throws Exception{
        empresaController.create(empresa);
    }
    
    // EDITAR EMPRESA

    /**
     *
     * @param equipamento
     * @throws Exception
     */
    public void editarEmpresa(Empresa equipamento) throws Exception{
        empresaController.edit(equipamento);
    }
    
    // REMOVER EMPRESA

    /**
     *
     * @param equipamentoID
     * @throws NonexistentEntityException
     */
    public void removerEmpresa(int equipamentoID) throws NonexistentEntityException{
        empresaController.destroy(equipamentoID);
    }
    
    // LISTAR TODOS EMPRESAS

    /**
     *
     * @return
     */
    public List<Empresa> getTodasEmpresas(){
        return empresaController.findEmpresaEntities();
    }
    
    // LISTAR EMPRESA POR ID

    /**
     *
     * @param equipamentoID
     * @return
     */
    public Empresa getEmpresaId(int equipamentoID){
        return empresaController.findEmpresa(equipamentoID);
    }
    
}
