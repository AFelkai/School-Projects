import java.util.ArrayList;

public class Student extends Person {

	//name inherited from Person.java
	//id inherited from Person.java
	double credits;
	double points;
	String clevel;
	ArrayList<Course> courses = new ArrayList<Course>();
	double gpa;

	Student(String identification, String fn, String ln) {
		
		super(identification, fn, ln);
	}

	public boolean equals(int identification) {
		boolean a;
		if(identification == id)
			a = false;
		else a = true;

		return a;
	}

	public void setCredits(double creds) {
		credits = creds;
	}

	public double getCredits() {		
		return credits;
	}
	
	public String getClevel() {
		return clevel;
	}

	public void setPoints(double pts) {
		points = pts;
	}

	public double getPoints() {
		return points;
	}

	public void calculateGPA(double pts, double creds) {
		if(creds != 0)
		gpa = pts/creds;
		else gpa = 0;
	}

	public void setCourses(Course desiredCourse) {
		courses.add(desiredCourse);
	}
	
	public ArrayList<Course> getCourse() {
		return courses;
	}
	
	public void setClevel(String classLevel) {
		clevel = classLevel;
	}

	public double getGPA() {
		return gpa;
	}
}