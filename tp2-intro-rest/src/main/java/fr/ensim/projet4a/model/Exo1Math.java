package fr.ensim.projet4a.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Exo1Math extends Exercice{
	private int idExo1Math;
	
	private ParamEm1 paramEm1;
	
	private Eleve el; 
	
	private int score; 
	
    private Timestamp date;

    /**
     * Liste des calculs
     */
    private ArrayList<Calcul> calculEnonce;

    /**
     * Liste des listes de bornes
     */
    private ArrayList<Borne> bornes;
     
   
    

   public Eleve getEl() {
		return el;
	}

	public void setEl(Eleve el) {
		this.el = el;
	}

public ArrayList<Calcul> getCalculEnonce() {
		return calculEnonce;
	}

	public void setCalculEnonce(ArrayList<Calcul> calculEnonce) {
		this.calculEnonce = calculEnonce;
	}

	public ArrayList<Borne> getBornes() {
		return bornes;
	}

	public void setBornes(ArrayList<Borne> bornes) {
		this.bornes = bornes;
	}

	public int getResultats() {
		return score;
	}

	public void setResultats(int resultats) {
		this.score = resultats;
	}

	public ParamEm1 getParam() {
		return paramEm1;
	}

	public void setParam(ParamEm1 param) {
		this.paramEm1 = param;
	}

	public int getIdExo1Math() {
		return idExo1Math;
	}

	public void setIdExo1Math(int idExo1Math) {
		this.idExo1Math = idExo1Math;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public Exo1Math(int idExo1Math, ParamEm1 paramEm1, Eleve el, int score, Timestamp date,
			ArrayList<Calcul> calculEnonce, ArrayList<Borne> bornes) {
		
		this.idExo1Math = idExo1Math;
		this.paramEm1 = paramEm1;
		this.el = el;
		this.score = score;
		this.date = date;
		this.calculEnonce = calculEnonce;
		this.bornes = bornes;
	}

}
