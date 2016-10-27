package com.cf.domain;

/**
 * Classe abstraite representant une Personne dans le contexte du tp3.
 * Les personnes peuvent etre instancie en temps que Administratif ou Chercheur
 * @author canis
 *
 */
public abstract class Personne {

	
	/**
	 * id de la personne
	 */
	private Integer id;
	/**
	 * nom de la personne
	 */
	private String nom;
	/**
	 * numero de telephone de la personne
	 */
	private String numero;
	
	/**
	 * Represente l'id du bureau dans lequelle la personne est affecte. Ne pas modifier cette attribut excepte avec les methodes
	 * SMGestionnaireBureau.enleverPersonneBureau/affecterPersonneBureau
	 */
	private Integer idBureau;
	
	/**
	 * Constructeur de l'objet personne, sera appele dans les classe heritant de Personne
	 * @param nom nom de la personne
	 * @param numero numero de telephone de la personne
	 */
	public Personne( String nom, String numero) {
		super();
		this.id = null;
		this.nom = nom;
		this.numero = numero;
		idBureau = null;
	}
	
	/**
	 * Constructeur de l'objet personne, sera appele dans les classe heritant de Personne
	 * @param id id de la personne
	 * @param nom nom de la personne
	 * @param numero numero de telephone de la personne
	 */
	public Personne(Integer id, String nom, String numero) {
		super();
		this.id = id;
		this.nom = nom;
		this.numero = numero;
		idBureau = null;
	}

	/**
	 * Accesseur du champ id
	 * @return id de la personne
	 */
	public Integer getId() {
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
		this.nom = nom;
	}
	/**
	 * Accesseur du champ numero de telephone
	 * @return le numero de telephone de la personne
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * Accesseur du champ numero
	 * @param numero nouveau numero de telephone la personne
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Permet de changer le numero de la personne
	 * @param nouveauNumero nouveau numero de telephone la personne
	 */
	public void changerNumero(String nouveauNumero){
		this.setNumero(nouveauNumero);
	}

	/**
	 * Accesseur du champ idBureau
	 * @return l'ID du bureau
	 */
	public Integer getIdBureau() {
		return idBureau;
	}

	/**
	 * Permet de changer l'ID du bureau de la personne
	 * @param idBureau id du nouveau bureau de la personne
	 */
	public void setIdBureau(Integer idBureau) {
		this.idBureau = idBureau;
	}
	
	
}
