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

    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("firstScreenReal.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Student Survey Analysis");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static List<File> filesArray = new LinkedList<>();
    public static ArrayList<File> usableArray; //WE WILL USE THIS ARRAY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static ArrayList<File_f> multifileGoingToBeUsedInGraphs;

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



    @FXML
    private TableView<Comments> commentTable;

    @FXML
    public TableColumn<Comments,String> comment;

    @FXML
    public TableColumn<Comments,String> userAddedComment;


    @FXML
    public Label savePathText;

    @FXML
    public RadioButton enableSavePathText;

    @Override
    //These are mandatory
    public void initialize(URL location, ResourceBundle resources) {
        //set up table columns
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        section.setCellValueFactory(new PropertyValueFactory<>("section"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        term.setCellValueFactory(new PropertyValueFactory<>("term"));

        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        userAddedComment.setCellValueFactory(new PropertyValueFactory<>("userAddedComment"));

        userAddedComment.setCellFactory(TextFieldTableCell.forTableColumn());
        commentTable.setEditable(true);

        savePathText.setText("");
    }


    public void choseFileButtonPushed(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));

        filesArray = fileChooser.showOpenMultipleDialog(null);

        //load data
        courseTable.setItems(GUITableMethods.getCourse());
        multifileGoingToBeUsedInGraphs = new ArrayList<>();
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray);
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs));
    }

    public void choseDirectoryButtonPushed(ActionEvent event) throws IOException{
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory Dialog");
        filesArray = new LinkedList<>();

        File directory = directoryChooser.showDialog(null);
        List<File> tempFilesArray = new LinkedList<>();
        tempFilesArray = Arrays.asList(directory.listFiles());

        for(int i=0; i<tempFilesArray.size();i++){
            String x = tempFilesArray.get(i).getName();
            String[] splittedName = x.split("\\.");
            if (splittedName[splittedName.length-1].equals("xlsx")){
                System.out.println("x");
                filesArray.add(tempFilesArray.get(i));
            }
        }

        //load data
        courseTable.setItems(GUITableMethods.getCourse());
        multifileGoingToBeUsedInGraphs = new ArrayList<>();
        fileReadOperations.Load_Multi_Files( multifileGoingToBeUsedInGraphs, usableArray);
        commentTable.setItems(GUITableMethods.getComments(multifileGoingToBeUsedInGraphs));
    }


    public void saveButtonPushed(ActionEvent event) throws IOException {
        if(usableArray.size()==1){
            File_f graphs = multifileGoingToBeUsedInGraphs.get(0);
            saveOperations.saveToPDF(graphs);
        }
    }

    public void changeUserAddedCommentCellEvent(TableColumn.CellEditEvent editedCell){
        Comments commentSelected = commentTable.getSelectionModel().getSelectedItem();
        commentSelected.setUserAddedComment(editedCell.getNewValue().toString());
    }

    public void savePathTextEnabled(){
        try{
            boolean x = enableSavePathText.isSelected();
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
