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

import static com.cf.persistence.Extraction.extrairePersonne;
/**
 * Created by baptiste on 22/10/16.
 * Class représentante d'un mapper de personne
 */
public class PersonneMapper implements InterfaceMapper<Personne> {
	private static final String SELECT_FROM_PERSONNE_WHERE_ID = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne " +
			"FROM personne p " +
			"WHERE p.id_personne=?";
	private static final String UPDATE_PERSONNE_SET_INFOS_WHERE_ID = "UPDATE personne " +
			"SET nom=?,telephone=?,domaine=?,qualification=?,formation=?,typePersonne=? " +
			"WHERE id_personne=?";
	private static final String SELECT_FROM_PERSONNE = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne " +
			"FROM personne p";
	private static final String INSERT_INTO_PERSONNE_VALUES = "INSERT INTO personne VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE_FROM_PERSONNE_WHERE_ID = "DELETE FROM personne WHERE id_personne=?";
	private static final String UPDATE_BUREAU = "UPDATE personne " +
			"SET id_bureau=? " +
			"WHERE id_personne=?";
	private static final String SEARCH_MAX_ID = "SELECT MAX(id_personne) as maxid FROM personne";

	/**
	 * Problème ID: si la BD contient des valeurs ( id 0,1,2,3... ), que l'on souhaite insérer un id de 999
	 * Ici, on se base sur le param ID initialisé à 0
	 * Ça lance des exceptions ( logique ) car l'ID exsite déjà.
	 * Solution simple: se baser que sur l'ID de l'objet en paramètre > l'ID dans les mappers ne sert à rien
	 * Pas dérangeant pour notre sujet, 2/3 insertions...
	 *
	 * Solution + chiante: CHECK validité de l'ID en base > Si valide: ID de l'objet Personne,Sinon: MAX(ID)+1
	 * Plus éfficace et automatique
	 */
	private static int ID;

//	private int chercherMAXID() throws SQLException{
//		String req = SEARCH_MAX_ID;
//		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
//		ResultSet rs = ps.executeQuery();
////		Si il existe des données en base
//		if(rs.next()) {
//			return rs.getInt("maxid")+1;
//		}
//		return 0;
//	}
	/**
	 * Inserer une nouvelle personne
	 * @param p la personne à insérer
	 */
	@Override
	public void insert(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		String req = INSERT_INTO_PERSONNE_VALUES;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, p.getId());
		ps.setString(2, p.getNom());
		ps.setString(3, p.getNumero());

		if ( p instanceof Chercheur ) {
			ps.setString(4,((Chercheur) p).getDomaine());
			ps.setNull(5, Types.VARCHAR);
			ps.setNull(6,Types.VARCHAR);
			ps.setString(7,"Chercheur");
		} else {
			ps.setNull(4, Types.VARCHAR);
			ps.setString(5,((Administratif) p).getFormation());
			ps.setString(6,((Administratif) p).getQualification());
			ps.setString(7,"Administratif");
		}
		ps.setNull(8,Types.INTEGER);
		ps.executeUpdate();
		ID++;
	}
	/**
	 * Supprimer une personne
	 * @param p la personne à supprimer
	 */
	@Override
	public void delete(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		String req = DELETE_FROM_PERSONNE_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, p.getId());
		ps.executeUpdate();
	}
	/**
	 * Modifier une personne
	 * @param p la personne à modifier
	 */
	@Override
	public void update(Personne p) throws SQLException {
		// TODO Auto-generated method stub
		String req = UPDATE_PERSONNE_SET_INFOS_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setString(1, p.getNom());
		ps.setString(2, p.getNumero());
		if ( p instanceof Chercheur ) {
			ps.setString(3,((Chercheur)p).getDomaine());
			ps.setNull(4,Types.VARCHAR);
			ps.setNull(5,Types.VARCHAR);
			ps.setString(6,"Chercheur");
		} else {
			ps.setNull(3,Types.VARCHAR);
			ps.setString(4,((Administratif)p).getQualification());
			ps.setString(5,((Administratif)p).getFormation());
			ps.setString(6,"Administratif");
		}
		ps.setInt(7,p.getId());
		ps.executeUpdate();
	}
	/**
	 * Lister les personnes
	 */
	@Override
	public List<Personne> find() throws SQLException {
		// TODO Auto-generated method stub
		String req = SELECT_FROM_PERSONNE;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ResultSet rs = ps.executeQuery();
		List<Personne> listPers = new ArrayList<>();

		while(rs.next()) {
			int idPers = rs.getInt(1);
			String nomPers = rs.getString(2);
			String telPers = rs.getString(3);
			String typePers = rs.getString(7);

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
	/**
	 * Récupérer une personne
	 * @param id de la personne à récupérer
	 */
	@Override
	public Personne findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		String req = SELECT_FROM_PERSONNE_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
//		Si on a au moins une ligne en retour
		if(rs.next())
			return extrairePersonne(rs);
		return null;
	}
	public void deleteBureau(int idPersonne) throws SQLException {
		String req=UPDATE_BUREAU;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setNull(1, Types.INTEGER);
		ps.setInt(2, idPersonne);
		ps.executeUpdate();
	}
	public void updateBureau(int idPersonne,int idBureau) throws SQLException {
		String req=UPDATE_BUREAU;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, idBureau);
		ps.setInt(2, idPersonne);
		ps.executeUpdate();
	}
}
