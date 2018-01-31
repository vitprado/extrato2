package br.exacta.dto;

import br.exacta.jpacontroller.CarregamentoJpaFilter;
import br.exacta.jpacontroller.ResumoCarregamentoDTOJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ResumoCarregamentoDTODAO {
    private final ResumoCarregamentoDTOJpaController jpaController;
    private final EntityManagerFactory emf;

    public ResumoCarregamentoDTODAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        jpaController = new ResumoCarregamentoDTOJpaController(emf);
    }

    public List<ResumoCarregamentoDTO> buscaTodos(CarregamentoJpaFilter filter) {
        return jpaController.buscaTodos(filter);
    }

    public List<String> getEquipamentosDistinct() {
        return jpaController.findEquipamentoDistinct();
    }
}
