package fr.ensim.projet4a.model;

import java.util.ArrayList;

public class Exo1Math extends Exercice{

    /**
     * Liste des énoncés
     */
    private ArrayList<Calcul> calculEnonce;

    /**
     * Liste des listes de bornes
     */
    private ArrayList<Borne> bornes;

    /**
     * Liste des résultats
     */
    private int[] resultats;

    /**
     * paramètre selon lesquels les listes sont créées
     */
    private ParamEm1 param;

    /**
     * constructeur de l'exercice
     * créé les enoncés et les bornes corespondantes
     *
     * @param param paramètres de l'exercice
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
            while (!this.param.getOperateur()[choixOperateur]);   //tant que l'opérateur ne correspond pas aux paramètres


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
                    //on ordonne les opérandes pour que le résultat ne soit pas négatif
                    if (operandes[0] < operandes[1]) {
                        int swap = operandes[0];
                        operandes[0] = operandes[1];
                        operandes[1] = swap;
                    }

                //on calcul le résultat
                resultats[i] = calculResultat(operandes[0], operandes[1], choixOperateur);

            }
            while (resultats[i] > this.param.getValMax());    //tant que, résultat ne correspond pas aux paramètres

            //on construit l'énoncé selon l'opérateur puis on l'ajoute à la liste
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
                //on génère un entier correspondant aux paramètres
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

                        //on a fait un changement, il faut revérifier le début
                        trie = false;
                    }
                }
            } while (!trie);
            //tant que la liste n'est pas triée


            //si le paramètre indique qu'une borne doit être égale à +/- 1 à un opérande
            if (param.getBorneEqualsOp()) {
                int min = param.getValMax();
                int numBorne = 0;
                int numOperande = 0;

                //on cherche la borne la plus proche de la valeur d'un opérande
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

                //TODO paramètre distance entre borne et opérande
                //on modifie la borne afin qu'elle corresponde aux paramètres
                int borneModifiee;
                do {
                    borneModifiee = operandes[numOperande] + (int) (Math.random() * 3) - 1;
                }
                while ((borneModifiee == resultats[i] && param.getBorneSelectionnable()) || borneModifiee == 0);

                bornesTempo.set(numBorne, borneModifiee);
            }

            //on ajoute les bornes à la liste
            bornes.add(bornesTempo);
        }
    }

    /**
     * calcul le resultat de l'opération "valA operateur valB" et retourne le resultat
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
