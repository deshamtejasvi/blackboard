package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

import dataModels.Course;
import dataModels.DepartmentSelection;
import dataModels.JDBCExample;
import dataModels.MarksInfo;
import dataModels.SelectedCourse;
import dataModels.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CourseSelectionController implements Initializable {

	@FXML
	private TextField SelectCourseNo;
	int count = 0 ;
	int counttemp =0;
	@FXML
	private TextField SelectCourseName;
	@FXML
	private ListView<String> list1;
	@FXML
	private ListView<String> list2;
	ArrayList<SelectedCourse> finalList = new ArrayList<SelectedCourse>();
	ObservableList<SelectedCourse> listcourse = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> Addedcourse = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> listRemove = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> submitcourse = FXCollections.observableArrayList();
	ObservableList<SelectedCourse> search = FXCollections.observableArrayList();


	@FXML private TableView<SelectedCourse> table1,table2;
	@FXML private TableColumn<SelectedCourse, Integer> T1sectionno;
	@FXML private TableColumn<SelectedCourse, Integer> T2sectionno;
	@FXML private TableColumn<SelectedCourse, Integer> T1courseno,T2courseno;
	@FXML private TableColumn<SelectedCourse, String> T1coursename,T2coursename;
	@FXML private TableColumn<SelectedCourse, String> T1profname,T2profname;
	@FXML private TableColumn<SelectedCourse, String> T1day,T2day;
	@FXML private TableColumn<SelectedCourse, String> T1time,T2time;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		T1sectionno.setCellValueFactory(new PropertyValueFactory<SelectedCourse, Integer>("sectionno"));
		T2sectionno.setCellValueFactory(new PropertyValueFactory<SelectedCourse, Integer>("sectionno"));
		T1courseno.setCellValueFactory(new PropertyValueFactory<SelectedCourse, Integer>("courseno"));
		T2courseno.setCellValueFactory(new PropertyValueFactory<SelectedCourse, Integer>("courseno"));
		T1coursename.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("course"));
		T2coursename.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("course"));
		T1profname.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Profname"));
		T2profname.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Profname"));
		T1day.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Day"));
		T2day.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Day"));
		T1time.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Time"));
		T2time.setCellValueFactory(new PropertyValueFactory<SelectedCourse, String>("Time"));
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

	public void okSubmission(ActionEvent event) throws IOException{
		for(SelectedCourse finallist : table2.getItems()){
			finalList.add(finallist);
		}
		if(finalList.size() >=2 && finalList.size() <=4 ){
			((Node)(event.getSource())).getScene().getWindow().hide();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CourseSubmission.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			CourseSubmissionController controller = fxmlLoader.<CourseSubmissionController>getController();
			controller.setfinalrecord(finalList);
			stage.show();
		}
		else{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("Please select minimum of 2 courses and maximum of 4 courses to proceed further");
			stage.show();
		}
	}

	public void cancel(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Course.fxml"));		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/views/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void Add(ActionEvent event) throws IOException, SQLException{
		Addedcourse.clear();
		if(table1.getSelectionModel().getSelectedIndex() >= 0){
			Addedcourse.add(table1.getSelectionModel().getSelectedItem());

			int count =0;
			int j = 0; 
			if((search.isEmpty())){
				count = 1;
			}
			if((count == 0)){
				for(SelectedCourse list :search){
					if(Addedcourse.get(0).getCourseno() == list.getCourseno()){
						j=1;
					}
				}
				if(j ==1){
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(new Scene(root1));
					SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
					controller.setName("You have already selected this course in other term");
					stage.show();
				}
				else{

					count =1;
				}
			}
			if(count == 1){
				int i =0;
				submitcourse.clear();
				table2.refresh();
				submitcourse.addAll(table2.getItems());
				for(SelectedCourse list :submitcourse){
					if(Addedcourse.get(0).getCourseno() == list.getCourseno() ){
						i=1;
					}
					else if(list.getDay().equals(Addedcourse.get(0).getDay()) && list.getTime().equals(Addedcourse.get(0).getTime())){
						i=2;
					}
				}
				if(i ==1){
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(new Scene(root1));
					SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
					controller.setName("You have already selected this course");
					stage.show();
				}
				else if(i==2){
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(new Scene(root1));
					SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
					controller.setName("You have selected two courses at same time");
					stage.show();
				}
				else{
					table2.getItems().addAll(Addedcourse);	
					table2.refresh();
				}		
			}
		}
		else{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root1));
			SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
			controller.setName("please choose the course to add");
			stage.show();
		}
		Addedcourse.clear();	
		table2.refresh();
	}
	public void Remove(ActionEvent event) throws IOException, SQLException{
		listRemove = table2.getSelectionModel().getSelectedItems();
		for (SelectedCourse Lisd : listRemove){
			table2.getItems().remove(Lisd);
		}
		table2.refresh();
		table1.refresh();

	}
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	public void search(ActionEvent event) throws IOException, SQLException{
		listcourse.clear();
		table1.getItems().clear();
		count++;

		String course = SelectCourseNo.getText();
		String courseName = SelectCourseName.getText();
		JDBCExample n = new JDBCExample();
		Connection con =(n.connection());
		Course coursesearch = new Course();
		DepartmentSelection cc = new DepartmentSelection();
		String department = cc.getDepartment();
		if(con!= null){
			java.sql.PreparedStatement statement;
			if((course.isEmpty()) && courseName.isEmpty()){
				statement =  con.prepareStatement("SELECT * FROM jg_course,jg_professor  where jg_course.department='"+department+"' AND jg_course.professor_id = jg_professor.professor_id");

			}
			else if (course.isEmpty()) {
				statement =  con.prepareStatement("SELECT * FROM jg_course,jg_professor where (course_name = '"+courseName+"' and jg_course.department='"+department+"' AND jg_course.professor_id = jg_professor.professor_id)");
			}
			else if (courseName.isEmpty()){
				statement =  con.prepareStatement("SELECT * FROM jg_course,jg_professor where (course_no = '"+Integer.parseInt(course)+"' and jg_course.department='"+department+"' AND jg_course.professor_id = jg_professor.professor_id)");
			}
			else{
				statement =  con.prepareStatement("SELECT * FROM jg_course,jg_professor where (course_name = '"+courseName+"' and course_no = '"+Integer.parseInt(course)+"' and jg_course.department='"+department+"' AND jg_course.professor_id = jg_professor.professor_id)");
			}
			ResultSet result = statement.executeQuery();
			while(result.next())
			{	
				listcourse.addAll(new SelectedCourse(result.getInt("section_no"),result.getInt("course_no"),result.getString("course_name"),result.getString("prof_first_name") +" " +result.getString("prof_last_name"),result.getString("day"),result.getString("time")));
			}
			if(listcourse.size() == 0){
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wrongPopUp.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(new Scene(root1));
				SearchWrongController controller = fxmlLoader.<SearchWrongController>getController();
				controller.setName("Value entered is Invaild!, Please Enter a valid input");
				stage.show();
			}
			else{
				table1.setItems(listcourse);
			}
		}
		else{
		}

	}


	public void setRegistered(ObservableList<SelectedCourse> selected,ObservableList<SelectedCourse> selected1) {
		// TODO Auto-generated method stub
		search.clear();
		search.addAll(selected1);
		for(SelectedCourse s:selected){
			System.out.println(s.getCourse());
		}
		table2.setItems(selected);
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
