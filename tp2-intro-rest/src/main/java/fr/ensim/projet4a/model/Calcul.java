package fr.ensim.projet4a.model;

public class Calcul {
	private String reponseJuste;
	private String reponseEleve;
	private String operation;
	private int idExo1Math;
	private int idExo2Math;
	private Borne[] bornes;
	private int idCalcul;


	public Calcul(String reponseJuste, String reponseEleve, String operation, Borne[] bornes) {
		super();
		this.reponseJuste = reponseJuste;
		this.reponseEleve = reponseEleve;
		this.operation = operation;
		this.bornes = bornes;
	}

	public Calcul(String reponseJuste, String reponseEleve, String operation, int idExo1Math, int idExo2Math,
			Borne[] bornes, int idCalcul) {
		super();
		this.reponseJuste = reponseJuste;
		this.reponseEleve = reponseEleve;
		this.operation = operation;
		this.idExo1Math = idExo1Math;
		this.idExo2Math = idExo2Math;
		this.bornes = bornes;
		this.idCalcul = idCalcul;
	}

	public Borne[] getBornes() {
		return bornes;
	}

	public void setBornes(Borne[] bornes) {
		this.bornes = bornes;
	}

	public int getIdCalcul() {
		return idCalcul;
	}

	public void setIdCalcul(int idCalcul) {
		this.idCalcul = idCalcul;
	}

	public String getReponseJuste() {
		return reponseJuste;
	}

	public void setReponseJuste(String reponseJuste) {
		this.reponseJuste = reponseJuste;
	}

	public String getReponseEleve() {
		return reponseEleve;
	}

	public void setReponseEleve(String reponseEleve) {
		this.reponseEleve = reponseEleve;
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


}
