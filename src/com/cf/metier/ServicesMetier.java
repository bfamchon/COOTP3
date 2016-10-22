package com.cf.metier;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.BureauMapper;
import com.cf.persistence.InterfaceMapper;
import com.cf.persistence.PersonneMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by baptiste on 22/10/16.
 * Class regroupant les différents services métiers demandés
 */
public class ServicesMetier {
    public List<Personne> listerPersonnesDansBureau(int idBureau) throws SQLException {
        InterfaceMapper<Bureau> bMapper = new BureauMapper();
        try {
            return bMapper.findById(idBureau).getOccupants();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void affecterPersonneBureau(int idPersonne,int idBureau) throws SQLException {
        try {
            PersonneMapper personneMapper = new PersonneMapper();
            personneMapper.updateBureau(idPersonne,idBureau);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void enleverPersonneBureau(int idPersonne) throws SQLException {
        try {
            PersonneMapper personneMapper = new PersonneMapper();
            personneMapper.deleteBureau(idPersonne);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changerNumPersonne(Personne p,String telephone) throws SQLException {
        InterfaceMapper<Personne> pMapper = new PersonneMapper();
        try {
            p.setNumero(telephone);
            pMapper.update(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void chercherPersonne(String telephone) throws SQLException {
        InterfaceMapper<Personne> pMapper = new PersonneMapper();
//        try {
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
