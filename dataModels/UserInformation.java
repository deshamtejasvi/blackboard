package dataModels;

public class UserInformation {
	private static String UserName;
	private static String role;
	private static int userid;
	private static String userfirstName;
	private static String userLastName;
	
	public static String getUserName() {
		return UserName;
	}
	public static void setUserName(String userName) {
		UserName = userName;
	}
	public static String getRole() {
		return role;
	}
	public static void setRole(String role) {
		UserInformation.role = role;
	}
	public static int getUserid() {
		return userid;
	}
	public static void setUserid(int userid) {
		UserInformation.userid = userid;
	}
	public static String getUserLastName() {
		return userLastName;
	}
	public static void setUserLastName(String userLastName) {
		UserInformation.userLastName = userLastName;
	}
	public static String getUserfirstName() {
		return userfirstName;
	}
	public static void setUserfirstName(String userfirstName) {
		UserInformation.userfirstName = userfirstName;
	}
	
}
