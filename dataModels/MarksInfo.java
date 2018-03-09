package dataModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MarksInfo {

	private Integer studentID;
	private String firstName;
	private String lastName;
	private Integer assignment1;
	private Integer assignment2;
	private Integer assignment3;
	private  Integer assignment4;
	private  Integer midterm;
	private  Integer finalterm;
	private  Integer project;
	private  Double percentage;
	
	public MarksInfo(Integer studentID, String firstName, String lastName, Integer assignment1, Integer assignment2,
			Integer assignment3, Integer assignment4, Integer midterm, Integer finalterm, Integer project,
			Double percentage) {
		super();
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.assignment1 = assignment1;
		this.assignment2 = assignment2;
		this.assignment3 = assignment3;
		this.assignment4 = assignment4;
		this.midterm = midterm;
		this.finalterm = finalterm;
		this.project = project;
		this.percentage = percentage;
	}

	public Integer getStudentID() {
		return studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAssignment1() {
		return assignment1;
	}

	public Integer getAssignment2() {
		return assignment2;
	}

	public Integer getAssignment3() {
		return assignment3;
	}

	public Integer getAssignment4() {
		return assignment4;
	}

	public Integer getMidterm() {
		return midterm;
	}

	public Integer getFinalterm() {
		return finalterm;
	}

	public Integer getProject() {
		return project;
	}

	public Double getPercentage() {
		return percentage;
	}
	
	
	}
