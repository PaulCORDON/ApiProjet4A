package fr.ensim.projet4a.model;

public abstract class Exercice {

    private Double note;

    private int bonneRep;

    private int mauvaiseRep;


    public Double getNote() {return note;}

    public int getBonneRep() {return bonneRep;}

    public int getMauvaiseRep() {return mauvaiseRep;}


    public void setNote(Double note) {this.note = note;}

    public void setBonneRep(int bonneRep) {this.bonneRep = bonneRep;}

    public void setMauvaiseRep(int mauvaiseRep) {this.mauvaiseRep = mauvaiseRep;}
}
