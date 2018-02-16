package br.exacta.dao;

import br.exacta.jpacontroller.ItemTratoJpaController;
import br.exacta.persistencia.ItemTrato;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ItemTratoDAO {

    private final ItemTratoJpaController itemTratoController;
    private final EntityManagerFactory emf;

    public ItemTratoDAO(){
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        itemTratoController = new ItemTratoJpaController(emf);
    }

    public void adicionarItemTrato(ItemTrato itemTrato) throws Exception{
        itemTratoController.create(itemTrato);
    }

    public void editarItemTrato(ItemTrato itemTrato) throws Exception{
        itemTratoController.edit(itemTrato);
    }

    public void removerItemTrato(Integer id) throws Exception{
        itemTratoController.destroy(id);
    }

    public ItemTrato getItemTrato(Integer id){ return itemTratoController.findItemTrato(id);}
}
