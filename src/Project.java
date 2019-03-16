import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Project {


    private static final String FILE_NAME = "COMP816_1_2017_2.xlsx";

    public static void main(String[] args) {
        File_f file=new File_f();

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Row row=datatypeSheet.getRow(2);
            Cell cell=row.getCell(1);
            file.setÖğretimElemanı(cell.getStringCellValue());
            System.out.println(file.getÖğretimElemanı());
            Cell cell2=row.getCell(3);
            file.setÖğrenciSayısı((int)cell2.getNumericCellValue());
            System.out.println(file.getÖğrenciSayısı());
            Row row2=datatypeSheet.getRow(3);
            Cell cell3=row2.getCell(1);
            file.setDersKoduSection(cell3.getStringCellValue());
            System.out.println(file.getDersKoduSection());
            Cell cell4=row2.getCell(3);
            file.setCevapAdedi((int)cell4.getNumericCellValue());
            System.out.println(file.getCevapAdedi());
            Row row3=datatypeSheet.getRow(4);
            Cell cell5=row3.getCell(3);
            file.setCevaplamaOranı(cell5.getStringCellValue());
            System.out.println(file.getCevaplamaOranı());
            String name[]=new String[10];
            int index=-1;
            int carryon=0;

            for (int i=5;i<45;i++){
                Row r=datatypeSheet.getRow(i);
                Cell control=r.getCell(1);
                Cell control1=r.getCell(0);



                    if (control.getCellTypeEnum()==CellType.STRING && control.getStringCellValue().equals("Ortalama")){
                        int subindex=-1;
                        Section section=new Section();
                        section.setText(r.getCell(0).getStringCellValue());
                        file.section[++index]=section;
                        i++;}

                r=datatypeSheet.getRow(i);

                Subsection subsection=new Subsection();
                subsection.text=r.getCell(0).getStringCellValue();
                if(subsection.text.equals("Written Comments")){
                    carryon=i;
                    break;
                }
                subsection.average=r.getCell(1).getNumericCellValue();
                subsection.stddev=r.getCell(2).getNumericCellValue();
                subsection.na=r.getCell(3).getNumericCellValue();
                subsection.no=r.getCell(4).getNumericCellValue();
                subsection.one=r.getCell(5).getNumericCellValue();
                subsection.two=r.getCell(6).getNumericCellValue();
                subsection.three=r.getCell(7).getNumericCellValue();
                subsection.four=r.getCell(8).getNumericCellValue();
                subsection.five=r.getCell(9).getNumericCellValue();
                subsection.universityAverage=r.getCell(10).getNumericCellValue();
                file.section[index].subsections.add(subsection);
               // System.out.println(subsection.text);
            }
            int end=datatypeSheet.getLastRowNum();
            for (int i=carryon;i<end;i++) {
                Row r = datatypeSheet.getRow(i);
                Cell c = r.getCell(0);
                file.comments.add(c.getStringCellValue());

            }











            for (int i=0;i<7;i++) {
                System.out.println(file.section[i].getText());
                for (Subsection s:file.section[i].subsections) {
                    System.out.println(s.universityAverage);

                }
            }
            for (String s:file.comments
                 ) {
                System.out.println(s);
            }




           /*
             int start=datatypeSheet.getFirstRowNum();
            int end=datatypeSheet.getLastRowNum() ;
           for (int i=start;i<end;i++){
                Row row=datatypeSheet.getRow(i);
                for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                    Cell cell=row.getCell(j);
                    if (cell.getCellTypeEnum()==CellType.STRING){
                        System.out.print(cell.getStringCellValue()+ " - ");
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "  ");
                    }

                }
                System.out.println();
            }*/

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
