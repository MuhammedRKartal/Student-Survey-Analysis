package GUIDemo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainObjects.File_f;

import java.io.File;
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
    }


    public void choseFileButtonPushed(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));
        filesArray =  fileChooser.showOpenMultipleDialog(null);


        //load dummy data
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

}