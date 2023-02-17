package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.InfoPrenotazioneBean;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.UserBean;

public class InfoPrenotazioneDB extends QueryAbstract <InfoPrenotazioneBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected InfoPrenotazioneBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		InfoPrenotazioneBean infoPrntBean = new InfoPrenotazioneBean();
		
		infoPrntBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		infoPrntBean.setDataPrenotazione(rs.getTimestamp("data_prenotazione"));
		infoPrntBean.setDataInserimento(rs.getTimestamp("data_inserimento"));
		infoPrntBean.setStatoCompletato(rs.getInt("stato_completato"));
		infoPrntBean.setIdUser(rs.getInt("id_user"));
		infoPrntBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		
		infoPrntBean.setDenominazione(rs.getString("denominazione"));
		infoPrntBean.setComune(rs.getString("comune"));
		infoPrntBean.setProvincia(rs.getString("provincia"));
		infoPrntBean.setRegione(rs.getString("regione"));
		infoPrntBean.setIndirizzo(rs.getString("indirizzo"));
		
		return infoPrntBean;
	}

	
	public List<InfoPrenotazioneBean> getInfoPrenotazioneByCodice(InfoPrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_codice")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getPrenotazioneByCodice(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByCodice(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	public List<InfoPrenotazioneBean> getInfoPrenotazioneByCodiceActive(InfoPrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_codice_active")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByCodiceActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByCodiceActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	public List<InfoPrenotazioneBean> getInfoPrenotazioneByCodiceNoActive(InfoPrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_codice_no_active")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByCodiceNoActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByCodiceNoActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	
	public List<InfoPrenotazioneBean> getPrenotazioniByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_id_user")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUser(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUserInfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	public List<InfoPrenotazioneBean> getPrenotazioniByIdUserActive(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_id_user_active")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUserActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUserActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	public List<InfoPrenotazioneBean> getPrenotazioniByIdNoUserActive(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_id_user_no_active")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUserNoActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getInfoPrenotazioneByIdUserNoActive(InfoPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	
	public List<InfoPrenotazioneBean> getPrenotazioniByCodiceCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_codice_active_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getPrenotazioniByCodiceCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getPrenotazioniByCodiceCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	
	public List<InfoPrenotazioneBean> getPrenotazioniByCodiceNoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_codice_active_no_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	
	public List<InfoPrenotazioneBean> getPrenotazioniByIdStatoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_id_user_active_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getPrenotazioniByIdStatoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getPrenotazioniByIdStatoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	
	
	public List<InfoPrenotazioneBean> getPrenotazioniByIdStatoNoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_prnt_data_by_id_user_active_no_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoPrenotazioneDB", "Errore SQLException Servlet InfoPrenotazioneDB -> getPrenotazioniByIdStatoNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoPrenotazioneDB", "Errore ExceptionManager Servlet InfoPrenotazioneDB -> getPrenotazioniByIdStatoNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoPrenotazioneBean>();
	}
	


	public boolean removePrenotazione(InfoPrenotazioneBean prnt) {
		try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "remove_prnt_by_codice_prenotazione")
			){
				stmtPrep.setString(1, prnt.getCodicePrenotazione());
				
				return (stmtPrep.executeUpdate() > 0 ?  true :  false );
				
			} catch(SQLException exc){
				new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> insertCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
			}
			
			return false; // non riuscito
	}
	
	
	public boolean updateStatoCompletatoByCodice(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_update_stato_completato_by_codice")
		){
			stmtPrep.setInt(1, prnt.getStatoCompletato());
			stmtPrep.setString(2, prnt.getCodicePrenotazione());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> updatePrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
}