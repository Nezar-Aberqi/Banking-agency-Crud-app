package application;

import javafx.fxml.FXML;


import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class AdminMenuController2 {
	@FXML
	private TableView<Compte> tvComptes;
	@FXML
	private TableColumn<Compte,Integer> colIdCompte;
	@FXML
	private TableColumn<Compte,String> colName;
	@FXML
	private TableColumn<Compte,Double> colBalance;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfBalance;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnClear;
	@FXML
	private TextField tfRechercherapide;
	@FXML
	private Button btnBack;
	@FXML
	private Label labelIDInfo;

	// Event Listener on TableView[#tvComptes].onMouseClicked
	// cette méthode permet de remplir les champs "Name" et "Balance" avec les donées de la ligne séléctionnée dans la tableView tvComptes. 
	@FXML
	public void ShowInfo(MouseEvent event) {
		 Compte compte  = tvComptes.getSelectionModel().getSelectedItem();
		 tfName.setText(compte.getNom());
		 tfBalance.setText(""+compte.getSolde());
	}
	 // initializing the table with already existing bank  acccounts .
		public void initialize() {
		 ShowAccounts(); 
		}
		// Event Listener on Button[#btnNew].onAction
		// ajouter un nouveau compte.
		@FXML                                               
		public void AddAccount(ActionEvent event) {
			 String query =  "INSERT INTO Compte(Nom_Client,solde) Values('"+tfName.getText()+"'"+ ","+tfBalance.getText()+")";
			 dothequery(query); 
			 ShowAccounts(); 
		}
		// Event Listener on Button[#btnClear].onAction
		// supprimer un compte existant.
		@FXML
		public void DeleteAccount(ActionEvent event) {
			String query = "delete from  Compte where idCompte = (select * from (select idCompte from  compte where Nom_Client ='"+tfName.getText()+"' and solde = "+tfBalance.getText()+""+ ")"+"as Deletetable"+")"; ;
			dothequery(query); 
		    ShowAccounts(); 
		}
		// Event Listener on Button[#btnBack].onAction
		// revenir vers la scène précédente . 
		@FXML
		public void LogOut(ActionEvent event)throws IOException {
			 Main m = new Main(); 
			 m.changeScene("AdminLogin.fxml");
		}
	 
		 // créer une connecxion  avec notre base de données.
		public  Connection getConnection() {
			try {
				Connection conx =  DriverManager.getConnection("jdbc:mysql://localhost:3306/nezardb","root", "nezarab01");
					return conx  ; 
				}catch(Exception e) {
					System.out.println("Erreur!!");
					return null ;  
		     	}
			}
		// Cette méthode nous permet de récupérer le ResultSet et le placer dans une ObservableList.
			public  ObservableList<Compte> getAccounts(){
				 ObservableList<Compte> ComptesList = FXCollections.observableArrayList();
				 Connection connect = getConnection();
				 String query = " Select * from Compte ";  
				 java.sql.Statement   stat ; 
				 ResultSet  result  ; 
				 try {
					 
					stat = connect.createStatement();
					result =   stat.executeQuery(query);
					Compte  compte ;   
					while (result.next()) {
					    compte =  new Compte(result.getInt("idCompte"),result.getString("Nom_Client"),result.getDouble("solde"));
						ComptesList.add(compte);		
					}
				 }catch (Exception e) {
					 e.printStackTrace();
				}
				 return ComptesList ;  
			 }

			// remplir notre tableview avec le contenu du ResultSet qui a été placé dans une ObservableList
			public void ShowAccounts() {
				ObservableList<Compte> array =  getAccounts();
				colIdCompte.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("idCompte"));
				colName.setCellValueFactory(new PropertyValueFactory<Compte,String>("nom"));
				colBalance.setCellValueFactory(new PropertyValueFactory<Compte,Double>("solde"));
				tvComptes.setItems(array);
				
			
				// on va initializer la liste filtrée ,  la liste va se filtrer elle meme si elle trouve une ligne qui continnent la nouvelle
				//valeur du text field (Recherche Rapide) , sinon , la liste will remain empty .  
				

			FilteredList<Compte> listeComptes = new FilteredList<>(array, b -> true);
			    tfRechercherapide.textProperty().addListener((observable, oldValue, newValue) -> {
				listeComptes.setPredicate(Compte -> {
			       
			        if (newValue.isEmpty() || newValue == null) {
			            return true;
			        }
			       String  motclé  = newValue.toLowerCase();
			        if (Compte.getIdCompte().toString().indexOf(motclé)>-1) {
			              return true; // Means we found a match in the id of the account 
			         } else if (Compte.getNom().toLowerCase().indexOf(motclé)> -1) {
			              return true; // we found a match in the name  
			        }else  if (Compte.getSolde().toString().indexOf(motclé)>-1) {
			             return true; // we found a match in the balance 
			         } else 
			        	 return false ; // no match was found 
			       
				});
			});
			
			SortedList<Compte> listetriéecomptes =  new SortedList<>(listeComptes); 
			//  Bind sorted data  here : 
			listetriéecomptes.comparatorProperty().bind(tvComptes.comparatorProperty());
			
			 // and finally we apply the sorted data to the table view tvComptes 
			tvComptes.setItems(listetriéecomptes);		       
			       
			}

			// exexute the insert and clear queries here  
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
}

       