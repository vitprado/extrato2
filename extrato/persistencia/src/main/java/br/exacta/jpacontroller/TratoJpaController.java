/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.jpacontroller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.Curral;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.Trato;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thales
 */
public class TratoJpaController implements Serializable {

    public TratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Trato create(Trato trato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(trato);
            em.getTransaction().commit();
            return trato;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trato trato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            trato = em.merge(trato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                 Integer id = trato.getTrtCodigo();
                if (findTrato(id) == null) {
                    throw new NonexistentEntityException("The trato with id " + id + " no longer exists.");
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
            Trato trato;
            try {
                trato = em.getReference(Trato.class, id);
                trato.getTrtCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trato with id " + id + " no longer exists.", enfe);
            }
            em.remove(trato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trato> findTratoEntities() {
        return findTratoEntities(true, -1, -1);
    }

    public List<Trato> findTratoEntities(int maxResults, int firstResult) {
        return findTratoEntities(false, maxResults, firstResult);
    }

    private List<Trato> findTratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trato.class));
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

    public Trato findTrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trato> rt = cq.from(Trato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
