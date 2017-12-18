/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.Ingredientes;
import java.util.ArrayList;
import java.util.List;
import br.exacta.persistencia.OrdemProcucao;
import br.exacta.persistencia.Receita;
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
        if (receita.getIngredientesList() == null) {
            receita.setIngredientesList(new ArrayList<Ingredientes>());
        }
        if (receita.getOrdemProcucaoList() == null) {
            receita.setOrdemProcucaoList(new ArrayList<OrdemProcucao>());
        }
        if (receita.getTratoList() == null) {
            receita.setTratoList(new ArrayList<Trato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingredientes> attachedIngredientesList = new ArrayList<Ingredientes>();
            for (Ingredientes ingredientesListIngredientesToAttach : receita.getIngredientesList()) {
                ingredientesListIngredientesToAttach = em.getReference(ingredientesListIngredientesToAttach.getClass(), ingredientesListIngredientesToAttach.getIngCodigo());
                attachedIngredientesList.add(ingredientesListIngredientesToAttach);
            }
            receita.setIngredientesList(attachedIngredientesList);
            List<OrdemProcucao> attachedOrdemProcucaoList = new ArrayList<OrdemProcucao>();
            for (OrdemProcucao ordemProcucaoListOrdemProcucaoToAttach : receita.getOrdemProcucaoList()) {
                ordemProcucaoListOrdemProcucaoToAttach = em.getReference(ordemProcucaoListOrdemProcucaoToAttach.getClass(), ordemProcucaoListOrdemProcucaoToAttach.getOrdCodigo());
                attachedOrdemProcucaoList.add(ordemProcucaoListOrdemProcucaoToAttach);
            }
            receita.setOrdemProcucaoList(attachedOrdemProcucaoList);
            List<Trato> attachedTratoList = new ArrayList<Trato>();
            for (Trato tratoListTratoToAttach : receita.getTratoList()) {
                tratoListTratoToAttach = em.getReference(tratoListTratoToAttach.getClass(), tratoListTratoToAttach.getTratoPK());
                attachedTratoList.add(tratoListTratoToAttach);
            }
            receita.setTratoList(attachedTratoList);
            em.persist(receita);
            for (Ingredientes ingredientesListIngredientes : receita.getIngredientesList()) {
                ingredientesListIngredientes.getReceitaList().add(receita);
                ingredientesListIngredientes = em.merge(ingredientesListIngredientes);
            }
            for (OrdemProcucao ordemProcucaoListOrdemProcucao : receita.getOrdemProcucaoList()) {
                Receita oldRctCodigoOfOrdemProcucaoListOrdemProcucao = ordemProcucaoListOrdemProcucao.getRctCodigo();
                ordemProcucaoListOrdemProcucao.setRctCodigo(receita);
                ordemProcucaoListOrdemProcucao = em.merge(ordemProcucaoListOrdemProcucao);
                if (oldRctCodigoOfOrdemProcucaoListOrdemProcucao != null) {
                    oldRctCodigoOfOrdemProcucaoListOrdemProcucao.getOrdemProcucaoList().remove(ordemProcucaoListOrdemProcucao);
                    oldRctCodigoOfOrdemProcucaoListOrdemProcucao = em.merge(oldRctCodigoOfOrdemProcucaoListOrdemProcucao);
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
            List<Ingredientes> ingredientesListOld = persistentReceita.getIngredientesList();
            List<Ingredientes> ingredientesListNew = receita.getIngredientesList();
            List<OrdemProcucao> ordemProcucaoListOld = persistentReceita.getOrdemProcucaoList();
            List<OrdemProcucao> ordemProcucaoListNew = receita.getOrdemProcucaoList();
            List<Trato> tratoListOld = persistentReceita.getTratoList();
            List<Trato> tratoListNew = receita.getTratoList();
            List<String> illegalOrphanMessages = null;
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
            List<Ingredientes> attachedIngredientesListNew = new ArrayList<Ingredientes>();
            for (Ingredientes ingredientesListNewIngredientesToAttach : ingredientesListNew) {
                ingredientesListNewIngredientesToAttach = em.getReference(ingredientesListNewIngredientesToAttach.getClass(), ingredientesListNewIngredientesToAttach.getIngCodigo());
                attachedIngredientesListNew.add(ingredientesListNewIngredientesToAttach);
            }
            ingredientesListNew = attachedIngredientesListNew;
            receita.setIngredientesList(ingredientesListNew);
            List<OrdemProcucao> attachedOrdemProcucaoListNew = new ArrayList<OrdemProcucao>();
            for (OrdemProcucao ordemProcucaoListNewOrdemProcucaoToAttach : ordemProcucaoListNew) {
                ordemProcucaoListNewOrdemProcucaoToAttach = em.getReference(ordemProcucaoListNewOrdemProcucaoToAttach.getClass(), ordemProcucaoListNewOrdemProcucaoToAttach.getOrdCodigo());
                attachedOrdemProcucaoListNew.add(ordemProcucaoListNewOrdemProcucaoToAttach);
            }
            ordemProcucaoListNew = attachedOrdemProcucaoListNew;
            receita.setOrdemProcucaoList(ordemProcucaoListNew);
            List<Trato> attachedTratoListNew = new ArrayList<Trato>();
            for (Trato tratoListNewTratoToAttach : tratoListNew) {
                tratoListNewTratoToAttach = em.getReference(tratoListNewTratoToAttach.getClass(), tratoListNewTratoToAttach.getTratoPK());
                attachedTratoListNew.add(tratoListNewTratoToAttach);
            }
            tratoListNew = attachedTratoListNew;
            receita.setTratoList(tratoListNew);
            receita = em.merge(receita);
            for (Ingredientes ingredientesListOldIngredientes : ingredientesListOld) {
                if (!ingredientesListNew.contains(ingredientesListOldIngredientes)) {
                    ingredientesListOldIngredientes.getReceitaList().remove(receita);
                    ingredientesListOldIngredientes = em.merge(ingredientesListOldIngredientes);
                }
            }
            for (Ingredientes ingredientesListNewIngredientes : ingredientesListNew) {
                if (!ingredientesListOld.contains(ingredientesListNewIngredientes)) {
                    ingredientesListNewIngredientes.getReceitaList().add(receita);
                    ingredientesListNewIngredientes = em.merge(ingredientesListNewIngredientes);
                }
            }
            for (OrdemProcucao ordemProcucaoListOldOrdemProcucao : ordemProcucaoListOld) {
                if (!ordemProcucaoListNew.contains(ordemProcucaoListOldOrdemProcucao)) {
                    ordemProcucaoListOldOrdemProcucao.setRctCodigo(null);
                    ordemProcucaoListOldOrdemProcucao = em.merge(ordemProcucaoListOldOrdemProcucao);
                }
            }
            for (OrdemProcucao ordemProcucaoListNewOrdemProcucao : ordemProcucaoListNew) {
                if (!ordemProcucaoListOld.contains(ordemProcucaoListNewOrdemProcucao)) {
                    Receita oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao = ordemProcucaoListNewOrdemProcucao.getRctCodigo();
                    ordemProcucaoListNewOrdemProcucao.setRctCodigo(receita);
                    ordemProcucaoListNewOrdemProcucao = em.merge(ordemProcucaoListNewOrdemProcucao);
                    if (oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao != null && !oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao.equals(receita)) {
                        oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao.getOrdemProcucaoList().remove(ordemProcucaoListNewOrdemProcucao);
                        oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao = em.merge(oldRctCodigoOfOrdemProcucaoListNewOrdemProcucao);
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
            List<Ingredientes> ingredientesList = receita.getIngredientesList();
            for (Ingredientes ingredientesListIngredientes : ingredientesList) {
                ingredientesListIngredientes.getReceitaList().remove(receita);
                ingredientesListIngredientes = em.merge(ingredientesListIngredientes);
            }
            List<OrdemProcucao> ordemProcucaoList = receita.getOrdemProcucaoList();
            for (OrdemProcucao ordemProcucaoListOrdemProcucao : ordemProcucaoList) {
                ordemProcucaoListOrdemProcucao.setRctCodigo(null);
                ordemProcucaoListOrdemProcucao = em.merge(ordemProcucaoListOrdemProcucao);
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
    
}
