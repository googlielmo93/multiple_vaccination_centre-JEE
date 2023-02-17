package it.googlielmo93.apsw.model;

import java.io.Serializable;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class CentroVaccinaleBean implements Serializable {
 
	private int indexCentroVaccinale;
	private String denominazione;
	private String comune;
	private String provincia;
	private String regione;
	private String indirizzo;
	 
	 
	//DEFAULT
	public CentroVaccinaleBean() throws ExceptionManager{
		this.indexCentroVaccinale = -1;
		this.denominazione = "";
		this.comune = "";
		this.provincia = "";
		this.regione = "";
		this.indirizzo = "";
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