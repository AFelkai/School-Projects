import java.util.ArrayList;
public class Course {

	String regCode;
	String courseName;
	int credits;
	int duration;
	String preReqs;
	Session sessions;
	ArrayList<Student> students = new ArrayList<Student>();

	Course(String name, String code) {

		regCode = code;
		courseName = name;

	}

	public void setName(String name) {
		courseName = name;
	}

	public String getName() {
		return courseName;
	}

	public void setRegCode(String code) {
		regCode = code;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setCredits(int creds) {
		credits = creds;
	}

	public int getCredits() {
		return credits;
	}

	public void setDuration(int dur) {
		duration = dur;
	}

	public int getDuration() {
		return duration;
	}

	public void setPreReq(String preReq) {
		preReqs = preReq;
	}

	public String getPreReq() {
		return preReqs;
	}

	public Session getSession() {
		return sessions;
	}

	public void addStudent(Student s) {
		if(students.size() < 35)
			students.add(s);
		else System.out.println("Class Full");
	}

	public String displayCourse() {
		String course = courseName + " " + regCode + " " + credits + " " + duration + " " + preReqs + " " + sessions.getName();
		return course;
	}
	
	public void createSession() {
		//create a new session for this course
	}

}
