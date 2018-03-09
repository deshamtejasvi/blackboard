package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataModels.JDBCExample;
import dataModels.MarksInfo;
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

public class MarksUpdateController {
	@FXML
	private TextField M1,M2,M3,M4,M5,M6,M7;

	int id;
	ArrayList<Integer> marks = new ArrayList<Integer>();
	int courseno;
	int section;
	double m8 = 0;
	int count = 0;
	public void MarksUpdate(int id, int courseno, int section,int[] temp) {
		this.id = id;
		this.courseno = courseno;
		this.section = section;
		M1.setText(Integer.toString(temp[0]));
		M2.setText(Integer.toString(temp[1]));
		M3.setText(Integer.toString(temp[2]));
		M4.setText(Integer.toString(temp[3]));
		M5.setText(Integer.toString(temp[4]));
		M6.setText(Integer.toString(temp[5]));
		M7.setText(Integer.toString(temp[6]));
		
		
	}
	public void update(ActionEvent event) throws IOException, SQLException{
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		if(con!= null){
			marks.add(Integer.parseInt(M1.getText()));
			marks.add(Integer.parseInt(M2.getText()));
			marks.add(Integer.parseInt(M3.getText()));
			marks.add(Integer.parseInt(M4.getText()));
			marks.add(Integer.parseInt(M5.getText()));
			marks.add(Integer.parseInt(M6.getText()));
			marks.add(Integer.parseInt(M7.getText()));
			for(int m:marks){
				if(m != 0){
					m8 = m8 + m;
					count++;
				}
			}
			m8 =m8/count;
			java.sql.PreparedStatement statement =  con.prepareStatement("UPDATE jg_marks SET assignment1 = "+Integer.parseInt(M1.getText())+",assignment2 = "+Integer.parseInt(M2.getText())+",assignment3 = "+Integer.parseInt(M3.getText())+",assignment4 = "+Integer.parseInt(M4.getText())+",midterm = "+Integer.parseInt(M5.getText())+",finalexam = "+Integer.parseInt(M6.getText())+",project = "+Integer.parseInt(M7.getText())+",percentage = "+m8+" where jg_marks.course_no = "+courseno+" and jg_marks.section_no="+section+" and jg_marks.student_id = "+id+"");
			int result = statement.executeUpdate();
			((Node)(event.getSource())).getScene().getWindow().hide();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Marks have been updated, Click on course to view updated information");
			stage.show();
		}
		else{
			System.out.println("notsuccess");
		}
	}
	public void cancel(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

}
