import java.util.ArrayList;

public class Instructor extends Person {

	//name inherited from Person.java
	//id inherited from Person.java
	ArrayList<Course> courses = new ArrayList<Course>();
	String dept;
	
	Instructor(String identification, String fn, String ln) {
		super(identification, fn, ln);
	}
	
	public void setDepartment(Course course) {
		String cse = course.getName();
		int space = cse.indexOf(" ");
		
		dept = cse.substring(0, space);
	}
	
	public String getDepartment() {
		return dept;
	}
	
	public void setCourses(Course cse) {
		courses.add(cse);
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public String displayInstructorData() {
		String instructor = name + " " + id + " " + courses.get(0).getName();
		return instructor;
	}
}