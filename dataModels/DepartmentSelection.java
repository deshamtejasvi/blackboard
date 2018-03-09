package dataModels;

public class DepartmentSelection {
	public static String Term;
	public static int Year;
	public static String Department;

	public static String getDepartment() {
		return Department;
	}

	public static void setDepartment(String department) {
		Department = department;
	}

	public static String getTerm() {
		return Term;
	}

	public static void setTerm(String term) {
		Term = term;
	}

	public static Integer getYear() {
		return Year;
	}

	public void setYear(Integer year) {
		Year = year;
	}

}
