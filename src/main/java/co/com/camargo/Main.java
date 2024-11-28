package co.com.camargo;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream("src/test/resources/testdata.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        System.out.println("Available sheets:");
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            System.out.println("Sheet " + i + ": " + workbook.getSheetName(i));
        }

        workbook.close();
    }
}
