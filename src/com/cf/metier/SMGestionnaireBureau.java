package com.cf.metier;

import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.BureauMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

/**
 * Classe de gestion metier du Bureau
 */
public class SMGestionnaireBureau {
	/**
	 * bureauMapper commun aux methodes de class
	 */
	private BureauMapper bureauMapper;

	/**
	 * Constructeur du Gestionnaire de Bureau
	 */
    public SMGestionnaireBureau() {
		super();
		this.bureauMapper =  new BureauMapper();
	}

	/**
	 * Constructeur de l'objet personne, sera appelé dans les classe héritant de Personne
	 * @param bureau le bureau duquel on liste les personnes
	 * @return la liste des personnes
	 * @throws SQLException
	 */
	public List<Personne> listerPersonnesDansBureau(Bureau bureau)
			throws SQLException {
    		return bureau.getOccupants();
    }
    
    /**
     * Ajoute la personne passe en parametre au bureau passe en parametre. Si la personne n'existe pas en base la cree
     * Si le bureau n'existe pas en base, le cree
     * @param personne
     * @param bureau
     * @throws SQLException
     */
    public void affecterPersonneBureau(Personne personne,Bureau bureau)
			throws SQLException {
        try {
        	if(bureau.getId() != null && trouverBureau(bureau) != null){
            	personne.setIdBureau(bureau.getId());
	        	bureauMapper.update(bureau);
	        	bureau.ajouterOccupant(personne);
	        	insererOuModifierPersonne(personne);
        	}else{
        		bureauMapper.insert(bureau);
            	personne.setIdBureau(bureau.getId());
            	bureau.ajouterOccupant(personne);
            	for(Personne p : bureau.getOccupants()){
    	        	insererOuModifierPersonne(p);
            	}
        	} 	
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Inserer ou modifier une personne en base
	 * Si la personne n'existe pas, elle est creee. Sinon, on la met a jour
	 * @param personne
	 * @throws SQLException
	 */
	private void insererOuModifierPersonne(Personne personne )
			throws SQLException {
    	SMGestionnairePersonne gestionnairePersonne = new SMGestionnairePersonne();
		//On verifie que la personne que l'on souhaite ajouter existe en base sinon on le cree
		if(gestionnairePersonne.rechercherPersonneById(personne.getId())!= null)
			gestionnairePersonne.modifierPersonne(personne);
		else{
			gestionnairePersonne.ajouterPersonne(personne);
		}
	}

	/**
	 * Enlever une personne d'un bureau en base et dans les objets
	 * On test d'abord si la personne a pu etre retirer en objet ( si elle existe bien )
	 * @param personne
	 * @param bureau
	 * @throws SQLException
	 */
    public void enleverPersonneBureau(Personne personne,Bureau bureau)
			throws SQLException {
        try {
        	SMGestionnairePersonne gestionnairePersonne = new SMGestionnairePersonne();
        	if(bureau.retirerOccupant(personne.getId())){
	        	personne.setIdBureau(null);
	        	gestionnairePersonne.modifierPersonne(personne);
	        	bureauMapper.update(bureau);
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	/**
	 * On cherche le bureau passe en parametre
	 * @param bureau
	 * @return le bureau trouve
	 * @throws SQLException
	 */
    public Bureau trouverBureau(Bureau bureau)
			throws SQLException {
        try {
        	return bureauMapper.findById(bureau.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * On liste les bureaux
	 * @return la liste des bureaux
	 * @throws SQLException
	 */
    public List<Bureau> ListerBureau()
			throws SQLException {
        try {
        	return bureauMapper.find();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
  
    
    @Override
    public void finalize(){
    	DBConfig.getInstance().fermerConnexion();
    }
}
