package br.exacta.jpacontroller;

import br.exacta.dto.ResumoCarregamentoDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

public class ResumoCarregamentoDTOJpaController {

    private EntityManagerFactory emf = null;

    public ResumoCarregamentoDTOJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public List<ResumoCarregamentoDTO> buscaTodos(CarregamentoJpaFilter filter) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query nativeQuery = entityManager.createQuery(getSql(filter), ResumoCarregamentoDTO.class);

            return nativeQuery.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private String getSql(CarregamentoJpaFilter filter) {
        StringBuilder stringBuilder = new StringBuilder("SELECT new br.exacta.dto.ResumoCarregamentoDTO( " +
                "  c.rdcReceita          ," +
                "  c.rdcDataJson        ," +
                "  c.rdcNumtrato         ," +
                "  c.rdcEquipamento      ," +
                "  c.rdcCodigo           ," +
                "  c.rdcIngrediente      ," +
                "  c.rdcPesorequisitado  ," +
                "  c.rdcPesorealizado)    " +
                " FROM Carregamento c " +
                " WHERE 1 = 1 ");

        applyFilter(filter, stringBuilder);

        stringBuilder.append(" ORDER BY c.rdcReceita, c.rdcDataJson, c.rdcNumtrato, c.rdcIngrediente ");

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
}
