package br.exacta.dto;

import br.exacta.dao.OrdemProcucaoDAO;
import br.exacta.jpacontroller.ConsultaOrdemJpaController;
import br.exacta.persistencia.OrdemProducao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ConsultaOrdemDTODAO {
    private final ConsultaOrdemJpaController jpaController;
    private final EntityManagerFactory emf;

    public ConsultaOrdemDTODAO() {
        emf = Persistence.createEntityManagerFactory("br.exacta_Persistencia_jar_1.0.0PU");
        jpaController = new ConsultaOrdemJpaController(emf);
    }

    public List<ConsultaOrdemDTO> findAll() {
        List<ConsultaOrdemDTO> listConsultaOrdem = jpaController.findAll();
        List<ConsultaOrdemDTO> novaListConsultaOrdem = new ArrayList<>();
        String ultimoEquipamentoVerificado = " ";
        for (ConsultaOrdemDTO consultaOrdemDTO : listConsultaOrdem) {
            if (!ultimoEquipamentoVerificado.equals(consultaOrdemDTO.getEquipamento())) {
                if (!novaListConsultaOrdem.isEmpty())
                    preencherOrdemTrato(novaListConsultaOrdem.get(novaListConsultaOrdem.size() - 1));
                ultimoEquipamentoVerificado = consultaOrdemDTO.getEquipamento();
                novaListConsultaOrdem.add(new ConsultaOrdemDTO(consultaOrdemDTO.getEquipamento(), consultaOrdemDTO.getNordens()));
                novaListConsultaOrdem.get(novaListConsultaOrdem.size() - 1).getListOrdCodigo().add(consultaOrdemDTO.getOrdCodigo());
            } else {
                novaListConsultaOrdem.get(novaListConsultaOrdem.size() - 1).getListOrdCodigo().add(consultaOrdemDTO.getOrdCodigo());
            }
        }
        if (!novaListConsultaOrdem.isEmpty())
            preencherOrdemTrato(novaListConsultaOrdem.get(novaListConsultaOrdem.size() - 1));

        return novaListConsultaOrdem;
    }

    public ConsultaOrdemDTO findByFilter(ConsultaOrdemFilter consultaOrdemFilter) {
        List<ConsultaOrdemDTO> listConsultaOrdem = jpaController.findByFilter(consultaOrdemFilter);
        if (listConsultaOrdem.isEmpty())
            return new ConsultaOrdemDTO();

        ConsultaOrdemDTO novaConsultaOrdem = new ConsultaOrdemDTO(
                listConsultaOrdem.get(0).getEquipamento(),
                listConsultaOrdem.get(0).getNordens());
        for (ConsultaOrdemDTO consultaOrdemDTO : listConsultaOrdem) {
            novaConsultaOrdem.getListOrdCodigo().add(consultaOrdemDTO.getOrdCodigo());
        }
        preencherOrdemTrato(novaConsultaOrdem);

        return novaConsultaOrdem;
    }

    public List<EquipamentoConsultaOrdemDTO> findByEquipamentoForListaOrdem(Integer eqpCodigo) {
        List<ListaOrdemProducaoDTO> listaOrdemProducaoDTO = jpaController.findByEquipamentoForListaOrdens(eqpCodigo);
        if (listaOrdemProducaoDTO.isEmpty())
            return null;

        List<EquipamentoConsultaOrdemDTO> listaEquipamentoConsultaOrdem = new ArrayList<>();
        for (ListaOrdemProducaoDTO ordem : listaOrdemProducaoDTO) {
            //TODO finalizar
        }
        return listaEquipamentoConsultaOrdem;
    }

    private void preencherOrdemTrato(ConsultaOrdemDTO novaConsultaOrdem) {
        for (Integer ordem : novaConsultaOrdem.getListOrdCodigo()) {
            List<OrdemTratosDTO> listOrdemTrato = jpaController.findOrdemTrato(ordem);
            OrdemTratosDTO novaOrdemTrato;
            if (listOrdemTrato.isEmpty()) {
                OrdemProducao ordemProducao = new OrdemProcucaoDAO().getOrdemProcucao(ordem);
                novaOrdemTrato = new OrdemTratosDTO(
                        ordemProducao.getOrdDescricao(),
                        ordemProducao.getTratos().size());
            } else {
                novaOrdemTrato = new OrdemTratosDTO(
                        listOrdemTrato.get(0).getOrdemproducao(),
                        listOrdemTrato.get(0).getNtratos());
            }
            Boolean primeiraVez = true;
            for (OrdemTratosDTO ordemTratosDTO : listOrdemTrato) {
                novaOrdemTrato.getListTrtCodigo().add(ordemTratosDTO.getTrtCodigo());
                preencherItemTratoCurral(novaOrdemTrato, primeiraVez);
                novaOrdemTrato.getListRctCodigo().add(ordemTratosDTO.getRctCodigo());
                preencherReceitaIngrediente(novaOrdemTrato);
                primeiraVez = false;
            }
            novaConsultaOrdem.getOrdens().add(novaOrdemTrato);
        }
    }

    private void preencherItemTratoCurral(OrdemTratosDTO novaOrdemTrato, Boolean primeiraVez) {
        List<ItemTratoCurralDTO> listItemTratoCurral = jpaController.findItemTratoCurral(novaOrdemTrato.getListTrtCodigo().get(novaOrdemTrato.getListTrtCodigo().size() - 1));

        if (primeiraVez) {
            if (listItemTratoCurral.isEmpty()) {
                novaOrdemTrato.setNcurrais(0);
                return;
            }
            novaOrdemTrato.setNcurrais(listItemTratoCurral.get(0).getNcurrais());
            novaOrdemTrato.getPesos().add(new ArrayList<>());
            for (ItemTratoCurralDTO itemTratoCurral : listItemTratoCurral) {
                novaOrdemTrato.getCurrais().add(itemTratoCurral.getCurral());
                novaOrdemTrato.getPesos().get(0).add(itemTratoCurral.getPeso().toString());
            }
            return;
        }

        if (listItemTratoCurral.isEmpty()) {
            return;
        }
        novaOrdemTrato.getPesos().add(new ArrayList<>());
        for (ItemTratoCurralDTO itemTratoCurral : listItemTratoCurral) {
            novaOrdemTrato.getPesos().get(novaOrdemTrato.getPesos().size() - 1).add(itemTratoCurral.getPeso().toString());
        }
    }

    private void preencherReceitaIngrediente(OrdemTratosDTO novaOrdemTrato) {
        if (novaOrdemTrato.getListRctCodigo().size() == 0)
            return;
        List<ReceitaIngredienteDTO> listReceitaIngrediente = jpaController.findReceitaIngrediente(novaOrdemTrato.getListRctCodigo().get(novaOrdemTrato.getListRctCodigo().size() - 1));

        if (listReceitaIngrediente.isEmpty()) {
            return;
        }
        novaOrdemTrato.getReceitas().add(listReceitaIngrediente.get(0).getReceita());
        novaOrdemTrato.getIngredientes().add(new ArrayList<>());
        novaOrdemTrato.getTolerancias().add(new ArrayList<>());
        novaOrdemTrato.getPesosrequisitados().add(new ArrayList<>());
        Integer pesoTotal = 0;

        if (novaOrdemTrato.getPesos().size() != 0) {
            for (String s : novaOrdemTrato.getPesos().get(novaOrdemTrato.getPesos().size() - 1)) {
                pesoTotal = pesoTotal + Integer.parseInt(s);
            }
        }

        for (ReceitaIngredienteDTO receitaIngrediente : listReceitaIngrediente) {
            novaOrdemTrato.getIngredientes().get(novaOrdemTrato.getIngredientes().size() - 1).add(receitaIngrediente.getIngrediente());

            Double pesoTotalComProporcao = (pesoTotal * receitaIngrediente.getProporcao()) / 100;

            Double tolerancia = (pesoTotalComProporcao * receitaIngrediente.getTolerancia()) / 100;

            novaOrdemTrato.getTolerancias().get(novaOrdemTrato.getTolerancias().size() - 1).add(tolerancia.toString());
            novaOrdemTrato.getPesosrequisitados().get(novaOrdemTrato.getPesosrequisitados().size() - 1).add(String.valueOf(pesoTotalComProporcao));
        }
    }
}
