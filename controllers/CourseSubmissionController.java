package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.api.jdbc.Statement;

import dataModels.DepartmentSelection;
import dataModels.JDBCExample;
import dataModels.SelectedCourse;
import dataModels.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CourseSubmissionController {
	
	ArrayList<SelectedCourse> finalList = new ArrayList<SelectedCourse>();
	ArrayList<SelectedCourse> selectedArray = new ArrayList<SelectedCourse>();
	ObservableList<SelectedCourse> selectedArray2 = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> Selected =FXCollections.observableArrayList();

	public void lastsubmit(ActionEvent event) throws IOException, SQLException{
		DepartmentSelection depSel = new DepartmentSelection();
		String[] finalarray = null;
		String term = depSel.getTerm();
		int year = depSel.getYear();
		String department = depSel.getDepartment();
		int userid = UserInformation.getUserid();
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		if(con!= null){
			java.sql.PreparedStatement statement2 =  con.prepareStatement("select * FROM jg_registration,jg_course,jg_professor WHERE (jg_registration.student_id = '"+userid+"' and jg_registration.year = '"+year+"' and jg_registration.term = '"+term+"' and jg_registration.section_no = jg_course.section_no AND jg_registration.course_no = jg_course.course_no and jg_course.professor_id=jg_professor.professor_id )");
			ResultSet result2 = statement2.executeQuery();
			int i=0;
			while(result2.next()){
				selectedArray.add(new SelectedCourse(result2.getInt("section_no"),result2.getInt("course_no"),result2.getString("course_name"),result2.getString("prof_first_name") +" " +result2.getString("prof_last_name"),result2.getString("day"),result2.getString("time")));
			}
			
			String[] t;
			for(SelectedCourse temp : selectedArray){
				if(!(finalList.contains(temp))){
					int courseno = temp.getCourseno();
					int sectionno = temp.getSectionno();
					java.sql.PreparedStatement statement =  con.prepareStatement("DELETE FROM jg_registration WHERE (jg_registration.student_id = '"+userid+"' and jg_registration.course_no = '"+courseno+"' and jg_registration.section_no = '"+sectionno+"')");
					int result1 = statement.executeUpdate();
					java.sql.PreparedStatement statement4 =  con.prepareStatement("DELETE FROM jg_marks WHERE (jg_marks.student_id = '"+userid+"' and jg_marks.course_no = '"+courseno+"' and jg_marks.section_no = '"+sectionno+"')");
					int result4 = statement4.executeUpdate();
				}
			}
			for(SelectedCourse temp : finalList){
				if(selectedArray.isEmpty() || (!(selectedArray.contains(temp)))){
					int courseno = temp.getCourseno();
					int sectionno = temp.getSectionno();
					java.sql.PreparedStatement statement1 =  con.prepareStatement("INSERT INTO jg_registration Values ('"+courseno+"','"+sectionno+"','"+userid+"','"+term+"','"+year+"','Registerd')");
					int result1 = statement1.executeUpdate();
					java.sql.PreparedStatement statement4 =  con.prepareStatement("INSERT INTO jg_marks(course_no,section_no,student_id) Values ('"+courseno+"','"+sectionno+"','"+userid+"')");
					int result4 = statement4.executeUpdate();
				}
			}
			java.sql.PreparedStatement statement3 =  con.prepareStatement("select * FROM jg_registration,jg_course,jg_professor WHERE (jg_registration.student_id = '"+userid+"' and jg_registration.section_no = jg_course.section_no AND jg_registration.course_no = jg_course.course_no and jg_course.professor_id=jg_professor.professor_id and Not(jg_registration.year = '"+year+"' and jg_registration.term = '"+term+"'))");
			ResultSet result3 = statement3.executeQuery();
			while(result3.next()){
				selectedArray2.add(new SelectedCourse(result3.getInt("section_no"),result3.getInt("course_no"),result3.getString("course_name"),result3.getString("prof_first_name") +" " +result3.getString("prof_last_name"),result3.getString("day"),result3.getString("time")));
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
		controller.setRegistered(Selected,selectedArray2);
		stage.show();
	}

	public void setfinalrecord(ArrayList<SelectedCourse> finalList) {
		// TODO Auto-generated method stub
		Selected.addAll(finalList);
		this.finalList.addAll(finalList);
	}
	
	public void lastcancel(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Course.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
