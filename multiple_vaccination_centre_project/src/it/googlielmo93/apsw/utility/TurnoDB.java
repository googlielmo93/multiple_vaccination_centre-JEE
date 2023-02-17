package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.TurnoBean;

public class TurnoDB extends QueryAbstract <TurnoBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected TurnoBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		TurnoBean turnoBean = new TurnoBean();
		
		turnoBean.setIdTurno(rs.getInt("id_turno"));
		turnoBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		turnoBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		turnoBean.setDataInserimento(rs.getTimestamp("data_inserimento"));
		
		return turnoBean;
	}
	
	
	public List<TurnoBean> getTurnoByIdCentroVaccinale(TurnoBean turnoBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_turno_by_id_centro_vaccinale")
			){
				stmtPrep.setInt(1, turnoBean.getIdCentroVaccinale());
				stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> getTurnoByIdCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("TurnoDB", "Errore ExceptionManager Servlet TurnoDB -> getTurnoByIdCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<TurnoBean>();
	}
	
	public List<TurnoBean> getTurnoByCodicePrenotazioneStartDay(TurnoBean turnoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_turno_by_codice_prenotazione")
		){
			stmtPrep.setString(1, turnoBean.getCodicePrenotazione());
			stmtPrep.setTimestamp(2, DateUtility.getCurrentTimestampTimeZeros());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> getTurnoByCodicePrenotazioneStartDay(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("TurnoDB", "Errore ExceptionManager Servlet TurnoDB -> getTurnoByCodicePrenotazioneStartDay(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
		}
		
		return new ArrayList<TurnoBean>();
	}
	
	
	public boolean removeTurnoByCodicePrenotazione(TurnoBean turnoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "remove_turno_by_codice_prenotazione")
		){
			stmtPrep.setString(1, turnoBean.getCodicePrenotazione());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
			
		} catch(SQLException exc){
			new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> insertCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}

	
	public boolean insertTurnoBean(TurnoBean turnoBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_turno_insert")
		){
			stmtPrep.setInt(1, turnoBean.getIdCentroVaccinale());
			stmtPrep.setString(2, turnoBean.getCodicePrenotazione());
			stmtPrep.setTimestamp(3, turnoBean.getDataInserimentoTSSQL());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("TurnoDB", "Errore SQLException Servlet TurnoDB -> insertCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("TurnoDB", "Errore ExceptionManager Servlet TurnoDB -> insertCentroVaccinale(TurnoBean turnoBean). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
}