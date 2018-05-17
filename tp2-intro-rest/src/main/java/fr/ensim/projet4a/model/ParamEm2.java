package fr.ensim.projet4a.model;

public class ParamEm2 extends ParamMath{
	 private int typeRep;
	    /**
	     * type de r�ponse � donner par l'�l�ve
	     */

	    private int nbCalcul;
	    /**
	     * Nombre de question
	     */

	    private int valMaxOperande;

	    private Boolean nombrePair;
	    private Boolean nombreImpair;
	    private int typeNombre;


	    private Boolean repDeuxBornes;
	    private Boolean repQuatreBornes;
	    private Boolean repPaveNum;




	    public ParamEm2()
	    /**
	     * Constructeur de param�tres par d�fault
	     */
	    {
	        super();
	        typeRep = 0;
	        nbCalcul = 5;
	        nombrePair = true;
	        nombreImpair = true;
	        repDeuxBornes = false;
	        repQuatreBornes = false;
	        repPaveNum = true;
	        valMaxOperande = 10;
	        typeNombre = 0;
	    }

	    public ParamEm2(int typeRep,int nbCalcul,int valMaxOperande,boolean nombreImpair,boolean nombrePair,boolean repDeuxBornes,boolean repPaveNum,boolean repQuatreBornes)
	    /**
	     * Constructeur de param�tres personalis�s
	     */
	    {
	        super();
	        this.typeRep = typeRep;
	        this.nbCalcul = nbCalcul;
	        this.nombrePair = nombrePair;
	        this.nombreImpair = nombreImpair;
	        this.repDeuxBornes = repDeuxBornes;
	        this.repQuatreBornes = repQuatreBornes;
	        this.repPaveNum = repPaveNum;
	        this.valMaxOperande = valMaxOperande;
	        if (nombrePair){
	            this.typeNombre = 0;
	        }
	        if(nombreImpair){
	            this.typeNombre = 1;
	        }
	        if(nombreImpair && nombrePair){
	            this.typeNombre = 2;
	        }


	    }

	    /**
	     * @return nbBornes
	     */
	    public int gettypeRep() {
	        return typeRep;
	    }

	    public int getNbCalcul() {return nbCalcul;}

	    public int getValMaxOperande() {return valMaxOperande;}

	    public Boolean getNombrePair() {return nombrePair;}

	    public Boolean getNombreImpair() {return nombreImpair;}

	    public Boolean getRepDeuxBornes() {return repDeuxBornes;}

	    public Boolean getRepQuatreBornes() {return repQuatreBornes;}

	    public Boolean getRepPaveNum() {return repPaveNum;}

	    public int getTypeNombre(){return typeNombre;}

}
