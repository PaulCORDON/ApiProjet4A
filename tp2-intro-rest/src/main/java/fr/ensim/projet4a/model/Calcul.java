package fr.ensim.projet4a.model;

public class Calcul {
	private boolean reponseJuste;
	private String operation;
	private int idExo1Math;
	private int idExo2Math;
	private int idCalcul;
	public boolean isReponseJuste() {
		return reponseJuste;
	}
	public void setReponseJuste(boolean reponseJuste) {
		this.reponseJuste = reponseJuste;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public int getIdExo1Math() {
		return idExo1Math;
	}
	public void setIdExo1Math(int idExo1Math) {
		this.idExo1Math = idExo1Math;
	}
	public int getIdExo2Math() {
		return idExo2Math;
	}
	public void setIdExo2Math(int idExo2Math) {
		this.idExo2Math = idExo2Math;
	}
	public int getIdCalcul() {
		return idCalcul;
	}
	public void setIdCalcul(int idCalcul) {
		this.idCalcul = idCalcul;
	}
	
	public Calcul(boolean reponseJuste, String operation, int idExo1Math, int idExo2Math, int idCalcul) {
		this.reponseJuste = reponseJuste;
		this.operation = operation;
		this.idExo1Math = idExo1Math;
		this.idExo2Math = idExo2Math;
		this.idCalcul = idCalcul;
	}
	public Calcul(boolean reponseJuste, String operation) {
		super();
		this.reponseJuste = reponseJuste;
		this.operation = operation;
	}

}
