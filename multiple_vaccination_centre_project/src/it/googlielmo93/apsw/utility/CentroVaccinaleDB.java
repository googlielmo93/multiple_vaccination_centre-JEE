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

public class CentroVaccinaleDB extends QueryAbstract <CentroVaccinaleBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected CentroVaccinaleBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		CentroVaccinaleBean centrovaccinaleBean = new CentroVaccinaleBean();
		
		centrovaccinaleBean.setIndexCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		centrovaccinaleBean.setDenominazione(rs.getString("denominazione"));
		centrovaccinaleBean.setComune(rs.getString("comune"));
		centrovaccinaleBean.setProvincia(rs.getString("provincia"));
		centrovaccinaleBean.setRegione(rs.getString("regione"));
		centrovaccinaleBean.setIndirizzo(rs.getString("indirizzo"));
		
		return centrovaccinaleBean;
	}

	
	public List<CentroVaccinaleBean> getCentroVaccinaleById(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_Id")
		){
			stmtPrep.setInt(1, cntvacc.getIndexCentroVaccinale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleById(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleById(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
	}
	
	public List<CentroVaccinaleBean> getCentroVaccinaleByIdRef(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_Id")
		){
			stmtPrep.setInt(1, prnt.getIdCentroVaccinale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
	}

	public List<CentroVaccinaleBean> getCentroVaccinaleByIdRef(SomministrazioneBean smnt) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_Id")
			){
				stmtPrep.setInt(1, smnt.getIdCentroVaccinale());
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
				
			} catch(SQLException exc){
				new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			}
			
			//lista vuota se non trova nulla
			return new ArrayList<CentroVaccinaleBean>();
	}
	
	public List<CentroVaccinaleBean> getCentroVaccinaleByAllData(CentroVaccinaleBean cntr) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_all_data")
		){
			stmtPrep.setString(1, cntr.getRegione());
			stmtPrep.setString(2, cntr.getProvincia());
			stmtPrep.setString(3, cntr.getComune());
			stmtPrep.setString(4, cntr.getDenominazione());
			stmtPrep.setString(5, cntr.getIndirizzo());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByIdRef(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
}

	public List<CentroVaccinaleBean> getCentroVaccinaleByComune(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_comune")
		){
			stmtPrep.setString(1, cntvacc.getComune());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByComune(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByComune(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
	}
	
	public List<CentroVaccinaleBean> getCentroVaccinaleByProvincia(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_provincia")
		){
			stmtPrep.setString(1, cntvacc.getProvincia());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByProvincia(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByProvincia(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
	}
	
	public List<CentroVaccinaleBean> getCentroVaccinaleByRegione(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_regione")
		){
			stmtPrep.setString(1, cntvacc.getRegione());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegione(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegione(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<CentroVaccinaleBean>();
	}
	
	

	public List<CentroVaccinaleBean> getCentroVaccinaleByRegProvComune(CentroVaccinaleBean cntvacc) {
		try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_reg_prov_comune")
			){
				stmtPrep.setString(1, cntvacc.getRegione());
				stmtPrep.setString(2, cntvacc.getProvincia());
				stmtPrep.setString(3, cntvacc.getComune());
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegProvComun(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegProvComun(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			}
			
			//lista vuota se non trova nulla
			return new ArrayList<CentroVaccinaleBean>();
	}
	
	public List<CentroVaccinaleBean> getCentroVaccinaleByRegProv(CentroVaccinaleBean cntvacc) {
		try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_data_by_reg_prov")
			){
				stmtPrep.setString(1, cntvacc.getRegione());
				stmtPrep.setString(2, cntvacc.getProvincia());
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegProv(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("CentroVaccinaleDB", "Errore ExceptionManager Servlet CentroVaccinaleDB -> getCentroVaccinaleByRegProv(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
			}
			
			//lista vuota se non trova nulla
			return new ArrayList<CentroVaccinaleBean>();
	}
	
	
	public boolean isCentroVaccinaleById(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isCentroVaccinaleById")
		){
			stmtPrep.setInt(1, cntvacc.getIndexCentroVaccinale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> isCentroVaccinaleById(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}	
	
	
	public boolean insertCentroVaccinale(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_insert")
		){
			stmtPrep.setString(1, cntvacc.getDenominazione());
			stmtPrep.setString(2, cntvacc.getComune());
			stmtPrep.setString(3, cntvacc.getProvincia());
			stmtPrep.setString(4, cntvacc.getRegione());
			stmtPrep.setString(5, cntvacc.getIndirizzo());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> insertCentroVaccinale(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
	
	public boolean updateCentroVaccinaleById(CentroVaccinaleBean cntvacc){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_cntvacc_update_by_id")
		){
			stmtPrep.setString(1, cntvacc.getDenominazione());
			stmtPrep.setString(2, cntvacc.getComune());
			stmtPrep.setString(3, cntvacc.getProvincia());
			stmtPrep.setString(4, cntvacc.getRegione());
			stmtPrep.setString(5, cntvacc.getIndirizzo());
			stmtPrep.setInt(6, cntvacc.getIndexCentroVaccinale());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("CentroVaccinaleDB", "Errore SQLException Servlet CentroVaccinaleDB -> updateCentroVaccinaleById(CentroVaccinaleBean cntvacc). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
}