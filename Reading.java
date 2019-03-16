import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;

public class Reading {

    private static final String FILE_NAME = "COMP816_1_2017_2.xlsx";

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

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            int start=datatypeSheet.getFirstRowNum();
            int end=datatypeSheet.getLastRowNum() ;
            for (int i=start;i<end;i++){
                Row row=datatypeSheet.getRow(i);
                for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                    Cell cell=row.getCell(j);
                    if (cell.getCellTypeEnum()==CellType.STRING){
                        System.out.print(cell.getStringCellValue()+ "  ");
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "  ");
                    }

                }
                System.out.println();
            }

           /* while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                }
                System.out.println();
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
