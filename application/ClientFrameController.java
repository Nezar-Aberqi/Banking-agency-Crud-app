package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class ClientFrameController {
	@FXML
	private Button btnShowOps;
	@FXML
	private TextField tfAmount;
	@FXML
	private TableView<Operation> tvOp;
	@FXML
	private TableColumn<Operation,String> colDate;
	@FXML
	private TableColumn<Operation,String> coltype;
	@FXML
	private TableColumn<Operation,Double> colDeposit;
	@FXML
	private TableColumn<Operation,Double> colWithdraw;
	@FXML
	private RadioButton RbDeposit;
	@FXML
	private RadioButton RbWithdraw;
	@FXML
	private Label labelName;
	@FXML
	private TextField tfIdCompte;
	@FXML
	private Label labelBalance;

	// Event Listener on Button[#btnShowOps].onAction
	// consulter le relevé bancaire apres avoir cliquer sur "Show bank Statment".
	@FXML
	public void ShowBankStatement(ActionEvent event) {
	//	labelName.setText();
		Connection connect = getConnection();
	 	String query = " Select Nom_Client,solde from  Compte where idCompte ="+tfIdCompte.getText();  
		 java.sql.Statement   stat ; 
		 ResultSet  result  ; 
		 try {
			 
			stat = connect.createStatement();
			result =   stat.executeQuery(query);   
			while (result.next()) {
			   String name =  result.getString("Nom_Client");
			   Double balance = result.getDouble("solde"); 
			   labelName.setText(name);
			   labelBalance.setText(balance.toString());
			   DisplayOperations(); 
			}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
	// Event Listener on RadioButton[#RbDeposit].onAction
	@FXML
	public void DeposerAmount  (ActionEvent event) { 	   
		  String depositquery= "Insert into Operation (idcompte,Date,Type,MontantDeposit) Values ("+tfIdCompte.getText()+","+"now(),"+"'Deposit',"+tfAmount.getText()+")";
		  String UpdateBalance = "Update Compte set solde = solde +"+tfAmount.getText()+"where idCompte ="+tfIdCompte.getText();
		  Double x =  Double.parseDouble(tfAmount.getText().toString());
		  if (x<0) {
			  Alert alert = new Alert(Alert.AlertType.ERROR);
			  alert.setContentText(" SQL CONSTRAINT HAS BEEN VIOLATED , PLEASE CHECK THAT THE AMOUNT IS GREATER OR EQUAL THAN 0 DHS."); 
			  alert.show();
		  }  
		  else {  
			  dothequery(depositquery); 
			  DisplayOperations();    
		      dothequery(UpdateBalance); 
		  }
	}
	// Event Listener on RadioButton[#RbWithdraw].onAction
	@FXML
	public void RetirerMontant(ActionEvent event) {
		  
		String  Withdrawquery  = "Insert into Operation (idcompte,Date,Type,MontantDeposit,MontantWithdraw) Values ("+tfIdCompte.getText()+","+"now(),"+"'Withdraw',"+"0.00,"+tfAmount.getText()+")";
	    String UpdateBalance = "Update Compte set solde = solde -"+tfAmount.getText()+"where idCompte ="+tfIdCompte.getText();
	    String getBalance ="Select solde from Compte where  idCompte = "+tfIdCompte.getText();
	    Double x =  Double.parseDouble(tfAmount.getText().toString());
		  if (x<0) {
			  Alert alert = new Alert(Alert.AlertType.ERROR);
			  alert.setContentText(" SQL CONSTRAINT HAS BEEN VIOLATED , PLEASE CHECK THAT THE AMOUNT IS GREATER OR EQUAL THAN 0 DHS."); 
			  alert.show();
		  } 
		  // before even executing the query u need to check  if amount < balance and then call them both ;
		  Connection connect = getConnection();
		  java.sql.Statement   stat ; 
		  ResultSet  rs  ; 
			 try {
				stat = connect.createStatement();
				rs =   stat.executeQuery(getBalance);  
				while (rs.next()) {
					Double SoldeRestant =  rs.getDouble("solde"); 
				    if (Double.parseDouble(tfAmount.getText().toString())>SoldeRestant) {
					     Alert al = new Alert(Alert.AlertType.INFORMATION);
				   	    al.setAlertType(AlertType.WARNING);
					    al.setContentText("YOUR BALANCE IS NOT  SUFFICIENT TO DO THE MONEY WITHDRAWAL !!");
					    al.show();
				    }else { 
						 dothequery(Withdrawquery); 
						 DisplayOperations();

				    }
				}    
			 }catch (Exception e) {
				 e.printStackTrace();
			 }
	}
	// Event Listener on  Button[#btnLogOut].onAction
	// revenir vers la première scène.
		@FXML
		public void LogOut(ActionEvent event) throws IOException {
			Main m  = new Main () ; 
			m.changeScene("firstscene.fxml");
		    
		}
		
	// établir une connecetion avec notre base de données.
	public  Connection getConnection() {
		try {
			Connection conx =  DriverManager.getConnection("jdbc:mysql://localhost:3306/nezardb","root", "nezarab01");
				return conx  ; 
			}catch(Exception e) {
				System.out.println("Erreur!!");
				return null ;  
	     	}
	}
	// cette méthode est utilisé pour éviter de redondance de ce bloc de code dans chaque reqete SQL. 
	public void dothequery(String query) {
		Connection  conx = getConnection(); 
		java.sql.Statement st ;
		try {
			st= conx.createStatement();
		    st.executeUpdate(query); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Cette méthode nous permet de récupérer le ResultSet et le placer dans une ObservableList.
	public  ObservableList<Operation> getOperations(){
		 ObservableList<Operation> OperationsList = FXCollections.observableArrayList();
		 Connection connect = getConnection();
		 String query = "Select Date,Type,MontantDeposit,MontantWithdraw from  Operation where idCompte ="+tfIdCompte.getText();  
		 java.sql.Statement   stat ; 
		 ResultSet  result  ; 
		 try {
			 
			stat = connect.createStatement();
			result =   stat.executeQuery(query);
			Operation op ;   
			while (result.next()) {
			    op =  new Operation(result.getDate("date"),result.getString("Type"),result.getDouble("MontantDeposit"),result.getDouble("MontantWithdraw"));//  ,columns in the mySQL database .
			    OperationsList.add(op);		
			}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
		 return OperationsList ;  
	 }
	// remplir notre tableview avec le contenu du ResultSet qui a été placé dans une ObservableList. 
    public void DisplayOperations() {
    	ObservableList<Operation> array = getOperations(); 
    	
    	colDate.setCellValueFactory(new PropertyValueFactory<Operation,String>("date"));
    	coltype.setCellValueFactory(new PropertyValueFactory<Operation,String>("type"));
    	colDeposit.setCellValueFactory(new PropertyValueFactory<Operation,Double>("MontantDeposit"));
    	colWithdraw.setCellValueFactory(new PropertyValueFactory<Operation,Double>("MontantWithdraw"));
    	tvOp.setItems(array);
    }
	 
}
  
