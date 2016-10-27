package com.cf.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.gestionnaireconnexion.DBConfig;

import static com.cf.persistence.Extraction.extrairePersonneBureau;

/**
 * Class représentante d'un mapper de bureau, avec un findJoinPersonne supplémentaire
 * pour avoir les bureaux ainsi que les occupants
 */
public class BureauMapper implements InterfaceMapper<Bureau>{
	/**
	 * Differentes constantes que nous utilisons pour nos requetes
	 */
	private static final String SELECT_FROM_BUREAU_WHERE_ID = "SELECT b.id_bureau, b.description, p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM bureau b INNER JOIN personne p ON b.id_bureau = p.id_bureau WHERE b.id_bureau=?";
	private static final String UPDATE_BUREAU_SET_DESCR_WHERE_ID = "UPDATE bureau SET description=? WHERE id_bureau=?";
	private static final String SELECT_FROM_BUREAU_JOIN_PERSONNE= "SELECT b.id_bureau, b.description, p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne FROM bureau b LEFT JOIN personne p ON b.id_bureau = p.id_bureau";
	private static final String SELECT_FROM_BUREAU= "SELECT b.id_bureau, b.description FROM bureau b";
	private static final String INSERT_INTO_BUREAU_VALUES = "INSERT INTO bureau VALUES(?,?)";
	private static final String DELETE_FROM_BUREAU_WHERE_ID = "DELETE FROM bureau WHERE id_bureau= ?";
	private static final String SEARCH_MAX_ID = "SELECT MAX(id_bureau) as maxid FROM bureau";

	/**
	 * On utilise une recherche d'ID max en base
	 * On considere que l'utilisateur n'a pas a gerer l'ID des objets
	 */
	public static int ID = chercherMAXID();


	/**
	 * M�thode permettant de rechercher l'id maximum disponible en base
	 * @return
	 */
	private static int chercherMAXID()  {
		String req = SEARCH_MAX_ID;
		PreparedStatement ps;
		try {
			ps = DBConfig.getInstance().getConn().prepareStatement(req);
			ResultSet rs = ps.executeQuery();
	//		Si il existe des données en base
			if(rs.next()) {
				return rs.getInt("maxid")+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Inserer un nouveau bureau
	 * @param b le bureau à insérer
	 */
	@Override
	public void insert(Bureau b) throws SQLException {
		String req = INSERT_INTO_BUREAU_VALUES;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, ID);
		ps.setString(2, b.getDescription());
		ps.executeUpdate();
		b.setId(ID);
		ID++;
	}

	/**
	 * Supprimer un bureau
	 * @param b le bureau à supprimer
	 */
	@Override
	public void delete(Bureau b) throws SQLException {
		String req = DELETE_FROM_BUREAU_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, b.getId());
		ps.executeUpdate();
	}

	/**
	 * Modifier un bureau
	 * @param b le bureau à modifier
	 */
	@Override
	public void update(Bureau b) throws SQLException {
		String req = UPDATE_BUREAU_SET_DESCR_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setString(1, b.getDescription());
		ps.setInt(2, b.getId());
		ps.executeUpdate();
	}

	/**
	 * Lister les bureaux existants avec ou sans les occupants
	 *
	 */
	@Override
	public List<Bureau> find() throws SQLException {
		String req = SELECT_FROM_BUREAU_JOIN_PERSONNE;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ResultSet rs = ps.executeQuery();

		List<Bureau> listBureau = new ArrayList<>();
		Bureau b = null;

		while(rs.next()) {
			int idBureau = rs.getInt(1);
			String descriptionBureau = rs.getString(2);
			Personne personne = extrairePersonneBureau(rs);
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
			if(personne != null){
				personne.setIdBureau(b.getId());
				b.ajouterOccupant(personne);
			}
		}

		// Si la liste est vide ( la jointure n'a pas pu être faite ), on essaye de lister les bureaux sans occupants
		if (listBureau.isEmpty()) {
			String req2 = SELECT_FROM_BUREAU;
			PreparedStatement ps2 = DBConfig.getInstance().getConn().prepareStatement(req2);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()) {
				int idBureau = rs2.getInt(1);
				String descriptionBureau = rs2.getString(2);
				listBureau.add(new Bureau(idBureau, descriptionBureau));
			}
		}
		return listBureau;
	}

	/**
	 * Récupérer le bureau demandé
	 * @param id l'id du bureau à récupérer
	 */
	@Override
	public Bureau findById(Integer id) throws SQLException {
		String req = SELECT_FROM_BUREAU_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Bureau b = null;
		
		if(rs.next()){		
			int idBureau = rs.getInt(1);
			String descriptionBureau = rs.getString(2);
			b = new Bureau(idBureau,descriptionBureau);
			
			Personne p = extrairePersonneBureau(rs);
			p.setIdBureau(idBureau);
			b.ajouterOccupant(p);
	
			while(rs.next()) {
				p = extrairePersonneBureau(rs);
				p.setIdBureau(idBureau);
				b.ajouterOccupant(p);
			}
		}
		return b;
	}
}
