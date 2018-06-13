package fr.ensim.projet4a.model;



public class Borne {

	private int idBorne;
	private int nombre;
	private int idExo1Math;
	
	public Borne(int idBorne, int bornes, int idExo1Math, int idCalcul) {

		this.idBorne = idBorne;
		this.setNombre(bornes);
		this.idExo1Math = idExo1Math;
		
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public int getIdBorne() {
		return idBorne;
	}

	public void setIdBorne(int idBorne) {
		this.idBorne = idBorne;
	}

	public int getIdExo1Math() {
		return idExo1Math;
	}

	public void setIdExo1Math(int idExo1Math) {
		this.idExo1Math = idExo1Math;
	}

}

