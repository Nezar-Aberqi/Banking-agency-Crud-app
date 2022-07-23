package application;

import java.util.ArrayList;

import java.util.List;



public class Compte {
	private Integer  idCompte ;
	private  String nom ;
	private Double solde ;

     
    public Integer getIdCompte() {
		return idCompte;
	}
	public void setidCompte(Integer i) {
		this.idCompte = i;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Double getSolde() {
		
		return solde;
	}
	public void setSolde(Double solde) throws Exception {
		if (solde<0) throw new Exception ("Attention");
		this.solde = solde;
		
	}
	
    public Compte(Integer  idCompte , String nom , Double  solde) {
    	this.idCompte = idCompte;
		this.nom = nom;
		this.solde = solde;
		
		
	}
	
	public Compte(Compte compte) {
		 this.nom = compte.nom ; 
		 this.solde = compte.solde; 
		 this.idCompte = compte.idCompte; 
	} 
	public void deposit(double montant) {
       this.solde+=montant;
    
    }
    public void withdraw(double montant) {
      this.solde -= montant ; 
     
    }


    public boolean equals (Compte C ) {
    	if (this.idCompte == C.idCompte) {
    		return true ; 
    		
    	}
    	else {
    		return false;
    	}
    }
    public String toString () {
    	return  "nom : " + nom +"    numéro : " + idCompte 
    			+" " + "    solde actuel : "+" "+solde;
    }	
}
