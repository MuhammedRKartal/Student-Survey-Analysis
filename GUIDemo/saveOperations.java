package GUIDemo;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import mainObjects.File_f;
import org.knowm.xchart.BitmapEncoder;

import java.io.FileOutputStream;
import java.io.IOException;

public class saveOperations {
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
