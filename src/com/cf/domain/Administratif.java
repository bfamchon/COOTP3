package com.cf.domain;

/**
 * Extend de personne, représente une personne de type administratif
 * @author canis
 *
 */
public class Administratif extends Personne {

	/**
	 * Qualification de la personne administratif
	 */
	private String qualification;
	/**
	 * Formation de la personne administratif
	 */
	private String formation;
	
	/**
	 * Constructeur de la personne Administratif, appel le constructeur de Personne
	 * @param id
	 * @param nom
	 * @param numero
	 * @param qualification
	 * @param formation
	 */
	public Administratif(int id, String nom, String numero,String qualification,String formation) {
		super(id, nom, numero);
		this.qualification = qualification;
		this.formation = formation;
	}
	/**
	 * Accesseur du champ qualification
	 * @return la qualification de la personne
	 */
	public String getQualification() {
		return qualification;
	}
	/**
	 * Accesseur du champ qualification
	 * @param qualification la nouvelle qualification de la personne
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	/**
	 * Accesseur du champ formation
	 * @return le champ formation
	 */
	public String getFormation() {
		return formation;
	}
	/**
	 * Accesseur du champ formation
	 * @param formation nouvelle formation de la personne
	 */
	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "Id : "+this.getId()+" / Nom : "+this.getNom()+" / Numero : "+
					this.getNumero()+" / Qualification : "+this.getQualification()+ " / Formation :" +
					this.getFormation();
		 
	}
	
	
}
