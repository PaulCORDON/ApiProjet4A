package fr.ensim.projet4a.model;

import java.util.ArrayList;

public class Exo1Math extends Exercice{

    /**
     * Liste des �nonc�s
     */
    private ArrayList<Calcul> calculEnonce;

    /**
     * Liste des listes de bornes
     */
    private ArrayList<Borne> bornes;

    /**
     * Liste des r�sultats
     */
    private int[] resultats;

    /**
     * param�tre selon lesquels les listes sont cr��es
     */
    private ParamEm1 param;

    /**
     * constructeur de l'exercice
     * cr�� les enonc�s et les bornes corespondantes
     *
     * @param param param�tres de l'exercice
     */
    public Exo1Math(ParamEm1 param) {
        //initialisation
        this.param = param;
        calculEnonce = new ArrayList<Calcul>(this.param.getNbQuestions());
        bornes = new ArrayList<Borne>(this.param.getNbQuestions());
        resultats = new int[param.getNbQuestions()];

        //variables
        int operandes[] = new int[2];
        int choixOperateur;

        //pour chaque question
        for (int i = 0; i < this.param.getNbQuestions(); i++) {

            // choix de l'operateur
            do {
                choixOperateur = (int) (Math.random() * 4);
            }
            while (!this.param.getOperateur()[choixOperateur]);   //tant que l'op�rateur ne correspond pas aux param�tres


            //choix des operandes et calcul du resultat
            do {
                //pour chaque operande
                for (int j = 0; j <= 1; j++) {
                    do {
                        operandes[j] = (int) (Math.random() * this.param.getValMax());
                    } while (operandes[j] == 0 || (param.getPairOnly() && operandes[j] % 2 != 0));
                }

                //si on est dans le cas d'une soustraction
                if (choixOperateur == 1)
                    //on ordonne les op�randes pour que le r�sultat ne soit pas n�gatif
                    if (operandes[0] < operandes[1]) {
                        int swap = operandes[0];
                        operandes[0] = operandes[1];
                        operandes[1] = swap;
                    }

                //on calcul le r�sultat
                resultats[i] = calculResultat(operandes[0], operandes[1], choixOperateur);

            }
            while (resultats[i] > this.param.getValMax());    //tant que, r�sultat ne correspond pas aux param�tres

            //on construit l'�nonc� selon l'op�rateur puis on l'ajoute � la liste
            switch (choixOperateur) {
                case 0:
                    calculEnonce.add(new Calcul(operandes[0],operandes[1],'+'));
                    break;
                case 1:
                    calculEnonce.add(new Calcul(operandes[0],operandes[1],'-'));
                    break;
                case 2:
                    calculEnonce.add(new Calcul(operandes[0],operandes[1],'*'));
                    break;
                case 3: //passage par une multiplication afin de toujours avoir des nombres entiers
                    int swap = resultats[i];
                    resultats[i] = operandes[0];
                    operandes[0] = swap;
                    calculEnonce.add(new Calcul(operandes[0],operandes[1],'/'));
                    break;
            }

            //initialisation des bornes
            Borne bornesTempo = new Borne(param.getNbBornes());
            
            //pour chaque borne
            for (int j = 0; j < param.getNbBornes(); j++) {
                //on g�n�re un entier correspondant aux param�tres
                int borne;
                boolean correct;

                do {
                    correct = true;
                    borne = (int) (Math.random() * param.getValMax());

                    if ((!param.getBorneSelectionnable() && borne == resultats[i]) || borne == 0)
                        correct = false;

                    for (int b : bornesTempo.bornes) {
                        if (borne == b) correct = false;
                    }

                } while (!correct);

                bornesTempo.add(borne);
            }

            boolean trie;
            do {
                trie = true;
                for (int j = 0; j < bornesTempo.size() - 1; j++) {
                    int swap;

                    //si il ne sont pas dans l'ordre on les interchange
                    if (bornesTempo.get(j) > bornesTempo.get(j + 1)) {
                        swap = bornesTempo.get(j);
                        bornesTempo.set(j, bornesTempo.get(j + 1));
                        bornesTempo.set(j + 1, swap);

                        //on a fait un changement, il faut rev�rifier le d�but
                        trie = false;
                    }
                }
            } while (!trie);
            //tant que la liste n'est pas tri�e


            //si le param�tre indique qu'une borne doit �tre �gale � +/- 1 � un op�rande
            if (param.getBorneEqualsOp()) {
                int min = param.getValMax();
                int numBorne = 0;
                int numOperande = 0;

                //on cherche la borne la plus proche de la valeur d'un op�rande
                for (int b : bornesTempo.bornes) {
                    if (Math.abs(b - operandes[0]) < min) {
                        min = Math.abs(b - operandes[0]);
                        numBorne = bornesTempo.indexOf(b);
                        numOperande = 0;
                    }
                    if (Math.abs(b - operandes[1]) < min) {
                        min = Math.abs(b - operandes[1]);
                        numBorne = bornesTempo.indexOf(b);
                        numOperande = 1;
                    }
                }

                //TODO param�tre distance entre borne et op�rande
                //on modifie la borne afin qu'elle corresponde aux param�tres
                int borneModifiee;
                do {
                    borneModifiee = operandes[numOperande] + (int) (Math.random() * 3) - 1;
                }
                while ((borneModifiee == resultats[i] && param.getBorneSelectionnable()) || borneModifiee == 0);

                bornesTempo.set(numBorne, borneModifiee);
            }

            //on ajoute les bornes � la liste
            bornes.add(bornesTempo);
        }
    }

    /**
     * calcul le resultat de l'op�ration "valA operateur valB" et retourne le resultat
     *
     * @param valA
     * @param valB
     * @param operateur
     * @return resultat
     */
    private int calculResultat(int valA, int valB, int operateur) {
        switch (operateur) {
            case 0:
                return valA + valB;
            case 1:
                return valA - valB;
            case 2:
                return valA * valB;
            case 3:
                return valA * valB;
            default:
                return 0;
        }
    }

    /**
     * @return calculEnonce
     */
    public ArrayList<Calcul> getCalculEnonce() {
        return calculEnonce;
    }

    /**
     * @return param
     */
    public ParamEm1 getParam() {
        return param;
    }

    /**
     * @return bornes
     */
    public ArrayList<Borne> getBornes() {
        return bornes;
    }

    /**
     * @return resultats
     */
    public int[] getResultats() {
        return resultats;
    }



}
