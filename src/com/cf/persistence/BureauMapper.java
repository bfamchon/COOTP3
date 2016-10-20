package com.cf.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cf.domain.Administratif;
import com.cf.domain.Bureau;
import com.cf.domain.Chercheur;
import com.cf.domain.Personne;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

public class BureauMapper implements InterfaceMapper<Bureau>{
	private static final String SELECT_FROM_BUREAU_WHERE_ID = "SELECT b.id_bureau, b.description, p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM bureau b INNER JOIN personne p ON b.id_bureau = p.id_bureau WHERE b.id_bureau=?";
	private static final String UPDATE_BUREAU_SET_DESCR_WHERE_ID = "UPDATE bureau SET descr=? WHERE id_bureau=?";
	private static final String SELECT_FROM_BUREAU = "SELECT b.id_bureau, b.description, p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM bureau b INNER JOIN personne p ON b.id_bureau = p.id_bureau";
	private static final String INSERT_INTO_BUREAU_VALUES = "INSERT INTO bureau VALUES(?,?)";
	private static final String DELETE_FROM_BUREAU_WHERE_ID = "DELETE FROM bureau WHERE id_bureau= ?";
	private int ID=0;
	
	@Override
	public void insert(Bureau b) throws SQLException {
		String req = INSERT_INTO_BUREAU_VALUES;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, ID);
		ps.setString(2, b.getDescription());
		ps.executeUpdate();
		ID++;
	}
	@Override
	public void delete(Bureau b) throws SQLException {
		String req = DELETE_FROM_BUREAU_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, b.getId());
		ps.executeUpdate();
	}
	@Override
	public void update(Bureau b) throws SQLException {
		String req = UPDATE_BUREAU_SET_DESCR_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setString(1, b.getDescription());
		ps.setInt(2, ID);
		ps.executeUpdate();
	}
	@Override
	public List<Bureau> find() throws SQLException {
		String req = SELECT_FROM_BUREAU;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ResultSet rs = ps.executeQuery();
		List<Bureau> listBureau = new ArrayList<Bureau>();
		Bureau b = null;
		while(rs.next()) {
			int idBureau = rs.getInt(1);
			String descriptionBureau = rs.getString(2);
			Personne personne = extrairePersonne(rs, b);
			boolean flag = false;
			for(Bureau bureau : listBureau){
				if(bureau.getId()==idBureau){
					flag = true;
					b=bureau;
					break;
				}
			}
			if(!flag){
				b= new Bureau(idBureau, descriptionBureau);
				listBureau.add(b);
			}
			b.ajouterOccupant(personne);
		}
		
		return listBureau;
	}
	@Override
	public Bureau findById(int id) throws SQLException {
		String req = SELECT_FROM_BUREAU_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Bureau b = null;
		
		if(rs.next()){		
			int idBureau = rs.getInt(1);
			String descriptionBureau = rs.getString(2);
			b = new Bureau(idBureau,descriptionBureau);
			
			b.ajouterOccupant(extrairePersonne(rs, b));
	
			while(rs.next()) {
				extrairePersonne(rs, b);
				b.ajouterOccupant(extrairePersonne(rs, b));
			}
		}
		return b;
	}
	private Personne extrairePersonne(ResultSet rs, Bureau b) throws SQLException {
		Personne personne;
		int idPers = rs.getInt(3);
		String nomPers = rs.getString(4);
		String telPers = rs.getString(5);
		String domainePers = rs.getString(6);
		String qualifPers = rs.getString(7);
		String formPers = rs.getString(8);
		String typePers = rs.getString(9);
		
		if (typePers.equals("Chercheur")) {
			personne = new Chercheur(idPers,nomPers,telPers,domainePers);
		} else {
			personne = new Administratif(idPers,nomPers,telPers,qualifPers,formPers);
		}
		return personne;
	}
}
