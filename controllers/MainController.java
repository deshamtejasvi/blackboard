package controllers;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.PreparedStatement;

import dataModels.Course;
import dataModels.JDBCExample;
import dataModels.Registration;
import dataModels.UserInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

	public static String UserName;
	@FXML
	private Label lblstatus;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;
	private int loginid;
	private String welcomename;
	private String role;
	public void username() throws Exception{
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		java.sql.PreparedStatement statement;
		if(con!= null){
			if(role.equals("student")){
				statement =  con.prepareStatement("SELECT  stu_first_name, stu_last_name from jg_student where student_id='"+loginid+"'");
			}
			else if(role.equals("professor")){
				statement =  con.prepareStatement("SELECT  prof_first_name, prof_last_name from jg_professor where professor_id='"+loginid+"'");
			}
			else {
				statement =  con.prepareStatement("SELECT  admin_first_name, admin_last_name from jg_admin where admin_id='"+loginid+"'");
			}
			ResultSet result = statement.executeQuery();
			while(result.next()){
				UserInformation.setUserfirstName(result.getString(1));
				UserInformation.setUserLastName(result.getString(2));
				welcomename = result.getString(1) + " " +result.getString(2);
			}
		}
		else{
		}
	}
	public void login(ActionEvent event) throws Exception{
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		UserName= txtUserName.getText();
		String password  = txtPassword.getText();
		if(con!= null){
			java.sql.PreparedStatement statement =  con.prepareStatement("SELECT loginid,role,count(UserName) as count from jg_login where UserName = '"+UserName+"' and password='"+password+"'");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				loginid = result.getInt("loginid");
				if (result.getInt("count") != 0){
					role = result.getString("role");
					lblstatus.setText("login success");
					UserInformation.setUserName(UserName);
					UserInformation.setUserid(result.getInt("loginid"));
					UserInformation.setRole(result.getString("role"));
					username();
					course();
					(((Node) event.getSource())).getScene().getWindow().hide();
					lblstatus.setText("Login Successful");
					if (result.getString("role").equals("student")){
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginitems.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(new Scene(root1));
						LoginitemsController controller = fxmlLoader.<LoginitemsController>getController();
						controller.setName("Welcome " +welcomename);
						stage.show();
					}
					else if(result.getString("role").equals("professor")){	
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ProfessorGrade.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(new Scene(root1));
						ProfGradeController controller = fxmlLoader.<ProfGradeController>getController();
						controller.setName("Welcome " +welcomename);
						controller.addbutton();
						stage.show();
					}
					else if(result.getString("role").equals("admin")){	
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminLogin.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(new Scene(root1));
						AdminController controller = fxmlLoader.<AdminController>getController();
						controller.setName("Welcome " +welcomename);
						stage.show();
					}
				}
				else{
					lblstatus.setText("Username or Password is Invalid!");
				}

			}

		}
		else{
		}
	}
	private void course() throws SQLException {
		Course Course = new Course();
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		String course[] = new String[10];
		int courseno[] = new int[10];
		String Profname[] = new String[10];
		String Regstatus[] = new String[10];
        int Sectionno[] = new int[10];
        String Day[] = new String[10];
        String Time[] = new String[10];
        
		if(con!= null){
			int userid = UserInformation.getUserid();
			String userName = UserInformation.getUserName();
			ResultSet result;
			if(role.equals("student")){
			java.sql.PreparedStatement statement =  con.prepareStatement("SELECT * FROM jg_course, jg_registration,jg_professor WHERE (jg_course.section_no = jg_registration.section_no) AND (jg_course.course_no = jg_registration.course_no) AND (jg_registration.student_id ='"+userid+"') AND (jg_course.professor_id = jg_professor.professor_id)");
			result = statement.executeQuery();
			}
			else{
				java.sql.PreparedStatement statement =  con.prepareStatement("SELECT * FROM jg_course,jg_professor where (jg_course.professor_id ='"+userid+"') AND (jg_course.professor_id = jg_professor.professor_id)");
				result = statement.executeQuery();
			}
			int i=0;
			while(result.next())
			{	
				course[i] = result.getString("course_name");
				courseno[i] = result.getInt("course_no");
				Profname[i] = result.getString("prof_first_name") +" " +result.getString("prof_last_name");
				Sectionno[i] = result.getInt("section_no");
				Day[i] = result.getString("day");
				Time[i] = result.getString("time");
						
				i++;
			}
			
			Course.setCourse(course);
			Course.setCourseno(courseno);
			Course.setProfname(Profname);
			Registration.setRegstatus(Regstatus);
			Registration.setSectionno(Sectionno);
			Course.setSectionno(Sectionno);
			Course.setDay(Day);
			Course.setTime(Time);
		}
		else{
		}
	}
}


