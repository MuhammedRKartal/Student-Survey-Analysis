public class File {
    public String name;
    public Section[] sections;

    File(){}

    File(String name, Section[] sections){
        this.name = name;
        this.sections = sections;
    }
}
