package it.googlielmo93.apsw.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.UserDB;


/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet({"/RegistrazioneServlet", "/registrazione"})
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
			
				UserBean userBean = null;
				
				try {
					// creo un bean di tipo UserBean con parametri di default
					userBean = new UserBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("RegistrazioneServlet", "Errore ExceptionManager Servlet RegistrazioneServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
					address = "./view/registrazione.jsp?msgError=registrazioneFail";
		            response.sendRedirect(address);
					return;
				}
				

				// setto con i valori sottomessi dal form il bean di default che verra' inviato alla classe di utilita' UserDB per l'inserimento nel DB
				try {
					userBean.setUserName(request.getParameter("userName"));
					userBean.setPasswordHashCode(request.getParameter("password"));
					userBean.setRuolo(request.getParameter("ruolo_toggle"));
					userBean.setNome(request.getParameter("nome"));
					userBean.setCognome(request.getParameter("cognome"));
					userBean.setEmail(request.getParameter("email"));
					userBean.setCodiceFiscale(request.getParameter("codiceFiscale"));
					if(request.getParameter("ruolo_toggle").equals("user")) { //solo user, per gli altri mantiene i valori di default
						userBean.setSesso(request.getParameter("sesso_toggle"));
						userBean.setDataNascita(request.getParameter("dataNascita"));
						userBean.setCittaResidenza(request.getParameter("cittaResidenza"));
						userBean.setProvinciaResidenza(request.getParameter("provinciaResidenza"));
						userBean.setRegioneResidenza(request.getParameter("regioneResidenza"));
						
					}

				}catch(ExceptionManager exc) {
					exc.setLogError("RegistrazioneServlet", "Errore generico Servlet RegistrazioneServlet; errore durante la creazione dell'inserimento dei parametri nell'oggetto UserBean; Nei log precedenti sono descritti i dettagli. Eccezione lanciata: {0}", exc);
					address = "./view/registrazione.jsp?msgError=registrazioneFail";
		            response.sendRedirect(address);
					return;
				}
				

				try {
					UserDB userDB = new UserDB();
					
					//verifico che non esista utente con lo stesso username
					if(!userDB.isUserByUserName(userBean) && !userDB.isUserByCFRole(userBean)) { 
							
						if(userDB.insertUser(userBean)) { // true inserito correttamente
							
							request.setAttribute("userBean", userBean);
							
							//effettuo il login automaticamente
							address = "/LoginServlet";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
							return;

						}else {
							address = "./view/registrazione.jsp?msgError=registrazioneFail";
							response.sendRedirect(address);
							return;
						}
						
					}else {
						
						request.setAttribute("userBean", userBean);
						
						if(userDB.isUserByUserName(userBean))
							address = "./view/registrazione.jsp?msgError=userExistUserName";
						else if(userDB.isUserByCFRole(userBean))
							address = "./view/registrazione.jsp?msgError=userExistCF";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
					}
				}
				catch(Exception exc) {
		        	new ExceptionManager("RegistrazioneServlet", "Servlet RegistrazioneServlet; Errore durante la verifica esistenza utente -> Operazioni DB. Eccezione lanciata: {0}", exc);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("RegistrazioneServlet", "Errore generico Servlet RegistrazioneServlet; errore durante la registrazione. Eccezione lanciata: {0}", exc);
			address = "./view/registrazione.jsp?msgError=registrazioneFail";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("RegistrazioneServlet", "Errore generico Servlet RegistrazioneServlet; errore durante la registrazione -> errore forward. Eccezione lanciata: {0}", excForward);
			}
        }
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
