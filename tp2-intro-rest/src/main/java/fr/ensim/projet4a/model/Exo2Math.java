package fr.ensim.projet4a.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Exo2Math {
	private int idExo2Math;
	private ParamEm2 paramEm2;
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

	public int getIdExo2Math() {
		return idExo2Math;
	}

	public void setIdExo2Math(int idExo2Math) {
		this.idExo2Math = idExo2Math;
	}

	public ParamEm2 getParamEm2() {
		return paramEm2;
	}

	public void setParamEm2(ParamEm2 paramEm2) {
		this.paramEm2 = paramEm2;
	}

	public Eleve getEl() {
		return el;
	}

	public void setEl(Eleve el) {
		this.el = el;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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

	public Exo2Math(int idExo2Math, ParamEm2 paramEm2, Eleve el, int score, Timestamp date,
			ArrayList<Calcul> calculEnonce, ArrayList<Borne> bornes) {

		this.idExo2Math = idExo2Math;
		this.paramEm2 = paramEm2;
		this.el = el;
		this.score = score;
		this.date = date;
		this.calculEnonce = calculEnonce;
		this.bornes = bornes;
	}

}
