package br.exacta.jpacontroller;

import br.exacta.dto.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

import static java.lang.String.format;

public class ConsultaOrdemJpaController {

    private EntityManagerFactory emf = null;

    public ConsultaOrdemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<ConsultaOrdemDTO> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindAll(), ConsultaOrdemDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getFindAll() {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.ConsultaOrdemDTO( e.eqpDescricao , " +
                " (SELECT COUNT(ie.eqpCodigo) " +
                " FROM Equipamento ie JOIN OrdemProducao iop " +
                " WHERE ie.eqpCodigo = iop.equipamento.eqpCodigo AND ie.eqpCodigo = e.eqpCodigo) , " +
                " op.ordCodigo) " +
                " FROM OrdemProducao op JOIN Equipamento e WHERE op.equipamento.eqpCodigo =  e.eqpCodigo " +
                " ORDER BY e.eqpDescricao,  op.ordCodigo ");

        return stringBuilder.toString();
    }

    public List<ConsultaOrdemDTO> findByFilter(ConsultaOrdemFilter filter) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindByFilter(filter), ConsultaOrdemDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getFindByFilter(ConsultaOrdemFilter filter) {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.ConsultaOrdemDTO(e.eqpDescricao, " +
                " (SELECT COUNT(ie.eqpCodigo) " +
                " FROM Equipamento ie, OrdemProducao iop " +
                " WHERE ie.eqpCodigo = iop.equipamento.eqpCodigo AND ie.eqpCodigo = e.eqpCodigo), " +
                " op.ordCodigo)" +
                " FROM Equipamento e, OrdemProducao op " +
                " WHERE e.eqpCodigo = op.equipamento.eqpCodigo ");

        if (filter.getEquipamento() != null) {
            stringBuilder.append(format("AND e.eqpCodigo = '%s' ORDER BY op.ordCodigo ",
                    filter.getEquipamento().getEqpCodigo()));
        }

        return stringBuilder.toString();
    }

    public List<ListaOrdemProducaoDTO> findByEquipamentoForListaOrdens(Integer ordCodigo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindByEquipamentoForListaOrdens(ordCodigo), ConsultaOrdemDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getFindByEquipamentoForListaOrdens(Integer eqpCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ListaOrdemProcucaoDTO(e.eqpDescricao, op.ordCodigo, op.ordDescricao, r.rct_nome) " +
                " FROM equipamento e JOIN ordem_producao op JOIN trato t JOIN receita r " +
                " WHERE e.eqp_codigo = op.eqp_codigo " +
                " AND op.ord_codigo = t.ord_codigo " +
                " AND t.rct_codigo = r.rct_codigo " +
                " AND op.ord_codigo = '%s' " +
                " ORDER BY op.ord_Codigo, t.trtCodigo ", eqpCodigo.toString()));

        return stringBuilder.toString();
    }

    public List<OrdemTratosDTO> findOrdemTrato(Integer ordCodigo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindOrdemTrato(ordCodigo), OrdemTratosDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public String getFindOrdemTrato(Integer ordCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.OrdemTratosDTO(op.ordDescricao, " +
                " (SELECT COUNT(iop.ordCodigo)" +
                " FROM OrdemProducao iop JOIN Trato it " +
                " WHERE iop.ordCodigo = it.ordemProducao.ordCodigo AND iop.ordCodigo = op.ordCodigo), " +
                " t.trtCodigo, " +
                " t.receita.rctCodigo) " +
                " FROM OrdemProducao op JOIN Trato t " +
                " WHERE op.ordCodigo = t.ordemProducao.ordCodigo AND op.ordCodigo =  '%s' " +
                " ORDER BY t.trtNumero ", ordCodigo.toString()));

        return stringBuilder.toString();
    }

    public List<ItemTratoCurralDTO> findItemTratoCurral(Integer trtCodigo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindItemTratoCurral(trtCodigo), ItemTratoCurralDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getFindItemTratoCurral(Integer trtCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ItemTratoCurralDTO( " +
                " (SELECT COUNT(iit.trato.trtCodigo) " +
                " FROM ItemTrato iit JOIN Curral ic " +
                " WHERE iit.curral.curCodigo = ic.curCodigo AND iit.trato.trtCodigo = '%s' ) " +
                " , c.curDescricao " +
                " , it.ittPeso) " +
                " FROM ItemTrato it JOIN Curral c " +
                " WHERE it.curral.curCodigo = c.curCodigo AND it.trato.trtCodigo = '%s' " +
                " ORDER BY it.ittSequencia ", trtCodigo.toString(), trtCodigo.toString()));

        return stringBuilder.toString();
    }

    public List<ReceitaIngredienteDTO> findReceitaIngrediente(Integer rctCodigo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindReceitaIngrediente(rctCodigo), ReceitaIngredienteDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getFindReceitaIngrediente(Integer rctCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ReceitaIngredienteDTO(r.rctNome, i.ingAbreviacao, i.ingTolerancia, rti.rtiProporcao) " +
                " FROM ReceitaTemIngredientes rti, Receita r, Ingredientes i " +
                " WHERE r.rctCodigo = rti.receita.rctCodigo and " +
                " rti.ingredientes.ingCodigo = i.ingCodigo AND r.rctCodigo =  '%s' " +
                " ORDER BY i.ingCodigo ", rctCodigo.toString()));

        return stringBuilder.toString();
    }


}
