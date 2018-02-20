package br.exacta.jpacontroller;

import br.exacta.dto.ConsultaOrdemDTO;
import br.exacta.dto.ItemTratoCurralDTO;
import br.exacta.dto.OrdemTratosDTO;
import br.exacta.dto.ReceitaIngredienteDTO;

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

    public List<ConsultaOrdemDTO> findByEquipamento(Integer ordCodigo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getFindByEquipamento(ordCodigo), ConsultaOrdemDTO.class);

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
                " ORDER BY e.eqpDescricao");

        return stringBuilder.toString();
    }

    private String getFindByEquipamento(Integer eqpCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ConsultaOrdemDTO(e.eqpDescricao, " +
                " (SELECT COUNT(ie.eqpCodigo) " +
                " FROM Equipamento ie JOIN OrdemProducao iop " +
                " WHERE ie.eqpCodigo = iop.eqpCodigo AND ie.eqpCodigo = e.eqpCodigo), " +
                " op.ord_codigo)" +
                " FROM Equipamento e JOIN OrdemProducao op " +
                " WHERE e.eqpCodigo = op.eqpCodigo AND e.eqpCodigo = '%s' ORDER BY e.eqpDescricao", eqpCodigo.toString()));

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
                " WHERE op.ordCodigo = t.ordemProducao.ordCodigo AND op.ordCodigo =  '%s' ", ordCodigo.toString()));

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

    public String getFindItemTratoCurral(Integer trtCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ItemTratoCurralDTO( " +
                " (SELECT COUNT(iit.trato.trtCodigo) " +
                " FROM ItemTrato iit JOIN Curral ic " +
                " WHERE iit.curral.curCodigo = ic.curCodigo AND iit.trato.trtCodigo = '%s' ) " +
                " , c.curDescricao " +
                " , it.ittPeso) " +
                " FROM ItemTrato it JOIN Curral c " +
                " WHERE it.curral.curCodigo = c.curCodigo AND it.trato.trtCodigo = '%s' " +
                " ORDER BY c.curDescricao", trtCodigo.toString(), trtCodigo.toString()));

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

    public String getFindReceitaIngrediente(Integer rctCodigo) {
        StringBuilder stringBuilder = new StringBuilder(format("SELECT new br.exacta.dto.ReceitaIngredienteDTO(r.rctNome, i.ingNome, i.ingTolerancia, rti.rtiProporcao) " +
                " FROM ReceitaTemIngredientes rti " +
                " JOIN Receita r ON r.rctCodigo = rti.receita.rctCodigo " +
                " JOIN Ingredientes i " +
                " WHERE rti.ingredientes.ingCodigo = i.ingCodigo AND r.rctCodigo =  '%s' ", rctCodigo.toString()));

        return stringBuilder.toString();
    }
}
