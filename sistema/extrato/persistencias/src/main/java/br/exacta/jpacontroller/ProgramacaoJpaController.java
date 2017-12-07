/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Programacao;
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
public class ProgramacaoJpaController implements Serializable {

    public ProgramacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programacao programacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(programacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programacao programacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            programacao = em.merge(programacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = programacao.getPrgCodigo();
                if (findProgramacao(id) == null) {
                    throw new NonexistentEntityException("The programacao with id " + id + " no longer exists.");
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
            Programacao programacao;
            try {
                programacao = em.getReference(Programacao.class, id);
                programacao.getPrgCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(programacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programacao> findProgramacaoEntities() {
        return findProgramacaoEntities(true, -1, -1);
    }

    public List<Programacao> findProgramacaoEntities(int maxResults, int firstResult) {
        return findProgramacaoEntities(false, maxResults, firstResult);
    }

    private List<Programacao> findProgramacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programacao.class));
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

    public Programacao findProgramacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programacao> rt = cq.from(Programacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
