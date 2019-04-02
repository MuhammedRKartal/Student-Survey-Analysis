/**
 * @Author Muhammed Rahmetullah Kartal
 * @Date 30.03.2019
 * @Description: Course object for FXML GUI Course Table.
 */

package Project.GUIDemo;

import javafx.beans.property.SimpleStringProperty;

//This object is for JavaFX table on the first screen and to pace file names.
public class Course {
    //types must be simplestringproperty
    //or simglestringint kinda
    private SimpleStringProperty courseCode;
    private SimpleStringProperty section;
    private SimpleStringProperty year;
    private SimpleStringProperty term;

    public Course(String courseCode, String section, String year, String term){
        this.courseCode= new SimpleStringProperty(courseCode);
        this.section=new SimpleStringProperty(section);
        this.year=new SimpleStringProperty(year);
        this.term=new SimpleStringProperty(term);
    }

    public String getCourseCode() {
        return courseCode.get();
    }

    public String getSection() {
        return section.get();
    }

    public String getTerm() {
        return term.get();
    }

    public String getYear() {
        return year.get();
    }

    public void setYear(String year) { this.year = new SimpleStringProperty(year); }

    public void setCourseCode(String courseCode) { this.courseCode = new SimpleStringProperty(courseCode); }

    public void setTerm(String term) { this.term = new SimpleStringProperty(term); }

    public void setSection(String section) {
        this.section = new SimpleStringProperty(section);
    }
}
