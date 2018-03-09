package dataModels;

import java.sql.ResultSet;

public class SelectedCourse {

	private String course;
	private int courseno;
	private int sectionno;
	private String Profname;
	private String Day;
	private String Time;
	public SelectedCourse( int sectionno,int courseno,String course, String profname, String day, String time) {
		super();
		this.course = course;
		this.courseno = courseno;
		this.sectionno = sectionno;
		Profname = profname;
		Day = day;
		Time = time;
	}
	public String getCourse() {
		return course;
	}
	public int getCourseno() {
		return courseno;
	}
	public int getSectionno() {
		return sectionno;
	}
	public String getProfname() {
		return Profname;
	}
	public String getDay() {
		return Day;
	}
	public String getTime() {
		return Time;
	}

	


}
