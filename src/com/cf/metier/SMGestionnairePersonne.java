package com.cf.metier;

import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.InterfaceMapper;
import com.cf.persistence.PersonneMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

/**
 * Classe de gestion métier de la Personne
 */
public class SMGestionnairePersonne {
	/**
	 * personneMapper commun aux methodes de class
	 */
	private PersonneMapper pMapper;

	/**
	 * Constructeur du Gestionnaire de Personne
	 */
    public SMGestionnairePersonne() {
		super();
        this.pMapper = new PersonneMapper();
	}

	/**
	 * Change le numero de telephone de la personne en base et dans l'objet p
	 * @param p
	 * @param telephone
	 * @throws SQLException
	 */
	public void changerNumPersonne(Personne p,String telephone) throws SQLException {
        try {
            p.setNumero(telephone);
            pMapper.update(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Met à jour la personne p avec ses nouveaux parametres
	 * @param p
	 * @throws SQLException
	 */
	public void modifierPersonne(Personne p) throws SQLException {
        try {
            pMapper.update(p);
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Rechercher la personne par son ID
	 * @param idPersonne l'id de la personne recherchee
	 * @return la personne si elle est trouvee
	 * @throws SQLException
	 */
	public Personne rechercherPersonneById(Integer idPersonne) throws SQLException {
		try {
			return ((PersonneMapper) pMapper).findById(idPersonne);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Rechercher la personne par son numero de telephone, tout d'abord dans l'application puis en base.
	 * Si listBureau est null, la recherche sera uniquement effectue en base.
	 * @param telephone le numero de la personne recherchee
	 * @param listBureau la liste de bureaux dans laquelle on va rechercher la personne
	 * @return la personne si elle est trouvee
	 * @throws SQLException
	 */
    public Personne rechercherPersonneByTel(String telephone, List<Bureau> listBureau) throws SQLException {
        try {
        	if(listBureau != null){
	        	for(Bureau bureau : listBureau){
	        		List<Personne> listPersonne = bureau.getOccupants();
	        		for(Personne personne : listPersonne){
	        			if(telephone.equals(personne.getNumero())){
	        				return personne;
	        			}
	        		} 		
	        	}   	
        	}
	        return ((PersonneMapper) pMapper).findByNumTel(telephone); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * Ajouter une personne dans la base
	 * @param personne
	 * @throws SQLException
	 */
	public void ajouterPersonne(Personne personne) throws SQLException{
		try {
			this.pMapper.insert(personne);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    /* 
     * Ferme la connexion � la base de donnees si l'objet n'est plus utilise
     */
    @Override
    public void finalize(){
    	DBConfig.getInstance().fermerConnexion();
    }
}
