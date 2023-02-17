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

import it.googlielmo93.apsw.model.InfoPrenotazioneBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoPrenotazioneDB;


/**
 * Servlet implementation class RecuperaPrenotazioneServlet
 */
@WebServlet({"/RecuperaPrenotazioneServlet", "/recupera_prenotazione", "/elimina_prenotazione"})
public class RecuperaPrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaPrenotazioneServlet() {
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
					address = "/WEB-INF/view/info_prenotazioni.jsp";
		            response.sendRedirect(address);
					return;
				}
				
				InfoPrenotazioneBean infoPrenotazioneBean = null;
				UserBean userBean = null;
	        	
	        	try {
					userBean = (UserBean) session.getAttribute("userBean");
				}catch(IllegalStateException exc) {
	            	new ExceptionManager("RecuperaPrenotazioneServlet",  "Errore IllegalStateException Servlet RecuperaPrenotazioneServlet -> recupero attributo userBean. Eccezione lanciata: {0}", exc );
				}
	        	
	        	if (userBean == null) {
	        		address = "/WEB-INF/view/info_prenotazioni.jsp";
		            response.sendRedirect(address);
					return;
				}
				
				try {
					// creo un bean di tipo InfoPrenotazioneBean con parametri di default
					infoPrenotazioneBean = new InfoPrenotazioneBean();
					
				}catch(ExceptionManager exc) {
		        	exc.setLogError("RecuperaPrenotazioneServlet", "Errore ExceptionManager Servlet RecuperaPrenotazioneServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/info_prenotazioni.jsp";
		            response.sendRedirect(address);
					return;
				}
				
				infoPrenotazioneBean.setIdUser(userBean.getIndexUser());
				

				try {
					
					String operation = request.getParameter("operation");
					
					String codice = request.getParameter("codice");


					InfoPrenotazioneDB InfoPrenotazioneDB = new InfoPrenotazioneDB();
					
					List<InfoPrenotazioneBean> infoPrntActive = InfoPrenotazioneDB.getPrenotazioniByIdUserActive(userBean);
					
					if(operation != null && operation.equals("delete_prenotazione")) {
						if(!infoPrntActive.isEmpty()) {
							infoPrenotazioneBean.setCodicePrenotazione(codice);
							
							if(InfoPrenotazioneDB.removePrenotazione(infoPrenotazioneBean)) {
								String msgError = "Prenotazione eliminata con successo!";
								request.setAttribute("msgError", msgError);
								address = "/WEB-INF/view/delete_prenotazione_success.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
								return;
							}
						}

						String msgError = "Impossibile procedere con l'eliminazione! Provare nuovamente, se il problema sussiste la preghiamo di contattarci";
						request.setAttribute("msgError", msgError);
						address = "/WEB-INF/view/delete_prenotazione_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
						
					}else {
						
					List<InfoPrenotazioneBean> infoPrntNoActive = InfoPrenotazioneDB.getPrenotazioniByIdNoUserActive(userBean);
					
						
					request.setAttribute("infoPrenotazioniActive", infoPrntActive);
					request.setAttribute("infoPrenotazioniNoActive", infoPrntNoActive);
					}
				
					address = "/WEB-INF/view/info_prenotazioni.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					
					try {
						dispatcher.forward(request, response);
					}catch(Exception excForward) {
			        	new ExceptionManager("RecuperaPrenotazioniServlet", "Errore generico Servlet RecuperaPrenotazioniServlet; errore durante il forward per il json con date -> errore forward. Eccezione lanciata: {0}", excForward);
					}
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("InfoSomministrazioneServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("InfoSomministrazioneServlet", "Errore generico Servlet InfoSomministrazioneServlet; errore durante la prenotazione. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/info_prenotazioni.jsp";
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











