package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class InfoDipendenteApprovatoBean implements Serializable {
 
	private int id_dipendenti_centro_vaccinale;
	private int idUser;
	private int idCentroVaccinale;
	private int flagApprovato;
	
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
	
	private String denominazione;
	private String comune;
	private String provincia;
	private String regione;
	private String indirizzo;
	 
	 
	//DEFAULT
	public InfoDipendenteApprovatoBean() throws ExceptionManager{
		
		this.id_dipendenti_centro_vaccinale = -1;
		this.idUser = -1;
		this.idCentroVaccinale = -1;
		this.flagApprovato = 0;
		
		this.ruolo = "";
		this.nome = "";
		this.cognome = "";
		this.email = "";
		this.sesso = "";
		this.codiceFiscale = "";
		this.dataNascita = DateUtility.getCurrentTimestamp();
		this.dataRegistrazione = dataNascita;
		this.cittaResidenza = "";
		this.provinciaResidenza = "";
		this.regioneResidenza = "";
		
		this.denominazione = "";
		this.comune = "";
		this.provincia = "";
		this.regione = "";
		this.indirizzo = "";
	}

	public int getIdDipendenteApprovatoCentroVaccinale() {
		return id_dipendenti_centro_vaccinale;
	}
	public void setIdDipendenteApprovatoCentroVaccinale(int id_dipendenti_centro_vaccinale) {
		this.id_dipendenti_centro_vaccinale = id_dipendenti_centro_vaccinale;
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
	
	public int getFlagApprovato() {
		return flagApprovato;
	}

	public void setFlagApprovato(int flagApprovato) {
		this.flagApprovato = flagApprovato;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Timestamp getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
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