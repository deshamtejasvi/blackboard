package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminController {

	@FXML
	private Label welcomemsg;
	public void setName(String name) {
		welcomemsg.setText(name);

	}
	public void logout(ActionEvent event) throws IOException{
		((Node)(event.getSource())).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void forStudent(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addemplyee.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root1));
		AddEmpoyeesController controller = fxmlLoader.<AddEmpoyeesController>getController();
		controller.Student("student");
		stage.show();
	}

	public void forProfessor(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addemplyee.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root1));
		AddEmpoyeesController controller = fxmlLoader.<AddEmpoyeesController>getController();
		controller.Student("professor");
		stage.show();
	}

	public void foradmin(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addemplyee.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root1));
		AddEmpoyeesController controller = fxmlLoader.<AddEmpoyeesController>getController();
		controller.Student("admin");
		stage.show();
	}


}
