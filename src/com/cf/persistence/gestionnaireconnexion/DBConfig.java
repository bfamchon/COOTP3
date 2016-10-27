package com.cf.persistence.gestionnaireconnexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cf.constante.Constante;

/**
 * Class pour le parametrage de l'acces à notre base
 *
 */
public class DBConfig {

	private static DBConfig instance;
	private Connection conn;

	/**
	 * Singleton utilise pour qu'une instance unique de la classe soit creer
	 */
	public static DBConfig getInstance(){
	      if(instance == null) {
	          instance = new DBConfig();
	       }
	       return instance;
	}

	/**
	 * Initialise la connexion si null ou fermee ( penser à changer la config dans la class Constante )
	 */
	public Connection getConn(){
		try {
			if(conn==null || conn.isClosed()){
//					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG);
//					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG_BAPTISTE);
					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG_LAURENT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Rompre la connexion avec la base, appelee dans les methodes finalize ( mappers )
	 */
	public void fermerConnexion(){
		try {
			this.conn.close();
			this.conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
