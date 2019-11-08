import java.util.ArrayList;

public class Session extends Course {

	int startTime;
	int stopTime;
	Course course;
	Instructor instructor;
	int studentsEnrolled;
	ArrayList<Student> registeredStudentsInCourseSession = new ArrayList<Student>();
	final int MAX_STUDENTS = 35;
	
	Session(String courseName, String regCode) {
		super(courseName, regCode);
	}
	
	public void setInstructor(Instructor instr) {
		instructor = instr;
	}
	
	public Instructor getInstructor() {
		return instructor;
	}
	
	public void setTimes(int start, int stop) {
		startTime = start;
		stopTime = stop;
	} 
	
	public void setCourse(Course cse) {
		course = cse;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getStopTime() {
		return stopTime;
	}
	
	public boolean inSession(int id) {
		boolean tf = false;
		
		for(int i = 0; i < students.size(); i++) {
			int idNum = students.get(i).getID();
		
			if(idNum == id) tf = true;
		}
		return tf;
	}
	
	public void removeStudent(Student s) {
		if(registeredStudentsInCourseSession.indexOf(s) != -1)
			registeredStudentsInCourseSession.remove(s);
		else System.out.println("Student not found");
	}
	
	public void addStudent(Student s) {
			if(students.size() < MAX_STUDENTS)
			students.add(s);
			else System.out.println("Class full, make new session");
	}
	
	public String displayStudents() {
		String students = "";
		for(int i = 0; i < studentsEnrolled; i++)
			students = registeredStudentsInCourseSession.get(i).getID() + " " + registeredStudentsInCourseSession.get(i).getName() + " " + registeredStudentsInCourseSession.get(i).getClevel() + " " + registeredStudentsInCourseSession.get(i).getCourse().get(0).getName() + " " + registeredStudentsInCourseSession.get(i).getCredits() + " " + registeredStudentsInCourseSession.get(i).getPoints() + " " + registeredStudentsInCourseSession.get(i).getGPA() + "\n";
		
		return students;
	}
	
	public String displaySession() {
		String session = regCode + " " + courseName + " " + instructor.getName() + " " + startTime + " " + stopTime + " " + studentsEnrolled;
		return session;
	}
}
