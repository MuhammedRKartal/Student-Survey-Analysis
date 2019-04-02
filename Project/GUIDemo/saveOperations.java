/**
 * @Author Muhammed Rahmetullah Kartal
 * @Date 31.03.2019
 * @Description: Saving graphs to PDF.
 */


package Project.GUIDemo;

import Project.mainObjects.File_f;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import org.knowm.xchart.BitmapEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class saveOperations {
    public static String currentPath = ""; //Global variable for current path.

    //This method saves the graphs to PDF file.
    //Allows user to chose save directory.
    public static void saveToPDF(File_f file) throws IOException {
        //Asking for save directory.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to select save directory?");
        alert.setTitle("Question");
        alert.showAndWait();

        //Chosing the save path.
        Path currentRelativePath = Paths.get("");
        if (alert.getResult() == ButtonType.OK){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Path");
            File directory = directoryChooser.showDialog(null);
            currentRelativePath = Paths.get(directory.getPath());
        }
        currentPath= currentRelativePath.toAbsolutePath().toString();


        //Single file save operations, it firstly saves the graphs on PNG format then adds that PNGs to PDF file.
//        if (usableArray.size()==1){
        BitmapEncoder.saveBitmap(Charts.createCevaplamaOraniPieChart(file), currentPath+"\\chart1singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createSectionOrtalamariPieChart(file), currentPath+"\\chart2singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createScoringForAllSectionsPieChart(file), currentPath+"\\chart3singlefile.png", BitmapEncoder.BitmapFormat.PNG);

        BitmapEncoder.saveBitmap(Charts.createScoringForAllSectionsBarChart(file), currentPath+"\\chart4singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createMultipleAveragesBarChart(file), currentPath+"\\chart5singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createScoringOfLearningOutComesBarChart(file), currentPath+"\\chart6singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariBarChart(file), currentPath+"\\chart7singlefile.png", BitmapEncoder.BitmapFormat.PNG);

        BitmapEncoder.saveBitmap(Charts.createMultipleAveragesRadarChart(file), currentPath+"\\chart8singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariRadarChart(file),currentPath+"\\chart9singlefile.png", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmap(Charts.createScoringOfLearningOutComesRadarChart(file),currentPath+"\\chart10singlefile.png", BitmapEncoder.BitmapFormat.PNG);

        Document document = new Document();

        String input1 = currentPath+"\\chart1singlefile.png"; // .gif and .jpg are ok too!
        String input2 = currentPath+"\\chart2singlefile.png";
        String input3 = currentPath+"\\chart3singlefile.png";
        String input4 = currentPath+"\\chart4singlefile.png";
        String input5 = currentPath+"\\chart5singlefile.png";
        String input6 = currentPath+"\\chart6singlefile.png";
        String input7 = currentPath+"\\chart7singlefile.png";
        String input8 = currentPath+"\\chart8singlefile.png";
        String input9 = currentPath+"\\chart9singlefile.png";
        String input10 = currentPath+"\\chart10singlefile.png";

        String output = currentPath+"\\outputs.pdf";

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
