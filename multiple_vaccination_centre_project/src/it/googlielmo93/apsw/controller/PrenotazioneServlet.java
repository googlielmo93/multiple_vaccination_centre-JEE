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

import it.googlielmo93.apsw.model.PostiDisponibiliBean;
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.SomministrazioneBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.PostiDisponibiliDB;
import it.googlielmo93.apsw.utility.PrenotazioneDB;
import it.googlielmo93.apsw.utility.SomministrazioneDB;


/**
 * Servlet implementation class PrenotazioneServlet
 */
@WebServlet({"/PrenotazioneServlet", "/prenotazione_vaccino"})
public class PrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrenotazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    //metodo privato e sincronizzato per poter inserire una prenotazione; qui possono accedere tutti i thread;
    //e' una risorsa condivisa e sincronizzata
    private synchronized boolean inserisciPrenotazione(PostiDisponibiliDB pstDispDB, PrenotazioneDB prntDB, PrenotazioneBean prntBean, int id_giorno_apertura) throws ExceptionManager {
    	if(pstDispDB != null && prntBean != null && prntDB != null) {
			
			PostiDisponibiliBean pstDispBean = new PostiDisponibiliBean();
			pstDispBean.setIdGiornoApertura(id_giorno_apertura);
					
			List<PostiDisponibiliBean> postiBeans = pstDispDB.getGiornoAperturaById(pstDispBean);
			
			if(!postiBeans.isEmpty()) {
				if(postiBeans.get(0).getPostiDisponibili() > 0) {

					prntBean.setIdCentroVaccinale(postiBeans.get(0).getIndexCentroVaccinale());
					prntBean.setDataPrenotazione(postiBeans.get(0).getDataPostiDisponibili());
					
					if(prntDB.insertPrenotazione(prntBean)) {
						return pstDispDB.updatePostiDisponibiliById(postiBeans.get(0));
					}
				}
			}
    	}
    	
    	return false;
    }
    
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
				HttpSession session = request.getSession(false);

				if(session == null){
					address = "/WEB-INF/view/result_prenotazione_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
				}
				
				PrenotazioneBean prenotazioneBean = null;
				UserBean userBean = null;
				PostiDisponibiliDB pstDispDB = new PostiDisponibiliDB();
				int id_giorno_apertura = -1;
	        	
	        	try {
					userBean = (UserBean) session.getAttribute("userBean");
				}catch(IllegalStateException exc) {
	            	new ExceptionManager("PrenotazioneServlet",  "Errore IllegalStateException Servlet PrenotazioneServlet -> recupero attributo userBean. Eccezione lanciata: {0}", exc );
				}
	        	
	        	if (userBean == null) {
					address = "/WEB-INF/view/result_prenotazione_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
				}
	        	
	        	
				
				try {
					// creo un bean di tipo PrenotazioneBean con parametri di default
					prenotazioneBean = new PrenotazioneBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("PrenotazioneServlet", "Errore ExceptionManager Servlet PrenotazioneServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_prenotazione_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				

				try {
					prenotazioneBean.setCodicePrenotazioneCF(userBean.getCodiceFiscale());
					prenotazioneBean.setIdUser(userBean.getIndexUser());
					
					if(request.getParameter("id_giorno_apertura") != null){
						try{
							id_giorno_apertura = Integer.parseInt(request.getParameter("id_giorno_apertura"));
						}catch(NumberFormatException exc){
				        	new ExceptionManager("PrenotazioneServlet", "Errore durante il parseInt dell'attributo idCentroVaccinale. Eccezione lanciata: {0}", exc);		            
				            address = "/WEB-INF/view/prenotazione_error.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
							return;
						}
					}else {
						address = "/WEB-INF/view/result_prenotazione_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
			            return;
					}
					
					

				}catch(ExceptionManager exc) {
					exc.setLogError("PrenotazioneServlet", "Errore generico Servlet PrenotazioneServlet; errore durante la creazione dell'inserimento dei parametri nell'oggetto PrenotazioneBean; Nei log precedenti sono descritti i dettagli. Eccezione lanciata: {0}", exc);
					address = "/WEB-INF/view/result_prenotazione_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				
				
				try {
					
					PrenotazioneDB prenotazioneDB = new PrenotazioneDB();
					SomministrazioneDB somministrazioneDB = new SomministrazioneDB();
					
					//verifico che non esiste la prenotazione con il codice relativo;
					//verifico inoltre se non e' gia' stato completato il ciclo, altrimenti non fa prenotare.
					//Il numero di vaccinazioni per completare il ciclo e' definito nella costante numVacciniPerPersona
					List<PrenotazioneBean> isPrnt = prenotazioneDB.getPrenotazioniByIdUserActive(userBean);
					Integer countVaccSmnt = somministrazioneDB.countSomministrazioniByIdUser(prenotazioneBean);
					
					
					if(isPrnt.isEmpty() && countVaccSmnt <= SomministrazioneBean.numVacciniPerPersona) {
							
						try {
							if(inserisciPrenotazione(pstDispDB, prenotazioneDB, prenotazioneBean, id_giorno_apertura)) {	//inserito con successo

								request.setAttribute("info_insert_prenotazione", prenotazioneBean);
								address = "/WEB-INF/view/result_prenotazione_success.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
							}else {
								
								address = "/WEB-INF/view/result_prenotazione_fail.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
							}
						
						}catch(ExceptionManager exc) {
							exc.setLogError("PrenotazioneServlet", "Errore generico Servlet PrenotazioneServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
							address = "/WEB-INF/view/result_prenotazione_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						}
						
					}else {
						if(!isPrnt.isEmpty())
							address = "/WEB-INF/view/result_prenotazione_fail.jsp";
						else if(countVaccSmnt > SomministrazioneBean.numVacciniPerPersona)
							address = "/WEB-INF/view/result_prenotazione_success.jsp";
						
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					}
				}
				catch(Exception exc) {
		        	new ExceptionManager("PrenotazioneServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_prenotazione_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("PrenotazioneServlet", "Errore generico Servlet PrenotazioneServlet; errore durante la prenotazione. Eccezione lanciata: {0}", exc);
			address = "./view/login.jsp?msgError=areaProtetta";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("PrenotazioneServlet", "Errore generico Servlet PrenotazioneServlet; errore durante la prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











