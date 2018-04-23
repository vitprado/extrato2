/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.OrdemProducao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
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

    public OrdemProducao create(OrdemProducao ordemProducao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ordemProducao);
            em.getTransaction().commit();
            return ordemProducao;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdemProducao ordemProducao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ordemProducao = em.merge(ordemProducao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordemProducao.getOrdCodigo();
                if (findOrdemProcucao(id) == null) {
                    throw new NonexistentEntityException("The ordemProducao with id " + id + " no longer exists.");
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
            OrdemProducao ordemProducao;
            try {
                ordemProducao = em.getReference(OrdemProducao.class, id);
                ordemProducao.getOrdCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordemProducao with id " + id + " no longer exists.", enfe);
            }
            em.remove(ordemProducao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdemProducao> findOrdemProcucaoEntities() {
        return findOrdemProcucaoEntities(true, -1, -1);
    }

    public List<OrdemProducao> findOrdemProcucaoEntities(int maxResults, int firstResult) {
        return findOrdemProcucaoEntities(false, maxResults, firstResult);
    }

    private List<OrdemProducao> findOrdemProcucaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdemProducao.class));
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

    public OrdemProducao findOrdemProcucao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdemProducao.class, id);
        } finally {
            em.close();
        }
    }

    public OrdemProducao findOrdemProcucao(String descricao) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdemProducao.class, descricao);
        } finally {
            em.close();
        }
    }

    public int getOrdemProcucaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdemProducao> rt = cq.from(OrdemProducao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<OrdemProducao> findOrdemProducaoEntities() { return findOrdemProcucaoEntities(true, -1, -1);}


    public int getOrdemProcucaoMaxNum() {
        EntityManager em = getEntityManager();
        try {

            em.getTransaction().begin();
            Query nativeQuery = em.createNativeQuery("SELECT CAST(cod_int as int) as cod_int from "
            		+ "(select SUBSTR(ORD_DESCRICAO, 6) as cod_int from "
            		+ "ORDEM_PRODUCAO) as tabela_aux order by cod_int DESC FETCH FIRST 1 ROWS ONLY");

            return (int) nativeQuery.getSingleResult();
        } catch (NoResultException e) {
        	return 0;
        	
        } finally {
            em.close();
        }

    }
}
