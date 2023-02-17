package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.InfoTurnoBean;

public class InfoTurnoDB extends QueryAbstract <InfoTurnoBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected InfoTurnoBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		InfoTurnoBean infoTurnoBean = new InfoTurnoBean();
		
		infoTurnoBean.setIdTurno(rs.getInt("id_turno"));
		infoTurnoBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		infoTurnoBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		infoTurnoBean.setDataInserimentoATurno(rs.getTimestamp("data_inserimento"));
		
		infoTurnoBean.setIdUser(rs.getInt("id_user"));
		infoTurnoBean.setStato_completato(rs.getInt("stato_completato"));
		infoTurnoBean.setDataPrenotazione(rs.getTimestamp("data_prenotazione"));
		
		infoTurnoBean.setNome(rs.getString("nome"));
		infoTurnoBean.setCognome(rs.getString("cognome"));
		infoTurnoBean.setEmail(rs.getString("email"));
		infoTurnoBean.setSesso(rs.getString("sesso"));
		infoTurnoBean.setCodiceFiscale(rs.getString("codice_fiscale"));
		infoTurnoBean.setDataNascita(rs.getTimestamp("data_nascita"));
		infoTurnoBean.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
		infoTurnoBean.setCittaResidenza(rs.getString("citta_residenza"));
		infoTurnoBean.setProvinciaResidenza(rs.getString("provincia_residenza"));
		infoTurnoBean.setRegioneResidenza(rs.getString("regione_residenza"));
		
		infoTurnoBean.setDenominazione(rs.getString("denominazione"));
		infoTurnoBean.setComune(rs.getString("comune"));
		infoTurnoBean.setProvincia(rs.getString("provincia"));
		infoTurnoBean.setRegione(rs.getString("regione"));
		infoTurnoBean.setIndirizzo(rs.getString("indirizzo"));
		
		return infoTurnoBean;
	}
	
	
	public List<InfoTurnoBean> getInfoTurnoByIdCentroVaccinale(InfoTurnoBean infoTurnoBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_info_turno_by_id_centro_vaccinale")
			){
				stmtPrep.setInt(1, infoTurnoBean.getIdCentroVaccinale());
				stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
				stmtPrep.setTimestamp(3, DateUtility.getNextDayTS(1));
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> getTurnoByIdCentroVaccinale(InfoTurnoBean infoTurnoBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("TurnoDB", "Errore ExceptionManager Servlet TurnoDB -> getTurnoByIdCentroVaccinale(InfoTurnoBean infoTurnoBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<InfoTurnoBean>();
	}
	
	public List<InfoTurnoBean> getInfoTurnoByCodicePrenotazioneStartDay(InfoTurnoBean infoTurnoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_turno_by_codice_prenotazione")
		){
			stmtPrep.setString(1, infoTurnoBean.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> getTurnoByCodicePrenotazioneStartDay(InfoTurnoBean infoTurnoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("TurnoDB", "Errore ExceptionManager Servlet TurnoDB -> getTurnoByCodicePrenotazioneStartDay(InfoTurnoBean infoTurnoBean). Eccezione lanciata: {0}", exc);
		}
		
		return new ArrayList<InfoTurnoBean>();
	}

	
}