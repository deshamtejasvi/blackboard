package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dataModels.Course;
import dataModels.JDBCExample;
import dataModels.MarksInfo;
import dataModels.Registration;
import dataModels.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProfGradeController implements Initializable {
	int index=1;
	String[] btnname;
	int[] no = new int[10];
	int[] section = new int[10];
	String[] professor = new String[10];
	String[] time = new String[10];
	String[] day = new String[10];
	int[] temp = new int[10];
	int i;
	@FXML
	private Label L1,L2,L3,L4,L5,L6;
	@FXML
	private TextField M1,M2,M3,M4,M5,M6;
	@FXML
	GridPane button_grid;
	
	@FXML
	private Label welcomemsg;
	public void setName(String name) {
		welcomemsg.setText(name);
		
	}
	
	@FXML private TableView<MarksInfo> table;
	@FXML private TableColumn<MarksInfo, Integer> studentID;
	@FXML private TableColumn<MarksInfo, String> firstName;
	@FXML private TableColumn<MarksInfo, String> lastName;
	@FXML private TableColumn<MarksInfo, Integer> assignment1;
	@FXML private TableColumn<MarksInfo, Integer> assignment2;
	@FXML private TableColumn<MarksInfo, Integer> assignment3;
	@FXML private TableColumn<MarksInfo, Integer> assignment4,midterm,finalterm,project;
	@FXML private TableColumn<MarksInfo, Double> percentage;
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				// TODO Auto-generated method stub
		studentID.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("studentID"));
		firstName.setCellValueFactory(new PropertyValueFactory<MarksInfo, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<MarksInfo, String>("lastName"));
		assignment1.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("assignment1"));
		assignment2.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("assignment2"));
		assignment3.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("assignment3"));
		assignment4.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("assignment4"));
		midterm.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("midterm"));
		finalterm.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("finalterm"));
		project.setCellValueFactory(new PropertyValueFactory<MarksInfo, Integer>("project"));
		percentage.setCellValueFactory(new PropertyValueFactory<MarksInfo, Double>("percentage"));
			}
	
	public void addbutton(){
		Course Course = new Course();
		btnname = Course.getCourse();
		no = Course.getCourseno();
		section = Course.getSectionno();
		professor = Course.getProfname();
		time = Course.getTime();
		day = Course.getDay();
		i = 0;
		while(btnname[i] != null){
			Button btn = new Button(btnname[i]+ " - Section no : " +section[i]);
			btn.setId(Integer.toString(i));
			button_grid.add(btn,0,i+1);
			System.out.println("ekada vachindi" + section[i]);
			i++;
			
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
							try {
								table.setItems(getMarksInfo(no[Integer.parseInt(btn.getId())],section[Integer.parseInt(btn.getId())]));
								table.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

									@Override
									public void handle(MouseEvent event){
										// TODO Auto-generated method stub
										if(event.getClickCount()> 1){
											if(table.getSelectionModel().getSelectedIndex() >= 0){												
												int id = table.getSelectionModel().getSelectedItem().getStudentID();
												temp[0] =  table.getSelectionModel().getSelectedItem().getAssignment1();
												temp[1] =  table.getSelectionModel().getSelectedItem().getAssignment2();
												temp[2] =  table.getSelectionModel().getSelectedItem().getAssignment3();
												temp[3] =  table.getSelectionModel().getSelectedItem().getAssignment4();
												temp[4] =  table.getSelectionModel().getSelectedItem().getMidterm();
												temp[5] =  table.getSelectionModel().getSelectedItem().getFinalterm();
												temp[6] =  table.getSelectionModel().getSelectedItem().getProject();
												try {
													marksupdate(id,no[Integer.parseInt(btn.getId())],section[Integer.parseInt(btn.getId())]);
													table.setItems(getMarksInfo(no[Integer.parseInt(btn.getId())],section[Integer.parseInt(btn.getId())]));
													table.refresh();
												} catch (IOException | NumberFormatException | SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									}
									public void marksupdate(int id,int courseno,int section) throws IOException, SQLException{
										FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MarksUpdate.fxml"));
										Parent root1 = (Parent) fxmlLoader.load();
										Stage stage = new Stage();
										stage.initModality(Modality.APPLICATION_MODAL);
										stage.initStyle(StageStyle.UNDECORATED);
										stage.setScene(new Scene(root1));
										MarksUpdateController controller = fxmlLoader.<MarksUpdateController>getController();
										controller.MarksUpdate(id,courseno,section,temp);
										stage.show();										
										
									}
								});

							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
				}
			});
		}		
		}
		
	public static ObservableList<MarksInfo> getMarksInfo(int course,int section) throws SQLException{
		ObservableList<MarksInfo> data = FXCollections.observableArrayList();
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		if(con!= null){
			java.sql.PreparedStatement statement =  con.prepareStatement("SELECT * from jg_marks,jg_student where jg_marks.course_no = '"+course+"' and jg_marks.section_no='"+section+"' and jg_student.student_id = jg_marks.student_id");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				data.addAll(new MarksInfo (result.getInt("student_id"),result.getString("stu_first_name"), result.getString("stu_last_name"),result.getInt("assignment1"),result.getInt("assignment2"),result.getInt("assignment3"),result.getInt("assignment4"),result.getInt("midterm"),result.getInt("finalexam"),result.getInt("project"),result.getDouble("percentage"))) ;
			}
		}
		else{
			System.out.println("notsuccess");
		}
		return data;

	}

	public void logout(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
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
}
