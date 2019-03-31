package GUIDemo;

import barCharts.barChart;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainObjects.File_f;
import mainObjects.Section;
import mainObjects.Subsection;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.knowm.xchart.*;
import pieCharts.pieChart;
import radarCharts.radarChart;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class Project extends Application {
    Parent root;

    {
        try {
            root = FXMLLoader.load(getClass().getResource("firstScreenReal.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Scene scene = new Scene(root);

//    private static final String FILE_NAME = x.get(0).getAbsolutePath();
    public void start(Stage stage) throws Exception {
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void readButtonPushed(ActionEvent event) throws IOException {
        read();
    }

    public static File_f read() throws IOException {
        ArrayList<File> x = FirstPageController.usableArray;
        String FILE_NAME = x.get(0).getAbsolutePath();
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
            file.setDersKodusection(cell3.getStringCellValue());
            System.out.println(file.getDersKodusection());
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
                    Section section= new Section();
                    section.setText(r.getCell(0).getStringCellValue());
                    file.section[++index]=section;
                    i++;}

                r=datatypeSheet.getRow(i);

                Subsection subsection= new Subsection();
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
//printCevaplamaOraniPieChart(file);

        //Bu doğru ancak pie chart'ı yüzdelik değilde direk değerleri göstermek isityorum

//        printSectionOrtalamalariPieChart(file);

//        printScoringForAllSectionsPieChart(file);



//        printSectionOrtalamalariBarChart(file);

//        printScoringForAllSectionsBarChart(file);
//
//        printMultipleAveragesBarChart(file);

//        printScoringOfLearningOutComesBarChart(file);


        //not finished
//        printMultipleAveragesRadarChart(file);
        return file;


    }

    public void saveButtonPushed(ActionEvent event) throws IOException {
        File_f file = read();
        saveToPDF(file);
    }

    public static void saveToPDF(File_f file) throws IOException {
        BitmapEncoder.saveBitmap(createCevaplamaOraniPieChart(file), "C:/Users/hiroh/Desktop/Try/chart1singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createSectionOrtalamariPieChart(file), "C:/Users/hiroh/Desktop/Try/chart2singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createScoringForAllSectionsPieChart(file), "C:/Users/hiroh/Desktop/Try/chart3singlefile.png", BitmapEncoder.BitmapFormat.PNG);

        BitmapEncoder.saveBitmap(createScoringForAllSectionsBarChart(file), "C:/Users/hiroh/Desktop/Try/chart4singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createMultipleAveragesBarChart(file), "C:/Users/hiroh/Desktop/Try/chart4singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createScoringOfLearningOutComesBarChart(file), "C:/Users/hiroh/Desktop/Try/chart6singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createSectionOrtalamalariBarChart(file), "C:/Users/hiroh/Desktop/Try/chart7singlefile.png", BitmapEncoder.BitmapFormat.PNG);

        BitmapEncoder.saveBitmap(createMultipleAveragesRadarChart(file), "C:/Users/hiroh/Desktop/Try/chart8singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createSectionOrtalamalariRadarChart(file),"C:/Users/hiroh/Desktop/Try/chart9singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(createScoringOfLearningOutComesRadarChart(file),"C:/Users/hiroh/Desktop/Try/chart10singlefile.png", BitmapEncoder.BitmapFormat.PNG);




        Document document = new Document();

        String input1 = "C:/Users/hiroh/Desktop/Try/chart1singlefile.png"; // .gif and .jpg are ok too!
        String input2 = "C:/Users/hiroh/Desktop/Try/chart2singlefile.png";
        String input3 = "C:/Users/hiroh/Desktop/Try/chart3singlefile.png";
        String input4 = "C:/Users/hiroh/Desktop/Try/chart4singlefile.png";
        String input5 = "C:/Users/hiroh/Desktop/Try/chart5singlefile.png";
        String input6 = "C:/Users/hiroh/Desktop/Try/chart6singlefile.png";
        String input7 = "C:/Users/hiroh/Desktop/Try/chart7singlefile.png";
        String input8 = "C:/Users/hiroh/Desktop/Try/chart8singlefile.png";
        String input9 = "C:/Users/hiroh/Desktop/Try/chart9singlefile.png";
        String input10 = "C:/Users/hiroh/Desktop/Try/chart10singlefile.png";

        String output = "C:/Users/hiroh/Desktop/Try/outputs.pdf";

        try {
            FileOutputStream fos = new FileOutputStream(output);
            PdfWriter writer = (PdfWriter) PdfWriter.getInstance(document, fos);
            writer.open();

            document.open();
            document.add(com.itextpdf.text.Image.getInstance(input1));
            document.add(com.itextpdf.text.Image.getInstance(input2));
            document.add(com.itextpdf.text.Image.getInstance(input3));
            document.add(com.itextpdf.text.Image.getInstance(input4));
            document.add(com.itextpdf.text.Image.getInstance(input5));
            document.add(com.itextpdf.text.Image.getInstance(input6));
            document.add(com.itextpdf.text.Image.getInstance(input7));
            document.add(com.itextpdf.text.Image.getInstance(input8));
            document.add(com.itextpdf.text.Image.getInstance(input9));
            document.add(com.itextpdf.text.Image.getInstance(input10));
            document.close();

            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }










    public static RadarChart createSectionOrtalamalariRadarChart(File_f file) {
        // Create Chart
        radarChart chart = new radarChart(500,500,"Course Averages");

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


        ArrayList<Double> valuesCourse = new ArrayList<>();
        valuesCourse.add((double) sectionAverages[0]/5);
        valuesCourse.add((double) sectionAverages[1]/5);
        valuesCourse.add((double) sectionAverages[2]/5);
        valuesCourse.add((double) sectionAverages[3]/5);
        valuesCourse.add((double) sectionAverages[4]/5);
        valuesCourse.add((double) sectionAverages[5]/5);
        valuesCourse.add((double) sectionAverages[6]/5);



        chart.setLabelNames(parameters);
        chart.addElements("Values Course", valuesCourse);


        return chart.getChart();
    }

    public static void printSectionOrtalamalariRadarChart(File_f file){
        RadarChart chart = createSectionOrtalamalariRadarChart(file);
        new SwingWrapper<RadarChart>(chart).displayChart();
    }








    public static RadarChart createScoringOfLearningOutComesRadarChart(File_f file) {
        radarChart chart = new radarChart(500,500,"Scoring of Learning Outcomes");

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Score 1");
        parameters.add("Score 2");
        parameters.add("Score 3");
        parameters.add("Score 4");
        parameters.add("Score 5");

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

        int totalScoringForRange = numberOfOne+numberOfTwo+numberOfThree+numberOfFour+numberofFive;


        ArrayList<Double> valuesCourse = new ArrayList<>();
        valuesCourse.add((double)numberOfOne/totalScoringForRange);
        valuesCourse.add((double)numberOfTwo/totalScoringForRange);
        valuesCourse.add((double)numberOfThree/totalScoringForRange);
        valuesCourse.add((double)numberOfFour/totalScoringForRange);
        valuesCourse.add((double)numberofFive/totalScoringForRange);



        chart.setLabelNames(parameters);
        chart.addElements("Scoring of Course", valuesCourse);

        return chart.getChart();

    }

    public static void printScoringOfLearningOutComesRadarChart(File_f file){
        RadarChart chart = createScoringOfLearningOutComesRadarChart(file);
        new SwingWrapper<RadarChart>(chart).displayChart();
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



    // #####################################################################################################################################
    // #####################################################################################################################################
    // #####################################################################################################################################
    // ##############################################################BarCharts##############################################################
    // #####################################################################################################################################
    // #####################################################################################################################################
    // #####################################################################################################################################



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





    // #####################################################################################################################################
    // #####################################################################################################################################
    // #####################################################################################################################################
    // #############################################################PieChart################################################################
    // #####################################################################################################################################
    // #####################################################################################################################################
    // #####################################################################################################################################

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








    public static PieChart createCevaplamaOraniPieChart(File_f file){
        pieChart chart1 = new pieChart(400,250,"Cevaplama Oranı");
        chart1.addSingleElement("Cevaplayan Öğrenci",file.getCevapAdedi());
        chart1.addSingleElement("Cevaplamayan Öğrenci", file.getÖğrenciSayısı());
        return chart1.getChart();
    }

    public static void printCevaplamaOraniPieChart(File_f file){
        PieChart chart = createCevaplamaOraniPieChart(file);
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