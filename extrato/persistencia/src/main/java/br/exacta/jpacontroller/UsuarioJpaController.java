/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.Empresa;
import br.exacta.persistencia.NivelAcesso;
import br.exacta.persistencia.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thales
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empCodigo = usuario.getEmpCodigo();
            if (empCodigo != null) {
                empCodigo = em.getReference(empCodigo.getClass(), empCodigo.getEmpCodigo());
                usuario.setEmpCodigo(empCodigo);
            }
            NivelAcesso nvaCodigo = usuario.getNvaCodigo();
            if (nvaCodigo != null) {
                nvaCodigo = em.getReference(nvaCodigo.getClass(), nvaCodigo.getNvaCodigo());
                usuario.setNvaCodigo(nvaCodigo);
            }
            em.persist(usuario);
            if (empCodigo != null) {
                empCodigo.getUsuarioList().add(usuario);
                empCodigo = em.merge(empCodigo);
            }
            if (nvaCodigo != null) {
                nvaCodigo.getUsuarioList().add(usuario);
                nvaCodigo = em.merge(nvaCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuCodigo());
            Empresa empCodigoOld = persistentUsuario.getEmpCodigo();
            Empresa empCodigoNew = usuario.getEmpCodigo();
            NivelAcesso nvaCodigoOld = persistentUsuario.getNvaCodigo();
            NivelAcesso nvaCodigoNew = usuario.getNvaCodigo();
            if (empCodigoNew != null) {
                empCodigoNew = em.getReference(empCodigoNew.getClass(), empCodigoNew.getEmpCodigo());
                usuario.setEmpCodigo(empCodigoNew);
            }
            if (nvaCodigoNew != null) {
                nvaCodigoNew = em.getReference(nvaCodigoNew.getClass(), nvaCodigoNew.getNvaCodigo());
                usuario.setNvaCodigo(nvaCodigoNew);
            }
            usuario = em.merge(usuario);
            if (empCodigoOld != null && !empCodigoOld.equals(empCodigoNew)) {
                empCodigoOld.getUsuarioList().remove(usuario);
                empCodigoOld = em.merge(empCodigoOld);
            }
            if (empCodigoNew != null && !empCodigoNew.equals(empCodigoOld)) {
                empCodigoNew.getUsuarioList().add(usuario);
                empCodigoNew = em.merge(empCodigoNew);
            }
            if (nvaCodigoOld != null && !nvaCodigoOld.equals(nvaCodigoNew)) {
                nvaCodigoOld.getUsuarioList().remove(usuario);
                nvaCodigoOld = em.merge(nvaCodigoOld);
            }
            if (nvaCodigoNew != null && !nvaCodigoNew.equals(nvaCodigoOld)) {
                nvaCodigoNew.getUsuarioList().add(usuario);
                nvaCodigoNew = em.merge(nvaCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuCodigo();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Empresa empCodigo = usuario.getEmpCodigo();
            if (empCodigo != null) {
                empCodigo.getUsuarioList().remove(usuario);
                empCodigo = em.merge(empCodigo);
            }
            NivelAcesso nvaCodigo = usuario.getNvaCodigo();
            if (nvaCodigo != null) {
                nvaCodigo.getUsuarioList().remove(usuario);
                nvaCodigo = em.merge(nvaCodigo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
