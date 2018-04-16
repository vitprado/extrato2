package br.exacta.jpacontroller;

import br.exacta.dto.DescarregamentoDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class DescarregamentoDTOJpaController {

    private EntityManagerFactory emf = null;

    public DescarregamentoDTOJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<DescarregamentoDTO> findDescarregamentoDTO() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(sqlFindDescarregamentoDTO(), DescarregamentoDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String sqlFindDescarregamentoDTO() {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.DescarregamentoDTO( " +
                " c.rdgOrdem       , " +
                " c.rdgEquipamento , " +
                " c.rdgDatajson)     " +
                " FROM Descarregamento c " +
                " GROUP BY c.rdgOrdem, c.rdgEquipamento, c.rdgDatajson ");

        return stringBuilder.toString();
    }
}