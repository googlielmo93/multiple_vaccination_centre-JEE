package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class InfoSomministrazioneBean implements Serializable {
 
	 private int id_somministrazione;
	 private String tipoVaccino;
	 private String codiceVaccino;
	 private String lotto;
	 private Timestamp dataSomministrazione;
	 private String codicePrenotazione;
	 private int idUser;
	 private int idMedico;
	 private int idCentroVaccinale;
	 
	 private String denominazione;
	 private String comune;
	 private String provincia;
	 private String regione;
	 private String indirizzo;
	 
	 
	 //DEFAULT
	 public InfoSomministrazioneBean() throws ExceptionManager{
		 
		 this.tipoVaccino = "";
		 this.codiceVaccino = "";
		 this.lotto = "";
		 this.dataSomministrazione = DateUtility.getCurrentTimestamp();
		 this.codicePrenotazione = "";
		 this.idUser = -1;	// un id negativo non � permesso nel db per il campo id_user ne per id_centro_vaccinale, verrebbe rifiutato un insert o un update pertanto
		 this.idMedico = -1; 
		 this.idCentroVaccinale = -1;
		 
		 this.denominazione = "";
		 this.comune = "";
		 this.provincia = "";
		 this.regione = "";
		 this.indirizzo = "";
		 
	}
	 
	 
	public int getIdSomministrazione() {
		return this.id_somministrazione;
	}
	public void setIdSomministrazione(int id_somministrazione) {
		this.id_somministrazione = id_somministrazione;
		
	}
	
	public String getCodicePrenotazione() {
		return this.codicePrenotazione;
	}
	
	// non uso l'username perche' la prenotazione potrebbe essere fatta con un account temporaneo e l'username cambierebbe al primo accesso dell'utente
	public void setCodicePrenotazioneCF(String codiceFiscale) throws ExceptionManager {
		try {
			this.codicePrenotazione = ((Integer)(codiceFiscale + this.dataSomministrazione).hashCode()).toString();
		}catch(Exception exc) {
			throw new ExceptionManager("PrenotazioneBean", "Errore generico Servlet PrenotazioneBean -> setCodicePrenotazione(String codiceFiscale). Eccezione lanciata: {0}", exc);
		}
	}
	public void setCodicePrenotazione(String codicePrenotazione) throws ExceptionManager {
		this.codicePrenotazione = codicePrenotazione;
	}
	
	
	public String getDataSomministrazione() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataSomministrazione, false);
	}
	public String getDataSomministrazioneEN() throws ExceptionManager {
		return DateUtility.getFormattedDataENByTimestamp(this.dataSomministrazione);
	}
	public Timestamp getDataSomministrazioneTSSQL() throws ExceptionManager {
		 return this.dataSomministrazione;
	}
	public void setDataSomministrazione(Timestamp dataSomministrazione) {
		this.dataSomministrazione = dataSomministrazione;
	}
	public void setDataSomministrazioneByString(String dataSomministrazione) throws ExceptionManager {
		this.dataSomministrazione = DateUtility.getTimestampByString(dataSomministrazione);
	}

	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public int getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}
	
	public int getIdCentroVaccinale() {
		return idCentroVaccinale;
	}
	public void setIdCentroVaccinale(int idCentroVaccinale) {
		this.idCentroVaccinale = idCentroVaccinale;
	}
	
	public String getLotto() {
		return lotto;
	}
	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getTipoVaccino() {
		return tipoVaccino;
	}
	public void setTipoVaccino(String tipoVaccino) {
		this.tipoVaccino = tipoVaccino;
	}
	
	public String getCodiceVaccino() {
		return this.codiceVaccino;
	}
	public void setCodiceVaccino(String codiceVaccino) {
		this.codiceVaccino = codiceVaccino;
	}
	

	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getRegione() {
		return regione;
	}


	public void setRegione(String regione) {
		this.regione = regione;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public boolean isEmpty(String value) {
		return (value.trim().equals(""));
	}

}