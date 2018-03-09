package dataModels;

public class Registration {

	private static String[] Regstatus;
	private static int[] Sectionno;

	public static String[] getRegstatus() {
		return Regstatus;
	}

	public static void setRegstatus(String[] regstatus) {
		Regstatus = regstatus;
	}

	public static int[] getSectionno() {
		return Sectionno;
	}

	public static void setSectionno(int[] sectionno) {
		Sectionno = sectionno;
	}
}
