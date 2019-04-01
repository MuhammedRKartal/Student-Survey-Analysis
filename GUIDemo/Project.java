package GUIDemo;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainObjects.File_f;
import org.knowm.xchart.BitmapEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Project extends Application implements Initializable {

    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("firstScreenReal.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static List<File> filesArray;
    public static ArrayList<File> usableArray; //WE WILL USE THIS ARRAY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    //These are mandatory to use fxml Tableview
    @FXML
    private TableView<Course> courseTable;

    @FXML
    public TableColumn<Course, String> courseCode;

    @FXML
    public TableColumn<Course,String> section;

    @FXML
    public TableColumn<Course,String> year;

    @FXML
    public TableColumn<Course,String> term;


    @Override
    //These are mandatory
    public void initialize(URL location, ResourceBundle resources) {
        //set up table columns
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        section.setCellValueFactory(new PropertyValueFactory<>("section"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        term.setCellValueFactory(new PropertyValueFactory<>("term"));
    }

    // Adding chosen files to the table on first screen.
    public ObservableList<Course> getCourse(){
        //Must be ObservableList type
        ObservableList<Course> courses = FXCollections.observableArrayList();

        ArrayList<File> tempFileArray= new ArrayList<>();
        usableArray = new ArrayList<>();

        for(int i = 0; i<filesArray.size(); i++){
            tempFileArray.add(filesArray.get(i));
        }

        for (File x:tempFileArray) {
            System.out.println(x.getName());
        }
//        if(tempFileArray.size()==0){
//            System.out.println("Please select file(s)");
//        }
        try{
            int counter = 0;
            for (int i = 0; i < tempFileArray.size(); i++) {
                String name = tempFileArray.get(i).getName();
                String temp = name.split("x")[0];
                try{
                    String[] nameParts = temp.split("_");
                    String courseCode = nameParts[0];
                    String section = nameParts[1];
                    String year = nameParts[2];
                    String term = nameParts[3];
                    Course oneCourse = new Course(courseCode, section, year, term);
                    courses.add(oneCourse);
                    counter++;
                    usableArray.add(tempFileArray.get(i));
                }
                catch (Exception exception){
                    System.out.println("Please enter file name on CourseCode_Section_Year_Term.xlsx format");
                    System.out.println(tempFileArray.get(counter).getName() + " is the problem.");
                    counter++;
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception);
        }

        return courses;
    }


    public void choseFileButtonPushed(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));
        filesArray =  fileChooser.showOpenMultipleDialog(null);


        //load dummy data
        courseTable.setItems(getCourse());
    }


    public static ArrayList<File_f> multifileGoingToBeUsedInGraphs = new ArrayList<>();

    public void saveButtonPushed(ActionEvent event) throws IOException {
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray);
        if(usableArray.size()==1){
            File_f graphs = multifileGoingToBeUsedInGraphs.get(0);
            saveToPDF(graphs);
        }

    }




    public static void saveToPDF(File_f file) throws IOException {

//        if (usableArray.size()==1){
            BitmapEncoder.saveBitmap(Charts.createCevaplamaOraniPieChart(file), "C:/Users/hiroh/Desktop/Try/chart1singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamariPieChart(file), "C:/Users/hiroh/Desktop/Try/chart2singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createScoringForAllSectionsPieChart(file), "C:/Users/hiroh/Desktop/Try/chart3singlefile.png", BitmapEncoder.BitmapFormat.PNG);

            BitmapEncoder.saveBitmap(Charts.createScoringForAllSectionsBarChart(file), "C:/Users/hiroh/Desktop/Try/chart4singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createMultipleAveragesBarChart(file), "C:/Users/hiroh/Desktop/Try/chart5singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createScoringOfLearningOutComesBarChart(file), "C:/Users/hiroh/Desktop/Try/chart6singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariBarChart(file), "C:/Users/hiroh/Desktop/Try/chart7singlefile.png", BitmapEncoder.BitmapFormat.PNG);

            BitmapEncoder.saveBitmap(Charts.createMultipleAveragesRadarChart(file), "C:/Users/hiroh/Desktop/Try/chart8singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariRadarChart(file),"C:/Users/hiroh/Desktop/Try/chart9singlefile.png", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmap(Charts.createScoringOfLearningOutComesRadarChart(file),"C:/Users/hiroh/Desktop/Try/chart10singlefile.png", BitmapEncoder.BitmapFormat.PNG);




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
//        }

//        else{
            //multifile grafikleri
//        }

    }

}