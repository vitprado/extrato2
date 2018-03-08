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
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.Receita;
import br.exacta.persistencia.ReceitaTemIngredientes;
import br.exacta.persistencia.ReceitaTemIngredientesPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thales
 */
public class ReceitaTemIngredientesJpaController implements Serializable {

    public ReceitaTemIngredientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReceitaTemIngredientes receitaTemIngredientes) throws PreexistingEntityException, Exception {
        if (receitaTemIngredientes.getReceitaTemIngredientesPK() == null) {
            receitaTemIngredientes.setReceitaTemIngredientesPK(new ReceitaTemIngredientesPK());
        }
        receitaTemIngredientes.getReceitaTemIngredientesPK().setRctCodigo(receitaTemIngredientes.getReceita().getRctCodigo());
        receitaTemIngredientes.getReceitaTemIngredientesPK().setIngCodigo(receitaTemIngredientes.getIngredientes().getIngCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredientes ingredientes = receitaTemIngredientes.getIngredientes();
            if (ingredientes != null) {
                ingredientes = em.getReference(ingredientes.getClass(), ingredientes.getIngCodigo());
                receitaTemIngredientes.setIngredientes(ingredientes);
            }
            Receita receita = receitaTemIngredientes.getReceita();
            if (receita != null) {
                receita = em.getReference(receita.getClass(), receita.getRctCodigo());
                receitaTemIngredientes.setReceita(receita);
            }
            em.persist(receitaTemIngredientes);
            if (ingredientes != null) {
                ingredientes.getReceitaTemIngredientesList().add(receitaTemIngredientes);
                ingredientes = em.merge(ingredientes);
            }
            if (receita != null) {
                receita.getReceitaTemIngredientesList().add(receitaTemIngredientes);
                receita = em.merge(receita);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReceitaTemIngredientes(receitaTemIngredientes.getReceitaTemIngredientesPK()) != null) {
                throw new PreexistingEntityException("ReceitaTemIngredientes " + receitaTemIngredientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReceitaTemIngredientes receitaTemIngredientes) throws NonexistentEntityException, Exception {
        receitaTemIngredientes.getReceitaTemIngredientesPK().setRctCodigo(receitaTemIngredientes.getReceita().getRctCodigo());
        receitaTemIngredientes.getReceitaTemIngredientesPK().setIngCodigo(receitaTemIngredientes.getIngredientes().getIngCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReceitaTemIngredientes persistentReceitaTemIngredientes = em.find(ReceitaTemIngredientes.class, receitaTemIngredientes.getReceitaTemIngredientesPK());
            Ingredientes ingredientesOld = persistentReceitaTemIngredientes.getIngredientes();
            Ingredientes ingredientesNew = receitaTemIngredientes.getIngredientes();
            Receita receitaOld = persistentReceitaTemIngredientes.getReceita();
            Receita receitaNew = receitaTemIngredientes.getReceita();
            if (ingredientesNew != null) {
                ingredientesNew = em.getReference(ingredientesNew.getClass(), ingredientesNew.getIngCodigo());
                receitaTemIngredientes.setIngredientes(ingredientesNew);
            }
            if (receitaNew != null) {
                receitaNew = em.getReference(receitaNew.getClass(), receitaNew.getRctCodigo());
                receitaTemIngredientes.setReceita(receitaNew);
            }
            receitaTemIngredientes = em.merge(receitaTemIngredientes);
            if (ingredientesOld != null && !ingredientesOld.equals(ingredientesNew)) {
                ingredientesOld.getReceitaTemIngredientesList().remove(receitaTemIngredientes);
                ingredientesOld = em.merge(ingredientesOld);
            }
            if (ingredientesNew != null && !ingredientesNew.equals(ingredientesOld)) {
                ingredientesNew.getReceitaTemIngredientesList().add(receitaTemIngredientes);
                ingredientesNew = em.merge(ingredientesNew);
            }
            if (receitaOld != null && !receitaOld.equals(receitaNew)) {
                receitaOld.getReceitaTemIngredientesList().remove(receitaTemIngredientes);
                receitaOld = em.merge(receitaOld);
            }
            if (receitaNew != null && !receitaNew.equals(receitaOld)) {
                receitaNew.getReceitaTemIngredientesList().add(receitaTemIngredientes);
                receitaNew = em.merge(receitaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ReceitaTemIngredientesPK id = receitaTemIngredientes.getReceitaTemIngredientesPK();
                if (findReceitaTemIngredientes(id) == null) {
                    throw new NonexistentEntityException("The receitaTemIngredientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ReceitaTemIngredientesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReceitaTemIngredientes receitaTemIngredientes;
            try {
                receitaTemIngredientes = em.getReference(ReceitaTemIngredientes.class, id);
                receitaTemIngredientes.getReceitaTemIngredientesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receitaTemIngredientes with id " + id + " no longer exists.", enfe);
            }
            Ingredientes ingredientes = receitaTemIngredientes.getIngredientes();
            if (ingredientes != null) {
                ingredientes.getReceitaTemIngredientesList().remove(receitaTemIngredientes);
                ingredientes = em.merge(ingredientes);
            }
            Receita receita = receitaTemIngredientes.getReceita();
            if (receita != null) {
                receita.getReceitaTemIngredientesList().remove(receitaTemIngredientes);
                receita = em.merge(receita);
            }
            em.remove(receitaTemIngredientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deleteByReceita(int rctCodigo) {
        EntityManager em = getEntityManager();
        try {

            em.getTransaction().begin();
            Query nativeQuery = em.createNativeQuery("delete from RECEITA_TEM_INGREDIENTES where RCT_CODIGO = " + rctCodigo);
            int i = nativeQuery.executeUpdate();
            em.getTransaction().commit();

            return i > 0;
        } finally {
            em.close();
        }
    }

    public List<ReceitaTemIngredientes> findReceitaTemIngredientesEntities() {
        return findReceitaTemIngredientesEntities(true, -1, -1);
    }

    public List<ReceitaTemIngredientes> findReceitaTemIngredientesEntities(int maxResults, int firstResult) {
        return findReceitaTemIngredientesEntities(false, maxResults, firstResult);
    }

    private List<ReceitaTemIngredientes> findReceitaTemIngredientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReceitaTemIngredientes.class));
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

    public ReceitaTemIngredientes findReceitaTemIngredientes(ReceitaTemIngredientesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReceitaTemIngredientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getReceitaTemIngredientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReceitaTemIngredientes> rt = cq.from(ReceitaTemIngredientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
