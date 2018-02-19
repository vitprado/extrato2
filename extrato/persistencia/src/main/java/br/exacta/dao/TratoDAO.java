package br.exacta.dao;

import br.exacta.jpacontroller.TratoJpaController;
import br.exacta.persistencia.Trato;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TratoDAO {

	private final TratoJpaController tratoController;
	private final EntityManagerFactory emf;

	public TratoDAO() {
		emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
		tratoController = new TratoJpaController(emf);
	}

	public Trato adicionarTrato(Trato trato) throws Exception { return tratoController.create(trato);}

	public void editarTrato(Trato trato) throws Exception {	tratoController.edit(trato);}

	public void removerTrato(Integer id) throws Exception {	tratoController.destroy(id);}

	public Trato getTrato(Integer id) {	return tratoController.findTrato(id);}
}
