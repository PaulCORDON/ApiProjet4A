package fr.ensim.projet4a.model;

import java.util.ArrayList;


public class Classe {
	
	private String nom;	
	private ArrayList<Eleve> listeEleve;
	
	public Classe(String nom, ArrayList<Eleve> listeEleve) {
		this.nom = nom;
		this.listeEleve = new ArrayList<Eleve>();
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ArrayList<Eleve> getListeEleve() {
		return listeEleve;
	}
	public void setListeEleve(ArrayList<Eleve> listeEleve) {
		this.listeEleve = listeEleve;
	}
	public void addEleve(Eleve el) {
		this.listeEleve.add(el);
	}
	
	
}
