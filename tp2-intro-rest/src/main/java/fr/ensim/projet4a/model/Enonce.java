package fr.ensim.projet4a.model;

public class Enonce {
	private int idEnonce;	
	private String mot1;	
	private String mot2;
	private String mot3;
	private String mot4;
	private String mot5;
	private String mot6;
	public Enonce() {}
	public Enonce(String mot1, String mot2, String mot3, String mot4, String mot5, String mot6) {
		
		this.mot1 = mot1;
		this.mot2 = mot2;
		this.mot3 = mot3;
		this.mot4 = mot4;
		this.mot5 = mot5;
		this.mot6 = mot6;
	}
	public Enonce(int idEnonce, String mot1, String mot2, String mot3, String mot4, String mot5, String mot6) {
		this.idEnonce = idEnonce;
		this.mot1 = mot1;
		this.mot2 = mot2;
		this.mot3 = mot3;
		this.mot4 = mot4;
		this.mot5 = mot5;
		this.mot6 = mot6;
	}
	public int getIdEnonce() {
		return idEnonce;
	}
	public void setIdEnonce(int idEnonce) {
		this.idEnonce = idEnonce;
	}
	public String getMot1() {
		return mot1;
	}
	public void setMot1(String mot1) {
		this.mot1 = mot1;
	}
	public String getMot2() {
		return mot2;
	}
	public void setMot2(String mot2) {
		this.mot2 = mot2;
	}
	public String getMot3() {
		return mot3;
	}
	public void setMot3(String mot3) {
		this.mot3 = mot3;
	}
	public String getMot4() {
		return mot4;
	}
	public void setMot4(String mot4) {
		this.mot4 = mot4;
	}
	public String getMot5() {
		return mot5;
	}
	public void setMot5(String mot5) {
		this.mot5 = mot5;
	}
	public String getMot6() {
		return mot6;
	}
	public void setMot6(String mot6) {
		this.mot6 = mot6;
	}
	
}
