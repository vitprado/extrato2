package br.exacta.relatorio.xls;

import br.exacta.persistencia.Carregamento;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.util.List;

public class RelatorioCarregamentoXLS extends RelatorioXLS<Carregamento> {

    private static final String FILE_NAME = "RelatorioCarregamento.xlsx";
    private static final String[] columns = {"Data", "Código da Ordem", "Número do Trato", "Ingrediente", "Peso Requisitado", "Peso Realizado"};

    public RelatorioCarregamentoXLS(File file) {
        super(file, columns, FILE_NAME);
    }

    @Override
    public void geraLinhasXLS(List<Carregamento> carregamentos, XSSFSheet sheet) {
        int rowCount = 1;
        for (Carregamento carregamento : carregamentos) {
            Row row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(carregamento.getRdcDatajson());
            row.createCell(1).setCellValue(carregamento.getRdcOrdem());
            row.createCell(2).setCellValue(carregamento.getRdcNumtrato());
            row.createCell(3).setCellValue(carregamento.getRdcIngrediente());
            row.createCell(4).setCellValue(carregamento.getRdcPesorequisitado());
            row.createCell(5).setCellValue(carregamento.getRdcPesorealizado());
        }
    }

}
