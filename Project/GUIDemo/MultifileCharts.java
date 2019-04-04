package Project.GUIDemo;

import Project.barCharts.barChart;
import Project.mainObjects.File_f;
import org.knowm.xchart.CategoryChart;

import java.util.ArrayList;

import static Project.GUIDemo.Project.*;

public class MultifileCharts {
    public static ArrayList<File_f> getSelectedFilesByCourseCode(String courseCode){
        ArrayList<File_f> files = new ArrayList<>();
        for (int i=0; i<multifileGoingToBeUsedInGraphs.size();i++){
            if (multifileGoingToBeUsedInGraphs.get(i).getDersKodu().equals(SelectedCourseCode)){
                files.add(multifileGoingToBeUsedInGraphs.get(i));
            }
        }
        return files;
    }

    public static CategoryChart createMultifileYearSectionGivenCourseCodeGraph(){


        ArrayList<File_f> selectedFilesByCourseCode = getSelectedFilesByCourseCode(SelectedCourseCode);
        double [][] allSectionAverages= MGDataExtractor.givenCourseCodeAveragesOfAllSections(selectedFilesByCourseCode);

        ArrayList<String> legends = new ArrayList<>();
        ArrayList<Double> yAxis2014 = new ArrayList<>();
        ArrayList<Double> yAxis2015 = new ArrayList<>();
        ArrayList<Double> yAxis2016 = new ArrayList<>();
        ArrayList<Double> yAxis2017 = new ArrayList<>();


        if (SelectedYears.contains(2014)){
            legends.add("2014");
        }
        if (SelectedYears.contains(2015)){
            legends.add("2015");
        }
        if (SelectedYears.contains(2016)){
            legends.add("2016");
        }
        if (SelectedYears.contains(2017)){
            legends.add("2017");
        }


        for(int i=0; i<allSectionAverages.length; i++){
            if (SelectedYears.contains(2014)){
                yAxis2014.add(allSectionAverages[i][0]);
            }
            if (SelectedYears.contains(2015)){
                yAxis2015.add(allSectionAverages[i][1]);
            }
            if (SelectedYears.contains(2016)){
                yAxis2016.add(allSectionAverages[i][2]);
            }
            if (SelectedYears.contains(2017)){
                yAxis2017.add(allSectionAverages[i][3]);
            }

        }

        barChart chart = new barChart(500,500,"title","xAxis","yAxis");
        if(SelectedYears.contains(2014)){
            chart.addElements("2014",Charts.sectionNames,yAxis2014);
        }
        if(SelectedYears.contains(2015)){
            chart.addElements("2015",Charts.sectionNames,yAxis2015);
        }
        if(SelectedYears.contains(2016)){
            chart.addElements("2016",Charts.sectionNames,yAxis2016);
        }
        if(SelectedYears.contains(2017)){
            chart.addElements("2017",Charts.sectionNames,yAxis2017);
        }

        return chart.getChart();
    }
}

