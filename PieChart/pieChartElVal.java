public class pieChartElVal  {
    private String element;
    private double value;

    pieChartElVal(){}

    pieChartElVal(String element, Number value){
        this.element = element;
        this.value = (double) value;
    }

    public double getValue() {
        return value;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
