package com.cf.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class représentant un bureau
 * @author canis
 */
public class Bureau {

	/**
	 * id du bureau
	 */
	private int id;
	/**
	 * description du bureau
	 */
	private String description;
	/**
	 * ArrayList de Personne représentant les occupants actuel du bureau 
	 */
	private List<Personne> occupants;

	
	
	public Bureau(int id, String description) {
		super();
		this.id = id;
		this.description = description;
		this.occupants = new ArrayList<Personne>();
	}

	public Bureau(int id, String description, List<Personne> occupants) {
		super();
		this.id = id;
		this.description = description;
		this.occupants = occupants;
	}

	/**
	 * Accesseur du champ id
	 * @return retourne l'id du bureau
	 */
	public int getId() {
		return id;
	}

	/**
	 * Accesseur du champ id
	 * @param id nouvelle id du bureau
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Accesseur du champ description
	 * @return description du bureau
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Accesseur du champ description
	 * @param description nouvelle description du bureau
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Accesseur du champ occupant
	 * @return retourne la liste des occupants du bureau
	 */
	public List<Personne> getOccupants() {
		return occupants;
	}

	/**
	 * Accesseur du champ occupant
	 * @param occupants nouvelle liste d'occupant
	 */
	public void setOccupants(List<Personne> occupants) {
		this.occupants = occupants;
	}

	/**
	 * Permet de retourner une liste des occupants du bureau
	 * @return la liste des occupants du bureau
	 */
	public List<Personne> listerOccupants() {
		return this.getOccupants();

	}

	/**
	 * ajoute un occupant dans le bureau
	 * @param nouveau nouvelle occupant du bureau
	 */
	public void ajouterOccupant(Personne nouveau) {
		this.occupants.add(nouveau);
	}

	/**
	 * retire un occupant du bureau
	 * @param idOccupants id de l'occupant que l'on souhaite retirer
	 */
	public void retirerOccupant(int idOccupants) {
		for (Personne occupant : occupants) {
			if (occupant.getId() == idOccupants) {
				this.occupants.remove(occupant);
			}
		}
	}

}