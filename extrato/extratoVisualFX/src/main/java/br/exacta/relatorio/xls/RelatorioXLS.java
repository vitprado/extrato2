package br.exacta.relatorio.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public abstract class RelatorioXLS<T> {

    private final File file;
    private final String[] columns;
    private final String filename;

    public RelatorioXLS(File file, String[] columns, String filename) {
        this.file = file;
        this.columns = columns;
        this.filename = filename;
    }

    abstract void geraLinhasXLS(List<T> list, XSSFSheet sheet);

    public Boolean geraRelatorio(List<T> list) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Relatório de Carregamento");

            geraTitulo(sheet);
            geraLinhasXLS(list, sheet);
            return salvaNoCaminho(workbook, file);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    void geraTitulo(XSSFSheet sheet) {
        Row firstRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    Boolean salvaNoCaminho(XSSFWorkbook workbook, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(String.format("%s/%s", file.getPath(), filename));
            workbook.write(outputStream);
            workbook.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
