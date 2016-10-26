package com.cf.metier;

import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.BureauMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

public class SMGestionnaireBureau {

	private BureauMapper bureauMapper;

	
    public SMGestionnaireBureau() {
		super();
		this.bureauMapper =  new BureauMapper();
	}

	public List<Personne> listerPersonnesDansBureau(Bureau bureau) throws SQLException {
    		return bureau.getOccupants();
    }
    
    /**
     * Ajoute la personne passe en parametre au bureau passe en parametre. Si la personne n'existe pas en base la cree
     * Si le bureau n'existe pas en base, le cree
     * @param personne
     * @param bureau
     * @throws SQLException
     */
    public void affecterPersonneBureau(Personne personne,Bureau bureau) throws SQLException {
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

	private void insererOuModifierPersonne(Personne personne )
			throws SQLException {
    	SMGestionnairePersonne gestionnairePersonne = new SMGestionnairePersonne();
		//On vérifie que la personne que l'on souhaite ajouter existe en base sinon on le crée
		if(gestionnairePersonne.rechercherPersonneById(personne.getId())!= null)
			gestionnairePersonne.modifierPersonne(personne);
		else{
			gestionnairePersonne.ajouterPersonne(personne);
		}
	}
    public void enleverPersonneBureau(Personne personne,Bureau bureau) throws SQLException {
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
  
    public Bureau trouverBureau(Bureau bureau) throws SQLException {
        try {
        	return bureauMapper.findById(bureau.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
  
    public List<Bureau> ListerBureau() throws SQLException {
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
