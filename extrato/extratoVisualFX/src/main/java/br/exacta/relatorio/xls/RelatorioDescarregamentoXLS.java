package br.exacta.relatorio.xls;

import br.exacta.persistencia.Descarregamento;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.util.List;

public class RelatorioDescarregamentoXLS extends RelatorioXLS<Descarregamento> {

    private static final String FILE_NAME = "RelatorioDescarregamento.xlsx";
    private static final String[] columns = {"Data", "Código da Ordem", "Número do Trato", "Curral", "Peso Requisitado", "Peso Realizado"};

    public RelatorioDescarregamentoXLS(File file) {
        super(file, columns, FILE_NAME);
    }

    @Override
    public void geraLinhasXLS(List<Descarregamento> descarregamentos, XSSFSheet sheet) {
        int rowCount = 1;
        for (Descarregamento descarregamento : descarregamentos) {
            Row row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(descarregamento.getRdgDatajson());
            row.createCell(1).setCellValue(descarregamento.getRdgOrdem());
            row.createCell(2).setCellValue(descarregamento.getRdgNumtrato());
            row.createCell(3).setCellValue(descarregamento.getRdgCurral());
            row.createCell(4).setCellValue(descarregamento.getRdgTratorequisitado());
            row.createCell(5).setCellValue(descarregamento.getRdgTratorealizado());
        }
    }

}
