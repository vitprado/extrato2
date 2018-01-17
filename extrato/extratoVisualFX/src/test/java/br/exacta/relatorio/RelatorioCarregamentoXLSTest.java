package br.exacta.relatorio;

import br.exacta.persistencia.Carregamento;
import br.exacta.relatorio.xls.RelatorioCarregamentoXLS;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RelatorioCarregamentoXLSTest {

    @Test
    public void geraRelatorioTest() throws IOException {
        Path tempDir = Files.createTempDirectory("tempfiles");
        RelatorioCarregamentoXLS relatorioCarregamentoXLS = new RelatorioCarregamentoXLS(tempDir.toFile());
        assertTrue(relatorioCarregamentoXLS.geraRelatorio(getCarregamentos()));
    }

    private List<Carregamento> getCarregamentos() {
        List<Carregamento> carregamentos = new ArrayList<>();

        Carregamento carregamento1 = new Carregamento();
        carregamento1.setRdcEquipamento("Equipamento 1");
        carregamento1.setRdcIngrediente("Ingrediente 1");
        carregamento1.setRdcNumtrato(1);
        carregamento1.setRdcOrdem("Código da ordem 1");
        carregamento1.setRdcPesorequisitado("200");
        carregamento1.setRdcPesorealizado("200");

        Carregamento carregamento2 = new Carregamento();
        carregamento2.setRdcEquipamento("Equipamento 2");
        carregamento2.setRdcIngrediente("Ingrediente 2");
        carregamento2.setRdcNumtrato(2);
        carregamento2.setRdcOrdem("Código da ordem 2");
        carregamento2.setRdcPesorequisitado("200");
        carregamento2.setRdcPesorealizado("200");

        carregamentos.add(carregamento1);
        carregamentos.add(carregamento2);

        return carregamentos;
    }

}