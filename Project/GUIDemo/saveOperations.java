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
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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


        ArrayList<CategoryChart> allSectionsVoteBCS = Charts.createAllSectionsVoteBCS(file);
        ArrayList<PieChart> allSectionsVotePCS = Charts.createAllSectionsVotePCS(file);




        //Single file save operations, it firstly saves the graphs on PNG format then adds that PNGs to PDF file.
//        if (usableArray.size()==1){

        Document document = new Document();
        
        String output = currentPath+"\\outputs.pdf";

        try {
            FileOutputStream fos = new FileOutputStream(output);
            PdfWriter writer = (PdfWriter) PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            String path;

            path = String.format("%s\\PieChartAnswering%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createCevaplamaOraniPieChart(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\PieChartSectionAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamariPieChart(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\BarChartSectionAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariBarChart(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            for (int i = 0; i<allSectionsVoteBCS.size(); i++) {
                path = String.format("%s\\BarChartSubsectionVote%dSinglefile.png", currentPath, i);
                BitmapEncoder.saveBitmap(allSectionsVoteBCS.get(i), path, BitmapEncoder.BitmapFormat.PNG);
                document.add(com.itextpdf.text.Image.getInstance(path));
            }
            for (int i = 0; i<allSectionsVoteBCS.size(); i++) {
                path = String.format("%s\\PieChartSubsectionVote%dSinglefile.png", currentPath, i);
                BitmapEncoder.saveBitmap(allSectionsVotePCS.get(i), path, BitmapEncoder.BitmapFormat.PNG);
                document.add(com.itextpdf.text.Image.getInstance(path));
            }

            path = String.format("%s\\RadarChartSubsectionAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createAllSubsectionAveragesRC(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\RadarChartSubsectionandUniAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createAllSubsectionAveragesUniAveragesRC(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\BarChartSubsectionandUniAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createAllSubsectionAveragesUniAveragesBC(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\RadarChartSectionAverages%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createSectionOrtalamalariRadarChart(file),path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\BarChartVotesSection%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createScoringForAllSectionsBarChart(file), path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

            path = String.format("%s\\RadarChartVotesSection%dSinglefile.png", currentPath, 0);
            BitmapEncoder.saveBitmap(Charts.createScoringOfLearningOutComesRadarChart(file),path, BitmapEncoder.BitmapFormat.PNG);
            document.add(com.itextpdf.text.Image.getInstance(path));

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
