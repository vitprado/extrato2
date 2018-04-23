package br.exacta.relatorio.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
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
        	
        	Calendar c = Calendar.getInstance();
            int ANO = c.get(Calendar.YEAR);
            int MES = c.get(Calendar.MONTH)+1;
            int DIA = c.get(Calendar.DAY_OF_MONTH);
            int HORA = c.get(Calendar.HOUR_OF_DAY);
            int MIN = c.get(Calendar.MINUTE);
            int SEG = c.get(Calendar.SECOND);

            FileOutputStream outputStream = new FileOutputStream(String.format("%s/%s", file.getPath(), String.format("%s%s%s_%s%s%s_%s", String.format("%02d",ANO), String.format("%02d",MES), String.format("%02d",DIA), String.format("%02d",HORA), String.format("%02d",MIN), String.format("%02d",SEG), filename)));
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
