package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.ItemTrato;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

public class ItemTratoJpaController implements Serializable {

    public ItemTratoJpaController(EntityManagerFactory emf){ this.emf = emf;}
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemTrato itemTrato){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(itemTrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemTrato itemTrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            itemTrato = em.merge(itemTrato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemTrato.getIttCodigo();
                if (findItemTrato(id) == null) {
                    throw new NonexistentEntityException("The ordemProcucao with id " + id + " no longer exists.");
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
            ItemTrato itemTrato;
            try {
                itemTrato = em.getReference(ItemTrato.class, id);
                itemTrato.getIttCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordemProcucao with id " + id + " no longer exists.", enfe);
            }
            em.remove(itemTrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public ItemTrato findItemTrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemTrato.class, id);
        } finally {
            em.close();
        }
    }
}
