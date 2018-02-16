//package br.exacta.dao;
//
//import br.exacta.jpacontroller.OrdemCurralJpaController;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//public class OrdemCurralDAO {
//
//    private final OrdemCurralJpaController ordemCurralController;
//    private final EntityManagerFactory emf;
//
//    public OrdemCurralDAO(){
//        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
//        ordemCurralController = new OrdemCurralJpaController(emf);
//    }
//
////    public OrdemCurral adicionarOrdemCurral(OrdemCurral ordemCurral) throws Exception {
////        return ordemCurralController.create(ordemCurral);
////    }
////
////    public void editarOrdemCurral(OrdemCurral ordemCurral) throws Exception {
////        ordemCurralController.edit(ordemCurral);
////    }
//
//    public void removerOrdemCurral(Integer id) throws Exception {
//        ordemCurralController.destroy(id);
//    }
//
////    public OrdemCurral getOrdemCurral(Integer id) {
////        return ordemCurralController.findOrdemCurral(id);
////    }
//}
