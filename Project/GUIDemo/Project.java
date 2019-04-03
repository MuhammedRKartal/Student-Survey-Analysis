/**
 * @Author Muhammed Rahmetullah Kartal
 * @Date 02.04.2019
 * @Description: Main program for GUI
 */


package Project.GUIDemo;

import Project.mainObjects.File_f;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Project extends Application implements Initializable {

    //JavaFX Screen Start
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("firstScreenReal.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Student Survey Analysis");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

    //Global Variables
    public static List<File> filesArray = new LinkedList<>(); //ArrayList for chosing pop-up screen.
    public static ArrayList<File> usableArray; //ArrayList for just accepted typed files.
    public static ArrayList<File_f> multifileGoingToBeUsedInGraphs; //File_f object casted usableArray.

    public static String SelectedCourseCode = "";
    public static String SelectedSection = "";
    public static String SelectedSubSection = "";
    public static Set<Integer> SelectedYears;


    //FXML Course Table elements for GUI (top-right table)
    @FXML private TableView<Course> courseTable;
    @FXML public TableColumn<Course, String> courseCode;
    @FXML public TableColumn<Course,String> tableSection;
    @FXML public TableColumn<Course,String> year;
    @FXML public TableColumn<Course,String> term;

    //FXML Comment Table elements for GUI (bottom-left table)
    @FXML private TableView<Comments> commentTable;
    @FXML public TableColumn<Comments,String> comment;
    @FXML public TableColumn<Comments,String> userAddedComment;

    //FXML Radio button elements for GUI, setting label to path when clicked etc.
    @FXML public RadioButton enableSavePathText;
    @FXML public Label savePathText;
    @FXML public Label savedText;

    //FXML ListView of Years
    @FXML public ListView listView;

    //FXML ChoiceBox of CourseCode
    @FXML public ChoiceBox choiceBoxCourseCodesBC;
    @FXML public ChoiceBox choiceBoxSectionsBC;
    @FXML public ChoiceBox choiceBoxSubSectionsBC;



    //Initializing GUI FXML elements.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up table columns
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        tableSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        term.setCellValueFactory(new PropertyValueFactory<>("term"));

        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        userAddedComment.setCellValueFactory(new PropertyValueFactory<>("userAddedComment"));

        userAddedComment.setCellFactory(TextFieldTableCell.forTableColumn());
        commentTable.setEditable(true); //To edit comment table.

        courseTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); //To select single file from course table.

        savePathText.setText("");
        savedText.setText("");

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //For selecting multiple years on multiple file operations.

    }






    //Selects course code to use it in other sections.
    public void selectCourseButtonPushed(ActionEvent event){
        SelectedCourseCode=getSelectedCourseCode();

        //Filling the years listview.
        ObservableList<Integer> yearsBC = FXCollections.observableArrayList(getYearsOfUsableArray(SelectedCourseCode));
        listView.setItems(yearsBC);

        //Filling the Sections choicebox
        ObservableList<String> sectionsBC = FXCollections.observableArrayList(getSectionsOfCourseCode(SelectedCourseCode));
        choiceBoxSectionsBC.setItems(sectionsBC);

        //Filling the SubSections choicebox
        ObservableList<String> subsectionsBC = FXCollections.observableArrayList(getsubsectionsOfCourseCode(SelectedCourseCode));
        choiceBoxSubSectionsBC.setItems(subsectionsBC);
    }


    //Makes ready the parameters BarChart.
    public void drawBarChartButtonPushed(ActionEvent event){

        SelectedYears = getSelectedYears();
        SelectedSection = getSelectedSection();
        SelectedSubSection = getSelectedSubSection();

        System.out.println(SelectedSection);
        System.out.println(SelectedSubSection);
        for (int year:SelectedYears){
            System.out.println(year);
        }
    }







    //################################################################################################################################################
    //################################################################################################################################################
    //################################################################Chose File Buttons##############################################################
    //################################################################################################################################################
    //################################################################################################################################################


    //When Select File(s) button pushed it pop ups a screen and ask you for selecting file(s).
    public void choseFileButtonPushed(ActionEvent event) throws IOException{

        try{
            listView.getItems().clear();
            choiceBoxSectionsBC.getItems().clear();
            choiceBoxSubSectionsBC.getItems().clear();
        }
        catch (Exception ex){ }

        //Setting the File Chooser pop-up screen.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));


        filesArray = fileChooser.showOpenMultipleDialog(null); //Popping up the screen and storing selected files in the List.


        //Load Data
        ObservableList<Course> courseTableItems = GUITableMethods.getCourse();
        courseTable.setItems(courseTableItems);//Load file names to course table.

        multifileGoingToBeUsedInGraphs = new ArrayList<>(); // Reseting the elements of ArrayList.
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray); //Reading the file(s).
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs)); //Adding comments of files to comment table.

        savedText.setText("");

        ObservableList<String> courseCodesBC = FXCollections.observableArrayList(getCourseCodesOfUsableArray());
        choiceBoxCourseCodesBC.setItems(courseCodesBC);

    }

    //When Select Directory button pushed it pop ups a screen and ask you for selecting directory and takes only excel files from this directory.
    public void choseDirectoryButtonPushed(ActionEvent event) throws IOException{
        try{
            listView.getItems().clear();
            choiceBoxSectionsBC.getItems().clear();
            choiceBoxSubSectionsBC.getItems().clear();
        }
        catch (Exception ex){ }


        //Setting the directory chooser.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory Dialog");

        filesArray = new LinkedList<>();//Resetting the elements of List

        //Setting the pop-up screen
        File directory = directoryChooser.showDialog(null);

        //Takes every file in a directory to List.
        List<File> tempFilesArray = new LinkedList<>();
        tempFilesArray = Arrays.asList(directory.listFiles());

        //Seperates only excel files and stores them on ArrayList
        for(int i=0; i<tempFilesArray.size();i++){
            String x = tempFilesArray.get(i).getName();
            String[] splittedName = x.split("\\.");
            if (splittedName[splittedName.length-1].equals("xlsx")){
                filesArray.add(tempFilesArray.get(i));
            }
        }

        //load data
        ObservableList<Course> courseTableItems = GUITableMethods.getCourse();
        courseTable.setItems(courseTableItems);

        multifileGoingToBeUsedInGraphs = new ArrayList<>();
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray);
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs));

        savedText.setText("");

        ObservableList<String> courseCodesBC = FXCollections.observableArrayList(getCourseCodesOfUsableArray());
        choiceBoxCourseCodesBC.setItems(courseCodesBC);


    }









    //################################################################################################################################################
    //################################################################################################################################################
    //################################################################Save File Buttons##############################################################
    //################################################################################################################################################
    //################################################################################################################################################


    //Checks for single or multifile, makes the analysis of file(s) and saves the analysis on selected directory.
    public void saveSingleFileButtonPushed(ActionEvent event) throws IOException {
        if(usableArray.size()==1){
            File_f graphs = multifileGoingToBeUsedInGraphs.get(0);
            saveOperations.saveSingleFileToPDF(graphs);

            savedText.setText("Saved ✓");
        }
        else{
            File_f graph = getSelectedSingleFile();
            saveOperations.saveSingleFileToPDF(graph);

            savedText.setText("Saved ✓");
        }
    }

    //Allows user to add UserAddedComment
    public void changeUserAddedCommentCellEvent(TableColumn.CellEditEvent editedCell){
        Comments commentSelected = commentTable.getSelectionModel().getSelectedItem();
        commentSelected.setUserAddedComment(editedCell.getNewValue().toString());
    }

    //If radiobox on GUI is selected it shows the save path.
    public void savePathTextEnabled(){
        try{
            if(enableSavePathText.isSelected()){
                savePathText.setText(saveOperations.currentPath);
            }
            else{
                savePathText.setText("");
            }
        }
        catch (Exception ex){
        }
    }







    //################################################################################################################################################
    //################################################################################################################################################
    //################################################################Draw Barchart Elements##########################################################
    //################################################################################################################################################
    //################################################################################################################################################



    //Gets unique years from selected files.
    public Set<Integer> getYearsOfUsableArray(String courseCode) {
        Set<Integer> years = new HashSet<>();
        try{
            for (int i = 0; i < multifileGoingToBeUsedInGraphs.size(); i++) {
                if (multifileGoingToBeUsedInGraphs.get(i).getDersKodu().equals(SelectedCourseCode)) {
                    years.add(multifileGoingToBeUsedInGraphs.get(i).year);
                }
            }
        }
        catch (Exception ex){
        }
        return years;
    }


    //Gets unique course codes from selected files.
    public Set<String> getCourseCodesOfUsableArray(){
        Set<String> courseCodes = new HashSet<>();
        try{
            for (int i=0; i<multifileGoingToBeUsedInGraphs.size(); i++){
                courseCodes.add(multifileGoingToBeUsedInGraphs.get(i).getDersKodu());
            }
        }
        catch (Exception ex){
        }
        return courseCodes;
    }

    //Gets sections of selected course code.
    public ArrayList<String> getSectionsOfCourseCode(String courseCode){
        ArrayList<String> sectionsOfCourse = new ArrayList<>();
        try{
            for (int i=0; i<multifileGoingToBeUsedInGraphs.size(); i++){
                for (int j=0; j<multifileGoingToBeUsedInGraphs.get(i).Sections.length;j++){
                    sectionsOfCourse.add(multifileGoingToBeUsedInGraphs.get(i).Sections[j].getText());
                }
            }
        }
        catch (Exception ex){
        }
        return sectionsOfCourse;
    }


    //Gets subsections of selected course code.
    public ArrayList<String> getsubsectionsOfCourseCode(String courseCode){
        ArrayList<String> subsectionsOfCourse = new ArrayList<>();
        try{
            for (int i=0; i<multifileGoingToBeUsedInGraphs.size(); i++){
                for (int j=0; j<multifileGoingToBeUsedInGraphs.get(i).Sections.length;j++){
                    for (int k=0; k<multifileGoingToBeUsedInGraphs.get(i).Sections[j].subsections.size();k++){
                        subsectionsOfCourse.add(multifileGoingToBeUsedInGraphs.get(i).Sections[j].subsections.get(k).text);
                    }
                }
            }
        }
        catch (Exception ex){
        }
        return subsectionsOfCourse;
    }

    public Set<Integer> getSelectedYears(){

        ObservableList<Integer> x = listView.getSelectionModel().getSelectedItems();
        Set<Integer> y = new HashSet<>();
        for (int i=0; i<x.size(); i++){
            y.add(x.get(i));
        }
        return y;
    }

    public String getSelectedCourseCode(){
        return choiceBoxCourseCodesBC.getValue().toString();
    }

    public String getSelectedSection(){
        return choiceBoxSectionsBC.getValue().toString();
    }

    public String getSelectedSubSection(){
        return choiceBoxSubSectionsBC.getValue().toString();
    }

    public File_f getSelectedSingleFile(){
        ObservableList<Course> x = courseTable.getSelectionModel().getSelectedItems();
        return x.get(0).getFileFile_f();
    }





}
