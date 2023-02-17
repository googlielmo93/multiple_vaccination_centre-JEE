package it.googlielmo93.apsw.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;

@SuppressWarnings("serial")
public class InfoTurnoBean implements Serializable {
 
	//turno
	private int idTurno;
	private int idCentroVaccinale;
	private String codicePrenotazione;
	private Timestamp dataInserimentoATurno;
	
	//prenotazione
	private Timestamp dataPrenotazione;
	private int stato_completato;
	private int idUser;
	
	//user
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
	
	//centro vaccinale
	private String denominazione;
	private String comune;
	private String provincia;
	private String regione;
	private String indirizzo;
	 
	 
	//DEFAULT
	public InfoTurnoBean() throws ExceptionManager{
		this.idTurno = -1;
		this.codicePrenotazione = "";
		this.idCentroVaccinale = -1;
		
		this.dataPrenotazione = DateUtility.getCurrentTimestamp();
		this.stato_completato = 0;
		this.idUser = -1;
		
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
	
	public Timestamp getDataInserimentoATurno() {
		return dataInserimentoATurno;
	}

	public void setDataInserimentoATurno(Timestamp dataInserimentoATurno) {
		this.dataInserimentoATurno = dataInserimentoATurno;
	}
	
	public int getIdCentroVaccinale() {
		return idCentroVaccinale;
	}
	public void setIdCentroVaccinale(int idCentroVaccinale) {
		this.idCentroVaccinale = idCentroVaccinale;
	}
	
	public Timestamp getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(Timestamp dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	
	public int getStato_completato() {
		return stato_completato;
	}

	public void setStato_completato(int stato_completato) {
		this.stato_completato = stato_completato;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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