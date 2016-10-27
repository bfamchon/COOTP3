package com.cf.domain;

/**
 * Extend de personne, représente une personne de type chercheur
 * @author canis
 * 
 */
public class Chercheur extends Personne {

	/**
	 * Représente le domaine de recherche du chercheur
	 */
	private String domaine;

	

	/**
	 * Constructeur de la personne chercheur, appel le constructeur de Personne
	 * @param nom
	 * @param numero
	 * @param domaine
	 */
	public Chercheur( String nom, String numero, String domaine) {
		super(nom, numero);
		this.domaine = domaine;
	}

	/**
	 * Constructeur de la personne chercheur, appel le constructeur de Personne
	 * @param id
	 * @param nom
	 * @param numero
	 * @param domaine
	 */
	public Chercheur(Integer id, String nom, String numero, String domaine) {
		super(id, nom, numero);
		this.domaine = domaine;
	}

	/**
	 * Accesseur du champ domaine
	 * @return retourne le champ domaine du chercheur
	 */
	public String getDomaine() {
		return domaine;
	}

	/**
	 * Accesseur du champ domaine
	 * @param domaine nouveau domaine du chercheur
	 */
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "Id : "+this.getId()+" / Nom : "+this.getNom()+" / Numero : "+
				this.getNumero()+" / Domaine :"+this.getDomaine();
	}
	
	
}
