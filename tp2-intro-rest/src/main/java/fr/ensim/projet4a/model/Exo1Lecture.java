package fr.ensim.projet4a.model;

import java.util.ArrayList;

public class Exo1Lecture {

    /**
     * Chaine de caract�re contenant l'�nonc� de l'exercice qui sera afficher en haut de l'�cran.
     */
    String enonce = new String();

    /**
     * Tableau de chaine de caract�re contenant l'�nonc� et des mots similaires.
     * Les chaines de caract�re contenues dans ce tableau serviront de propositions de r�ponce pour l'�l�ve.
     */
    ArrayList<String> apparition = new ArrayList<String>();

    /**
     * On instancie la classe ParamEl1 pour pouvoir charger les param�tres.
     */
    ParamEl1 paramEl1;



    public String getEnonce() {
        return enonce;
    }

    public ArrayList<String> getApparition() {
        return apparition;
    }

    /**
     * Constructeur de la classe Exo1Lecture.
     * Elle charge les param�tres et g�nere l'�nonc� ainsi que le tableau des apparition.
     */
    public Exo1Lecture(ParamEl1 param, String[] listeMots) {
       
        paramEl1 = param;
        apparition = genererApparition(listeMots);
        enonce = genererEnonce();
    }
    public Exo1Lecture(ParamEl1 param, String[] listeMots, String[] listeMots2) {
       
        paramEl1 = param;
        apparition = genererApparition(listeMots,listeMots2);
        enonce = genererEnonce();
    }
    /**
     * Methode qui g�n�re le tableau des apparition.
     *
     * @return Tableau de chaine de caract�re contenant l'�nonc� et des mots similaires.
     */
    private ArrayList<String> genererApparition(String [] listeMots){
        //TODO Il peut il y avoir des bugs avec les indices qui peuvent sortir du tableau
        String[] tousLesMots = listeMots;

        int i=tirageAleatoireEntre1EtLeNombreMitEnParam(tousLesMots.length-5);
        ArrayList<String> enonce=new ArrayList<String>();
        enonce.add(tousLesMots[i]);
        enonce.add(tousLesMots[i+1]);
        enonce.add(tousLesMots[i+2]);
        enonce.add(tousLesMots[i+3]);
        enonce.add(tousLesMots[i+4]);
        
        return enonce;
    }
    private ArrayList<String> genererApparition(String [] listeMots,String [] listeMots2){
        //TODO Il peut il y avoir des bugs avec les indices qui peuvent sortir du tableau
        String[] tousLesNom = listeMots2;
        String[] tousLesDeterminant = listeMots;
       
        int i=tirageAleatoireEntre1EtLeNombreMitEnParam(tousLesNom.length-3);
       
        ArrayList<String> enonce=new ArrayList<String>();
        int index=tirageAleatoireEntre1EtLeNombreMitEnParam(tousLesDeterminant.length-3);
        
        for (int j=index;j<index+3;j++){
            enonce.add(tousLesDeterminant[j]+" "+tousLesNom[i]);
            enonce.add(tousLesDeterminant[j]+" "+tousLesNom[i+1]);
            enonce.add(tousLesDeterminant[j]+" "+tousLesNom[i+2]);
        }

       
        return enonce;
    }

    /**
     * Methode qui g�n�re l'�nonc� de l'exercice.
     *
     * @return Enonc� de l'exercice qui sera afficher en haut de l'�cran
     */
    private String genererEnonce() {
        return apparition.get(0);
    }
    public static int tirageAleatoireEntre1EtLeNombreMitEnParam(int p) {
        int num =1+(int)(Math.random() * (p - 1)) ;

        return num;
    }
}
