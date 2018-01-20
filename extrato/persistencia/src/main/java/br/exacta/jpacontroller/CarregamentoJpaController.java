/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Carregamento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

/**
 *
 * @author Thales
 */
public class CarregamentoJpaController implements Serializable {

    public CarregamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carregamento carregamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(carregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carregamento carregamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            carregamento = em.merge(carregamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carregamento.getRdcCodigo();
                if (findCarregamento(id) == null) {
                    throw new NonexistentEntityException("The carregamento with id " + id + " no longer exists.");
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
            Carregamento carregamento;
            try {
                carregamento = em.getReference(Carregamento.class, id);
                carregamento.getRdcCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carregamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(carregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carregamento> findCarregamentoEntities() {
        return findCarregamentoEntities(true, -1, -1);
    }

    public List<Carregamento> findCarregamentoEntities(int maxResults, int firstResult) {
        return findCarregamentoEntities(false, maxResults, firstResult);
    }

    private List<Carregamento> findCarregamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carregamento.class));
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

    public Carregamento findCarregamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carregamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarregamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carregamento> rt = cq.from(Carregamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> findEquipamentoDistinct() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select DISTINCT RDC_EQUIPAMENTO from CARREGAMENTO");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Carregamento> findCarregamentos(CarregamentoJpaFilter filter) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder stringBuilder = new StringBuilder("select * from CARREGAMENTO where 1 = 1");

            if (filter.getEquipamento() != null) {
                stringBuilder.append(format(" and RDC_EQUIPAMENTO = '%s' ", filter.getEquipamento()));
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (filter.getDataInicio() != null) {
                String dataInicio = dateTimeFormatter.format(filter.getDataInicio());
                stringBuilder.append(format(" and RDC_DATA_JSON >= '%s' ", dataInicio));
            }

            if (filter.getDatafim() != null) {
                String dataFim = dateTimeFormatter.format(filter.getDatafim());
                stringBuilder.append(format(" and RDC_DATA_JSON <= '%s' ", dataFim));
            }

            Query query = em.createNativeQuery(stringBuilder.toString(), Carregamento.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
}
