package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import dataModels.Course;
import dataModels.DepartmentSelection;
import dataModels.EmailValidator;
import dataModels.JDBCExample;
import dataModels.SelectedCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddEmpoyeesController {

	@FXML
	private TextField firstname,lastname,address,phoneno,emailid,department;

	String role;

	public void Student(String string) {
		// TODO Auto-generated method stub
		role = string;

	}
	public static boolean isInteger(String s) {
		try { 
			Long.parseLong(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
	public void update(ActionEvent event) throws Exception{

		System.out.println(firstname.getText());
		EmailValidator emailValidator = new EmailValidator();
		if(phoneno.getText().equals(" ") || firstname.getText().equals(" ") || lastname.getText().equals("") || address.getText().equals("") || emailid.getText().equals("") || department.getText().equals("")){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("One or more entered values are invalid!");
			stage.show();
		}
		else if (!(isInteger(phoneno.getText()))){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Please enter a valid phone number");
			stage.show();
		}
		else if (!(phoneno.getText().length() == 10)){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Please enter a valid phone number");
			stage.show();
		}
		else if(!(emailValidator.validateEmail(emailid.getText()))) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Please enter a valid email ID");
			stage.show();
		}


		else
		{
			JDBCExample n = new JDBCExample();
			Connection con =(n.connection());
			int primaryKey;
			System.out.println(firstname.getText());
			System.out.println(lastname.getText());
			System.out.println(emailid.getText());
			System.out.println(phoneno.getText());
			System.out.println(department.getText());
			System.out.println(address.getText());
			String username,password;
			if(con!= null){
				java.sql.PreparedStatement statement = null;
				//statement =  con.prepareStatement("INSERT INTO jg_student(stu_first_name,stu_last_name,address,phone_no,email_id,department) Values ('"+firstname.getText()+"','"+lastname.getText()+"','"+address.getText()+"','"+Integer.parseInt(phoneno.getText())+"','"+emailid.getText()+"','"+department.getText()+"')",statement.RETURN_GENERATED_KEYS);
				if(role.equals("student")){
					System.out.println("1");
					statement =  con.prepareStatement("INSERT INTO project.jg_student(stu_first_name,stu_last_name,address,phone_no,email_id,department) Values ('"+firstname.getText()+"','"+lastname.getText()+"','"+address.getText()+"','"+phoneno.getText()+"','"+emailid.getText()+"','"+department.getText()+"')",statement.RETURN_GENERATED_KEYS);
				}
				else if (role.equals("professor")) {
					System.out.println("2");
					statement =  con.prepareStatement("INSERT INTO jg_professor(prof_first_name,prof_last_name,address,phone_no,email_id,department) Values ('"+firstname.getText()+"','"+lastname.getText()+"','"+address.getText()+"','"+phoneno.getText()+"','"+emailid.getText()+"','"+department.getText()+"')",statement.RETURN_GENERATED_KEYS);
				}
				else if (role.equals("admin")) {
					statement =  con.prepareStatement("INSERT INTO jg_admin(admin_first_name,admin_last_name,address,phone_no,email_id,department) Values ('"+firstname.getText()+"','"+lastname.getText()+"','"+address.getText()+"','"+phoneno.getText()+"','"+emailid.getText()+"','"+department.getText()+"')",statement.RETURN_GENERATED_KEYS);
				}
				try {  
					statement.executeUpdate();  
					ResultSet rs = statement.getGeneratedKeys();  
					rs.next();  
					primaryKey  = rs.getInt(1);  
				} finally {  
					statement.close();  
				}
				System.out.println(primaryKey);
				username = firstname.getText().substring(0,2) + lastname.getText().substring(0,2) + Integer.toString(primaryKey);
				password = lastname.getText() + "@" + Integer.toString(primaryKey);
				statement =  con.prepareStatement("INSERT INTO jg_login Values ('"+primaryKey+"','"+username+"','"+password+"','"+role+"')");
				statement.executeUpdate();  
				((Node)(event.getSource())).getScene().getWindow().hide();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(new Scene(root1));
				SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
				controller.setName("You have successfully added a user.");
				stage.show();
			}
			else{
			}
		}}


public void cancel(ActionEvent event) throws IOException{
	(((Node) event.getSource())).getScene().getWindow().hide();
}
}
