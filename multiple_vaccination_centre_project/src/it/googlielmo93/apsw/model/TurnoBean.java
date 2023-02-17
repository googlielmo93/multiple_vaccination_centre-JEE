package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class TurnoBean implements Serializable {
 
	private int idTurno;
	private String codicePrenotazione;
	private int idCentroVaccinale;
	private Timestamp dataInserimento;
	 
	 
	//DEFAULT
	public TurnoBean() throws ExceptionManager{
		this.idTurno = -1;
		this.codicePrenotazione = "";
		this.idCentroVaccinale = -1;
		this.dataInserimento = DateUtility.getCurrentTimestamp();
	}
	
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public String getCodicePrenotazione() {
		return this.codicePrenotazione;
	}
	public void setCodicePrenotazione(String codicePrenotazione) throws ExceptionManager {
		this.codicePrenotazione = codicePrenotazione;
	}
	
	public int getIdCentroVaccinale() {
		return idCentroVaccinale;
	}
	public void setIdCentroVaccinale(int idCentroVaccinale) {
		this.idCentroVaccinale = idCentroVaccinale;
	}
	
	//data e ora
	public String getDataInserimento() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.dataInserimento, false);
	}
	//solo la data
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
	
	public boolean isEmpty(String value) {
		return (value.trim().equals(""));
	}

}