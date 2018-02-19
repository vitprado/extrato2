package br.exacta.dao;

import br.exacta.jpacontroller.OrdemProcucaoJpaController;
import br.exacta.persistencia.OrdemProducao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class OrdemProcucaoDAO {

    private final OrdemProcucaoJpaController ordemProcucaoController;
    private final EntityManagerFactory emf;

    public OrdemProcucaoDAO (){
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        ordemProcucaoController = new OrdemProcucaoJpaController(emf);
    }

    public OrdemProducao adicionarOrdemProcucao(OrdemProducao ordemProducao) throws Exception { return ordemProcucaoController.create(ordemProducao);}

    public void editarOrdemProcucao(OrdemProducao ordemProducao) throws Exception { ordemProcucaoController.edit(ordemProducao);}

    public void removerOrdemProcucao(Integer id) throws Exception { ordemProcucaoController.destroy(id);}

    public OrdemProducao getOrdemProcucao(Integer id) { return ordemProcucaoController.findOrdemProcucao(id);}

    public OrdemProducao getOrdemProcucao(String descricao) { return ordemProcucaoController.findOrdemProcucao(descricao);}

    public int countOrdemProcucao(){ return ordemProcucaoController.getOrdemProcucaoCount();}

    public List<OrdemProducao> findAllOrdemProcucao() { return ordemProcucaoController.findOrdemProducaoEntities();}
}
