package it.googlielmo93.apsw.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.googlielmo93.apsw.dao.QueryAbstract;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.UserBean;

public class UserDB extends QueryAbstract <UserBean> {
	
	/* metodo override della classe abstract, crea un nuovo bean con i dati contenuti nella risposta.
	 * Questo metodo viene chiamato dal metodo ereditato resultQuery, che return una lista di bean cos� creati.
	 * Viene quindi usato lo stesso model, ma con bean diversi.
	 */
	
	@Override
	protected UserBean beanResult(ResultSet rs) throws SQLException, ExceptionManager{
		UserBean userBean = new UserBean();
		
		userBean.setIndexUser(rs.getInt("id_user"));
		userBean.setUserName(rs.getString("USER_LOGIN"));
		userBean.setPassword(rs.getString("USER_PSWD"));
		userBean.setRuolo(rs.getString("USER_ROLE"));
		userBean.setNome(rs.getString("nome"));
		userBean.setCognome(rs.getString("cognome"));
		userBean.setEmail(rs.getString("email"));
		userBean.setSesso(rs.getString("sesso"));
		userBean.setCodiceFiscale(rs.getString("codice_fiscale"));
		userBean.setDataNascitaTS(rs.getTimestamp("data_nascita"));
		userBean.setDataRegistrazioneTS(rs.getTimestamp("data_registrazione"));
		userBean.setCittaResidenza(rs.getString("citta_residenza"));
		userBean.setProvinciaResidenza(rs.getString("provincia_residenza"));
		userBean.setRegioneResidenza(rs.getString("regione_residenza"));
			
		return userBean;
	}
	
	public List<UserBean> getUsersByRole(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "list_user_data_by_role")
		){
			stmtPrep.setString(1, user.getRuolo());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			 
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> getUsersByRole(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("UserDB", "Errore ExceptionManager Servlet UserDB -> getUsersByRole(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<UserBean>();
	}
	
	
	public List<UserBean> getUserByUserName(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_data_by_userName")
		){
			stmtPrep.setString(1, user.getUserName());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> getUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("UserDB", "Errore ExceptionManager Servlet UserDB -> getUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<UserBean>();
	}	
	
	public List<UserBean> getUserByIdUser(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_data_by_id_user")
		){
			stmtPrep.setInt(1, user.getIndexUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> getUserByIdUser(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("UserDB", "Errore ExceptionManager Servlet UserDB -> getUserByIdUser(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<UserBean>();
	}
	
	public List<UserBean> getUserByIdUser(PrenotazioneBean prnt){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_data_by_id_user")
		){
			stmtPrep.setInt(1, prnt.getIdUser());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return resultQuery(rs);
			}
			
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> getUserByIdUser(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			exc.setLogError("UserDB", "Errore ExceptionManager Servlet UserDB -> getUserByIdUser(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		//lista vuota se non trova nulla
		return new ArrayList<UserBean>();
	}	
	
	
	public boolean isUserByUserName(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isUserByUserName")
		){
			stmtPrep.setString(1, user.getUserName());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> isUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}	
	
	public boolean isUserByCFRole(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "isUserByCFRole")
		){
			stmtPrep.setString(1, user.getRuolo());
			stmtPrep.setString(2, user.getCodiceFiscale());
			try(ResultSet rs = stmtPrep.executeQuery()){
				return rs.next();
			}
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> isUserByCFRole(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		return false;
	}
	
	public boolean insertUser(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_insert")
		){
			stmtPrep.setString(1, user.getUserName());
			stmtPrep.setString(2, user.getPassword());
			stmtPrep.setString(3, user.getRuolo());
			stmtPrep.setString(4, user.getNome());
			stmtPrep.setString(5, user.getCognome());
			stmtPrep.setString(6, user.getEmail());
			stmtPrep.setString(7, user.getSesso());
			stmtPrep.setString(8, user.getCodiceFiscale());
			stmtPrep.setTimestamp(9, user.getDataNascitaTSSQL());
			stmtPrep.setTimestamp(10, user.getDataRegistrazioneTSSQL()); //prende di default la data e ora corrente in timestamp
			stmtPrep.setString(11, user.getCittaResidenza());
			stmtPrep.setString(12, user.getProvinciaResidenza());
			stmtPrep.setString(13, user.getRegioneResidenza());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> insertUser(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("UserDB", "Errore ExceptionManager Servlet UserDB -> insertUser(UserBean user). Eccezione lanciata: {0}", exc);
		}
		return false; // non riuscito
	}
	
	
	public boolean updateUserByUserName(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_update_by_userName")
		){
			stmtPrep.setString(1, user.getUserName());
			stmtPrep.setString(2, user.getPassword());
			stmtPrep.setString(3, user.getRuolo());
			stmtPrep.setString(4, user.getNome());
			stmtPrep.setString(5, user.getCognome());
			stmtPrep.setString(6, user.getEmail());
			stmtPrep.setString(7, user.getSesso());
			stmtPrep.setString(8, user.getCodiceFiscale());
			stmtPrep.setTimestamp(9, user.getDataNascitaTSSQL());
			stmtPrep.setTimestamp(10, user.getDataRegistrazioneTSSQL()); //prende di default la data e ora corrente in timestamp
			stmtPrep.setString(11, user.getCittaResidenza());
			stmtPrep.setString(12, user.getProvinciaResidenza());
			stmtPrep.setString(13, user.getRegioneResidenza());
			stmtPrep.setString(14, user.getUserName());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> updateUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		} catch (ExceptionManager exc) {
			new ExceptionManager("UserDB", "Errore ExceptionManager Servlet UserDB -> updateUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
	
	public boolean updateDatiAccessoByCFRuolo(UserBean user){
		try( 
			Connection conn = getConnection();
			PreparedStatement stmtPrep = prepareStatement(conn, "single_user_update_dati_accesso_by_CF_ruolo")
		){
			stmtPrep.setString(1, user.getUserName());
			stmtPrep.setString(2, user.getPassword());
			stmtPrep.setString(3, user.getEmail());
			stmtPrep.setString(4, user.getCodiceFiscale());
			stmtPrep.setString(5, user.getRuolo());
			return (stmtPrep.executeUpdate() > 0 ?  true :  false );
		} catch(SQLException exc){
			new ExceptionManager("UserDB", "Errore SQLException Servlet UserDB -> updateUserByUserName(UserBean user). Eccezione lanciata: {0}", exc);
		}
		
		return false; //non riuscito
	}
	
	
	
	
}