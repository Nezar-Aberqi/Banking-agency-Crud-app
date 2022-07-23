package application;
	
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private static Stage stg ; 
	@Override
	public void start(Stage primaryStage) {
		try {
			
			stg = primaryStage; 
		    primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("firstscene.fxml"));  //we load the fxml  file in the root 
			Scene scene = new Scene(root);
			primaryStage.setScene(scene); 
			primaryStage.setTitle("Admin//User");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
     
	//  cette méthode va nous permettre de passer d'une scène à une autre.
    public void changeScene(String fxml ) throws IOException {
	    Parent pane = FXMLLoader.load(getClass().getResource(fxml));
	    Scene scene =  new Scene(pane); 
	    stg.setScene(scene);
	    stg.setResizable(false);
	    	    
    }
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
 
   