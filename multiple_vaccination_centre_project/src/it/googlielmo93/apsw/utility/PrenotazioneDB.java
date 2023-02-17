package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.TurnoBean;
import it.googlielmo93.apsw.model.UserBean;

public class PrenotazioneDB extends QueryAbstract <PrenotazioneBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected PrenotazioneBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
		
		prenotazioneBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		prenotazioneBean.setDataPrenotazione(rs.getTimestamp("data_prenotazione"));
		prenotazioneBean.setDataInserimento(rs.getTimestamp("data_inserimento"));
		prenotazioneBean.setStatoCompletato(rs.getInt("stato_completato"));
		prenotazioneBean.setIdUser(rs.getInt("id_user"));
		prenotazioneBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		
		return prenotazioneBean;
	}

	
	public List<PrenotazioneBean> getPrenotazioneByCodice(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	public List<PrenotazioneBean> getPrenotazioneByCodiceActive(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByCodiceActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByCodiceActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	public List<PrenotazioneBean> getPrenotazioneByCodiceNoActive(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_no_active")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByCodiceNoActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByCodiceNoActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByIdUserPrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	public List<PrenotazioneBean> getPrenotazioniByIdUserActive(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user_active")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByIdUserActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByIdUserActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	public List<PrenotazioneBean> getPrenotazioniByIdNoUserActive(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user_no_active")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioneByIdUserNoActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioneByIdUserNoActive(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	
	public List<PrenotazioneBean> getPrenotazioniByCodiceCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByCodiceCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByCodiceCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByIdUserNoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user_active_no_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByIdUserNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByIdUserNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByCodiceNoCompletato(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active_no_completato")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	public List<PrenotazioneBean> getPrenotazioniByCodiceNoCompletatoStartDay(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active_no_completato")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletatoStartDay(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletatoStartDay(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByCodiceNoCompletatoStartDay(TurnoBean turno){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active_no_completato")
		){
			stmtPrep.setString(1, turno.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletatoStartDay(TurnoBean turno){. Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletatoStartDay(TurnoBean turno){. Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByCodiceNoCompletato(TurnoBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_codice_active_no_completato")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByCodiceNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByIdStatoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user_active_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByIdStatoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByIdStatoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public List<PrenotazioneBean> getPrenotazioniByIdStatoNoCompletato(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_data_by_id_user_active_no_completato")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> getPrenotazioniByIdStatoNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> getPrenotazioniByIdStatoNoCompletato(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<PrenotazioneBean>();
	}
	
	
	public boolean isPrenotazioneByCodice(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isPrenotazioneByCodice")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> isPrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}	
	
	public boolean isPrenotazioneByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isPrenotazioneByIdUser")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> isPrenotazioneByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}

	
	public boolean insertPrenotazione(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_insert")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, prnt.getDataPrenotazioneTSSQL());
			stmtPrep.setTimestamp(3, prnt.getDataInserimentoTSSQL());
			stmtPrep.setInt(4, prnt.getStatoCompletato());
			stmtPrep.setInt(5, prnt.getIdUser());
			stmtPrep.setInt(6, prnt.getIdCentroVaccinale());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> insertPrenotazione(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> insertPrenotazione(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
	
	public boolean updatePrenotazioneByCodice(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_prnt_update_by_codice")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, prnt.getDataPrenotazioneTSSQL());
			stmtPrep.setTimestamp(3, prnt.getDataInserimentoTSSQL());
			stmtPrep.setInt(4, prnt.getStatoCompletato());
			stmtPrep.setInt(5, prnt.getIdUser());
			stmtPrep.setInt(6, prnt.getIdCentroVaccinale());
			stmtPrep.setString(7, prnt.getCodicePrenotazione());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("PrenotazioneDB", "Errore SQLException Servlet PrenotazioneDB -> updatePrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("PrenotazioneDB", "Errore ExceptionManager Servlet PrenotazioneDB -> updatePrenotazioneByCodice(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
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