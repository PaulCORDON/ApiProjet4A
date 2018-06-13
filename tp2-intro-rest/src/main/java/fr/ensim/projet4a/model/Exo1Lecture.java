package fr.ensim.projet4a.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Exo1Lecture {

	/**
	 * Chaine de caract�re contenant l'�nonc� de l'exercice qui sera afficher en
	 * haut de l'�cran.
	 */
	String enonce = new String();

	/**
	 * Tableau de chaine de caract�re contenant l'�nonc� et des mots similaires. Les
	 * chaines de caract�re contenues dans ce tableau serviront de propositions de
	 * r�ponce pour l'�l�ve.
	 */
	ArrayList<String> apparition = new ArrayList<String>();

	/**
	 * On instancie la classe ParamEl1 pour pouvoir charger les param�tres.
	 */
	private ParamEl1 paramEl1;
	private Eleve el;
	private int score;
	private Timestamp date;

	public Exo1Lecture(String enonce, ArrayList<String> apparition, ParamEl1 paramEl1, Eleve el, int score,
			Timestamp date) {
		super();
		this.enonce = enonce;
		this.apparition = apparition;
		this.paramEl1 = paramEl1;
		this.el = el;
		this.score = score;
		this.date = date;
	}

	public String getEnonce() {
		return enonce;
	}

	public ArrayList<String> getApparition() {
		return apparition;
	}

	public ParamEl1 getParamEl1() {
		return paramEl1;
	}

	public void setParamEl1(ParamEl1 paramEl1) {
		this.paramEl1 = paramEl1;
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

}
