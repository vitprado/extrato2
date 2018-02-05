/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Curral;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.Trato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thales
 */
public class CurralJpaController implements Serializable {

    public CurralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curral curral) {
        if (curral.getTratoList() == null) {
            curral.setTratoList(new ArrayList<Trato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Trato> attachedTratoList = new ArrayList<Trato>();
            for (Trato tratoListTratoToAttach : curral.getTratoList()) {
                tratoListTratoToAttach = em.getReference(tratoListTratoToAttach.getClass(), tratoListTratoToAttach.getTratoPK());
                attachedTratoList.add(tratoListTratoToAttach);
            }
            curral.setTratoList(attachedTratoList);
            em.persist(curral);
            for (Trato tratoListTrato : curral.getTratoList()) {
                Curral oldCurralOfTratoListTrato = tratoListTrato.getCurral();
                tratoListTrato.setCurral(curral);
                tratoListTrato = em.merge(tratoListTrato);
                if (oldCurralOfTratoListTrato != null) {
                    oldCurralOfTratoListTrato.getTratoList().remove(tratoListTrato);
                    oldCurralOfTratoListTrato = em.merge(oldCurralOfTratoListTrato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curral curral) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curral persistentCurral = em.find(Curral.class, curral.getCurCodigo());
            List<Trato> tratoListOld = persistentCurral.getTratoList();
            List<Trato> tratoListNew = curral.getTratoList();
            List<String> illegalOrphanMessages = null;
            for (Trato tratoListOldTrato : tratoListOld) {
                if (!tratoListNew.contains(tratoListOldTrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trato " + tratoListOldTrato + " since its curral field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trato> attachedTratoListNew = new ArrayList<Trato>();
            for (Trato tratoListNewTratoToAttach : tratoListNew) {
                tratoListNewTratoToAttach = em.getReference(tratoListNewTratoToAttach.getClass(), tratoListNewTratoToAttach.getTratoPK());
                attachedTratoListNew.add(tratoListNewTratoToAttach);
            }
            tratoListNew = attachedTratoListNew;
            curral.setTratoList(tratoListNew);
            curral = em.merge(curral);
            for (Trato tratoListNewTrato : tratoListNew) {
                if (!tratoListOld.contains(tratoListNewTrato)) {
                    Curral oldCurralOfTratoListNewTrato = tratoListNewTrato.getCurral();
                    tratoListNewTrato.setCurral(curral);
                    tratoListNewTrato = em.merge(tratoListNewTrato);
                    if (oldCurralOfTratoListNewTrato != null && !oldCurralOfTratoListNewTrato.equals(curral)) {
                        oldCurralOfTratoListNewTrato.getTratoList().remove(tratoListNewTrato);
                        oldCurralOfTratoListNewTrato = em.merge(oldCurralOfTratoListNewTrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = curral.getCurCodigo();
                if (findCurral(id) == null) {
                    throw new NonexistentEntityException("The curral with id " + id + " no longer exists.");
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
            Curral curral;
            try {
                curral = em.getReference(Curral.class, id);
                curral.getCurCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curral with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trato> tratoListOrphanCheck = curral.getTratoList();
            for (Trato tratoListOrphanCheckTrato : tratoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curral (" + curral + ") cannot be destroyed since the Trato " + tratoListOrphanCheckTrato + " in its tratoList field has a non-nullable curral field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(curral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curral> findCurralEntities() {
        return findCurralEntities(true, -1, -1);
    }

    public List<Curral> findCurralEntities(int maxResults, int firstResult) {
        return findCurralEntities(false, maxResults, firstResult);
    }

    private List<Curral> findCurralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curral.class));
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

    public Curral findCurral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curral.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curral> rt = cq.from(Curral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> findNameCurralDistinct() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select DISTINCT CUR_DESCRICAO from CURRAL");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
