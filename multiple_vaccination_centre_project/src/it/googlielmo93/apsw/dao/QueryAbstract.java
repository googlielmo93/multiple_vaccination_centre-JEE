package it.googlielmo93.apsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.googlielmo93.apsw.utility.ExceptionManager;

public abstract class QueryAbstract<T>{
	
	private DBManager dbManager;
	
	// COSTRUTTORE
	public QueryAbstract() {
		// creo la sola istanza del dbManager
		try {
			dbManager = DBManager.getInstance();
		}catch(ExceptionManager exc) {
			exc.setLogError("queryAbstract", "Errore durante la creazione dell'istanza a DBManager; Nei log precedenti sono descritti i dettagli", exc);
		}
	}
	
	
	protected Connection getConnection() throws SQLException {
		// genero la connessione al DB
		return dbManager.getConnection();
	}
	
	
	protected PreparedStatement prepareStatement(Connection conn, String queryId) throws SQLException {
		// preparo lo statement recuperandolo dalle properties in base al queryId; recupero la connessione
		return conn.prepareStatement(dbManager.getQuery(queryId));
	}
	
	
	protected  List<T> resultQuery(ResultSet rs) throws SQLException, ExceptionManager {
		// creo una lista di beans del tipo T con i dati della query
		ArrayList<T> beans = new ArrayList<>();
		while(rs.next())
		   beans.add(beanResult(rs));
		
		return beans;
	}
	
	
	protected String getQuery(String queryId) {
		// recupera la query in base al queryId usando il metodo della classe DBManager
		return dbManager.getQuery(queryId);
	}
	
	
	// metodo abstract da creare ad ogni estensione della classe
	protected abstract T beanResult(ResultSet rs) throws SQLException, ExceptionManager;
	
	
}