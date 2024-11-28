package co.com.camargo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    public Workbook workbook;

    public ExcelUtils(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);

        if (cell == null) {
            return ""; // Return empty string if the cell is null
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Handle numeric cells, convert to string if needed
                if (DateUtil.isCellDateFormatted(cell)) {
                    // If the cell contains a date, format it
                    return cell.getDateCellValue().toString(); // You can use SimpleDateFormat for custom formats
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // If you need the formula as a string
            case BLANK:
                return ""; // Handle blank cells
            default:
                throw new IllegalStateException("Unexpected cell type: " + cell.getCellType());
        }
    }

    public static void createReportExcel(List<String> successProducts, List<String> failedProducts, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Resultados");

        // Crear encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Productos Procesados Correctamente");
        header.createCell(1).setCellValue("Errores Encontrados");

        // Llenar datos
        int maxRows = Math.max(successProducts.size(), failedProducts.size());
        for (int i = 0; i < maxRows; i++) {
            Row row = sheet.createRow(i + 1);
            if (i < successProducts.size()) {
                row.createCell(0).setCellValue(successProducts.get(i));
            }
            if (i < failedProducts.size()) {
                row.createCell(1).setCellValue(failedProducts.get(i));
            }
        }

        // Escribir el archivo
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }

        workbook.close();
        System.out.println("Reporte generado exitosamente en: " + filePath);
    }

    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum() + 1;
    }

    public static void addTestResult(String sheetName, String testName, String result, String filePath) throws IOException {
        Workbook workbook;
        Sheet sheet;

        // Crear archivo si no existe
        File file = new File(filePath);
        if (!file.exists()) {
            workbook = new XSSFWorkbook(); // Crear nuevo workbook
            sheet = workbook.createSheet(sheetName);
            // Crear encabezados
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Nombre del Test");
            header.createCell(1).setCellValue("Resultado");
        } else {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            // Crear hoja si no existe
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Nombre del Test");
                header.createCell(1).setCellValue("Resultado");
            }
        }

        // Agregar datos al reporte
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount + 1);
        row.createCell(0).setCellValue(testName);
        row.createCell(1).setCellValue(result);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }

        workbook.close();
        System.out.println("Resultado agregado al reporte: " + testName + " -> " + result);
    }



    public void closeWorkbook() throws IOException {
        workbook.close();
    }
}
