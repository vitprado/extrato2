/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.dao;

import br.exacta.jpacontroller.DescarregamentoJpaController;
import br.exacta.jpacontroller.DescarregamentoJpaFilter;
import br.exacta.persistencia.Carregamento;
import br.exacta.persistencia.Descarregamento;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thales
 */
public class DescarregamentoDAO {

    private final DescarregamentoJpaController descarregamentoController;
    private final EntityManagerFactory emf;

    /**
     *
     */
    public DescarregamentoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        descarregamentoController = new DescarregamentoJpaController(emf);
    }

    // MÉTODOS DE ACESSO 
    /* CRUD - CREATE, READ, UPDATE e DELETE, respectivamente serão aqui tratados como:
            - ADICIONAR, LISTAR, EDITAR, E REMOVER
     */
    // ADICIONAR
    public void adicionarDescarregamento(Descarregamento descarregamento) throws Exception {
        descarregamentoController.create(descarregamento);
    }

    // EDITAR
    public void editarDescarregamento(Descarregamento descarregamento) throws Exception {
        descarregamentoController.edit(descarregamento);
    }

    // REMOVER
    public void removerDescarregamento(int id) throws Exception {
        descarregamentoController.destroy(id);
    }

    public void removerDescarregamento(String ordem) throws Exception {
        descarregamentoController.destroy(ordem);
    }

    // LISTAR TODOS
    public List<Descarregamento> getTodosDescarregamentos() {
        return descarregamentoController.findDescarregamentoEntities();
    }

    // LISTAR POR ID
    public Descarregamento getDescarregamentolId(int id) {
        return descarregamentoController.findDescarregamento(id);
    }

    public List<String> getEquipamentosDistinct() {
        return descarregamentoController.findEquipamentoDistinct();
    }

    public List<Carregamento> getOrdensDistinct(){
    	return descarregamentoController.findOrdemDistinct();
    }
    
    public List<Descarregamento> getDescarregamentos(DescarregamentoJpaFilter filter) {
        return descarregamentoController.findDescarregamentos(filter);
    }

}
