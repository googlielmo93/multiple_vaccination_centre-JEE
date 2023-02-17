package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class PostiDisponibiliBean implements Serializable {
 
	private int idGiornoApertura;
	private int idCentroVaccinale;
	private int postiDisponibili;
	private Timestamp data;
	private int numeroTabellone;
	
	private String denominazione;
	private String comune;
	private String provincia;
	private String regione;
	private String indirizzo;
	 
	 
	//DEFAULT
	public PostiDisponibiliBean() throws ExceptionManager{
		this.idGiornoApertura = -1;
		this.idCentroVaccinale = -1;
		this.postiDisponibili = 0;
		this.setDataPostiDisponibili(DateUtility.getCurrentTimestamp());
		this.numeroTabellone = 0;
		
		this.denominazione = "";
		this.comune = "";
		this.provincia = "";
		this.regione = "";
		this.indirizzo = "";
	}
	 
	public int getIdGiornoApertura() {
		return idGiornoApertura;
	}

	public void setIdGiornoApertura(int idGiornoApertura) {
		this.idGiornoApertura = idGiornoApertura;
	}

	public int getIndexCentroVaccinale() {
		return idCentroVaccinale;
	}
	public void setIndexCentroVaccinale(int indexCentroVaccinale) {
		this.idCentroVaccinale = indexCentroVaccinale;
	}
	
	public int getPostiDisponibili() {
		return postiDisponibili;
	}

	public void setPostiDisponibili(int postiLiberi) {
		this.postiDisponibili = postiLiberi;
	}
	

	public Timestamp getDataPostiDisponibili() {
		return data;
	}

	public void setDataPostiDisponibili(Timestamp dataPostiDisponibili) {
		this.data = dataPostiDisponibili;
	}
	
	public int getNumeroTabellone() {
		return numeroTabellone;
	}

	public void setNumeroTabellone(int numeroTabellone) {
		this.numeroTabellone = numeroTabellone;
	}

	public String getFormattedDataPostiDisp() throws ExceptionManager {
		return DateUtility.getFormattedStringByTimestamp(this.data, true);
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