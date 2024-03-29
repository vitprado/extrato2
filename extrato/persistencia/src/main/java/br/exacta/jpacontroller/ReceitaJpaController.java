/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Receita;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.ReceitaTemIngredientes;
import java.util.ArrayList;
import java.util.List;
import br.exacta.persistencia.Trato;

/**
 *
 * @author Thales
 */
public class ReceitaJpaController implements Serializable {

    public ReceitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Receita receita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(receita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receita receita) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            receita = em.merge(receita);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receita.getRctCodigo();
                if (findReceita(id) == null) {
                    throw new NonexistentEntityException("The receita with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receita receita;
            try {
                receita = em.getReference(Receita.class, id);
                receita.getRctCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receita with id " + id + " no longer exists.", enfe);
            }
            em.remove(receita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Receita> findReceitaEntities() {
        return findReceitaEntities(true, -1, -1);
    }

    public List<Receita> findReceitaEntities(int maxResults, int firstResult) {
        return findReceitaEntities(false, maxResults, firstResult);
    }

    private List<Receita> findReceitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Receita.class));
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

    public Receita findReceita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Receita.class, id);
        } finally {
            em.close();
        }
    }

    public int getReceitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Receita> rt = cq.from(Receita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> findNameReceitasDistinct() {
        EntityManager em = getEntityManager();
        try {
            Query query;
            query = em.createNativeQuery("select DISTINCT RCT_NOME from RECEITA");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Receita> findReceitaEntitiesAtiva() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb  = em.getCriteriaBuilder();
            CriteriaQuery<Receita> q = cb.createQuery(Receita.class);
            Root<Receita> c = q.from(Receita.class);
            ParameterExpression<Boolean> p = cb.parameter(Boolean.class);
            q.select(c).where(cb.equal(c.get("rctAtivo"), p));
            TypedQuery<Receita> query = em.createQuery(q);
            query.setParameter(p, true);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
