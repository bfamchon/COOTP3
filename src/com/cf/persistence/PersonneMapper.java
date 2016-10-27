package com.cf.persistence;

import static com.cf.persistence.Extraction.extrairePersonne;

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

/**
 * Created by baptiste on 22/10/16.
 * Class représentante d'un mapper de personne
 */
public class PersonneMapper implements InterfaceMapper<Personne> {

	/**
	 * Differentes constantes que nous utilisons pour nos requetes
	 */
	private static final String SELECT_FROM_PERSONNE_WHERE_ID = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne " +
			"FROM personne p " +
			"WHERE p.id_personne=?";
	private static final String SELECT_FROM_PERSONNE_WHERE_NUMTEL = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne " +
			"FROM personne p " +
			"WHERE p.telephone=?";
	private static final String UPDATE_PERSONNE_SET_INFOS_WHERE_ID = "UPDATE personne " +
			"SET nom=?,telephone=?,domaine=?,qualification=?,formation=?,typePersonne=?, id_bureau=? " +
			"WHERE id_personne=?";
	private static final String SELECT_FROM_PERSONNE = "SELECT p.id_personne, p.nom, p.telephone,p.domaine,p.qualification,p.formation,p.typePersonne " +
			"FROM personne p";
	private static final String INSERT_INTO_PERSONNE_VALUES = "INSERT INTO personne VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE_FROM_PERSONNE_WHERE_ID = "DELETE FROM personne WHERE id_personne=?";
	private static final String SEARCH_MAX_ID = "SELECT MAX(id_personne) as maxid FROM personne";

	/**
	 * On utilise une recherche d'ID max en base
	 * On considere que l'utilisateur n'a pas a gerer l'ID des objets
	 */
	public static int ID = chercherMAXID();

	private static int chercherMAXID() {
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
	 * Inserer une nouvelle personne ( Chercheur ou Administratif )
	 * @param p la personne à insérer
	 */
	@Override
	public void insert(Personne p)
			throws SQLException {
		String req = INSERT_INTO_PERSONNE_VALUES;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1, ID);
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
		p.setId(ID);
		ID++;
	}
	/**
	 * Supprimer une personne
	 * @param p la personne à supprimer
	 */
	@Override
	public void delete(Personne p)
			throws SQLException {
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
	public void update(Personne p)
			throws SQLException {
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
		if(p.getIdBureau() != null)
			ps.setInt(7, p.getIdBureau());
		else
			ps.setNull(7, Types.INTEGER);
		ps.setInt(8,p.getId());
		ps.executeUpdate();
	}
	/**
	 * Lister les personnes
	 * @return la liste des personnes trouvees
	 */
	@Override
	public List<Personne> find()
			throws SQLException {
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
	 * @return la personne trouvee
	 */
	@Override
	public Personne findById(Integer id)
			throws SQLException {
		String req = SELECT_FROM_PERSONNE_WHERE_ID;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setInt(1,id);
		ResultSet rs = ps.executeQuery();
//		Si on a au moins une ligne en retour
		if(rs.next())
			return extrairePersonne(rs);
		return null;
	}

	/**
	 * Recuperer une personne à partir de son numero de telephone
	 * @param numeroTel le telephone de la personne à chercher
	 * @return la personne trouvee
	 */
	public Personne findByNumTel(String numeroTel)
			throws SQLException {
		String req = SELECT_FROM_PERSONNE_WHERE_NUMTEL;
		PreparedStatement ps = DBConfig.getInstance().getConn().prepareStatement(req);
		ps.setString(1,numeroTel);
		ResultSet rs = ps.executeQuery();
//		Si on a au moins une ligne en retour
		if(rs.next())
			return extrairePersonne(rs);
		return null;
	}

}
