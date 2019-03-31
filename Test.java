import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Test {


    public static void main(String[] args) {


       //File_f a=Load_File("CSSE 377_1_2017_2.xlsx");
       ArrayList<File_f>files=new ArrayList<>();
//!!!!!!!!!!!!!!BU ARRAY GERÇEKTE (19.SATIR)  STRİNG DEĞİL FİLE  !!!!!!!!
      ArrayList<String> paths=new ArrayList<>();
      paths.add("COMP816_1_2017_2.xlsx");
      paths.add("CSSE 377_1_2017_2.xlsx");
      paths.add("COMP816_1_2016_2.xlsx");
      paths.add("COMP816_1_2015_2.xlsx");
      paths.add("COMP816_2_2016_2.xlsx");
      paths.add("COMP816_2_2017_2.xlsx");
      Load_Multi_Files(files,paths);
      givenCourseCode(files,"COMP816");//Takes course code
//      givenCourseSection(files,1,"Flipped Classroom");
//      givenCourseSubSection(files,1,"The instructor came to class prepared");










    }
    //!!!!!!!!!GERÇEK METOTDA PATH FİLE ÖYLE DE KALMASI LAZIM BEN GUİ KULLANMAK İSTEMEDİĞİM İÇİN BÖYLE YAPTIM SADECE 44. SATIRI COMMENTTEN ÇİKAR :)!!!!!!
        public static File_f Load_File(String path){
            File_f file=new File_f();

//            String path=single.getAbsolutePath();
            String s[]=path.split("_");
            file.yaer=Integer.parseInt(s[2]);
            file.setDersKodu(s[0]);
            file.setSect(Integer.parseInt(s[1]));


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
//             ----------In your code comment this 2 line as well----------------
//                Cell cell3=row2.getCell(1);
//                file.setDersKoduSection(cell3.getStringCellValue());
//
                System.out.println(file.getDersKodu()+" "+file.getSect());
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

                }
                //ADDING COMMENTS

                for (int i=carryon+2;i<carryon+9;i++) {
                    Row r = datatypeSheet.getRow(i);
                    Cell c = r.getCell(0);
                    file.comments.add(c.getStringCellValue());

                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return file;
        }
    //This method calls sinlgle File method and performs multiple file operations
        public static void  Load_Multi_Files(ArrayList<File_f> files,ArrayList<String> multi){

        for (int i=0;i<multi.size();i++){
//            File single=multi.get(i);
            String single=multi.get(i);
            files.add(Load_File(single));
        }

    }
//This method returns 2 d array like section1[2014,2015,2016,2017]
//                                   section2[2014,2015,2016,2017]
    public  static  double[][] givenCourseCode(ArrayList<File_f> files, String code){
        ArrayList<File_f> x1=new ArrayList<>();
        ArrayList<File_f> x2=new ArrayList<>();
        double average[][]=new double[2][4];//0 index is for section 1 "1" is for section 2

        for (int i = 0; i < files.size(); i++) {
            File_f f=files.get(i);
            if (code.equals(f.getDersKodu())){
                if (f.getSect()==1){
                    x1.add(f);
                }
                else {
                    x2.add(f);
                }
            }
        }
        int div1[]=new int[4];
        for (int i = 0; i < x1.size(); i++) {
            double total=0;
            int div=0;
            File_f f=x1.get(i);
            for (int j=0;j<7;j++) {
                for (Subsection sb:f.section[j].subsections) {
                    total+=sb.average;
                    //System.out.println(total);
                    div++;
                    }
            }
            int index=(f.yaer-2)%4;
            div1[index]+=f.getÖğrenciSayısı();
            System.out.println(f.yaer+"section "+f.getSect());
            average[0][index]=total/div*f.getÖğrenciSayısı();
//            System.out.println(average[0][index]=total/div);
        }
        for (int i = 0; i <average[0].length ; i++) {
            average[0][i]/=div1[i];
            System.out.println(average[0][i]);
        }
        int div2[]=new int[4];
        for (int i = 0; i < x2.size(); i++) {
            double total=0;
            int div=0;
            File_f f=x2.get(i);
            for (int j=0;j<7;j++) {
                for (Subsection sb:f.section[j].subsections) {
                    total+=sb.average;
                    //System.out.println(total);
                    div++;
                }
            }
            int index=(f.yaer-2)%4;
            div2[index]+=f.getÖğrenciSayısı();
            System.out.println(f.yaer+"section "+f.getSect());
            average[1][index]=total/div*f.getÖğrenciSayısı();
//            System.out.println(average[0][index]=total/div);
        }
        for (int i = 0; i <average[0].length ; i++) {
            average[1][i]/=div2[i];
            System.out.println(average[1][i]);
        }




    return average;
    }
//This method returns 1d array like   sectionX[2014,2015,2016,2017]
    public  static  double []  givenCourseSection(ArrayList<File_f> files, int code,String section){
        ArrayList<File_f> x1=new ArrayList<>();
        double average[]=new double[4];
        for (int i = 0; i < files.size(); i++) {
            File_f f=files.get(i);
            if (f.getSect()==code ){
                    x1.add(f);
            }
        }
        int div[]=new int[4];

        for (int i = 0; i < x1.size(); i++) {
            double total=0;
            int div1=0;

            File_f f=x1.get(i);
            for (int j=0;j<7;j++) {

                if (f.section[j].getText().equals(section)){
                for (Subsection sb:f.section[j].subsections) {
                    total += sb.average;
//                    System.out.println(f.section[j].getText()+" total ="+total);
                    div1++;
                }

                }
            }


            int index=(f.yaer-2)%4;
            div[index]+=f.getÖğrenciSayısı();
//            System.out.println("FOR GIVEN SECTION "+f.yaer+" section "+f.getSect()+"");
//            System.out.println("first "+ average[index]+"x"+f.getÖğrenciSayısı());
            average[index]+=total/div1*f.getÖğrenciSayısı();
//            System.out.println( average[index]+"x"+f.getÖğrenciSayısı());


        }
        for (int i = 0; i <average.length ; i++) {
            System.out.println("total is "+average[i] +" div ="+div[i]) ;
            average[i]=average[i]/div[i];
             System.out.println(average[i]);

        }


    return average;
    }
// This method returns 1d array like   sectionX[2014,2015,2016,2017]
    public  static  double[] givenCourseSubSection(ArrayList<File_f> files, int code,String subSection){
        ArrayList<File_f> x1=new ArrayList<>();
        double average[]=new double[4];
        for (int i = 0; i < files.size(); i++) {
            File_f f=files.get(i);
            if (f.getSect()==code ){
                x1.add(f);
            }
        }
        int div[]=new int[4];

        for (int i = 0; i < x1.size(); i++) {
            double total = 0;
            int div1 = 0;

            File_f f = x1.get(i);
            for (int j = 0; j < 7; j++) {


                    for (Subsection sb : f.section[j].subsections) {

                        if (sb.text.equals(subSection)){
                            int index=(f.yaer-2)%4;
                            div[index]+=f.getÖğrenciSayısı();
                            average[index]+=sb.average*f.getÖğrenciSayısı();
                            System.out.println(average[index]+" ögrenci sayısı="+div[index]);
                        }


                    }

                }
            }
        for (int i = 0; i <average.length ; i++) {
            System.out.println("total is "+average[i] +" div ="+div[i]) ;
            average[i]=average[i]/div[i];
            System.out.println(average[i]);

        }






        return average;
    }

    }

