package application;

import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;


import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class UserLoginController {
	@FXML
	private TextField tfUsername;

	@FXML
	private Button btnUserLogin;
	@FXML
	private Button btnback;
	@FXML
	private PasswordField pfpassword;

	// Event Listener on Button[#btnUserLogin].onAction
	// checking the user's input
	@FXML
	public void UserMenu(ActionEvent event) throws IOException {
		Main m =  new Main(); 
	     if(tfUsername.getText().isEmpty() && pfpassword.getText().isEmpty()) {
	    	 Alert alert = new Alert(Alert.AlertType.ERROR);
			 alert.setContentText(" PLease Fill the Empty Fields !"); 
			 alert.show();
		  }else if (tfUsername.getText().toString().equals("TPJAVA") && pfpassword.getText().toString().equals("BI")) {
			  m.changeScene("ClientFrame.fxml");
		  }else {
			     Alert alert = new Alert(Alert.AlertType.ERROR);
				 alert.setContentText(" Retry Please !"); 
				 alert.show();
		  }
	} 
	// return to previous scene after clicking on "Back".
	public void backMenu2(ActionEvent event) throws IOException {
		Main m =  new Main ();
		m.changeScene("firstscene.fxml");
	}


}
