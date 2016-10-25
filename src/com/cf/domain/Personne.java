package com.cf.domain;

/**
 * Classe abstraite représentant une Personne dans le contexte du tp3.
 * Les personnes peuvent être instancié en temps que Administratif ou Chercheur
 * @author canis
 *
 */
public abstract class Personne {

	
	/**
	 * id de la personne
	 */
	private int id;
	/**
	 * nom de la personne
	 */
	private String nom;
	/**
	 * numero de téléphone de la personne
	 */
	private String numero;
	
	/**
	 * Repr�sente l'id du bureau dans lequelle la personne est affect�. Ne pas modifier cette attribut except� avec les m�thodes 
	 * SMGestionnaireBureau.enleverPersonneBureau/affecterPersonneBureau
	 */
	private Integer idBureau;
	
	/**
	 * Constructeur de l'objet personne, sera appelé dans les classe héritant de Personne
	 * @param id id de la personne
	 * @param nom nom de la personne
	 * @param numero numero de téléphone de la personne
	 */
	public Personne(int id, String nom, String numero) {
		super();
		this.id = id;
		nom = nom;
		numero = numero;
	}
	


	/**
	 * Accesseur du champ id
	 * @return id de la personne
	 */
	public int getId() {
		return id;
	}
	/**
	 * Accesseur du champ id
	 * @param id nouvelle id de la personne
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Accesseur du champ nom
	 * @return le nom de la personne
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Accesseur du champ nom
	 * @param nom nouveau nom de la personne
	 */
	public void setNom(String nom) {
		nom = nom;
	}
	/**
	 * Accesseur du champ numero de téléphone
	 * @return le numero de téléphone de la personne
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * Accesseur du champ numero
	 * @param numero nouveau numero de téléphone la personne
	 */
	public void setNumero(String numero) {
		numero = numero;
	}
	
	
	/**
	 * Permet de changer le numéro de la personne
	 * @param nouveauNumero nouveau numero de téléphone la personne
	 */
	public void changerNumero(String nouveauNumero){
		this.setNumero(nouveauNumero);
	}



	public Integer getIdBureau() {
		return idBureau;
	}



	public void setIdBureau(Integer idBureau) {
		this.idBureau = idBureau;
	}
	
	
}
