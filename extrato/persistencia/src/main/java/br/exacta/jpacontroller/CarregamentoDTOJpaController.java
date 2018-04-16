package br.exacta.jpacontroller;

import br.exacta.dto.CarregamentoDTO;
import br.exacta.dto.CarregamentoResumoDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

public class CarregamentoDTOJpaController {

    private EntityManagerFactory emf = null;

    public CarregamentoDTOJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<CarregamentoResumoDTO> buscaTodos(CarregamentoJpaFilter filter) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getSql(filter), CarregamentoResumoDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getSql(CarregamentoJpaFilter filter) {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.CarregamentoResumoDTO( " +
                "  c.rdcReceita          ," +
                "  c.rdcDataJson         ," +
                "  c.rdcNumtrato         ," +
                "  c.rdcEquipamento      ," +
                "  c.rdcOrdem            ," +
                "  c.rdcIngrediente      ," +
                "  c.rdcPesorequisitado  ," +
                "  c.rdcPesorealizado)    " +
                " FROM Carregamento c " +
                " WHERE 1 = 1 ");

        applyFilter(filter, stringBuilder);

        stringBuilder.append(" ORDER BY c.rdcOrdem, c.rdcReceita, c.rdcDataJson, c.rdcNumtrato, c.rdcIngrediente ");

        return stringBuilder.toString();
    }

    private void applyFilter(CarregamentoJpaFilter filter, StringBuilder stringBuilder) {
        if (filter.getEquipamento() != null) {
            stringBuilder.append(format(" and c.rdcEquipamento = '%s' ", filter.getEquipamento()));
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (filter.getDataInicio() != null) {
            String dataInicio = dateTimeFormatter.format(filter.getDataInicio());
            stringBuilder.append(format(" and c.rdcDataJson >= '%s' ", dataInicio));
        }

        if (filter.getDatafim() != null) {
            String dataFim = dateTimeFormatter.format(filter.getDatafim());
            stringBuilder.append(format(" and c.rdcDataJson <= '%s' ", dataFim));
        }
    }

    public List<String> findEquipamentoDistinct() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery("select DISTINCT RDC_EQUIPAMENTO from CARREGAMENTO");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<CarregamentoDTO> findCarregamentoDTO() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(sqlFindCarregamentoDTO(), CarregamentoDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String sqlFindCarregamentoDTO() {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.CarregamentoDTO( " +
                " c.rdcOrdem       , " +
                " c.rdcEquipamento , " +
                " c.rdcDataJson)     " +
                " FROM Carregamento c " +
                " GROUP BY c.rdcOrdem, c.rdcEquipamento, c.rdcDataJson ");

        return stringBuilder.toString();
    }
}
