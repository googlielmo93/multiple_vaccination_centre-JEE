package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.CentroVaccinaleBean;
import it.googlielmo93.apsw.model.InfoSomministrazioneBean;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.UserBean;

public class InfoSomministrazioneDB extends QueryAbstract <InfoSomministrazioneBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected InfoSomministrazioneBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		InfoSomministrazioneBean infoSmntBean = new InfoSomministrazioneBean();
		
		infoSmntBean.setIdSomministrazione(rs.getInt("id_somministrazione"));
		infoSmntBean.setTipoVaccino(rs.getString("tipo_vaccino"));
		infoSmntBean.setCodiceVaccino(rs.getString("codice_vaccino"));
		infoSmntBean.setLotto(rs.getString("lotto"));
		infoSmntBean.setDataSomministrazione(rs.getTimestamp("data_somministrazione"));
		infoSmntBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		infoSmntBean.setIdUser(rs.getInt("id_user"));
		infoSmntBean.setIdMedico(rs.getInt("id_medico"));
		infoSmntBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		
		infoSmntBean.setDenominazione(rs.getString("denominazione"));
		infoSmntBean.setComune(rs.getString("comune"));
		infoSmntBean.setProvincia(rs.getString("provincia"));
		infoSmntBean.setRegione(rs.getString("regione"));
		infoSmntBean.setIndirizzo(rs.getString("indirizzo"));
		
		return infoSmntBean;
	}

	
	public List<InfoSomministrazioneBean> getInfoSomministrazioneByCodicePrenotazione(InfoSomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_codice_prenotazione")
		){
			stmtPrep.setString(1, smnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getSomministrazioneByCodicePrenotazione(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getSomministrazioneByCodicePrenotazione(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public List<InfoSomministrazioneBean> getSomministrazioneByCodiceVaccino(InfoSomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_codice_vaccino")
		){
			stmtPrep.setString(1, smnt.getCodiceVaccino());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getSomministrazioneByCodiceVaccino(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getSomministrazioneByCodiceVaccino(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public List<InfoSomministrazioneBean> getInfoSomministrazioneByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_id_user")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public List<InfoSomministrazioneBean> getInfoSomministrazioneByPrenotazione(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_codice_prenotazione")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public List<InfoSomministrazioneBean> getInfoSomministrazioneByIdMedico(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_id_medico")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdMedico(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdMedico(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public List<InfoSomministrazioneBean> getInfoSomministrazioneByIdCentroVaccinale(CentroVaccinaleBean centroVaccinaleBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_info_smnt_data_by_id_centro_vaccinale")
		){
			stmtPrep.setInt(1, centroVaccinaleBean.getIndexCentroVaccinale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdCentroVaccinale(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoSomministrazioneDB", "Errore ExceptionManager Servlet InfoSomministrazioneDB -> getInfoSomministrazioneByIdCentroVaccinale(InfoSomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<InfoSomministrazioneBean>();
	}
	
	public int countInfoSomministrazioneByIdUser(PrenotazioneBean prnt) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "countSomministrazioniByIdUser")
			){
				stmtPrep.setInt(1, prnt.getIdUser());
				try(ResultSet rs = stmtPrep.executeQuery()){
					if(rs.next()) {
						return rs.getInt("countSomministrazioni");
					}
				}
			} catch(SQLException exc){
				new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> countInfoSomministrazioneByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
			}
		return 0;
	}
	
	public int countSomministrazioneByIdUser(UserBean userBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "countSomministrazioniByIdUser")
			){
				stmtPrep.setInt(1, userBean.getIndexUser());
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					if(rs.next()) {
						return rs.getInt("countSomministrazioni");
					}
				}
			} catch(SQLException exc){
				new ExceptionManager("InfoSomministrazioneDB", "Errore SQLException Servlet InfoSomministrazioneDB -> countInfoSomministrazioneByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
			}
		return 0;
	}

}