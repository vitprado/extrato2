package br.exacta.dao;

import br.exacta.jpacontroller.OrdemProcucaoJpaController;
import br.exacta.persistencia.ItemTrato;
import br.exacta.persistencia.OrdemProducao;
import br.exacta.persistencia.Trato;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class OrdemProcucaoDAO {

    private final OrdemProcucaoJpaController ordemProcucaoController;
    private final EntityManagerFactory emf;

    public OrdemProcucaoDAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        ordemProcucaoController = new OrdemProcucaoJpaController(emf);
    }

    public OrdemProducao adicionarOrdemProcucao(OrdemProducao ordemProducao) throws Exception {
        return ordemProcucaoController.create(ordemProducao);
    }

    public void editarOrdemProcucao(OrdemProducao ordemProducao) throws Exception {
        ordemProcucaoController.edit(ordemProducao);
    }

    public void removerOrdemProcucao(Integer id) throws Exception {
        ordemProcucaoController.destroy(id);
    }

    public OrdemProducao getOrdemProcucao(Integer id) {
        OrdemProducao ordemProducao = ordemProcucaoController.findOrdemProcucao(id);
        ordenaOrdemProducao(ordemProducao);
        return ordemProducao;
    }

    public OrdemProducao getOrdemProcucao(String descricao) {
        OrdemProducao ordemProducao = ordemProcucaoController.findOrdemProcucao(descricao);
        ordenaOrdemProducao(ordemProducao);
        return ordemProducao;
    }

    public int countOrdemProcucao() {
        return ordemProcucaoController.getOrdemProcucaoCount();
    }

    public List<OrdemProducao> findAllOrdemProcucao() {
        return ordemProcucaoController.findOrdemProducaoEntities();
    }

    public int getOrdemProcucaoMaxNum() {
        return ordemProcucaoController.getOrdemProcucaoMaxNum();
    }
    
    private void ordenaOrdemProducao(OrdemProducao ordemProducao) {
        ordemProducao.getTratos()
                .sort(this::ordenarTratosDeProducao);
        ordemProducao.getTratos()
                .stream()
                .forEach(this::toTrato);
    }

    private void toTrato(Trato trato) {
        trato.getItemTratos().sort(this::ordenaItemDoTrato);
    }

    private int ordenaItemDoTrato(ItemTrato o1, ItemTrato o2) {
        return o1.getIttSequencia().compareTo(o2.getIttSequencia());
    }

    private int ordenarTratosDeProducao(Trato o1, Trato o2) {
        return o1.getTrtNumero().compareTo(o2.getTrtNumero());
    }
}
