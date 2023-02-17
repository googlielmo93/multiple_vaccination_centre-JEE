package it.googlielmo93.apsw.utility;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionManager extends Exception {
	/**
	 * Gestore delle eccezioni custom
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionManager( String nomeClasse, String message, Throwable exc) {
        this.setLogError(nomeClasse, message, exc);
	}
	
	public ExceptionManager( String nomeClasse, String message) {
        this.setLogError(nomeClasse, message);
	}
	
	public void setLogError(String nomeClasse, String message, Throwable exc) {
		Logger.getLogger(nomeClasse).log(Level.SEVERE, message, exc );
	}
	
	public void setLogError(String nomeClasse, String message) {
		Logger.getLogger(nomeClasse).log(Level.SEVERE, message );
	}
}
