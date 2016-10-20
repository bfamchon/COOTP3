package com.cf.persistence.gestionnaireconnexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cf.constante.Constante;

public class DBConfig {

	private static DBConfig instance;
	private Connection conn;

	
	public static DBConfig getInstance(){
	      if(instance == null) {
	          instance = new DBConfig();
	       }
	       return instance;
	}
	
	public Connection getConn(){
		try {
			if(conn==null || conn.isClosed()){
					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG);
//					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG_BAPTISTE);
// 					conn = DriverManager.getConnection(Constante.CONNEXION_CONFIG_LAURENT);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public void fermerConnexion(){
		try {
			this.conn.close();
			this.conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
