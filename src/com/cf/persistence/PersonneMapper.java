package com.cf.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.cf.domain.Administratif;
import com.cf.domain.Chercheur;
import com.cf.domain.Personne;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

public class PersonneMapper implements InterfaceMapper<Personne> {
	private static final String SELECT_FROM_PERSONNE_WHERE_ID = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne" +
			"FROM personne p";
	private static final String UPDATE_PERSONNE_SET_INFOS_WHERE_ID = "UPDATE PERSONNE " +
			"SET nom=?,telephone=?,domaine=?,qualification=?,formation=?,typePersonne=?,id_bureau=? " +
			"WHERE id_personne=?";
	private static final String SELECT_FROM_PERSONNE = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne,p.id_bureau " +
			"FROM personne p";
	private static final String INSERT_INTO_PERSONNE_VALUES = "INSERT INTO PERSONNE VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE_FROM_PERSONNE_WHERE_ID = "DELETE FROM PERSONNE WHERE id_personne = ?";
	private int ID=0;

	@Override
	public void insert(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		String req = INSERT_INTO_PERSONNE_VALUES;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, ID);
		ps.setString(2, p.getNom());
		ps.setString(3, p.getNumero());

		// La personne est un chercheur
		if ( p.getClass().equals(Chercheur.class) ) {
			// Ajouter le champ domaine ?

			ps.setNull(5, Types.VARCHAR);
			ps.setNull(6,Types.VARCHAR);
			ps.setString(7,"Chercheur");
			// Ajouter le champ id_bureau ?

		} else {
			ps.setNull(4, Types.VARCHAR);
			// Ajouter le champ formation, qualification ?


			ps.setString(7,"Administratif");
			// Ajouter le champ id_bureau ?

		}
		ps.executeUpdate();
		ID++;
	}

	@Override
	public void delete(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		String req = DELETE_FROM_PERSONNE_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, p.getId());
		ps.executeUpdate();
	}

	@Override
	public void update(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Personne> find() throws SQLException {
		// TODO Auto-generated method stub
		String req = SELECT_FROM_PERSONNE;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ResultSet rs = ps.executeQuery();
		List<Personne> listPers = new ArrayList<Personne>();

		while(rs.next()) {
			int idBureau = rs.getInt(8);
			int idPers = rs.getInt(1);
			String nomPers = rs.getString(2);
			String telPers = rs.getString(3);
			String typePers = rs.getString(7);
			// Champ id_bureau à ajouter aux personnes ?

			if (typePers.equals("Chercheur")) {
				String domainePers = rs.getString(4);
				listPers.add(new Chercheur(idPers,nomPers,telPers,domainePers));
			} else {
				String qualifPers = rs.getString(5);
				String formationPers = rs.getString(6);
				listPers.add(new Administratif(idPers,nomPers,telPers,qualifPers,formationPers));
			}

		}

		return listPers;
	}

	@Override
	public Personne findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
