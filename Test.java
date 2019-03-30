import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

       //File_f a=Load_File("ssa_file_with_formulas.xlsx");
       ArrayList<File_f>files=new ArrayList<>();
       ArrayList<File> paths=new ArrayList<>();

       Load_Multi_Files(files,paths);
        //System.out.println(files.get(2).getÖğrenciSayısı());









    }
    //This method performs single file operations
        public static File_f Load_File(File  single){
            File_f file=new File_f();
            String path=single.getPath();

            try {

                FileInputStream excelFile = new FileInputStream(new File(path));
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



                    if (control.getCellTypeEnum()== CellType.STRING && control.getStringCellValue().equals("Ortalama")){
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
                //ADDING COMMENTS
                //int end=datatypeSheet.getLastRowNum();
                for (int i=carryon+2;i<carryon+9;i++) {
                    Row r = datatypeSheet.getRow(i);
                    Cell c = r.getCell(0);
                    file.comments.add(c.getStringCellValue());

                }
                //PRINTING THE SECTION AND SUBSECTION
//                for (int i=0;i<7;i++) {
//                    System.out.println(file.section[i].getText());
//                    for (Subsection s:file.section[i].subsections) {
//                        System.out.println(s.universityAverage);
//
//                    }
//                }
                //PRINTING COMMENTS
//                for (String s:file.comments
//                        ) {
//                    System.out.println(s);
//                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return file;
        }
    //This method calls sinlgle File method and performs multiple file operations
        public static void  Load_Multi_Files(ArrayList<File_f> files,ArrayList<File> multi){

        for (int i=0;i<multi.size();i++){
            File single=multi.get(i);
            files.add(Load_File(single));
        }

    }


    }

