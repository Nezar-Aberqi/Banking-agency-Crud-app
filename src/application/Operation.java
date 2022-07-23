package application;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

// the possible operation are the deposit and the withdrawal . 
public class Operation {
	
	private Date date ; 
	private String  type ;
	private Double MontantDeposit; 
	private Double MontantWithdraw ; 
	

	public Operation(Date date ,String type ,Double  montantdeposit , Double montantwithdraw) {
		this.date = date ;  
		this.type = type ;
		this.MontantDeposit = montantdeposit ;
		this.MontantWithdraw =  montantwithdraw;	
		
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date  getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getMontantDeposit() {
		return MontantDeposit;
	}
	public void setMontantDeposit(Double montant) {
		this.MontantDeposit = montant;
	}
	public Double getMontantWithdraw() {
		return MontantWithdraw;
	}
	public void setMontantWithdraw(Double montant) {
		this.MontantWithdraw= montant;
	}

}
