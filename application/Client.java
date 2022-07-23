package application;

public class Client {
    private int idClient ; 
    private String Nom_Client ; 
    private String Adresse ;  
    private String Telephone  ;
	public Client(int idClient, String nom_Client, String adresse, String telephone) {
		super();
		this.idClient = idClient;
		Nom_Client = nom_Client;
		Adresse = adresse;
		Telephone = telephone;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getNom_Client() {
		return Nom_Client;
	}
	public void setNom_Client(String nom_Client) {
		Nom_Client = nom_Client;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}  

   
}
