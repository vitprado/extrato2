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
import br.exacta.persistencia.TratoPK;
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

    public void create(Trato trato) throws PreexistingEntityException, Exception {
        if (trato.getTratoPK() == null) {
            trato.setTratoPK(new TratoPK());
        }
        trato.getTratoPK().setCurCodigo(trato.getCurral().getCurCodigo());
        trato.getTratoPK().setRctCodigo(trato.getReceita().getRctCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curral curral = trato.getCurral();
            if (curral != null) {
                curral = em.getReference(curral.getClass(), curral.getCurCodigo());
                trato.setCurral(curral);
            }
            Receita receita = trato.getReceita();
            if (receita != null) {
                receita = em.getReference(receita.getClass(), receita.getRctCodigo());
                trato.setReceita(receita);
            }
            em.persist(trato);
            if (curral != null) {
                curral.getTratoList().add(trato);
                curral = em.merge(curral);
            }
            if (receita != null) {
                receita.getTratoList().add(trato);
                receita = em.merge(receita);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrato(trato.getTratoPK()) != null) {
                throw new PreexistingEntityException("Trato " + trato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trato trato) throws NonexistentEntityException, Exception {
        trato.getTratoPK().setCurCodigo(trato.getCurral().getCurCodigo());
        trato.getTratoPK().setRctCodigo(trato.getReceita().getRctCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trato persistentTrato = em.find(Trato.class, trato.getTratoPK());
            Curral curralOld = persistentTrato.getCurral();
            Curral curralNew = trato.getCurral();
            Receita receitaOld = persistentTrato.getReceita();
            Receita receitaNew = trato.getReceita();
            if (curralNew != null) {
                curralNew = em.getReference(curralNew.getClass(), curralNew.getCurCodigo());
                trato.setCurral(curralNew);
            }
            if (receitaNew != null) {
                receitaNew = em.getReference(receitaNew.getClass(), receitaNew.getRctCodigo());
                trato.setReceita(receitaNew);
            }
            trato = em.merge(trato);
            if (curralOld != null && !curralOld.equals(curralNew)) {
                curralOld.getTratoList().remove(trato);
                curralOld = em.merge(curralOld);
            }
            if (curralNew != null && !curralNew.equals(curralOld)) {
                curralNew.getTratoList().add(trato);
                curralNew = em.merge(curralNew);
            }
            if (receitaOld != null && !receitaOld.equals(receitaNew)) {
                receitaOld.getTratoList().remove(trato);
                receitaOld = em.merge(receitaOld);
            }
            if (receitaNew != null && !receitaNew.equals(receitaOld)) {
                receitaNew.getTratoList().add(trato);
                receitaNew = em.merge(receitaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TratoPK id = trato.getTratoPK();
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

    public void destroy(TratoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trato trato;
            try {
                trato = em.getReference(Trato.class, id);
                trato.getTratoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trato with id " + id + " no longer exists.", enfe);
            }
            Curral curral = trato.getCurral();
            if (curral != null) {
                curral.getTratoList().remove(trato);
                curral = em.merge(curral);
            }
            Receita receita = trato.getReceita();
            if (receita != null) {
                receita.getTratoList().remove(trato);
                receita = em.merge(receita);
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

    public Trato findTrato(TratoPK id) {
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
