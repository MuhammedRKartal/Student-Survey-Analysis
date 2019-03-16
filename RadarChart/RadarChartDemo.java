package radarCharts;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.SwingWrapper;

public class RadarChartDemo {
    public static RadarChart getChart() {

        // Create Chart
        RadarChart chart = new RadarChartBuilder().width(800).height(600).title("Radar Chart").build();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setHasAnnotations(true);

        // Series
        chart.setVariableLabels(new String[] {"Sales", "Marketing", "Development", "Customer Support", "Information Technology", "Administration" });
        chart.addSeries("New System", new double[] { 0.67, 0.73, 0.97, 0.95, 0.93, 0.73});

        return chart;
    }

    public static void main(String[] args) {

        RadarChart chart = getChart();
        new SwingWrapper(chart).displayChart();
    }
}
