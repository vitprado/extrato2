package br.exacta.dto;

import br.exacta.jpacontroller.ConsultaOrdemJpaController;

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
                ultimoEquipamentoVerificado = consultaOrdemDTO.getEquipamento();
                novaListConsultaOrdem.add(new ConsultaOrdemDTO(consultaOrdemDTO.getEquipamento(), consultaOrdemDTO.getNordens(), consultaOrdemDTO.getOrdCodigo()));
            } else {
                novaListConsultaOrdem.get(novaListConsultaOrdem.size() - 1).getListOrdCodigo().add(consultaOrdemDTO.getOrdCodigo());
            }
        }
        preencherOrdemTrato(novaListConsultaOrdem);

        return novaListConsultaOrdem;
    }

    private void preencherOrdemTrato(List<ConsultaOrdemDTO> novaListConsultaOrdem) {
        for (ConsultaOrdemDTO consultaOrdemDTO : novaListConsultaOrdem) {
            for (Integer ordem : consultaOrdemDTO.getListOrdCodigo()) {
                List<OrdemTratosDTO> listOrdemTrato = jpaController.findOrdemTrato(ordem);
                if (listOrdemTrato.isEmpty())
                    break;
                OrdemTratosDTO novaOrdemTrato = new OrdemTratosDTO(listOrdemTrato.get(0).getOrdemproducao(), listOrdemTrato.get(0).getNtratos(), listOrdemTrato.get(0).getTrtCodigo(), listOrdemTrato.get(0).getRctCodigo());
                Boolean primeiraVez = true;
                for (OrdemTratosDTO ordemTratosDTO : listOrdemTrato) {
                    novaOrdemTrato.getListTrtCodigo().add(ordemTratosDTO.getTrtCodigo());
                    preencherItemTratoCurral(novaOrdemTrato, primeiraVez);
                    novaOrdemTrato.getListRctCodigo().add(ordemTratosDTO.getRctCodigo());
                    preencherReceitaIngrediente(novaOrdemTrato);
                    primeiraVez = false;
                }
                consultaOrdemDTO.getOrdens().add(novaOrdemTrato);
            }
        }
    }

    private void preencherItemTratoCurral(OrdemTratosDTO novaOrdemTrato, Boolean primeiraVez) {
        List<ItemTratoCurralDTO> listItemTratoCurral = jpaController.findItemTratoCurral(novaOrdemTrato.getTrtCodigo());

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
        List<ReceitaIngredienteDTO> listReceitaIngrediente = jpaController.findReceitaIngrediente(novaOrdemTrato.getRctCodigo());

        if (listReceitaIngrediente.isEmpty()) {
            return;
        }
        novaOrdemTrato.getReceitas().add(listReceitaIngrediente.get(0).getReceita());
        novaOrdemTrato.getIngredientes().add(new ArrayList<>());
        novaOrdemTrato.getTolerancias().add(new ArrayList<>());
        novaOrdemTrato.getPesosrequisitados().add(new ArrayList<>());
        Integer pesoTotal = 0;

        for (String s : novaOrdemTrato.getPesos().get(novaOrdemTrato.getPesos().size() - 1)) {
            pesoTotal = pesoTotal + Integer.parseInt(s);
        }

        for (ReceitaIngredienteDTO receitaIngrediente : listReceitaIngrediente) {
            novaOrdemTrato.getIngredientes().get(novaOrdemTrato.getIngredientes().size() - 1).add(receitaIngrediente.getIngrediente());
            novaOrdemTrato.getTolerancias().get(novaOrdemTrato.getTolerancias().size() - 1).add(receitaIngrediente.getTolerancia().toString());
            novaOrdemTrato.getPesosrequisitados().get(novaOrdemTrato.getPesosrequisitados().size() - 1).add(String.valueOf(pesoTotal * receitaIngrediente.getProporcao() / 100));
        }
    }
}
