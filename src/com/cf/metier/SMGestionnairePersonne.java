package com.cf.metier;

import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.InterfaceMapper;
import com.cf.persistence.PersonneMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

public class SMGestionnairePersonne {
	InterfaceMapper<Personne> pMapper;
	
    public SMGestionnairePersonne() {
		super();
        pMapper = new PersonneMapper();
	}
    
	public void changerNumPersonne(Personne p,String telephone) throws SQLException {
        try {
            p.setNumero(telephone);
            pMapper.update(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	/**
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
	
	public Personne rechercherPersonneById(Integer idPersonne) {
		try {
			return ((PersonneMapper) pMapper).findById(idPersonne);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public Personne rechercherPersonneByTel(String telephone, List<Bureau> listBureau) throws SQLException {
        try {
        	for(Bureau bureau : listBureau){
        		List<Personne> listPersonne = bureau.getOccupants();
        		for(Personne personne : listPersonne){
        			if(telephone.equals(personne.getNumero())){
        				return personne;
        			}
        		} 		
        	}   	
        	return ((PersonneMapper) pMapper).findByNumTel(telephone); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void finalize(){
    	DBConfig.getInstance().fermerConnexion();
    }

	public void ajouterPersonne(Personne personne) {
		try {
			this.pMapper.insert(personne);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
