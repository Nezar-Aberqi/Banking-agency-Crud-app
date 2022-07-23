package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.image.ImageView;

public class firstsceneController {
	@FXML
	private ImageView img1;
	@FXML
	private Button btnadmin;
	@FXML
	private Button btnUser;

	// Event Listener on Button[#btnadmin].onAction
	@FXML
	public void AdminLogin(ActionEvent event) throws IOException {
		Main m =  new Main(); 
		m.changeScene("AdminLogin.fxml");
	}
	// Event Listener on Button[#btnUser].onAction
	@FXML
	public void UserLogin(ActionEvent event) throws IOException {
		Main m =  new Main(); 
		m.changeScene("UserLogin.fxml");
	}
}
