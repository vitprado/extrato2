/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.NivelAcesso;
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
public class NivelAcessoJpaController implements Serializable {

    public NivelAcessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelAcesso nivelAcesso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nivelAcesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelAcesso nivelAcesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nivelAcesso = em.merge(nivelAcesso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelAcesso.getNvaCodigo();
                if (findNivelAcesso(id) == null) {
                    throw new NonexistentEntityException("The nivelAcesso with id " + id + " no longer exists.");
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
            NivelAcesso nivelAcesso;
            try {
                nivelAcesso = em.getReference(NivelAcesso.class, id);
                nivelAcesso.getNvaCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelAcesso with id " + id + " no longer exists.", enfe);
            }
            em.remove(nivelAcesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelAcesso> findNivelAcessoEntities() {
        return findNivelAcessoEntities(true, -1, -1);
    }

    public List<NivelAcesso> findNivelAcessoEntities(int maxResults, int firstResult) {
        return findNivelAcessoEntities(false, maxResults, firstResult);
    }

    private List<NivelAcesso> findNivelAcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelAcesso.class));
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

    public NivelAcesso findNivelAcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelAcesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelAcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelAcesso> rt = cq.from(NivelAcesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
