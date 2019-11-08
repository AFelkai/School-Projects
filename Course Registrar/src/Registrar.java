import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class Registrar {

	final String studentData = "D:" + File.separator + "Documents" + File.separator + "Student Data.csv";
	final String instructorData = "D:" + File.separator + "Documents" + File.separator + "Instructor Data.csv";
	final String courseData = "D:" + File.separator + "Documents" + File.separator + "Course Data.csv";
	final int studentColumns = 7;
	final int instructorColumns = 4;
	final int courseColumns = 4;
	String printFile = "";
	ArrayList<String> studentInfo = new ArrayList<String>();
	ArrayList<String> instructorInfo = new ArrayList<String>();
	ArrayList<String> courseInfo = new ArrayList<String>();
	ArrayList<Student> students = new ArrayList<Student>();
	ArrayList<Instructor> instructors = new ArrayList<Instructor>();
	ArrayList<Course> courses = new ArrayList<Course>();
	String sortedByCoursesStudent = "";
	String sortedByCoursesInstructor = "";
	Stack<Student> comp100Students = new Stack<Student>();
	Stack<Student> comp110Students = new Stack<Student>();
	Stack<Student> comp182Students = new Stack<Student>();
	ArrayList<Instructor> comp100Instructors = new ArrayList<Instructor>();
	ArrayList<Instructor> comp110Instructors = new ArrayList<Instructor>();
	ArrayList<Instructor> comp182Instructors = new ArrayList<Instructor>();
	ArrayList<Session> sessionList = new ArrayList<Session>();
	String sessions = "";
	int comp100InstructorCount = 0;
	int comp110InstructorCount = 0;
	int comp182InstructorCount = 0;
	int comp100SessionNum = 1;
	int comp110SessionNum = 1;
	int comp182SessionNum = 1;
	int time = 480; //time from 8:00AM (480 mins) to 8:00PM (1200 mins)

	public ArrayList<String> readData(String csvLocation, int columns) {

		ArrayList<String> data = new ArrayList<String>();
		String csvFile = csvLocation;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int row = 0;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) != null) {

				String[] column = line.split(cvsSplitBy);

				if(row > 0) {

					for(int i = 0; i < columns; i++) {
						printFile += (column[i] + " ");
						data.add(column[i]);
					}
					printFile += ("\n");
				}
				row++;
			}
			printFile += "\n";
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {

			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public ArrayList<Student> makeStudentObject(ArrayList<String> a, ArrayList<Course> c) { //makes student object instances
		ArrayList<Student> b = new ArrayList<Student>();

		int id = 0;
		int fn = 1;
		int ln = 2;
		int clevel = 3;
		int course = 4;
		int credits = 5;
		int points = 6;

		for(int i = 0; i < a.size(); i+=7) {
			b.add(new Student(a.get(id+i), a.get(fn+i), a.get(ln+i)));

			for(int j = 0; j < c.size(); j++) {
				String courseName = c.get(j).getName();
				String desiredCourseName = a.get(course+i);
				if(desiredCourseName.contains(courseName)) {
					b.get(b.size()-1).setCourses(c.get(j));
				}
			}

			String cred = a.get(credits + i);
			String point = a.get(points + i);
			String clvl = a.get(clevel + i);

			b.get(b.size()-1).setCredits(Double.parseDouble(cred));
			b.get(b.size()-1).setPoints(Double.parseDouble(point));
			b.get(b.size()-1).setClevel(clvl);
			b.get(b.size()-1).calculateGPA(b.get(b.size()-1).points, b.get(b.size()-1).credits);

		}
		return b;
	}

	public ArrayList<Instructor> makeInstructorObject(ArrayList<String> a, ArrayList<Course> c) { //makes instructor object instances
		ArrayList<Instructor> b = new ArrayList<Instructor>();

		int id = 0;
		int fn = 1;
		int ln = 2;
		int course = 3;

		for(int i = 0; i < a.size(); i+=4) {

			b.add(new Instructor(a.get(id+i), a.get(fn+i), a.get(ln+i)));

			for(int j = 0; j < c.size(); j++) {
				if(c.get(j).courseName.contains(a.get(course + i))) {
					b.get(b.size()-1).setCourses(c.get(j));
				}
			}

		}
		return b;
	}

	public ArrayList<Course> makeCourseObject(ArrayList<String> a) {

		ArrayList<Course> b = new ArrayList<Course>();

		int regCode = 0;
		int name = 1;
		int credits = 2;
		int duration = 3;

		for(int i = 0; i < a.size(); i+=4) {

			b.add(new Course(a.get(name+i), a.get(regCode+i)));

			int creds = Integer.parseInt(a.get(credits + i));
			int dur = Integer.parseInt(a.get(duration + i));
			b.get(b.size()-1).setCredits(creds);
			b.get(b.size()-1).setDuration(dur);

		}
		return b;

	}

	public String sortByCoursesStudent(ArrayList<Student> students, ArrayList<Course> uniqueCourses) { //sorts and organizes students
		String sortedCourses = "";
		DecimalFormat numberFormat = new DecimalFormat("#.##");
		String courseName = "";

		for(int i = 0; i < uniqueCourses.size(); i++) {
			sortedCourses += "Students in " + uniqueCourses.get(i).getName() + ":\n\n ID|Name|Class Level|Courses|Credits|Points|GPA\n";
			for(int j = 0; j < students.size(); j++) {
				for(int k = 0; k < students.get(j).getCourse().size(); k++) {
					courseName = students.get(j).getCourse().get(k).getName();
				}
				if(courseName.contains(uniqueCourses.get(i).getName())) {
					sortedCourses += students.get(j).getID() + " " + students.get(j).getName() + " " + students.get(j).getClevel() + " " + students.get(j).getCourse().get(0).getName() + " " + students.get(j).getCredits() + " " + students.get(j).getPoints() + " " + numberFormat.format(students.get(j).getGPA()) + "\n";
					if(i == 0)
						comp100Students.push(students.get(j));
					else if(i == 1)
						comp110Students.push(students.get(j));
					else if(i == 2)
						comp182Students.push(students.get(j));
				}
			}
			sortedCourses += "\n";
		}

		return sortedCourses;
	}

	public String sortByCoursesInstructor(ArrayList<Instructor> instructors, ArrayList<Course> uniqueCourses) {
		String sortedCourses = "";

		for(int i = 0; i < uniqueCourses.size(); i++) {
			sortedCourses += "Instructors teaching " + uniqueCourses.get(i).getName() + ":\n\n ID|Name|Courses Taught\n";
			for(int j = 0; j < instructors.size(); j++) {
				if(instructors.get(j).getCourses().get(0).courseName.equals(uniqueCourses.get(i).getName())) {
					sortedCourses += instructors.get(j).getID() + " " + instructors.get(j).getName() + " " + instructors.get(j).getCourses().get(0).courseName + "\n";
					if(i == 0)
						comp100Instructors.add(instructors.get(j));
					else if(i == 1)
						comp110Instructors.add(instructors.get(j));
					else if(i == 2)
						comp182Instructors.add(instructors.get(j));
				}

			}
			sortedCourses += "\n";
		}
		return sortedCourses;
	}

	public String makeSessions() {
		Course currentCourse = courses.get(0);
		int sessionNum = comp100SessionNum;
		int studentsInSession = 0;
		DecimalFormat numberFormat = new DecimalFormat("#.##");


		if(comp100Students.isEmpty()) {
			currentCourse = courses.get(1);
			sessionNum = comp110SessionNum;
		}
		if(comp110Students.isEmpty()) {
			currentCourse = courses.get(2);
			sessionNum = comp182SessionNum;
		}

		sessions += currentCourse.getName() + " session " + sessionNum + ":\nID|Course Name|Number of Students|Start Time|End Time\n\n";

		sessionList.add(new Session(currentCourse.getRegCode(), currentCourse.getName()));

		int stopTime = time + currentCourse.getDuration();
		
		sessionList.get(sessionList.size()-1).setTimes(time, stopTime);
		
		if(time + currentCourse.getDuration() > 1200) time = 480;
		else time += currentCourse.getDuration();

		if(currentCourse == courses.get(0)) {
			sessionList.get(sessionList.size()-1).setInstructor(comp100Instructors.get(comp100InstructorCount));
			comp100InstructorCount++;
			comp100SessionNum++;
			if(comp100InstructorCount >= comp100Instructors.size()) comp100InstructorCount = 0; 
		}
		else if(currentCourse == courses.get(1)) {
			sessionList.get(sessionList.size()-1).setInstructor(comp110Instructors.get(comp110InstructorCount));
			comp110InstructorCount++;
			comp110SessionNum++;
			if(comp110InstructorCount >= comp110Instructors.size()) comp110InstructorCount = 0; 
		}
		else if(currentCourse == courses.get(2)) {
			sessionList.get(sessionList.size()-1).setInstructor(comp182Instructors.get(comp182InstructorCount));
			comp182InstructorCount++;
			comp182SessionNum++;
			if(comp182InstructorCount >= comp182Instructors.size()) comp182InstructorCount = 0; 
		}

		for(int i = 0; i < 35; i++) {
			if(comp100Students.isEmpty() && currentCourse == courses.get(0)) break;
			if(comp110Students.isEmpty() && currentCourse == courses.get(1)) break;
			if(comp182Students.isEmpty()) break;
			if(currentCourse == courses.get(0)) {
				sessionList.get(sessionList.size()-1).addStudent(comp100Students.pop());
				studentsInSession++;
			}
			else if(currentCourse == courses.get(1)) {
				sessionList.get(sessionList.size()-1).addStudent(comp110Students.pop());
				studentsInSession++;
			}
			else if(currentCourse == courses.get(2)) {
				sessionList.get(sessionList.size()-1).addStudent(comp182Students.pop());
				studentsInSession++;
			}
		}

		sessions += sessionList.get(sessionList.size()-1).getRegCode() + " " + sessionList.get(sessionList.size()-1).getName() + " " + studentsInSession + " " + sessionList.get(sessionList.size()-1).getStartTime() + " " + sessionList.get(sessionList.size()-1).getStopTime() + "\n\n";
		sessions += "Instructor:\nName|ID|Department|Courses Taught\n\n" + sessionList.get(sessionList.size()-1).getInstructor().getName() + " " + sessionList.get(sessionList.size()-1).getInstructor().getID() + " " + sessionList.get(sessionList.size()-1).getInstructor().getCourses().get(0).getName() + "\n\n";
		sessions += "Students:\nID|Name|Class Level|Desired Course|Credits|Points|GPA\n\n";

		for(int i = 0; i < studentsInSession; i++) {
			sessions += sessionList.get(sessionList.size()-1).students.get(i).getID() + " " + sessionList.get(sessionList.size()-1).students.get(i).getName() + " " + sessionList.get(sessionList.size()-1).students.get(i).getClevel() + " " + sessionList.get(sessionList.size()-1).students.get(i).getCourse().get(0).getName() + " " + sessionList.get(sessionList.size()-1).students.get(i).getCredits() + " " + sessionList.get(sessionList.size()-1).students.get(i).getPoints() + " " + numberFormat.format(sessionList.get(sessionList.size()-1).students.get(i).getGPA()) + "\n\n";
		}
		if(comp182Students.isEmpty())
			return sessions;
		else return makeSessions();
	}

	public String printCourseData(ArrayList<Course> courses) {
		String courseData = "Reg Code|Name|Credits|Duration|PreRequisite\n\n";

		for(int i = 0; i < courses.size(); i++) {
			courseData += courses.get(i).getRegCode() + " " + courses.get(i).getName() + " " + courses.get(i).getCredits() + " " + courses.get(i).getDuration();
			if(i == 2) courseData += " " + "CS110";
			courseData += "\n\n";
		}

		return courseData;
	}
	public static void main(String[] args) {

		Registrar a = new Registrar();

		a.studentInfo = a.readData(a.studentData, a.studentColumns);
		a.instructorInfo = a.readData(a.instructorData, a.instructorColumns);
		a.courseInfo = a.readData(a.courseData, a.courseColumns);
		System.out.println(a.printFile);

		a.courses = a.makeCourseObject(a.courseInfo);

		a.instructors = a.makeInstructorObject(a.instructorInfo, a.courses);

		a.students = a.makeStudentObject(a.studentInfo, a.courses);

		System.out.println(a.sortByCoursesStudent(a.students, a.courses));
		System.out.println(a.sortByCoursesInstructor(a.instructors, a.courses));

		System.out.println(a.printCourseData(a.courses));
		System.out.println(a.makeSessions());

	}
}