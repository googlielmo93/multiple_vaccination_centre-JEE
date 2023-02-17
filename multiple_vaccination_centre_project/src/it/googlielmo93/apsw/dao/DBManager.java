package it.googlielmo93.apsw.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import it.googlielmo93.apsw.utility.ExceptionManager;

public class DBManager {
	
	// verr� settata dal metodo statico getInstance
	private static DBManager dbManager;
	
	private Properties queryProperties;
	private Context ctx = null;
	private DataSource ds = null;
	
	private DBManager() throws ExceptionManager{
		
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/googlielmo93");
			
			try(
				InputStream queryPropFile = getClass().getResourceAsStream("query.properties");
			){
				queryProperties = new Properties();
				queryProperties.load(queryPropFile);
				
			} catch (IOException exc) {
				throw new ExceptionManager("DBManager", "Errore IOException Servlet DBManager -> Costruttore -> apertura file stream query. Eccezione lanciata: {0}", exc);
			}
			
		} catch (NamingException exc) {
			throw new ExceptionManager("DBManager", "Errore NamingException Servlet DBManager -> Costruttore -> lookup parametro: nome file non trovato. Eccezione lanciata: {0}", exc);
		} catch (Exception exc) {
			throw new ExceptionManager("DBManager", "Errore generico Servlet DBManager -> Costruttore. Eccezione lanciata: {0}", exc);
		}
	
	}
	
	// ritorna l�unica istanza della classe sulla quale � possibile invocare il metodo getConnection per ottenere una connessione
	public static DBManager getInstance() throws ExceptionManager {
		// dbManager � una variabile statica se � stata creata una istanza non ne crea di nuove finch� non verr� eliminata e quindi
		// il ref ad essa non sar� pi� valido e diventer� null.
		if (dbManager == null)
			dbManager = new DBManager();
		return dbManager;
	}
	
	// crea una nuova connessione
    public Connection getConnection() throws SQLException{
    	return ds.getConnection();
    }
    
    // per recuperare la stringa di una query.
	public String getQuery(String queryId) {
		return queryProperties.getProperty(queryId);
	}
}