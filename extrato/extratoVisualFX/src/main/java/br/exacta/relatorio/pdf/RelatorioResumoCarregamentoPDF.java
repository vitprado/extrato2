package br.exacta.relatorio.pdf;

import br.exacta.dto.CarregamentoResumoDTO;
import br.exacta.jpacontroller.CarregamentoJpaFilter;
import javafx.stage.DirectoryChooser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioResumoCarregamentoPDF {

    private Map<String, Object> params = new HashMap<>();

    public RelatorioResumoCarregamentoPDF() {
    }

    public RelatorioResumoCarregamentoPDF(Map<String, Object> params) {
        this.params = params;
    }

    public RelatorioResumoCarregamentoPDF(CarregamentoJpaFilter carregamentoJpaFilter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (carregamentoJpaFilter.getDataInicio() != null) {
            params.put("PERIODO_INICIAL_FILTRO", dateTimeFormatter.format(carregamentoJpaFilter.getDataInicio()));
        }

        if (carregamentoJpaFilter.getDatafim() != null) {
            params.put("PERIODO_FINAL_FILTRO", dateTimeFormatter.format(carregamentoJpaFilter.getDatafim()));
        }

        if (carregamentoJpaFilter.getEquipamento() != null) {
            params.put("EQUIPAMENTO_FILTRO", carregamentoJpaFilter.getEquipamento());
        }
    }

    public void geraRelatorio(List<CarregamentoResumoDTO> resumoCarregamentos) throws IOException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/relatorio/resumoCarregamento.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(resumoCarregamentos));
            JRPdfExporter jrPdfExporter = new JRPdfExporter();
            SimpleExporterInput exportInput = new SimpleExporterInput(jasperPrint);
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);

            try (FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s", file.getPath(), "ResumoDeCarregamento.pdf"))) {
                SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fileOutputStream);
                jrPdfExporter.setExporterInput(exportInput);
                jrPdfExporter.setExporterOutput(simpleOutputStreamExporterOutput);
                jrPdfExporter.exportReport();
            }

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
