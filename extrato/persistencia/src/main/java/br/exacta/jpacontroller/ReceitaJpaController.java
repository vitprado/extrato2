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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.ReceitaTemIngredientes;
import java.util.ArrayList;
import java.util.List;
import br.exacta.persistencia.Trato;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (receita.getReceitaTemIngredientesList() == null) {
            receita.setReceitaTemIngredientesList(new ArrayList<ReceitaTemIngredientes>());
        }
        if (receita.getTratoList() == null) {
            receita.setTratoList(new ArrayList<Trato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReceitaTemIngredientes> attachedReceitaTemIngredientesList = new ArrayList<ReceitaTemIngredientes>();
            for (ReceitaTemIngredientes receitaTemIngredientesListReceitaTemIngredientesToAttach : receita.getReceitaTemIngredientesList()) {
                receitaTemIngredientesListReceitaTemIngredientesToAttach = em.getReference(receitaTemIngredientesListReceitaTemIngredientesToAttach.getClass(), receitaTemIngredientesListReceitaTemIngredientesToAttach.getReceitaTemIngredientesPK());
                attachedReceitaTemIngredientesList.add(receitaTemIngredientesListReceitaTemIngredientesToAttach);
            }
            receita.setReceitaTemIngredientesList(attachedReceitaTemIngredientesList);
            List<Trato> attachedTratoList = new ArrayList<Trato>();
            for (Trato tratoListTratoToAttach : receita.getTratoList()) {
                tratoListTratoToAttach = em.getReference(tratoListTratoToAttach.getClass(), tratoListTratoToAttach.getTratoPK());
                attachedTratoList.add(tratoListTratoToAttach);
            }
            receita.setTratoList(attachedTratoList);
            em.persist(receita);
            for (ReceitaTemIngredientes receitaTemIngredientesListReceitaTemIngredientes : receita.getReceitaTemIngredientesList()) {
                Receita oldReceitaOfReceitaTemIngredientesListReceitaTemIngredientes = receitaTemIngredientesListReceitaTemIngredientes.getReceita();
                receitaTemIngredientesListReceitaTemIngredientes.setReceita(receita);
                receitaTemIngredientesListReceitaTemIngredientes = em.merge(receitaTemIngredientesListReceitaTemIngredientes);
                if (oldReceitaOfReceitaTemIngredientesListReceitaTemIngredientes != null) {
                    oldReceitaOfReceitaTemIngredientesListReceitaTemIngredientes.getReceitaTemIngredientesList().remove(receitaTemIngredientesListReceitaTemIngredientes);
                    oldReceitaOfReceitaTemIngredientesListReceitaTemIngredientes = em.merge(oldReceitaOfReceitaTemIngredientesListReceitaTemIngredientes);
                }
            }
            for (Trato tratoListTrato : receita.getTratoList()) {
                Receita oldReceitaOfTratoListTrato = tratoListTrato.getReceita();
                tratoListTrato.setReceita(receita);
                tratoListTrato = em.merge(tratoListTrato);
                if (oldReceitaOfTratoListTrato != null) {
                    oldReceitaOfTratoListTrato.getTratoList().remove(tratoListTrato);
                    oldReceitaOfTratoListTrato = em.merge(oldReceitaOfTratoListTrato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receita receita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receita persistentReceita = em.find(Receita.class, receita.getRctCodigo());
            List<ReceitaTemIngredientes> receitaTemIngredientesListOld = persistentReceita.getReceitaTemIngredientesList();
            List<ReceitaTemIngredientes> receitaTemIngredientesListNew = receita.getReceitaTemIngredientesList();
            List<Trato> tratoListOld = persistentReceita.getTratoList();
            List<Trato> tratoListNew = receita.getTratoList();
            List<String> illegalOrphanMessages = null;
            for (ReceitaTemIngredientes receitaTemIngredientesListOldReceitaTemIngredientes : receitaTemIngredientesListOld) {
                if (!receitaTemIngredientesListNew.contains(receitaTemIngredientesListOldReceitaTemIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReceitaTemIngredientes " + receitaTemIngredientesListOldReceitaTemIngredientes + " since its receita field is not nullable.");
                }
            }
            for (Trato tratoListOldTrato : tratoListOld) {
                if (!tratoListNew.contains(tratoListOldTrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trato " + tratoListOldTrato + " since its receita field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ReceitaTemIngredientes> attachedReceitaTemIngredientesListNew = new ArrayList<ReceitaTemIngredientes>();
            for (ReceitaTemIngredientes receitaTemIngredientesListNewReceitaTemIngredientesToAttach : receitaTemIngredientesListNew) {
                receitaTemIngredientesListNewReceitaTemIngredientesToAttach = em.getReference(receitaTemIngredientesListNewReceitaTemIngredientesToAttach.getClass(), receitaTemIngredientesListNewReceitaTemIngredientesToAttach.getReceitaTemIngredientesPK());
                attachedReceitaTemIngredientesListNew.add(receitaTemIngredientesListNewReceitaTemIngredientesToAttach);
            }
            receitaTemIngredientesListNew = attachedReceitaTemIngredientesListNew;
            receita.setReceitaTemIngredientesList(receitaTemIngredientesListNew);
            List<Trato> attachedTratoListNew = new ArrayList<Trato>();
            for (Trato tratoListNewTratoToAttach : tratoListNew) {
                tratoListNewTratoToAttach = em.getReference(tratoListNewTratoToAttach.getClass(), tratoListNewTratoToAttach.getTratoPK());
                attachedTratoListNew.add(tratoListNewTratoToAttach);
            }
            tratoListNew = attachedTratoListNew;
            receita.setTratoList(tratoListNew);
            receita = em.merge(receita);
            for (ReceitaTemIngredientes receitaTemIngredientesListNewReceitaTemIngredientes : receitaTemIngredientesListNew) {
                if (!receitaTemIngredientesListOld.contains(receitaTemIngredientesListNewReceitaTemIngredientes)) {
                    Receita oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes = receitaTemIngredientesListNewReceitaTemIngredientes.getReceita();
                    receitaTemIngredientesListNewReceitaTemIngredientes.setReceita(receita);
                    receitaTemIngredientesListNewReceitaTemIngredientes = em.merge(receitaTemIngredientesListNewReceitaTemIngredientes);
                    if (oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes != null && !oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes.equals(receita)) {
                        oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes.getReceitaTemIngredientesList().remove(receitaTemIngredientesListNewReceitaTemIngredientes);
                        oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes = em.merge(oldReceitaOfReceitaTemIngredientesListNewReceitaTemIngredientes);
                    }
                }
            }
            for (Trato tratoListNewTrato : tratoListNew) {
                if (!tratoListOld.contains(tratoListNewTrato)) {
                    Receita oldReceitaOfTratoListNewTrato = tratoListNewTrato.getReceita();
                    tratoListNewTrato.setReceita(receita);
                    tratoListNewTrato = em.merge(tratoListNewTrato);
                    if (oldReceitaOfTratoListNewTrato != null && !oldReceitaOfTratoListNewTrato.equals(receita)) {
                        oldReceitaOfTratoListNewTrato.getTratoList().remove(tratoListNewTrato);
                        oldReceitaOfTratoListNewTrato = em.merge(oldReceitaOfTratoListNewTrato);
                    }
                }
            }
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
            List<String> illegalOrphanMessages = null;
            List<ReceitaTemIngredientes> receitaTemIngredientesListOrphanCheck = receita.getReceitaTemIngredientesList();
            for (ReceitaTemIngredientes receitaTemIngredientesListOrphanCheckReceitaTemIngredientes : receitaTemIngredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receita (" + receita + ") cannot be destroyed since the ReceitaTemIngredientes " + receitaTemIngredientesListOrphanCheckReceitaTemIngredientes + " in its receitaTemIngredientesList field has a non-nullable receita field.");
            }
            List<Trato> tratoListOrphanCheck = receita.getTratoList();
            for (Trato tratoListOrphanCheckTrato : tratoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receita (" + receita + ") cannot be destroyed since the Trato " + tratoListOrphanCheckTrato + " in its tratoList field has a non-nullable receita field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

}
