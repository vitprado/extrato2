/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.IllegalOrphanException;
import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Ingredientes;
import br.exacta.persistencia.ReceitaTemIngredientes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thales
 */
public class IngredientesJpaController implements Serializable {

    public IngredientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingredientes ingredientes) {
        if (ingredientes.getReceitaTemIngredientesList() == null) {
            ingredientes.setReceitaTemIngredientesList(new ArrayList<ReceitaTemIngredientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReceitaTemIngredientes> attachedReceitaTemIngredientesList = new ArrayList<ReceitaTemIngredientes>();
            for (ReceitaTemIngredientes receitaTemIngredientesListReceitaTemIngredientesToAttach : ingredientes.getReceitaTemIngredientesList()) {
                receitaTemIngredientesListReceitaTemIngredientesToAttach = em.getReference(receitaTemIngredientesListReceitaTemIngredientesToAttach.getClass(), receitaTemIngredientesListReceitaTemIngredientesToAttach.getRtiCodigo());
                attachedReceitaTemIngredientesList.add(receitaTemIngredientesListReceitaTemIngredientesToAttach);
            }
            ingredientes.setReceitaTemIngredientesList(attachedReceitaTemIngredientesList);
            em.persist(ingredientes);
            for (ReceitaTemIngredientes receitaTemIngredientesListReceitaTemIngredientes : ingredientes.getReceitaTemIngredientesList()) {
                Ingredientes oldIngredientesOfReceitaTemIngredientesListReceitaTemIngredientes = receitaTemIngredientesListReceitaTemIngredientes.getIngredientes();
                receitaTemIngredientesListReceitaTemIngredientes.setIngredientes(ingredientes);
                receitaTemIngredientesListReceitaTemIngredientes = em.merge(receitaTemIngredientesListReceitaTemIngredientes);
                if (oldIngredientesOfReceitaTemIngredientesListReceitaTemIngredientes != null) {
                    oldIngredientesOfReceitaTemIngredientesListReceitaTemIngredientes.getReceitaTemIngredientesList().remove(receitaTemIngredientesListReceitaTemIngredientes);
                    oldIngredientesOfReceitaTemIngredientesListReceitaTemIngredientes = em.merge(oldIngredientesOfReceitaTemIngredientesListReceitaTemIngredientes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingredientes ingredientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredientes persistentIngredientes = em.find(Ingredientes.class, ingredientes.getIngCodigo());
            List<ReceitaTemIngredientes> receitaTemIngredientesListOld = persistentIngredientes.getReceitaTemIngredientesList();
            List<ReceitaTemIngredientes> receitaTemIngredientesListNew = ingredientes.getReceitaTemIngredientesList();
            List<String> illegalOrphanMessages = null;
            for (ReceitaTemIngredientes receitaTemIngredientesListOldReceitaTemIngredientes : receitaTemIngredientesListOld) {
                if (!receitaTemIngredientesListNew.contains(receitaTemIngredientesListOldReceitaTemIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReceitaTemIngredientes " + receitaTemIngredientesListOldReceitaTemIngredientes + " since its ingredientes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ReceitaTemIngredientes> attachedReceitaTemIngredientesListNew = new ArrayList<ReceitaTemIngredientes>();
            for (ReceitaTemIngredientes receitaTemIngredientesListNewReceitaTemIngredientesToAttach : receitaTemIngredientesListNew) {
                receitaTemIngredientesListNewReceitaTemIngredientesToAttach = em.getReference(receitaTemIngredientesListNewReceitaTemIngredientesToAttach.getClass(), receitaTemIngredientesListNewReceitaTemIngredientesToAttach.getRtiCodigo());
                attachedReceitaTemIngredientesListNew.add(receitaTemIngredientesListNewReceitaTemIngredientesToAttach);
            }
            receitaTemIngredientesListNew = attachedReceitaTemIngredientesListNew;
            ingredientes.setReceitaTemIngredientesList(receitaTemIngredientesListNew);
            ingredientes = em.merge(ingredientes);
            for (ReceitaTemIngredientes receitaTemIngredientesListNewReceitaTemIngredientes : receitaTemIngredientesListNew) {
                if (!receitaTemIngredientesListOld.contains(receitaTemIngredientesListNewReceitaTemIngredientes)) {
                    Ingredientes oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes = receitaTemIngredientesListNewReceitaTemIngredientes.getIngredientes();
                    receitaTemIngredientesListNewReceitaTemIngredientes.setIngredientes(ingredientes);
                    receitaTemIngredientesListNewReceitaTemIngredientes = em.merge(receitaTemIngredientesListNewReceitaTemIngredientes);
                    if (oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes != null && !oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes.equals(ingredientes)) {
                        oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes.getReceitaTemIngredientesList().remove(receitaTemIngredientesListNewReceitaTemIngredientes);
                        oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes = em.merge(oldIngredientesOfReceitaTemIngredientesListNewReceitaTemIngredientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingredientes.getIngCodigo();
                if (findIngredientes(id) == null) {
                    throw new NonexistentEntityException("The ingredientes with id " + id + " no longer exists.");
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
            Ingredientes ingredientes;
            try {
                ingredientes = em.getReference(Ingredientes.class, id);
                ingredientes.getIngCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingredientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ReceitaTemIngredientes> receitaTemIngredientesListOrphanCheck = ingredientes.getReceitaTemIngredientesList();
            for (ReceitaTemIngredientes receitaTemIngredientesListOrphanCheckReceitaTemIngredientes : receitaTemIngredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingredientes (" + ingredientes + ") cannot be destroyed since the ReceitaTemIngredientes " + receitaTemIngredientesListOrphanCheckReceitaTemIngredientes + " in its receitaTemIngredientesList field has a non-nullable ingredientes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ingredientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingredientes> findIngredientesEntities() {
        return findIngredientesEntities(true, -1, -1);
    }

    public List<Ingredientes> findIngredientesEntities(int maxResults, int firstResult) {
        return findIngredientesEntities(false, maxResults, firstResult);
    }

    private List<Ingredientes> findIngredientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingredientes.class));
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

    public Ingredientes findIngredientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingredientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingredientes> rt = cq.from(Ingredientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> findNameIngredientesDistinct() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select DISTINCT ING_NOME from INGREDIENTES");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<String> findAbrevIngredientesDistinct() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select DISTINCT ING_ABREVIACAO from INGREDIENTES");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
