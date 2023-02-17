package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class UserBean implements Serializable {
 
	 private int indexUser;
	 private String userName;
	 private String password;
	 private String ruolo;
	 private String nome;
	 private String cognome;
	 private String email;
	 private String sesso;
	 private String codiceFiscale;
	 private Timestamp dataNascita;
	 private Timestamp dataRegistrazione;
	 private String cittaResidenza;
	 private String provinciaResidenza;
	 private String regioneResidenza;
	 
	 
	 //DEFAULT
	 public UserBean() throws ExceptionManager{
		 this.indexUser = -1;
		 this.userName = "";
		 this.password = "";
		 this.ruolo = "user";
		 this.nome = "";
		 this.cognome = "";
		 this.email = "";
		 this.sesso = "N/D";
		 this.codiceFiscale = "";
		 this.dataNascita = DateUtility.getCurrentTimestamp();
		 this.dataRegistrazione = dataNascita;
		 this.cittaResidenza = "";
		 this.provinciaResidenza = "";
		 this.regioneResidenza = "";
	}
	 
	
	public void setIndexUser(int indexUser) {
		this.indexUser = indexUser;
	}
	public int getIndexUser() {
		return indexUser;
	}
	 
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {  // gia' convertita con la funzione hashcode
		return this.password;
	}
	public void setPassword(String password){	
		this.password = password;
	}
	public void setPasswordHashCode(String password) throws ExceptionManager {	// con la funzione hashcode crea la password dall'username e dalla password
		try {
			this.password = ((Integer)(this.getUserName() + password).hashCode()).toString();
		}catch(Exception exc) {
			throw new ExceptionManager("UserBean", "Errore generico Servlet UserBean -> setPasswordHashCode(String password). Eccezione lanciata: {0}", exc);
		}
	}
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSesso() {
		return this.sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
	public String getDataNascita() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataNascita, true);
	}
	public String getDataNascitaEN() throws ExceptionManager {
		return DateUtility.getFormattedDataENByTimestamp(this.dataNascita);
	}
	public Timestamp getDataNascitaTSSQL() throws ExceptionManager {
		 return this.dataNascita;
	}
	public void setDataNascitaTS(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setDataNascita(String dataNascita) throws ExceptionManager {
		this.dataNascita = DateUtility.getTimestampByString(dataNascita);
	}
	
	
	public String getDataRegistrazione() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataRegistrazione, false);
	}
	public Timestamp getDataRegistrazioneTSSQL() throws ExceptionManager {
		 return this.dataRegistrazione;
	}
	public void setDataRegistrazioneTS(Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public void setDataRegistrazione(String dataRegistrazione) throws ExceptionManager {
		this.dataNascita = DateUtility.getTimestampByString(dataRegistrazione);
	}
	

	public String getCittaResidenza() {
		return cittaResidenza;
	}
	public void setCittaResidenza(String cittaResidenza) {
		this.cittaResidenza = cittaResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getRegioneResidenza() {
		return regioneResidenza;
	}
	public void setRegioneResidenza(String regioneResidenza) {
		this.regioneResidenza = regioneResidenza;
	}
	
	public boolean isEmpty(String value) {
		return (value.trim().equals(""));
	}

}