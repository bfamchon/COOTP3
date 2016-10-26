package com.cf.persistence;

import com.cf.domain.Administratif;
import com.cf.domain.Chercheur;
import com.cf.domain.Personne;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by baptiste on 22/10/16.
 * Class regroupant les méthodes d'extraction des mappers
 */
public class Extraction {
    /**
     * Permet d'extraire une personne d'une requete venant de Bureau Mapper
     * @param rs une ligne résultante de la requete
     */
    public static Personne extrairePersonneBureau(ResultSet rs) throws SQLException {
        Personne personne;
        int idPers = rs.getInt(3);
        String nomPers = rs.getString(4);
        String telPers = rs.getString(5);
        String domainePers = rs.getString(6);
        String qualifPers = rs.getString(7);
        String formPers = rs.getString(8);
        String typePers = rs.getString(9);

        if(typePers != null){
	        if (typePers.equals("Chercheur")) {
	            personne = new Chercheur(idPers,nomPers,telPers,domainePers);
	        } else {
	            personne = new Administratif(idPers,nomPers,telPers,qualifPers,formPers);
	        }
	        return personne;
        }
        return null;
    }
    /**
     * Permet d'extraire une personne d'une requete venant de PersonneMapper
     * @param rs une ligne résultante de la requete
     */
    public static Personne extrairePersonne(ResultSet rs) throws SQLException {
        Personne personne;
        int idPers = rs.getInt(1);
        String nomPers = rs.getString(2);
        String telPers = rs.getString(3);
        String domainePers = rs.getString(4);
        String qualifPers = rs.getString(5);
        String formPers = rs.getString(6);
        String typePers = rs.getString(7);

        if (typePers.equals("Chercheur")) {
            personne = new Chercheur(idPers,nomPers,telPers,domainePers);
        } else {
            personne = new Administratif(idPers,nomPers,telPers,qualifPers,formPers);
        }
        return personne;
    }
}
