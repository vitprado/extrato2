package br.exacta.dto;

import br.exacta.jpacontroller.CarregamentoJpaFilter;
import br.exacta.jpacontroller.CarregamentoDTOJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CarregamentoDTODAO {
    private final CarregamentoDTOJpaController jpaController;
    private final EntityManagerFactory emf;

    public CarregamentoDTODAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        jpaController = new CarregamentoDTOJpaController(emf);
    }

    public List<CarregamentoResumoDTO> buscaTodos(CarregamentoJpaFilter filter) {
        return jpaController.buscaTodos(filter);
    }

    public List<String> getEquipamentosDistinct() {
        return jpaController.findEquipamentoDistinct();
    }

    public List<CarregamentoDTO> findCarregamentoDTO() { return jpaController.findCarregamentoDTO(); }
}
