package com.cf.persistence;

import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Personne;

public class PersonneMapper implements InterfaceMapper<Personne> {
	private static final String SELECT_FROM_PERSONNE_WHERE_ID = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM PERSONNE b INNER JOIN personne p ON b.id_PERSONNE = p.id_PERSONNE WHERE b.id_PERSONNE=?";
	private static final String UPDATE_PERSONNE_SET_INFOS_WHERE_ID = "UPDATE PERSONNE SET nom=?,telephone=?,domaine=?,qualification=?,formation=?,typePersonne=? WHERE id_personne=?";
	private static final String SELECT_FROM_PERSONNE = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM PERSONNE b INNER JOIN personne p ON b.id_PERSONNE = p.id_PERSONNE";
	private static final String INSERT_INTO_PERSONNE_VALUES = "INSERT INTO PERSONNE VALUES(?,?,?,?,?,?,?)";
	private static final String DELETE_FROM_PERSONNE_WHERE_ID = "DELETE FROM PERSONNE WHERE id_personne = ?";
	private final int ID=0;

	@Override
	public void insert(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Personne> find() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
