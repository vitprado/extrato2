/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Descarregamento;
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
public class DescarregamentoJpaController implements Serializable {

    public DescarregamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Descarregamento descarregamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(descarregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Descarregamento descarregamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            descarregamento = em.merge(descarregamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = descarregamento.getRdgCodigo();
                if (findDescarregamento(id) == null) {
                    throw new NonexistentEntityException("The descarregamento with id " + id + " no longer exists.");
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
            Descarregamento descarregamento;
            try {
                descarregamento = em.getReference(Descarregamento.class, id);
                descarregamento.getRdgCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The descarregamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(descarregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Descarregamento> findDescarregamentoEntities() {
        return findDescarregamentoEntities(true, -1, -1);
    }

    public List<Descarregamento> findDescarregamentoEntities(int maxResults, int firstResult) {
        return findDescarregamentoEntities(false, maxResults, firstResult);
    }

    private List<Descarregamento> findDescarregamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Descarregamento.class));
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

    public Descarregamento findDescarregamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Descarregamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDescarregamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Descarregamento> rt = cq.from(Descarregamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
