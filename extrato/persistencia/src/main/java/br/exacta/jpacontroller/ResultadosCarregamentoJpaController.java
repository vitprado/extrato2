/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.jpacontroller.exceptions.PreexistingEntityException;
import br.exacta.persistencia.ResultadosCarregamento;
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
public class ResultadosCarregamentoJpaController implements Serializable {

    public ResultadosCarregamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResultadosCarregamento resultadosCarregamento) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(resultadosCarregamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResultadosCarregamento(resultadosCarregamento.getRcgCodigoPai()) != null) {
                throw new PreexistingEntityException("ResultadosCarregamento " + resultadosCarregamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResultadosCarregamento resultadosCarregamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            resultadosCarregamento = em.merge(resultadosCarregamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resultadosCarregamento.getRcgCodigoPai();
                if (findResultadosCarregamento(id) == null) {
                    throw new NonexistentEntityException("The resultadosCarregamento with id " + id + " no longer exists.");
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
            ResultadosCarregamento resultadosCarregamento;
            try {
                resultadosCarregamento = em.getReference(ResultadosCarregamento.class, id);
                resultadosCarregamento.getRcgCodigoPai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resultadosCarregamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(resultadosCarregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResultadosCarregamento> findResultadosCarregamentoEntities() {
        return findResultadosCarregamentoEntities(true, -1, -1);
    }

    public List<ResultadosCarregamento> findResultadosCarregamentoEntities(int maxResults, int firstResult) {
        return findResultadosCarregamentoEntities(false, maxResults, firstResult);
    }

    private List<ResultadosCarregamento> findResultadosCarregamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResultadosCarregamento.class));
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

    public ResultadosCarregamento findResultadosCarregamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResultadosCarregamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getResultadosCarregamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResultadosCarregamento> rt = cq.from(ResultadosCarregamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
