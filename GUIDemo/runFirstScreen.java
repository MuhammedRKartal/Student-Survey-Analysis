package GUIDemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class runFirstScreen extends Application {

    //Calling the firstScreenReal.fxml on the stage.
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("firstScreenReal.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
