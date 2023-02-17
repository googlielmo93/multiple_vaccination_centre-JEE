package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class PrenotazioneBean implements Serializable {
 
	 private String codicePrenotazione;
	 private Timestamp dataPrenotazione;
	 private Timestamp dataInserimento;
	 private int stato_completato;
	 private int idUser;
	 private int idCentroVaccinale;
	 
	 
	 //DEFAULT
	 public PrenotazioneBean() throws ExceptionManager{
		 this.codicePrenotazione = "";
		 this.dataPrenotazione = DateUtility.getCurrentTimestamp();
		 this.dataInserimento = dataPrenotazione;
		 this.stato_completato = 0;
		 this.idUser = -1; // un id negativo non � permesso nel db per il campo id_user ne per id_centro_vaccinale, verrebbe rifiutato un insert o un update pertanto
		 this.idCentroVaccinale = -1;
	}
	 
	
	public String getCodicePrenotazione() {
		return this.codicePrenotazione;
	}
	// non uso l'username perche' la prenotazione potrebbe essere fatta con un account temporaneo e l'username cambierebbe al primo accesso dell'utente
	public void setCodicePrenotazioneCF(String codiceFiscale) throws ExceptionManager {
		try {
			Integer codice = (Integer)(codiceFiscale + this.dataPrenotazione).hashCode();
			if(codice <0 ) 
				codice = codice * (-1);
			
			String codiceString = Integer.toString(codice);
			StringBuffer codiceBuff = new StringBuffer(codiceString);
			
			if(codiceString.length()%3 == 0) {
				for(int i = 1; i<=codiceString.length()/3; i++) {
					if(codiceString.length()-(3*i)==0)
						break;
					codiceBuff.insert(codiceString.length()-(3*i), "-").toString();
				}
			}
			else if(codiceString.length()%2 == 0)
				for(int i = 1; i<=codiceString.length()/2; i++) {
					if(codiceString.length()-(2*i)==0)
						break;
					codiceBuff.insert(codiceString.length()-(2*i), "-").toString();
				}
			
			this.codicePrenotazione = codiceBuff.toString();
			
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
	
	public boolean isEmpty(String value) {
		return (value.trim().equals(""));
	}

}