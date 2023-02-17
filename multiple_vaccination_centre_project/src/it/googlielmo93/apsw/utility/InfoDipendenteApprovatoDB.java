package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean;

public class InfoDipendenteApprovatoDB extends QueryAbstract <InfoDipendenteApprovatoBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected InfoDipendenteApprovatoBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		InfoDipendenteApprovatoBean infoDipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
		
		infoDipendenteApprovatoBean.setIdDipendenteApprovatoCentroVaccinale(rs.getInt("id_dipendente_approvato"));
		infoDipendenteApprovatoBean.setIdUser(rs.getInt("id_user"));
		infoDipendenteApprovatoBean.setIdCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		infoDipendenteApprovatoBean.setFlagApprovato(rs.getInt("flag_approvato"));
		
		infoDipendenteApprovatoBean.setRuolo(rs.getString("USER_ROLE"));
		infoDipendenteApprovatoBean.setNome(rs.getString("nome"));
		infoDipendenteApprovatoBean.setCognome(rs.getString("cognome"));
		infoDipendenteApprovatoBean.setEmail(rs.getString("email"));
		infoDipendenteApprovatoBean.setSesso(rs.getString("sesso"));
		infoDipendenteApprovatoBean.setCodiceFiscale(rs.getString("codice_fiscale"));
		infoDipendenteApprovatoBean.setDataNascita(rs.getTimestamp("data_nascita"));
		infoDipendenteApprovatoBean.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
		infoDipendenteApprovatoBean.setCittaResidenza(rs.getString("citta_residenza"));
		infoDipendenteApprovatoBean.setProvinciaResidenza(rs.getString("provincia_residenza"));
		infoDipendenteApprovatoBean.setRegioneResidenza(rs.getString("regione_residenza"));
		
		infoDipendenteApprovatoBean.setDenominazione(rs.getString("denominazione"));
		infoDipendenteApprovatoBean.setComune(rs.getString("comune"));
		infoDipendenteApprovatoBean.setProvincia(rs.getString("provincia"));
		infoDipendenteApprovatoBean.setRegione(rs.getString("regione"));
		infoDipendenteApprovatoBean.setIndirizzo(rs.getString("indirizzo"));
		
		return infoDipendenteApprovatoBean;
	}
	
	
	public List<InfoDipendenteApprovatoBean> getDipByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_by_id_centro_vaccinale")
		){
			stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdCentroVaccinale());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB -> getDipByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB -> getDipByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		}
	return new ArrayList<InfoDipendenteApprovatoBean>();
}

public List<InfoDipendenteApprovatoBean> getDipByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_by_id_user")
		){
			stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdUser());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB ->getDipApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB ->getDipApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		}
	return new ArrayList<InfoDipendenteApprovatoBean>();
}
	
	
	public List<InfoDipendenteApprovatoBean> getDipApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_approvato_by_id_centro_vaccinale")
			){
				stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdCentroVaccinale());
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB -> getDipApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB -> getDipApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<InfoDipendenteApprovatoBean>();
	}
	
	public List<InfoDipendenteApprovatoBean> getDipApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_approvato_by_id_user")
			){
				stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdUser());
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB ->getDipApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB ->getDipApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<InfoDipendenteApprovatoBean>();
	}
	
	public List<InfoDipendenteApprovatoBean> getDipNoApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_no_approvato_by_id_centro_vaccinale")
		){
			stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdCentroVaccinale());
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB -> getDipNoApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB -> getDipNoApprByIdCentroVaccinale(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		}
		return new ArrayList<InfoDipendenteApprovatoBean>();
	}
	
	public List<InfoDipendenteApprovatoBean> getDipNoApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_no_approvato_by_id_user")
			){
				stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdUser());
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB ->getDipNoApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("InfoDipendenteApprovatoDB", "Errore ExceptionManager Servlet InfoDipendenteApprovatoDB ->getDipNoApprByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<InfoDipendenteApprovatoBean>();
	}
	
	
	public boolean approvaByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_approvato_update_by_id_user")
		){
			stmtPrep.setInt(1, 1);
			stmtPrep.setInt(2, infoDipendenteApprovatoBean.getIdUser());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB -> approvaByIdUser(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	

	public boolean insertInfoDipendenteApprovatoBean(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_dip_approvato_insert")
		){
			stmtPrep.setInt(1, infoDipendenteApprovatoBean.getIdUser());
			stmtPrep.setInt(2, infoDipendenteApprovatoBean.getIdCentroVaccinale());
			stmtPrep.setInt(3, infoDipendenteApprovatoBean.getFlagApprovato());
			
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("InfoDipendenteApprovatoDB", "Errore SQLException Servlet InfoDipendenteApprovatoDB -> insertInfoDipendenteApprovatoBean(InfoDipendenteApprovatoBean infoDipendenteApprovatoBean). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
}