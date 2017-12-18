/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Equipamento;
import br.exacta.persistencia.OrdemProcucao;
import br.exacta.persistencia.Receita;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thales
 */
public class OrdemProcucaoJpaController implements Serializable {

    public OrdemProcucaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdemProcucao ordemProcucao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curral curCodigo = ordemProcucao.getCurCodigo();
            if (curCodigo != null) {
                curCodigo = em.getReference(curCodigo.getClass(), curCodigo.getCurCodigo());
                ordemProcucao.setCurCodigo(curCodigo);
            }
            Equipamento eqpCodigo = ordemProcucao.getEqpCodigo();
            if (eqpCodigo != null) {
                eqpCodigo = em.getReference(eqpCodigo.getClass(), eqpCodigo.getEqpCodigo());
                ordemProcucao.setEqpCodigo(eqpCodigo);
            }
            Receita rctCodigo = ordemProcucao.getRctCodigo();
            if (rctCodigo != null) {
                rctCodigo = em.getReference(rctCodigo.getClass(), rctCodigo.getRctCodigo());
                ordemProcucao.setRctCodigo(rctCodigo);
            }
            em.persist(ordemProcucao);
            if (curCodigo != null) {
                curCodigo.getOrdemProcucaoList().add(ordemProcucao);
                curCodigo = em.merge(curCodigo);
            }
            if (eqpCodigo != null) {
                eqpCodigo.getOrdemProcucaoList().add(ordemProcucao);
                eqpCodigo = em.merge(eqpCodigo);
            }
            if (rctCodigo != null) {
                rctCodigo.getOrdemProcucaoList().add(ordemProcucao);
                rctCodigo = em.merge(rctCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdemProcucao ordemProcucao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdemProcucao persistentOrdemProcucao = em.find(OrdemProcucao.class, ordemProcucao.getOrdCodigo());
            Curral curCodigoOld = persistentOrdemProcucao.getCurCodigo();
            Curral curCodigoNew = ordemProcucao.getCurCodigo();
            Equipamento eqpCodigoOld = persistentOrdemProcucao.getEqpCodigo();
            Equipamento eqpCodigoNew = ordemProcucao.getEqpCodigo();
            Receita rctCodigoOld = persistentOrdemProcucao.getRctCodigo();
            Receita rctCodigoNew = ordemProcucao.getRctCodigo();
            if (curCodigoNew != null) {
                curCodigoNew = em.getReference(curCodigoNew.getClass(), curCodigoNew.getCurCodigo());
                ordemProcucao.setCurCodigo(curCodigoNew);
            }
            if (eqpCodigoNew != null) {
                eqpCodigoNew = em.getReference(eqpCodigoNew.getClass(), eqpCodigoNew.getEqpCodigo());
                ordemProcucao.setEqpCodigo(eqpCodigoNew);
            }
            if (rctCodigoNew != null) {
                rctCodigoNew = em.getReference(rctCodigoNew.getClass(), rctCodigoNew.getRctCodigo());
                ordemProcucao.setRctCodigo(rctCodigoNew);
            }
            ordemProcucao = em.merge(ordemProcucao);
            if (curCodigoOld != null && !curCodigoOld.equals(curCodigoNew)) {
                curCodigoOld.getOrdemProcucaoList().remove(ordemProcucao);
                curCodigoOld = em.merge(curCodigoOld);
            }
            if (curCodigoNew != null && !curCodigoNew.equals(curCodigoOld)) {
                curCodigoNew.getOrdemProcucaoList().add(ordemProcucao);
                curCodigoNew = em.merge(curCodigoNew);
            }
            if (eqpCodigoOld != null && !eqpCodigoOld.equals(eqpCodigoNew)) {
                eqpCodigoOld.getOrdemProcucaoList().remove(ordemProcucao);
                eqpCodigoOld = em.merge(eqpCodigoOld);
            }
            if (eqpCodigoNew != null && !eqpCodigoNew.equals(eqpCodigoOld)) {
                eqpCodigoNew.getOrdemProcucaoList().add(ordemProcucao);
                eqpCodigoNew = em.merge(eqpCodigoNew);
            }
            if (rctCodigoOld != null && !rctCodigoOld.equals(rctCodigoNew)) {
                rctCodigoOld.getOrdemProcucaoList().remove(ordemProcucao);
                rctCodigoOld = em.merge(rctCodigoOld);
            }
            if (rctCodigoNew != null && !rctCodigoNew.equals(rctCodigoOld)) {
                rctCodigoNew.getOrdemProcucaoList().add(ordemProcucao);
                rctCodigoNew = em.merge(rctCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordemProcucao.getOrdCodigo();
                if (findOrdemProcucao(id) == null) {
                    throw new NonexistentEntityException("The ordemProcucao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdemProcucao ordemProcucao;
            try {
                ordemProcucao = em.getReference(OrdemProcucao.class, id);
                ordemProcucao.getOrdCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordemProcucao with id " + id + " no longer exists.", enfe);
            }
            Curral curCodigo = ordemProcucao.getCurCodigo();
            if (curCodigo != null) {
                curCodigo.getOrdemProcucaoList().remove(ordemProcucao);
                curCodigo = em.merge(curCodigo);
            }
            Equipamento eqpCodigo = ordemProcucao.getEqpCodigo();
            if (eqpCodigo != null) {
                eqpCodigo.getOrdemProcucaoList().remove(ordemProcucao);
                eqpCodigo = em.merge(eqpCodigo);
            }
            Receita rctCodigo = ordemProcucao.getRctCodigo();
            if (rctCodigo != null) {
                rctCodigo.getOrdemProcucaoList().remove(ordemProcucao);
                rctCodigo = em.merge(rctCodigo);
            }
            em.remove(ordemProcucao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdemProcucao> findOrdemProcucaoEntities() {
        return findOrdemProcucaoEntities(true, -1, -1);
    }

    public List<OrdemProcucao> findOrdemProcucaoEntities(int maxResults, int firstResult) {
        return findOrdemProcucaoEntities(false, maxResults, firstResult);
    }

    private List<OrdemProcucao> findOrdemProcucaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdemProcucao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public OrdemProcucao findOrdemProcucao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdemProcucao.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdemProcucaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdemProcucao> rt = cq.from(OrdemProcucao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
