package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.CentroVaccinaleBean;
import it.googlielmo93.apsw.model.PostiDisponibiliBean;
import it.googlielmo93.apsw.model.PrenotazioneBean;

public class PostiDisponibiliDB extends QueryAbstract <PostiDisponibiliBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cosi' creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected PostiDisponibiliBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		PostiDisponibiliBean postiDisponibiliBean = new PostiDisponibiliBean();
		
		postiDisponibiliBean.setIdGiornoApertura(rs.getInt("id_giorno_apertura"));
		postiDisponibiliBean.setDataPostiDisponibili(rs.getTimestamp("data"));
		postiDisponibiliBean.setIndexCentroVaccinale(rs.getInt("id_centro_vaccinale"));
		postiDisponibiliBean.setPostiDisponibili(rs.getInt("posti_disponibili"));
		postiDisponibiliBean.setNumeroTabellone(rs.getInt("numero_tabellone"));
		
		postiDisponibiliBean.setDenominazione(rs.getString("denominazione"));
		postiDisponibiliBean.setComune(rs.getString("comune"));
		postiDisponibiliBean.setProvincia(rs.getString("provincia"));
		postiDisponibiliBean.setRegione(rs.getString("regione"));
		postiDisponibiliBean.setIndirizzo(rs.getString("indirizzo"));
		
		
		return postiDisponibiliBean;
	}
	
	
	public List<PostiDisponibiliBean> getGiornoAperturaById(PostiDisponibiliBean postiBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_by_id")
			){
				stmtPrep.setInt(1, postiBean.getIdGiornoApertura());
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> getGiornoAperturaById(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> getGiornoAperturaById(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<PostiDisponibiliBean>();
	}
	
	public List<PostiDisponibiliBean> getGiornoAperturaByIdCentroVaccinaleToday(PostiDisponibiliBean postiBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_by_id_centro_vaccinale_by_day")
		){
			stmtPrep.setInt(1, postiBean.getIndexCentroVaccinale());
			Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
			stmtPrep.setTimestamp(2, date);

			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> getGiornoAperturaByIdCentroVaccinaleToday(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> getGiornoAperturaByIdCentroVaccinaleToday(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
		}
		return new ArrayList<PostiDisponibiliBean>();
	}
	
	public List<PostiDisponibiliBean> getGiornoAperturaByIdCentroVaccinaleByDay(PostiDisponibiliBean postiBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_by_id_centro_vaccinale_by_day")
		){
			stmtPrep.setInt(1, postiBean.getIndexCentroVaccinale());
			stmtPrep.setTimestamp(2, postiBean.getDataPostiDisponibili());

			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> getGiornoAperturaByIdCentroVaccinaleByDay(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> getGiornoAperturaByIdCentroVaccinaleByDay(PostiDisponibiliBean postiBean). Eccezione lanciata: {0}", exc);
		}
		return new ArrayList<PostiDisponibiliBean>();
	}
	
	
	public List<PostiDisponibiliBean> postiDisponibiliByIdCentroVaccinale(CentroVaccinaleBean cntrBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "postiDisponibiliByIdCentroVaccinale")
			){
				stmtPrep.setInt(1, cntrBean.getIndexCentroVaccinale());
				Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
				stmtPrep.setTimestamp(2, date);	//prende il timestamp del giorno successivo alla data e time corrente
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinale(CentroVaccinaleBean cntrBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinale(CentroVaccinaleBean cntrBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<PostiDisponibiliBean>();
	}
	
	
	public List<PostiDisponibiliBean> postiDisponibiliByIdCentroVaccinale(PrenotazioneBean prntBean) {
			try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "postiDisponibiliByIdCentroVaccinale")
			){
				stmtPrep.setInt(1, prntBean.getIdCentroVaccinale());
				Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
				stmtPrep.setTimestamp(2, date);	//prende il timestamp del giorno successivo alla data e time corrente
				
				try(ResultSet rs = stmtPrep.executeQuery()){
					return resultQuery(rs);
				}
			} catch(SQLException exc){
				new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinale(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinale(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
			}
		return new ArrayList<PostiDisponibiliBean>();
	}
	
	public List<PostiDisponibiliBean> postiDisponibiliByIdCentroVaccinaleInData(PrenotazioneBean prntBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "postiDisponibiliByIdCentroVaccinaleInData")
		){
			stmtPrep.setInt(1, prntBean.getIdCentroVaccinale());
			Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
			stmtPrep.setTimestamp(2, date);
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinaleInData(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> postiDisponibiliByIdCentroVaccinaleInData(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
		}
		return new ArrayList<PostiDisponibiliBean>();
	
	}
	
	
	private int getNumeroTabellone(PostiDisponibiliBean tabelloneBean) {
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "postiDisponibiliByIdCentroVaccinaleInData")
		){
			stmtPrep.setInt(1, tabelloneBean.getIndexCentroVaccinale());
			Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
			stmtPrep.setTimestamp(2, date);
			
			try(ResultSet rs = stmtPrep.executeQuery()){
				if(rs.next())
					return rs.getInt("numero_tabellone");
			}
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> getNumeroTabellone(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> getNumeroTabellone(PrenotazioneBean prntBean). Eccezione lanciata: {0}", exc);
		}
		return 0;
	
	}


	public boolean incrementaTurno(PostiDisponibiliBean tabelloneBean) {
		try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_tabellone_incrementa_by_id_centro_vaccinale")
			){
				int numero_attuale = getNumeroTabellone(tabelloneBean) + 1;
				stmtPrep.setInt(1, numero_attuale);
				stmtPrep.setInt(2, tabelloneBean.getIndexCentroVaccinale());
				Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
				stmtPrep.setTimestamp(3, date);
				
				return (stmtPrep.executeUpdate() > 0 ?  true :  false );
			} catch(SQLException exc){
				new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> updatePostiDisponibiliById(PostiDisponibiliBean tabelloneBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> updatePostiDisponibiliById(PostiDisponibiliBean tabelloneBean). Eccezione lanciata: {0}", exc);
			}
			
			return false; //non riuscito
	}
	
	public boolean decrementaTurno(PostiDisponibiliBean tabelloneBean) {
		try( 
				Connection conn = getConnection();
				PreparedStatement stmtPrep = prepareStatement(conn, "single_tabellone_incrementa_by_id_centro_vaccinale")
			){
				int numero_attuale = getNumeroTabellone(tabelloneBean) - 1;
				if(numero_attuale < 0)
					return false;
				
				stmtPrep.setInt(1, numero_attuale);
				stmtPrep.setInt(2, tabelloneBean.getIndexCentroVaccinale());
				Timestamp date = DateUtility.getCurrentTimestampTimeZeros();
				stmtPrep.setTimestamp(3, date);
				
				return (stmtPrep.executeUpdate() > 0 ?  true :  false );
			} catch(SQLException exc){
				new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> updatePostiDisponibiliById(PostiDisponibiliBean tabelloneBean). Eccezione lanciata: {0}", exc);
			} catch (ExceptionManager exc) {
				exc.setLogError("PostiDisponibiliDB", "Errore ExceptionManager Servlet PostiDisponibiliDB -> updatePostiDisponibiliById(PostiDisponibiliBean tabelloneBean). Eccezione lanciata: {0}", exc);
			}
			
			return false; //non riuscito
	}
	
	public boolean insertPostiDisponibiliBean(PostiDisponibiliBean pstDisp){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_insert")
		){
			stmtPrep.setTimestamp(1, pstDisp.getDataPostiDisponibili());
			stmtPrep.setInt(2, pstDisp.getIndexCentroVaccinale());
			stmtPrep.setInt(3, pstDisp.getPostiDisponibili());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> insertPostiDisponibiliBean(PostiDisponibiliBean pstDisp). Eccezione lanciata: {0}", exc);
		}
		
		return false; // non riuscito
	}
	
	
	public boolean updatePostiDisponibiliBeanById(PostiDisponibiliBean pstDisp){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_update_by_id")
		){
			stmtPrep.setTimestamp(1, pstDisp.getDataPostiDisponibili());
			stmtPrep.setInt(2, pstDisp.getIndexCentroVaccinale());
			stmtPrep.setInt(3, pstDisp.getPostiDisponibili());
			stmtPrep.setInt(4, pstDisp.getIdGiornoApertura());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB -> updatePostiDisponibiliById(PostiDisponibiliBean pstDisp). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
	public boolean updatePostiDisponibiliById(PostiDisponibiliBean pstDisp){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_pstDisp_update_posti_disponibili_by_id")
		){
			int postiDecr = 0;
			if(pstDisp.getPostiDisponibili() > 0)
				postiDecr = (pstDisp.getPostiDisponibili()) - 1;
		
			stmtPrep.setInt(1, postiDecr);
			stmtPrep.setInt(2, pstDisp.getIdGiornoApertura());
			
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
			
		} catch(SQLException exc){
			new ExceptionManager("PostiDisponibiliDB", "Errore SQLException Servlet PostiDisponibiliDB ->updatePostiDisponibiliById(PostiDisponibiliBean pstDisp). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
}