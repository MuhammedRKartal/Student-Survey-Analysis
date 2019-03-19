package Project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.knowm.xchart.RadarChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.io.*;




public class Project2 {




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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //istediğinizi çalıştırın

//        printCevaplamaOraniPieChart(file);

        //Bu doğru ancak pie chart'ı yüzdelik değilde direk değerleri göstermek isityorum

//        printSectionOrtalamalariPieChart(file);

        //printScroingForAllSectionsPieChart(file);

        //printSectionOrtalamalariBarChart(file);

//        printScoringForAllSectionsBarChart(file);
//
//        printMultipleAveragesBarChart(file);

//        printScoringOfLearningOutComesBarChart(file);

        //not finished
//        printMultipleAveragesRadarChart(file);


    }

    public static RadarChart createMultipleAveragesRadarChart(File_f file) {
        // Create Chart
        radarChart chart = new radarChart(500,500,"Multiple Averages");

        // Series
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Flipped");
        parameters.add("Course");
        parameters.add("Instructor");
        parameters.add("Lab");
        parameters.add("Teaching Ass.");
        parameters.add("Overall Evalution");
        parameters.add("Learning Outcomes");

        double[] sectionAverages = new double[7];
        double sectionAverage=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverage=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).average;
                sectionAverage = total/counter;
            }
            sectionAverages[i]=sectionAverage;
        }

        double[] sectionAveragesUniversity = new double[7];
        double sectionAverageUniversity=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverageUniversity=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).universityAverage;
                sectionAverageUniversity = total/counter;
            }
            sectionAveragesUniversity[i]=sectionAverageUniversity;
        }


        ArrayList<Double> valuesCourse = new ArrayList<>();
        valuesCourse.add((double) sectionAverages[0]/5);
        valuesCourse.add((double) sectionAverages[1]/5);
        valuesCourse.add((double) sectionAverages[2]/5);
        valuesCourse.add((double) sectionAverages[3]/5);
        valuesCourse.add((double) sectionAverages[4]/5);
        valuesCourse.add((double) sectionAverages[5]/5);
        valuesCourse.add((double) sectionAverages[6]/5);

        ArrayList<Double> valuesUniversity = new ArrayList<>();
        valuesUniversity.add((double) sectionAveragesUniversity[0]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[1]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[2]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[3]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[4]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[5]/5);
        valuesUniversity.add((double) sectionAveragesUniversity[6]/5);

        chart.setLabelNames(parameters);
        chart.addElements("Values Course", valuesCourse);
        chart.addElements("Values University", valuesUniversity);

        return chart.getChart();
    }

    public static void printMultipleAveragesRadarChart(File_f file){
        RadarChart chart = createMultipleAveragesRadarChart(file);
        new SwingWrapper<RadarChart>(chart).displayChart();
    }


    public static CategoryChart createScoringOfLearningOutComesBarChart(File_f file){

        barChart chart = new barChart(600, 500, "Number of Scores", "Score Points", "Numbers");
        ArrayList<String> x = new ArrayList<>();
        x.add("1");
        x.add("2");
        x.add("3");
        x.add("4");
        x.add("5");

        int numberOfOne=0;
        int numberOfTwo=0;
        int numberOfThree=0;
        int numberOfFour=0;
        int numberofFive=0;


        for(int j = 0; j<file.section[6].subsections.size();j++) {
            numberOfOne += (int) file.section[6].subsections.get(j).one;
            numberOfTwo += (int) file.section[6].subsections.get(j).two;
            numberOfThree += (int) file.section[6].subsections.get(j).three;
            numberOfFour += (int) file.section[6].subsections.get(j).four;
            numberofFive += (int) file.section[6].subsections.get(j).five;
        }



        ArrayList<Double> y = new ArrayList<>();
        y.add((double) numberOfOne);
        y.add((double) numberOfTwo);
        y.add((double) numberOfThree);
        y.add((double) numberOfFour);
        y.add((double) numberofFive);



        chart.addElements("Number for Learning Outcome Scores",x,y);


        return chart.getChart();

    }

    public static void printScoringOfLearningOutComesBarChart(File_f file){
        CategoryChart chart = createScoringOfLearningOutComesBarChart(file);
        new SwingWrapper<CategoryChart>(chart).displayChart();
    }








    public static CategoryChart createMultipleAveragesBarChart(File_f file){

        barChart chart = new barChart(970, 500, "Section Ortalamalari", "Sections", "Averages");
        ArrayList<String> x = new ArrayList<>();
        x.add("Flipped");
        x.add("Course");
        x.add("Instructor");
        x.add("Lab");
        x.add("Teching Ass.");
        x.add("Overall Evalution");
        x.add("Learning OutComes");

        double[] sectionAverages = new double[7];
        double sectionAverage=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverage=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).average;
                sectionAverage = total/counter;
            }
            sectionAverages[i]=sectionAverage;
        }

        double[] sectionAveragesUniversity = new double[7];
        double sectionAverageUniversity=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverageUniversity=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).universityAverage;
                sectionAverageUniversity = total/counter;
            }
            sectionAveragesUniversity[i]=sectionAverageUniversity;
        }

        ArrayList<Double> y = new ArrayList<>();
        y.add((double) sectionAverages[0]);
        y.add((double) sectionAverages[1]);
        y.add((double) sectionAverages[2]);
        y.add((double) sectionAverages[3]);
        y.add((double) sectionAverages[4]);
        y.add((double) sectionAverages[5]);
        y.add((double) sectionAverages[6]);

        ArrayList<Double> t = new ArrayList<>();
        t.add((double) sectionAveragesUniversity[0]);
        t.add((double) sectionAveragesUniversity[1]);
        t.add((double) sectionAveragesUniversity[2]);
        t.add((double) sectionAveragesUniversity[3]);
        t.add((double) sectionAveragesUniversity[4]);
        t.add((double) sectionAveragesUniversity[5]);
        t.add((double) sectionAveragesUniversity[6]);

        chart.addElements("Course",x,y);
        chart.addElements("University",x,t);


        return chart.getChart();

    }

    public static void printMultipleAveragesBarChart(File_f file){
        CategoryChart chart = createMultipleAveragesBarChart(file);
        new SwingWrapper<CategoryChart>(chart).displayChart();
    }














    public static CategoryChart createScoringForAllSectionsBarChart(File_f file){

        barChart chart = new barChart(600, 500, "Number of Scores", "Score Points", "Numbers");
        ArrayList<String> x = new ArrayList<>();
        x.add("1");
        x.add("2");
        x.add("3");
        x.add("4");
        x.add("5");


        int numberOfOne=0;
        int numberOfTwo=0;
        int numberOfThree=0;
        int numberOfFour=0;
        int numberofFive=0;

        for(int i =0; i<7;i++){


            for(int j = 0; j<file.section[i].subsections.size();j++){
                numberOfOne += (int)file.section[i].subsections.get(j).one;
                numberOfTwo += (int)file.section[i].subsections.get(j).two;
                numberOfThree += (int)file.section[i].subsections.get(j).three;
                numberOfFour += (int)file.section[i].subsections.get(j).four;
                numberofFive += (int)file.section[i].subsections.get(j).five;


            }

        }

        ArrayList<Double> y = new ArrayList<>();
        y.add((double) numberOfOne);
        y.add((double) numberOfTwo);
        y.add((double) numberOfThree);
        y.add((double) numberOfFour);
        y.add((double) numberofFive);


        chart.addElements("Quantities",x,y);


        return chart.getChart();

    }

    public static void printScoringForAllSectionsBarChart(File_f file){
        CategoryChart chart = createScoringForAllSectionsBarChart(file);
        new SwingWrapper<CategoryChart>(chart).displayChart();
    }













    public static CategoryChart createSectionOrtalamalariBarChart(File_f file){

        barChart chart = new barChart(970, 500, "Section Ortalamalari", "Sections", "Averages");
        ArrayList<String> x = new ArrayList<>();
        x.add("Flipped");
        x.add("Course");
        x.add("Instructor");
        x.add("Lab");
        x.add("Teching Ass.");
        x.add("Overall Evalution");
        x.add("Learning OutComes");

        double[] sectionAverages = new double[7];
        double sectionAverage=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverage=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).average;
                sectionAverage = total/counter;
            }
            sectionAverages[i]=sectionAverage;
        }

        ArrayList<Double> y = new ArrayList<>();
        y.add((double) sectionAverages[0]);
        y.add((double) sectionAverages[1]);
        y.add((double) sectionAverages[2]);
        y.add((double) sectionAverages[3]);
        y.add((double) sectionAverages[4]);
        y.add((double) sectionAverages[5]);
        y.add((double) sectionAverages[6]);


        chart.addElements("Average",x,y);


        return chart.getChart();

    }

    public static void printSectionOrtalamalariBarChart(File_f file){
        CategoryChart chart = createSectionOrtalamalariBarChart(file);
        new SwingWrapper<CategoryChart>(chart).displayChart();
    }








    public static PieChart createScoringForAllSectionsPieChart(File_f file){

        int numberOfOne=0;
        int numberOfTwo=0;
        int numberOfThree=0;
        int numberOfFour=0;
        int numberofFive=0;

        for(int i =0; i<7;i++){


            for(int j = 0; j<file.section[i].subsections.size();j++){
                numberOfOne += (int)file.section[i].subsections.get(j).one;
                numberOfTwo += (int)file.section[i].subsections.get(j).two;
                numberOfThree += (int)file.section[i].subsections.get(j).three;
                numberOfFour += (int)file.section[i].subsections.get(j).four;
                numberofFive += (int)file.section[i].subsections.get(j).five;


            }

        }

        pieChart chart1 = new pieChart(500,450,"Scroring Averages");
        chart1.addSingleElement("1",numberOfOne);
        chart1.addSingleElement("2", numberOfTwo);
        chart1.addSingleElement("3",numberOfThree);
        chart1.addSingleElement("4", numberOfFour);
        chart1.addSingleElement("5", numberofFive);

        return chart1.getChart();
    }

    public static void printScroingForAllSectionsPieChart(File_f file){
        PieChart chart = createScoringForAllSectionsPieChart(file);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                // Create and set up the window.
                JFrame frame = new JFrame("Charts");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // chart
                JPanel chartPanel = new XChartPanel<PieChart>(chart);
                frame.add(chartPanel, BorderLayout.CENTER);


                frame.pack();
                frame.setVisible(true);
            }
        });
    }










    public static PieChart createSectionOrtalamariPieChart(File_f file){

        double[] sectionAverages = new double[7];
        double sectionAverage=0;

        for(int i =0; i<7;i++){
            int counter=0;
            sectionAverage=0;
            double total=0;
            for(int j = 0; j<file.section[i].subsections.size();j++){
                counter++;
                total = total + file.section[i].subsections.get(j).average;
                sectionAverage = total/counter;
            }
            sectionAverages[i]=sectionAverage;
        }

        pieChart chart1 = new pieChart(500,450,"Section Averages");
        chart1.addSingleElement("Flipped Classroom",sectionAverages[0]);
        chart1.addSingleElement("Course", sectionAverages[1]);
        chart1.addSingleElement("Instructor",sectionAverages[2]);
        chart1.addSingleElement("Labs", sectionAverages[3]);
        chart1.addSingleElement("Teaching Assistant", sectionAverages[4]);
        chart1.addSingleElement("Overall Evaluation", sectionAverages[5]);
        chart1.addSingleElement("Course Learning Outcomes", sectionAverages[6]);
        return chart1.getChart();
    }

    public static void printSectionOrtalamalariPieChart(File_f file){
        PieChart chart = createSectionOrtalamariPieChart(file);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                // Create and set up the window.
                JFrame frame = new JFrame("Charts");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // chart
                JPanel chartPanel = new XChartPanel<PieChart>(chart);
                frame.add(chartPanel, BorderLayout.CENTER);


                frame.pack();
                frame.setVisible(true);
            }
        });
    }


















    public static PieChart createCevaplamaOraniChart(File_f file){
        pieChart chart1 = new pieChart(400,250,"Cevaplama Oranı");
        chart1.addSingleElement("Cevaplayan Öğrenci",file.getCevapAdedi());
        chart1.addSingleElement("Cevaplamayan Öğrenci", file.getÖğrenciSayısı());
        return chart1.getChart();
    }

    public static void printCevaplamaOraniPieChart(File_f file){
        PieChart chart = createCevaplamaOraniChart(file);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                // Create and set up the window.
                JFrame frame = new JFrame("Charts");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // chart
                JPanel chartPanel = new XChartPanel<PieChart>(chart);
                frame.add(chartPanel, BorderLayout.CENTER);


                frame.pack();
                frame.setVisible(true);
            }
        });
    }





}
