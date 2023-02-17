package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.CentroVaccinaleBean;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.SomministrazioneBean;
import it.googlielmo93.apsw.model.UserBean;

public class SomministrazioneDB extends QueryAbstract <SomministrazioneBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected SomministrazioneBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		SomministrazioneBean somministrazioneBean = new SomministrazioneBean();
		
		somministrazioneBean.setIdSomministrazione(rs.getInt("id_somministrazione"));
		somministrazioneBean.setTipoVaccino(rs.getString("tipo_vaccino"));
		somministrazioneBean.setCodiceVaccino(rs.getString("codice_vaccino"));
		somministrazioneBean.setLotto(rs.getString("lotto"));
		somministrazioneBean.setDataSomministrazione(rs.getTimestamp("data_somministrazione"));
		somministrazioneBean.setCodicePrenotazione(rs.getString("codice_prenotazione"));
		somministrazioneBean.setIdUser(rs.getInt("id_user"));
		somministrazioneBean.setIdMedico(rs.getInt("id_medico"));
		somministrazioneBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		
		return somministrazioneBean;
	}

	
	public List<SomministrazioneBean> getSomministrazioneByCodicePrenotazione(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_codice_prenotazione")
		){
			stmtPrep.setString(1, smnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioneByCodicePrenotazione(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioneByCodicePrenotazione(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	public List<SomministrazioneBean> getSomministrazioneByCodiceVaccino(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_codice_vaccino")
		){
			stmtPrep.setString(1, smnt.getCodiceVaccino());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioneByCodiceVaccino(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioneByCodiceVaccino(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	public List<SomministrazioneBean> getSomministrazioniByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_id_user")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioniByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioniByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	public List<SomministrazioneBean> getSomministrazioniByPrenotazione(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_codice_prenotazione")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioniByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioniByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	public List<SomministrazioneBean> getSomministrazioniByIdMedico(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_id_medico")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioniByIdMedico(UserBean userBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioniByIdMedico(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	public List<SomministrazioneBean> getSomministrazioniByIdCentroVaccinale(CentroVaccinaleBean centroVaccinaleBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_data_by_id_centro_vaccinale")
		){
			stmtPrep.setInt(1, centroVaccinaleBean.getIndexCentroVaccinale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> getSomministrazioniByIdCentroVaccinale(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> getSomministrazioniByIdCentroVaccinale(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<SomministrazioneBean>();
	}
	
	
	public boolean isSomministrazioneByCodicePrenotazione(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isSomministrazioneByCodicePrenotazione")
		){
			stmtPrep.setString(1, smnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> isSomministrazioneByCodicePrenotazione(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}
	
	public boolean isSomministrazioneByCodicePrenotazione(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isSomministrazioneByCodicePrenotazione")
		){
			stmtPrep.setString(1, prnt.getCodicePrenotazione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> isSomministrazioneByCodicePrenotazione(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}	
	
	public boolean isSomministrazioneByIdUser(UserBean userBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isSomministrazioneByIdUser")
		){
			stmtPrep.setInt(1, userBean.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> isSomministrazioneByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}
	
	public boolean isSomministrazioneByIdUser(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isSomministrazioneByIdUser")
		){
			stmtPrep.setInt(1, prnt.getIdUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> isSomministrazioneByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}
	
	public int countSomministrazioniByIdUser(PrenotazioneBean prnt) {
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
				new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> countSomministrazioniByIdUser(PrenotazioneBean prnt). Eccezione lanciata: {0}", exc);
			}
		return 0;
	}
	
	public int countSomministrazioniByIdUser(UserBean userBean) {
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
				new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> countSomministrazioniByIdUser(UserBean userBean). Eccezione lanciata: {0}", exc);
			}
		return 0;
	}
	
	public boolean insertSomministrazione(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_insert")
		){
			stmtPrep.setString(1, smnt.getTipoVaccino());
			stmtPrep.setString(2, smnt.getCodiceVaccino());
			stmtPrep.setString(3, smnt.getLotto());
			stmtPrep.setTimestamp(4, DateUtility.getCurrentTimestamp());
			stmtPrep.setString(5, smnt.getCodicePrenotazione());
			stmtPrep.setInt(6, smnt.getIdUser());
			stmtPrep.setInt(7, smnt.getIdMedico());
			stmtPrep.setInt(8, smnt.getIdCentroVaccinale());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> insertSomministrazione(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> insertSomministrazione(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
	public boolean updateSomminsitrazioneByIdSomministrazione(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_update_by_smnt")
		){
			stmtPrep.setString(1, smnt.getTipoVaccino());
			stmtPrep.setString(2, smnt.getCodiceVaccino());
			stmtPrep.setString(3, smnt.getLotto());
			stmtPrep.setTimestamp(4, smnt.getDataSomministrazioneTSSQL());
			stmtPrep.setString(5, smnt.getCodicePrenotazione());
			stmtPrep.setInt(6, smnt.getIdMedico());
			stmtPrep.setInt(7, smnt.getIdCentroVaccinale());
			stmtPrep.setInt(8, smnt.getIdSomministrazione());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> updateSomminsitrazioneByCodice(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> updateSomminsitrazioneByCodice(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
	
	public boolean updateSomminsitrazioneByCodicePrenotazione(SomministrazioneBean smnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_smnt_update_by_codice_prenotazione")
		){
			stmtPrep.setString(1, smnt.getTipoVaccino());
			stmtPrep.setString(2, smnt.getCodiceVaccino());
			stmtPrep.setString(3, smnt.getLotto());
			stmtPrep.setTimestamp(4, smnt.getDataSomministrazioneTSSQL());
			stmtPrep.setString(5, smnt.getCodicePrenotazione());
			stmtPrep.setInt(6, smnt.getIdMedico());
			stmtPrep.setInt(7, smnt.getIdCentroVaccinale());
			stmtPrep.setString(8, smnt.getCodicePrenotazione());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("SomministrazioneDB", "Errore SQLException Servlet SomministrazioneDB -> updateSomminsitrazioneByCodice(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("SomministrazioneDB", "Errore ExceptionManager Servlet SomministrazioneDB -> updateSomminsitrazioneByCodice(SomministrazioneBean smnt). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}

}