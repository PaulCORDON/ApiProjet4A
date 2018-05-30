package fr.ensim.projet4a.model;

public class SousCompetence {
	public String name;
	public float progress;
	public int nbFoisTest;
	public String description;
	public String nomCompetenceMere;
	
	public SousCompetence(String name, float progress, int nbFoisTest,String descr,String CompetenceMere) {
		super();
		this.name = name;
		this.progress = progress;
		this.nbFoisTest = nbFoisTest;
		this.description=descr;
		this.nomCompetenceMere=CompetenceMere;
	}
}