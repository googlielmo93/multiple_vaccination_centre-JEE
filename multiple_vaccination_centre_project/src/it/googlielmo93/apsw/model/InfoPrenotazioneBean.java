package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class InfoPrenotazioneBean implements Serializable {
 
	 private String codicePrenotazione;
	 private Timestamp dataPrenotazione;
	 private Timestamp dataInserimento;
	 private int stato_completato;
	 private int idUser;
	 private int idCentroVaccinale;
	 
	 private int indexCentroVaccinale;
	 private String denominazione;
	 private String comune;
	 private String provincia;
	 private String regione;
	 private String indirizzo;
	 
	 
	 //DEFAULT
	 public InfoPrenotazioneBean() throws ExceptionManager{
		 this.codicePrenotazione = "";
		 this.dataPrenotazione = DateUtility.getCurrentTimestamp();
		 this.dataInserimento = dataPrenotazione;
		 this.setStatoCompletato(0);
		 this.idUser = -1; // un id negativo non � permesso nel db per il campo id_user ne per id_centro_vaccinale, verrebbe rifiutato un insert o un update pertanto
		 this.idCentroVaccinale = -1;
		 
		 this.denominazione = "";
		 this.comune = "";
		 this.provincia = "";
		 this.regione = "";
		 this.indirizzo = "";
	}
	 
	
	public String getCodicePrenotazione() {
		return this.codicePrenotazione;
	}
	
	// non uso l'username perche' la prenotazione potrebbe essere fatta con un account temporaneo e l'username cambierebbe al primo accesso dell'utente
	public void setCodicePrenotazioneCF(String codiceFiscale) throws ExceptionManager {
		try {
			this.codicePrenotazione = ((Integer)(codiceFiscale + this.dataPrenotazione).hashCode()).toString();
		}catch(Exception exc) {
			throw new ExceptionManager("PrenotazioneBean", "Errore generico Servlet PrenotazioneBean -> setCodicePrenotazione(String codiceFiscale). Eccezione lanciata: {0}", exc);
		}
	}
	public void setCodicePrenotazione(String codicePrenotazione) throws ExceptionManager {
		this.codicePrenotazione = codicePrenotazione;
	}
	
	
	public String getDataPrenotazione() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataPrenotazione, false);
	}
	public String getDataPrenotazioneDate() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataPrenotazione, true);
	}
	public String getDataPrenotazioneEN() throws ExceptionManager {
		return DateUtility.getFormattedDataENByTimestamp(this.dataPrenotazione);
	}
	public Timestamp getDataPrenotazioneTSSQL() throws ExceptionManager {
		 return this.dataPrenotazione;
	}
	public void setDataPrenotazione(Timestamp dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public void setDataPrenotazioneByString(String dataPrenotazione) throws ExceptionManager {
		this.dataPrenotazione = DateUtility.getTimestampByString(dataPrenotazione);
	}


	public String getDataInserimento() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataInserimento, false);
	}
	public String getDataInserimentoDate() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataInserimento, true);
	}
	public String getDataInserimentoEN() throws ExceptionManager {
		return DateUtility.getFormattedDataENByTimestamp(this.dataInserimento);
	}
	public Timestamp getDataInserimentoTSSQL() throws ExceptionManager {
		 Timestamp ts;
		 ts = this.dataInserimento;
		 return ts;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public void setDataInserimentoByString(String dataInserimento) throws ExceptionManager {
		this.dataInserimento = DateUtility.getTimestampByString(dataInserimento);
	}
	
	public int getStatoCompletato() {
		return stato_completato;
	}


	public void setStatoCompletato(int stato_completato) {
		this.stato_completato = stato_completato;
	}


	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public int getIdCentroVaccinale() {
		return idCentroVaccinale;
	}
	public void setIdCentroVaccinale(int idCentroVaccinale) {
		this.idCentroVaccinale = idCentroVaccinale;
	}
	

	public int getIndexCentroVaccinale() {
		return indexCentroVaccinale;
	}


	public void setIndexCentroVaccinale(int indexCentroVaccinale) {
		this.indexCentroVaccinale = indexCentroVaccinale;
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