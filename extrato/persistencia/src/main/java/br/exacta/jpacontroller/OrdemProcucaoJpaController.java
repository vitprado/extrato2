/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.OrdemProcucao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            em.persist(ordemProcucao);
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
            ordemProcucao = em.merge(ordemProcucao);
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
