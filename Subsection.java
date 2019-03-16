public class Subsection {
    public String text;
    public double average;
    public double stddev;
    public int na;
    public int no;
    public int one;
    public int two;
    public int three;
    public int four;
    public int five;
    public double universityAverage;

    Subsection(){}

    Subsection(String arr[]){
        arr = new String[10];
        text = arr[0];
        average = Double.parseDouble(arr[1]);
        stddev = Double.parseDouble(arr[2]);
        na = Integer.parseInt(arr[3]);
        no = Integer.parseInt(arr[4]);
        one = Integer.parseInt(arr[5]);
        two = Integer.parseInt(arr[6]);
        three = Integer.parseInt(arr[7]);
        four = Integer.parseInt(arr[8]);
        five = Integer.parseInt(arr[9]);
        universityAverage = Double.parseDouble(arr[10]);
    }

}
