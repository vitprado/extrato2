/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.jpacontroller;

import br.exacta.jpacontroller.exceptions.NonexistentEntityException;
import br.exacta.persistencia.Equipamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.exacta.persistencia.OrdemProcucao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (equipamento.getOrdemProcucaoList() == null) {
            equipamento.setOrdemProcucaoList(new ArrayList<OrdemProcucao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<OrdemProcucao> attachedOrdemProcucaoList = new ArrayList<OrdemProcucao>();
            for (OrdemProcucao ordemProcucaoListOrdemProcucaoToAttach : equipamento.getOrdemProcucaoList()) {
                ordemProcucaoListOrdemProcucaoToAttach = em.getReference(ordemProcucaoListOrdemProcucaoToAttach.getClass(), ordemProcucaoListOrdemProcucaoToAttach.getOrdCodigo());
                attachedOrdemProcucaoList.add(ordemProcucaoListOrdemProcucaoToAttach);
            }
            equipamento.setOrdemProcucaoList(attachedOrdemProcucaoList);
            em.persist(equipamento);
            for (OrdemProcucao ordemProcucaoListOrdemProcucao : equipamento.getOrdemProcucaoList()) {
                Equipamento oldEqpCodigoOfOrdemProcucaoListOrdemProcucao = ordemProcucaoListOrdemProcucao.getEqpCodigo();
                ordemProcucaoListOrdemProcucao.setEqpCodigo(equipamento);
                ordemProcucaoListOrdemProcucao = em.merge(ordemProcucaoListOrdemProcucao);
                if (oldEqpCodigoOfOrdemProcucaoListOrdemProcucao != null) {
                    oldEqpCodigoOfOrdemProcucaoListOrdemProcucao.getOrdemProcucaoList().remove(ordemProcucaoListOrdemProcucao);
                    oldEqpCodigoOfOrdemProcucaoListOrdemProcucao = em.merge(oldEqpCodigoOfOrdemProcucaoListOrdemProcucao);
                }
            }
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
            Equipamento persistentEquipamento = em.find(Equipamento.class, equipamento.getEqpCodigo());
            List<OrdemProcucao> ordemProcucaoListOld = persistentEquipamento.getOrdemProcucaoList();
            List<OrdemProcucao> ordemProcucaoListNew = equipamento.getOrdemProcucaoList();
            List<OrdemProcucao> attachedOrdemProcucaoListNew = new ArrayList<OrdemProcucao>();
            for (OrdemProcucao ordemProcucaoListNewOrdemProcucaoToAttach : ordemProcucaoListNew) {
                ordemProcucaoListNewOrdemProcucaoToAttach = em.getReference(ordemProcucaoListNewOrdemProcucaoToAttach.getClass(), ordemProcucaoListNewOrdemProcucaoToAttach.getOrdCodigo());
                attachedOrdemProcucaoListNew.add(ordemProcucaoListNewOrdemProcucaoToAttach);
            }
            ordemProcucaoListNew = attachedOrdemProcucaoListNew;
            equipamento.setOrdemProcucaoList(ordemProcucaoListNew);
            equipamento = em.merge(equipamento);
            for (OrdemProcucao ordemProcucaoListOldOrdemProcucao : ordemProcucaoListOld) {
                if (!ordemProcucaoListNew.contains(ordemProcucaoListOldOrdemProcucao)) {
                    ordemProcucaoListOldOrdemProcucao.setEqpCodigo(null);
                    ordemProcucaoListOldOrdemProcucao = em.merge(ordemProcucaoListOldOrdemProcucao);
                }
            }
            for (OrdemProcucao ordemProcucaoListNewOrdemProcucao : ordemProcucaoListNew) {
                if (!ordemProcucaoListOld.contains(ordemProcucaoListNewOrdemProcucao)) {
                    Equipamento oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao = ordemProcucaoListNewOrdemProcucao.getEqpCodigo();
                    ordemProcucaoListNewOrdemProcucao.setEqpCodigo(equipamento);
                    ordemProcucaoListNewOrdemProcucao = em.merge(ordemProcucaoListNewOrdemProcucao);
                    if (oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao != null && !oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao.equals(equipamento)) {
                        oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao.getOrdemProcucaoList().remove(ordemProcucaoListNewOrdemProcucao);
                        oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao = em.merge(oldEqpCodigoOfOrdemProcucaoListNewOrdemProcucao);
                    }
                }
            }
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
            List<OrdemProcucao> ordemProcucaoList = equipamento.getOrdemProcucaoList();
            for (OrdemProcucao ordemProcucaoListOrdemProcucao : ordemProcucaoList) {
                ordemProcucaoListOrdemProcucao.setEqpCodigo(null);
                ordemProcucaoListOrdemProcucao = em.merge(ordemProcucaoListOrdemProcucao);
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
