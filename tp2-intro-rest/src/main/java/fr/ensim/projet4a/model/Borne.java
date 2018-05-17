package fr.ensim.projet4a.model;

import java.util.ArrayList;

public class Borne {
	ArrayList<Integer> bornes;

    public Borne(int nbBornes) {
        bornes = new ArrayList<Integer>(nbBornes);
    }

    public void add(int borne) {
        bornes.add(borne);
    }

    public int size() {
        return bornes.size();
    }

    public int get(int j) {
        return bornes.get(j);
    }

    public void set(int j, int i) {
        bornes.set(j,i);
    }

    public int indexOf(int b) {
        return bornes.indexOf(b);
    }
}
