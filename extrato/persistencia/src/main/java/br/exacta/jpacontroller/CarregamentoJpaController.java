/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Carregamento;
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
public class CarregamentoJpaController implements Serializable {

    public CarregamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carregamento carregamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(carregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carregamento carregamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            carregamento = em.merge(carregamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carregamento.getRdcCodigo();
                if (findCarregamento(id) == null) {
                    throw new NonexistentEntityException("The carregamento with id " + id + " no longer exists.");
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
            Carregamento carregamento;
            try {
                carregamento = em.getReference(Carregamento.class, id);
                carregamento.getRdcCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carregamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(carregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carregamento> findCarregamentoEntities() {
        return findCarregamentoEntities(true, -1, -1);
    }

    public List<Carregamento> findCarregamentoEntities(int maxResults, int firstResult) {
        return findCarregamentoEntities(false, maxResults, firstResult);
    }

    private List<Carregamento> findCarregamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carregamento.class));
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

    public Carregamento findCarregamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carregamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarregamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carregamento> rt = cq.from(Carregamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
