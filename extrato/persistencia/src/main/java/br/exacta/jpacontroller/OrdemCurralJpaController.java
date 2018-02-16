//package br.exacta.jpacontroller;
//
//import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
//import br.exacta.persistencia.OrdemCurral;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityNotFoundException;
//import java.io.Serializable;
//
//public class OrdemCurralJpaController implements Serializable {
//
//    public OrdemCurralJpaController(EntityManagerFactory emf) { this.emf = emf;}
//    private EntityManagerFactory emf = null;
//
//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }
//
//    public OrdemCurral create(OrdemCurral ordemCurral){
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            em.persist(ordemCurral);
//            em.getTransaction().commit();
//            return ordemCurral;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void edit(OrdemCurral ordemCurral) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            ordemCurral = em.merge(ordemCurral);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = ordemCurral.getOrcCodigo();
//                if (findOrdemCurral(id) == null) {
//                    throw new NonexistentEntityException("The ordemCurral with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void destroy(Integer id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            OrdemCurral ordemCurral;
//            try {
//                ordemCurral = em.getReference(OrdemCurral.class, id);
//                ordemCurral.getOrcCodigo();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The ordemCurral with id " + id + " no longer exists.", enfe);
//            }
//            em.remove(ordemCurral);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public OrdemCurral findOrdemCurral(Integer id) {
//        EntityManager em = getEntityManager();
//        try {
//            return em.find(OrdemCurral.class, id);
//        } finally {
//            em.close();
//        }
//    }
//}
