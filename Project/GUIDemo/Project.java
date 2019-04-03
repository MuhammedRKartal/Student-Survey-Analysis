/**
 * @Author Muhammed Rahmetullah Kartal
 * @Date 02.04.2019
 * @Description: Main program for GUI
 */


package Project.GUIDemo;

import Project.mainObjects.File_f;
import javafx.application.Application;
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


    //FXML Course Table elements for GUI (top-right table)
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


    //FXML Comment Table elements for GUI (bottom-left table)
    @FXML
    private TableView<Comments> commentTable;

    @FXML
    public TableColumn<Comments,String> comment;

    @FXML
    public TableColumn<Comments,String> userAddedComment;


    //FXML Radio button elements for GUI, setting label to path when clicked etc.
    @FXML
    public RadioButton enableSavePathText;

    @FXML
    public Label savePathText;

    @FXML
    public Label savedText;



    //Initializing GUI FXML elements.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up table columns
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        section.setCellValueFactory(new PropertyValueFactory<>("Sections"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        term.setCellValueFactory(new PropertyValueFactory<>("term"));

        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        userAddedComment.setCellValueFactory(new PropertyValueFactory<>("userAddedComment"));

        userAddedComment.setCellFactory(TextFieldTableCell.forTableColumn());
        commentTable.setEditable(true);

        savePathText.setText("");
        savedText.setText("");
    }

    //When Select File(s) button pushed it pop ups a screen and ask you for selecting file(s).
    public void choseFileButtonPushed(ActionEvent event) throws IOException{
        //Setting the File Chooser pop-up screen.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));


        filesArray = fileChooser.showOpenMultipleDialog(null); //Popping up the screen and storing selected files in the List.


        //Load Data
        courseTable.setItems(GUITableMethods.getCourse()); //Load file names to course table.
        multifileGoingToBeUsedInGraphs = new ArrayList<>(); // Reseting the elements of ArrayList.
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray); //Reading the file(s).
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs)); //Adding comments of files to comment table.

        savedText.setText("");
    }

    //When Select Directory button pushed it pop ups a screen and ask you for selecting directory and takes only excel files from this directory.
    public void choseDirectoryButtonPushed(ActionEvent event) throws IOException{
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
        courseTable.setItems(GUITableMethods.getCourse());
        multifileGoingToBeUsedInGraphs = new ArrayList<>();
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray);
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs));

        savedText.setText("");
    }

    //Checks for single or multifile, makes the analysis of file(s) and saves the analysis on selected directory.
    public void saveButtonPushed(ActionEvent event) throws IOException {
        if(usableArray.size()==1){
            File_f graphs = multifileGoingToBeUsedInGraphs.get(0);
            saveOperations.saveToPDF(graphs);
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

}
