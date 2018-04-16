package br.exacta.dto;

import br.exacta.jpacontroller.DescarregamentoDTOJpaController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DescarregamentoDTODAO {

    private final DescarregamentoDTOJpaController jpaController;
    private final EntityManagerFactory emf;

    public DescarregamentoDTODAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        jpaController = new DescarregamentoDTOJpaController(emf);
    }

    public List<DescarregamentoDTO> findDescarregamentoDTO() { return jpaController.findDescarregamentoDTO(); }
}
