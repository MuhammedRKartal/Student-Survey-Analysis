/**
 * @Author Muhammed Rahmetullah Kartal
 * @Date 31.03.2019-31.03.2019
 * @Description: Comment Table, Course Table on GUI.
 */


package Project.GUIDemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Project.mainObjects.File_f;

import java.io.File;
import java.util.ArrayList;

public class GUITableMethods {
    // Adding chosen files to the table on first screen.
    public static ObservableList<Course> getCourse(){
        //Must be ObservableList type
        ObservableList<Course> courses = FXCollections.observableArrayList();

        ArrayList<File> tempFileArray= new ArrayList<>();
        Project.usableArray = new ArrayList<>();

        for(int i = 0; i<Project.filesArray.size(); i++){
            tempFileArray.add(Project.filesArray.get(i));
        }

        try{
            int counter = 0;
            for (int i = 0; i < tempFileArray.size(); i++) {
                String name = tempFileArray.get(i).getName();
                String temp = name.split("x")[0];
                try{
                    String[] nameParts = temp.split("_");
                    String courseCode = nameParts[0];
                    String tableSection = nameParts[1];
                    String year = nameParts[2];
                    String term = nameParts[3];
                    Course oneCourse = new Course(courseCode, tableSection, year, term);
                    oneCourse.setFileJustFile(tempFileArray.get(i));
                    courses.add(oneCourse);

                    counter++;
                    Project.usableArray.add(tempFileArray.get(i));

                }
                catch (Exception exception){
                    System.out.println("Please enter file name on CourseCode_Section_Year_Term.xlsx format");
                    System.out.println(tempFileArray.get(counter).getName() + " is the problem.");
                    counter++;
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception);
        }

        return courses;
    }


    public static ObservableList<Comments> getComments(ArrayList<File_f> usable){
        //Must be ObservableList type
        ObservableList<Comments> comments = FXCollections.observableArrayList();

        try{
            for(int i =0; i<usable.size(); i++){
                int commentsSize = usable.get(i).comments.size();
                ArrayList<String> commentsS= usable.get(i).comments;

                Comments fileName = new Comments(usable.get(i).getName(),"");
                comments.add(fileName);
                for(int j=0; j<commentsSize; j++){
                    String comment = commentsS.get(j);
                    Comments oneComment = new Comments(comment,"");
                    comments.add(oneComment);
                }
                comments.add(null);
            }
        }
        catch (Exception exception){
            System.out.println(exception);
        }

        return comments;
    }


}
