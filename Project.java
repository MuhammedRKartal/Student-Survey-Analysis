import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Project {


    public static void main(String[] args) {

        XSSFWorkbook wb = new XSSFWorkbook();
        //create a black sheet
        Sheet sheet = wb.createSheet("new sheet");
        //create a new row 0
        Row row = sheet.createRow((short)0);
        //create a new cell
        Cell cell = row.createCell(0);
        //insert value in the created cell
        cell.setCellValue(1.4);

        //add other cells with different types
        /*int*/row.createCell(1).setCellValue(7);
        /*int*/row.createCell(2).setCellValue(99);
        /*string*/row.createCell(3).setCellValue("string");
        /*boolean*/row.createCell(4).setCellValue(true);

        FileOutputStream fos;
        try {
            fos= new FileOutputStream("newFile.xlsx");
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
