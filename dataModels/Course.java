package dataModels;

import java.sql.ResultSet;

public class Course {

	private static String[] course;
	private static int[] courseno;
	private static int[] sectionno;
	private static String[] Profname;
	private static String[] Day;
	private static String[] Time;

	public static String[] getCourse() {
		return course;
	}
	public static void setCourse(String[] course) {
		Course.course = course;
	}
	public static int[] getCourseno() {
		return courseno;
	}
	public static void setCourseno(int[] courseno) {
		Course.courseno = courseno;
	}
	public static String[] getProfname() {
		return Profname;
	}
	public static void setProfname(String[] profname) {
		Profname = profname;
	}
	public static String[] getDay() {
		return Day;
	}
	public static void setDay(String[] day) {
		Day = day;
	}
	public static String[] getTime() {
		return Time;
	}
	public static void setTime(String[] time) {
		Time = time;
	}
	public static int[] getSectionno() {
		return sectionno;
	}
	public static void setSectionno(int[] sectionno) {
		Course.sectionno = sectionno;
	}



}
