public class Section {
    public String name;
    public Subsection[] subsections;

    Section(){}

    Section(String name, Subsection [] subsections){
        this.name = name;
        this.subsections = subsections;
    }
}
