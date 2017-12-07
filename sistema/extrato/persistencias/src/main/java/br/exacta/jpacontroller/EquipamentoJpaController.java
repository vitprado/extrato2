/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Equipamento;
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
public class EquipamentoJpaController implements Serializable {

    public EquipamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipamento equipamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(equipamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipamento equipamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            equipamento = em.merge(equipamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipamento.getEqpCodigo();
                if (findEquipamento(id) == null) {
                    throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.");
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
            Equipamento equipamento;
            try {
                equipamento = em.getReference(Equipamento.class, id);
                equipamento.getEqpCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(equipamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipamento> findEquipamentoEntities() {
        return findEquipamentoEntities(true, -1, -1);
    }

    public List<Equipamento> findEquipamentoEntities(int maxResults, int firstResult) {
        return findEquipamentoEntities(false, maxResults, firstResult);
    }

    private List<Equipamento> findEquipamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipamento.class));
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

    public Equipamento findEquipamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipamento> rt = cq.from(Equipamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
