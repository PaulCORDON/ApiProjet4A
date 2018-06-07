package fr.ensim.projet4a.model;
import java.sql.Date;
import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
//@JsonIgnoreProperties(value = { "id" })

public class Eleve {
	public Eleve(@NotEmpty String nomPrenom, Date dateDeNaissance) {
		super();
		this.nomPrenom = nomPrenom;
		this.dateDeNaissance = dateDeNaissance;
	}
	public Eleve() {
		super();
	}
	
	@NotEmpty
	private String nomPrenom;
	private Date dateDeNaissance;
	private int classeId;
	private String classeName;
	private int idEleve;
	private ArrayList<Competence> listeCompetence;
	
	
	public int getIdEleve() {
		return idEleve;
	}
	public void setIdEleve(int idEleve) {
		this.idEleve = idEleve;
	}
	public ArrayList<Competence> getListeCompetence() {
		return listeCompetence;
	}
	public void setListeCompetence(ArrayList<Competence> listeCompetence) {
		this.listeCompetence = listeCompetence;
	}
	public String getClasseName() {
		return classeName;
	}
	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}
	public int getClasseId() {
		return classeId;
	}
	public void setClasseId(int classeId) {
		this.classeId = classeId;
	}
	public Eleve( String nomPrenom, Date dateDeNaissance,String classeName) {
		this.nomPrenom = nomPrenom;
		this.dateDeNaissance = dateDeNaissance;
		this.classeName=classeName;
	}
	public Eleve( String nomPrenom, Date dateDeNaissance,String classeName,int classeId,int id) {
		this.nomPrenom = nomPrenom;
		this.dateDeNaissance = dateDeNaissance;
		this.classeName=classeName;
		this.classeId=classeId;
		this.idEleve=id;
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
