package radarCharts;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.SwingWrapper;

import java.util.ArrayList;

public class RadarChartDemo {
    public static RadarChart createRadarChart(int width,int height,String title) {
        // Create Chart
        radarChart chart = new radarChart(width,height,title);

        // Series
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Sales");
        parameters.add("Marketing");
        parameters.add("Yo");

        ArrayList<Double> values = new ArrayList<>();
        values.add((double) 0.33);
        values.add((double) 0.46);
        values.add((double) 0.77);

        chart.setLabelNames(parameters);
        chart.addElements("University Averages", values);

        return chart.getChart();
    }

    public static void main(String[] args) {
        RadarChart chart = createRadarChart(300,300,"University Averagess");
        new SwingWrapper<RadarChart>(chart).displayChart();
    }
}
