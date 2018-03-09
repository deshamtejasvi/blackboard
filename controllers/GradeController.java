package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataModels.Course;
import dataModels.JDBCExample;
import dataModels.Registration;
import dataModels.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GradeController {
	int index=1;
	String[] btnname;
	int[] no = new int[10];
	int[] section = new int[10];
	String[] professor = new String[10];
	String[] time = new String[10];
	String[] day = new String[10];
	int i;
	@FXML
	private Label L1;
	@FXML
	private Label L2;
	@FXML
	private Label L3;
	@FXML
	private Label L4;
	@FXML
	private Label L5;
	@FXML
	private Label L6;
	@FXML
	GridPane button_grid;
	@FXML
	private Label LL1;
	@FXML
	private Label LL2;
	@FXML
	private Label LL3;
	@FXML
	private Label LL4;
	@FXML
	private Label LL5;
	@FXML
	private Label LL6;
	@FXML
	private Label LL7;
	@FXML
	private Label LL8;
	public void addbutton(){
		Course Course = new Course();
		btnname = Course.getCourse();
		no = Course.getCourseno();
		section = Registration.getSectionno();
		professor = Course.getProfname();
		time = Course.getTime();
		day = Course.getDay();
		i = 0;
		while(btnname[i] != null){
			Button btn = new Button(btnname[i]);
			btn.setId(Integer.toString(i));
			button_grid.add(btn,0,i+1);			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int l1 =no[Integer.parseInt(btn.getId())];
					L1.setText(Integer.toString(l1));
					L2.setText(btnname[Integer.parseInt(btn.getId())]);
					String l3 = professor[Integer.parseInt(btn.getId())];
					L3.setText(l3);
					int l4 = section[Integer.parseInt(btn.getId())];
					L4.setText(Integer.toString(l4));
					L5.setText(day[Integer.parseInt(btn.getId())]);
					L6.setText(time[Integer.parseInt(btn.getId())]);							
							JDBCExample n = new JDBCExample();
							Connection con =(n.connection());
							if(con!= null){
								java.sql.PreparedStatement statement2;
								try {
									statement2 = con.prepareStatement("select * FROM jg_marks WHERE (jg_marks.student_id = '"+UserInformation.getUserid()+"' and jg_marks.course_no = '"+Integer.toString(l1)+"' and jg_marks.section_no = '"+Integer.toString(l4)+"')");
									ResultSet result2 = statement2.executeQuery();
								while(result2.next()){
									LL1.setText(Integer.toString(result2.getInt("assignment1")));
									LL2.setText(Integer.toString(result2.getInt("assignment2")));
									LL3.setText(Integer.toString(result2.getInt("assignment3")));
									LL4.setText(Integer.toString(result2.getInt("assignment4")));
									LL5.setText(Integer.toString(result2.getInt("midterm")));
									LL6.setText(Integer.toString(result2.getInt("finalexam")));
									LL7.setText(Integer.toString(result2.getInt("project")));
									LL8.setText(Double.toString(result2.getDouble("percentage")));
								}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							else{
							}
				}
			});
					i++;
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
	
	public void logout(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
