package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dataModels.Course;
import dataModels.DepartmentSelection;
import dataModels.JDBCExample;
import dataModels.Registration;
import dataModels.SelectedCourse;
import dataModels.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;


public class CourseController implements Initializable{

	ObservableList<SelectedCourse> Selected = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> Selected1 = FXCollections.observableArrayList();
	int countterm =0,countyear =0 ,countdep =0;
	DepartmentSelection ds = new DepartmentSelection();
	@FXML
	public ComboBox<String> combobox;
	ObservableList<String> list = FXCollections.observableArrayList("Spring","Fall","Summer");

	@FXML
	public ComboBox<Integer> combobox1;
	ObservableList<Integer> list1 = FXCollections.observableArrayList(2017,2018,2019);

	@FXML
	public ComboBox<String> combobox2;
	ObservableList<String> list2 = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			department();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		combobox.setItems(list);
		combobox1.setItems(list1);
		combobox2.setItems(list2);
	}
	public void department() throws SQLException{
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());

		if(con!= null){
			java.sql.PreparedStatement statement =  con.prepareStatement("SELECT DISTINCT department FROM jg_course");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{	
				list2.add(result.getString("department"));
			}
		}
		else{
		}
	}

	public void term(ActionEvent event){

		if(!(combobox.getValue().isEmpty())){
			ds.setTerm(combobox.getValue());
			countterm = 1;
		}
	}

	public void year(ActionEvent event){
		if(!(combobox1.getValue() == 0)){
			countyear =1;
			ds.setYear(combobox1.getValue());
		}
	}

	public void department(ActionEvent event){
		if(!(combobox2.getValue().isEmpty())){
			ds.setDepartment(combobox2.getValue());
			countdep =1;
		}
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

	public void ok(ActionEvent event) throws IOException, SQLException{
		if(countyear == 1 && countterm == 1 && countdep ==1){
			DepartmentSelection depSel = new DepartmentSelection();
			String term = depSel.getTerm();
			int year = depSel.getYear();
			String department = depSel.getDepartment();
			int userid = UserInformation.getUserid();
			JDBCExample n = new JDBCExample();
			Connection con =(n.connection());
			if(con!= null){
				java.sql.PreparedStatement statement =  con.prepareStatement("SELECT * FROM jg_course, jg_registration,jg_professor WHERE (jg_course.professor_id = jg_professor.professor_id AND jg_registration.course_no = jg_course.course_no AND jg_course.section_no = jg_registration.section_no AND jg_registration.student_id = '"+userid+"' and jg_registration.year = '"+year+"'and jg_course.department = '"+department+"' and jg_registration.term = '"+term+"')");
				ResultSet result = statement.executeQuery();
				while(result.next())
				{	
					Selected.addAll(new SelectedCourse(result.getInt("section_no") ,result.getInt("course_no"),result.getString("course_name"),result.getString("prof_first_name") +" " +result.getString("prof_last_name"),result.getString("day"),result.getString("time")));
				}
				java.sql.PreparedStatement statement2 =  con.prepareStatement("SELECT * FROM jg_course, jg_registration,jg_professor WHERE (jg_course.professor_id = jg_professor.professor_id AND jg_registration.course_no = jg_course.course_no AND jg_course.section_no = jg_registration.section_no AND jg_registration.student_id = '"+userid+"'and not(jg_registration.year = '"+year+"' and jg_registration.term = '"+term+"'))");
				ResultSet result2 = statement2.executeQuery();
				while(result2.next())
				{	
					Selected1.addAll(new SelectedCourse(result2.getInt("section_no") ,result2.getInt("course_no"),result2.getString("course_name"),result2.getString("prof_first_name") +" " +result2.getString("prof_last_name"),result2.getString("day"),result2.getString("time")));
				}
			}
			else{
			}
			((Node)(event.getSource())).getScene().getWindow().hide();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CourseSelection.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			CourseSelectionController controller = fxmlLoader.<CourseSelectionController>getController();
			controller.setRegistered(Selected,Selected1);
			stage.show();
		}
		else {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Please select values in all fileds");
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
