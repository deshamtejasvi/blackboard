package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginitemsController {

	@FXML
	private Label welcomemsg;
	public void setName(String name) {
		welcomemsg.setText(name);
		String course[] = new String[10];
		int courseno[] = new int[10];

	}
	public void gohome(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loginitems.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/views/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void gocourse(ActionEvent event) throws IOException{
		(((Node) event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Course.fxml"));		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/views/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void gomoodle(ActionEvent event) throws IOException, SQLException{
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
			java.sql.PreparedStatement statement =  con.prepareStatement("SELECT * FROM jg_course, jg_registration,jg_professor WHERE (jg_course.section_no = jg_registration.section_no) AND (jg_course.course_no = jg_registration.course_no) AND (jg_registration.student_id ='"+userid+"') AND (jg_course.professor_id = jg_professor.professor_id)");
			ResultSet result = statement.executeQuery();
			int i=0;
			while(result.next())
			{	
				course[i] = result.getString("course_name");
				courseno[i] = result.getInt("course_no");
				Profname[i] = result.getString("prof_first_name") +" " +result.getString("prof_last_name");
				Regstatus[i] = result.getString("status");
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
			Course.setDay(Day);
			Course.setTime(Time);
		}
		else{
		}
		if(courseno[0] == 0){
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("No Courses are registered yet! Please register for a course to view this page");
			stage.show();
			
			/*Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Course.fxml"));		
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/views/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
			
			
		}else{	
		(((Node) event.getSource())).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Grade.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root1));
		GradeController controller = fxmlLoader.<GradeController>getController();
		controller.addbutton();
		stage.show();
		}
		
	}
	
	public void logout(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
