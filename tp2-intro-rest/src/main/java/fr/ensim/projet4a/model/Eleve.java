package fr.ensim.projet4a.model;
import java.sql.Date;
import javax.validation.constraints.NotEmpty;
//@JsonIgnoreProperties(value = { "id" })

public class Eleve {
	private int ID;
	@NotEmpty
	private String nomPrenom;
	private Date dateDeNaissance;
	public Eleve () {
		
		
	}
	public Eleve(int iD, String nomPrenom, Date dateDeNaissance) {
		
		ID = iD;
		
		this.nomPrenom = nomPrenom;
		this.dateDeNaissance = dateDeNaissance;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNomPrenom() {
		return nomPrenom;
	}
	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom = nomPrenom;
	}
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

}
