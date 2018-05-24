package fr.ensim.projet4a.model;

public class ParamEm1 extends ParamMath{
	/**
	 * nom du parametre donner par le prof
	 */
	public String nom;
	
	private int nbBornes;
    /**
     * Nombre de bornes
     */

    private int nbQuestions;
    /**
     * Nombre de question
     */

    /*TODO modifier pour l'associer � ordre d'apparition,
        calcul apparait disparait puis borne apparait
        OU
        bornes apparaissent disparaissent puis calcul apparait
     */
    private Boolean disparition;
    /**
     * Vrai : le premier element disparait, Faux : les deux elements s'affiche en meme temps
     */

    private long tempsRestantApparant;
    /**
     * temps pendant lequel le premier element affich� reste apparant dans le cas ou il disparait
     */

    private Boolean ordreApparition;
    /**
     * Vrai : calcul puis bornes, Faux : bornes puis calcul
     */

    private Boolean borneSelectionnable;
    /**
     * Vrai : la borne peut etre le resultat o� il faut cliquer, Faux : les bornes ne seront jamais egales aux resultats
     */

    private Boolean borneEqualsOp;
    /**
     * Vrai : une bornes est toujours egale � un op�rande � +/- distance , Faux : les bornes sont al�atoires
     */
    //TODO param�tre distance entre borne et op�rande dans le cas o� une borne doit etre proche d'un op�rande

    private int valMax;

    /**
     * Valeur maximale pr�sente dans l'exercice
     */
    //TODO ajouter valMin, valeur minimum pr�sente dans l'exercice

    private boolean frise;

    /**
     * boolean pour dire si l'exercice utilisera une frise ou des boutons
     */

    public ParamEm1()
    /**
     * Constructeur de param�tres par d�fault
     */
    {
        super();
        nbBornes = 3;
        nbQuestions = 5;
        disparition = true;
        tempsRestantApparant = 3000;
        ordreApparition = true;
        borneSelectionnable = false;
        borneEqualsOp = false;
        valMax = 30;
        frise = true;
    }

    public ParamEm1(String nom, boolean f, Long t, Boolean p, Boolean[] o, int nbb, int nbq, Boolean d, long tra, Boolean oa, Boolean bs, Boolean beo, int vm)
    /**
     * Constructeur de param�tres personalis�s
     */
    {
        super(t, p, o);
        this.nom=nom;
        frise = f;
        nbBornes = nbb;
        nbQuestions = nbq;
        disparition = d;
        tempsRestantApparant = tra;
        ordreApparition = oa;
        borneSelectionnable = bs;
        borneEqualsOp = beo;
        valMax = vm;
    }

    /**
     * @return nbBornes
     */
    public int getNbBornes() {
        return nbBornes;
    }

    /**
     * @return nbQuestions
     */
    public int getNbQuestions() {
        return nbQuestions;
    }

    /**
     * @return disparition
     */
    public Boolean getDisparition() {
        return disparition;
    }

    /**
     * @return tempsRestantApparant
     */
    public long getTempsRestantApparant() {
        return tempsRestantApparant;
    }

    /**
     * @return ordreApparition
     */
    public Boolean getOrdreApparition() {
        return ordreApparition;
    }

    /**
     * @return borneSelectionnable
     */
    public Boolean getBorneSelectionnable() {
        return borneSelectionnable;
    }

    /**
     * @return borneEqualsOp
     */
    public Boolean getBorneEqualsOp() {
        return borneEqualsOp;
    }

    /**
     * @return valMax
     */
    public int getValMax() {
        return valMax;
    }

    /**
     * @return frise
     */
    public boolean getFrise() {
        return frise;
    }
}
