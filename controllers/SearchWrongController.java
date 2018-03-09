package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SearchWrongController {
	@FXML
	private Label wrongmessage;
	
	public void wrongok(ActionEvent event) throws IOException{
		(((Node) event.getSource())).getScene().getWindow().hide();
		
	}
	public void setName(String name) {
		wrongmessage.setText(name);
	}
}
