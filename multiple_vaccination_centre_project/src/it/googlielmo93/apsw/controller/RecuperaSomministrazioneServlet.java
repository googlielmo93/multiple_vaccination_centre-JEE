package it.googlielmo93.apsw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.googlielmo93.apsw.model.InfoSomministrazioneBean;
import it.googlielmo93.apsw.model.SomministrazioneBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoSomministrazioneDB;


/**
 * Servlet implementation class PrenotazioneServlet
 */
@WebServlet({"/RecuperaSomministrazioneServlet", "/recupera_somministrazione"})
public class RecuperaSomministrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaSomministrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
				HttpSession session = request.getSession(false);
				if(session == null){
					address = "./view/login.jsp?msgError=";
		            response.sendRedirect(address);
					return;
				}
				
				InfoSomministrazioneBean infoSomministrazioneBean = null;
				UserBean userBean = null;
	        	
	        	try {
					userBean = (UserBean) session.getAttribute("userBean");
				}catch(IllegalStateException exc) {
	            	new ExceptionManager("PrenotazioneServlet",  "Errore IllegalStateException Servlet PrenotazioneServlet -> recupero attributo userBean. Eccezione lanciata: {0}", exc );
				}
	        	
	        	if (userBean == null) {
					address = "./view/login.jsp?msgError=";
		            response.sendRedirect(address);
					return;
				}
				
				try {
					// creo un bean di tipo InfoPrenotazioneBean con parametri di default
					infoSomministrazioneBean = new InfoSomministrazioneBean();
					
				}catch(ExceptionManager exc) {
		        	exc.setLogError("PrenotazioneServlet", "Errore ExceptionManager Servlet PrenotazioneServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
					address = "./view/login.jsp?msgError=areaProtetta";
		            response.sendRedirect(address);
					return;
				}
				
				infoSomministrazioneBean.setIdUser(userBean.getIndexUser());
				

				try {

					InfoSomministrazioneDB infoSomministrazioneDB = new InfoSomministrazioneDB();
					
					List<InfoSomministrazioneBean> infoSmnt = infoSomministrazioneDB.getInfoSomministrazioneByIdUser(userBean);
					
					if(infoSmnt != null) {
						
						request.setAttribute("listSomministrazioni", infoSmnt);
						
						int countSmnt = infoSomministrazioneDB.countSomministrazioneByIdUser(userBean);
						// trovo le somministrazioni restanti secondo il limite imposto con la variabile statica numVacciniPerPersona
						countSmnt = SomministrazioneBean.numVacciniPerPersona - countSmnt;
						
						request.setAttribute("countSmntRimanenti", countSmnt);
					
						address = "/WEB-INF/view/info_somministrazioni.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						
						try {
							dispatcher.forward(request, response);
						}catch(Exception excForward) {
				        	new ExceptionManager("RecuperaSomministrazioniServlet", "Errore generico Servlet RecuperaSomministrazioniServlet; errore durante il forward per il json con date -> errore forward. Eccezione lanciata: {0}", excForward);
						}
					
					}
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("InfoSomministrazioneServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("InfoSomministrazioneServlet", "Errore generico Servlet InfoSomministrazioneServlet; errore durante la prenotazione. Eccezione lanciata: {0}", exc);
			address = "./view/login.jsp?msgError=areaProtetta";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("InfoSomministrazioneServlet", "Errore generico Servlet InfoSomministrazioneServlet; errore durante la prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











