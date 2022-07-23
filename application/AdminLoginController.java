package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class AdminLoginController {
	@FXML
	private TextField tfAdminname;
	@FXML
	private Label labeladminlogin;
	@FXML
	private Button btnlogin;
	@FXML
	private Button btnback;
	@FXML
	private PasswordField pfpassword;

	// Event Listener on Button[#btnlogin].onAction
	@FXML
	public void AdminMenu(ActionEvent event) throws IOException {
		Main  m = new Main(); 
	     if( tfAdminname.getText(). toString().equals("nezar") && pfpassword.getText().toString().equals("111")) {
	    	 labeladminlogin.setText("Log in Successful !");
			  m.changeScene("AdminFrame.fxml");
	     }
       else if(tfAdminname.getText().isEmpty() && pfpassword.getText().isEmpty()) {
       	labeladminlogin.setText("Please enter your data");
       }
		  else { labeladminlogin.setText("Wrong username or password!");}
	}
	public void backMenu(ActionEvent event) throws IOException {
		Main m =  new Main ();
		m.changeScene("firstscene.fxml");
	}
	 
	
}
