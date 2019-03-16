import java.util.ArrayList;

public class Section {
    private String text;
    public ArrayList<Subsection> subsections=new ArrayList<>();

    public String getText() {
        return text;
    }



    public void setText(String text) {
        this.text = text;
    }



    public Section() {
    }
}
