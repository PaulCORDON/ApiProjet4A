package fr.ensim.projet4a.model;

import java.util.ArrayList;

public class Competence {
	public String name;
	public float progress;
	public ArrayList<SousCompetence> tabSousComp;
	
	public Competence(String name, float progress, ArrayList<SousCompetence> tabSousComp) {
		super();
		this.name = name;
		this.progress = progress;
		this.tabSousComp = tabSousComp;
	}
}
