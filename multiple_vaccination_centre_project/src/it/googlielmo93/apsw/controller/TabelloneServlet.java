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

import it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean;
import it.googlielmo93.apsw.model.PostiDisponibiliBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoDipendenteApprovatoDB;
import it.googlielmo93.apsw.utility.PostiDisponibiliDB;


/**
 * Servlet implementation class TabelloneServlet
 */
@WebServlet({"/TabelloneServlet", "/tabellone"})
public class TabelloneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TabelloneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		String msgError="";
		
		try {
				
				PostiDisponibiliBean tabelloneBean = null;
				int id_utente = -1;
				int id_centro_vaccinale = -1;
				
				
				try {
					// creo un bean di tipo PostiDisponibiliBean con parametri di default
					tabelloneBean = new PostiDisponibiliBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("TabelloneServlet", "Errore ExceptionManager Servlet TabelloneServlet; errore durante la creazione dell'oggetto tabelloneBean di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				
				String operation = request.getParameter("operation");
				
				if(operation != null) {
					
					// recupero tutti gli utenti messi a turno tramite il centro vaccinale
					if(operation.equals("tabellone_by_id_centro_vaccinale")) {
						if(request.getParameter("id_centro_vaccinale") != null){
							
							try {
								String id_centro_vaccinale_string = request.getParameter("id_centro_vaccinale");
								if(id_centro_vaccinale_string != null)
									id_centro_vaccinale = Integer.parseInt(id_centro_vaccinale_string);
							}
							catch(NumberFormatException exc){
						    	new ExceptionManager("TabelloneServlet", "Errore durante il parseInt dell'attributo id_centro_vaccinale. Eccezione lanciata: {0}", exc);		            
						        address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
						        msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
						    	request.setAttribute("msgError", msgError);
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
							}
							
							tabelloneBean.setIndexCentroVaccinale(id_centro_vaccinale);
							
						}else {
							address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
				            return;
						}
					}
					
					// recupero tutti gli utenti messi a turno tramite l'id centro vaccinale recuperato dallo userBean dell'utente loggato
					if(operation.equals("tabellone_by_id_utente")) {

						HttpSession session = request.getSession(false);
						if(session == null){
							new ExceptionManager("TabelloneServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
							address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						    return;
						}
						
						List<InfoDipendenteApprovatoBean> dipBean = null;
						
						// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
						InfoDipendenteApprovatoBean dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
						
						InfoDipendenteApprovatoDB infoTurnoDB = new InfoDipendenteApprovatoDB();
						
						UserBean userBean = (UserBean) session.getAttribute("userBean");
						
						if(userBean != null){
							id_utente = userBean.getIndexUser();
							dipendenteApprovatoBean.setIdUser(id_utente);
						}else {
				        	new ExceptionManager("TabelloneServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
							address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						    return;
						}
						
						dipBean = infoTurnoDB.getDipByIdUser(dipendenteApprovatoBean);
						
						id_centro_vaccinale = dipBean.get(0).getIdCentroVaccinale();
						
						tabelloneBean.setIndexCentroVaccinale(id_centro_vaccinale);
						
					}
					
				}else {
					address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
		            return;
				}

				
				try {
					
					PostiDisponibiliDB tabelloneDB = new PostiDisponibiliDB();
					
					String subOperation = request.getParameter("sub_operation");
					
					// se definita la subOperation incrementa o decrementa il numero turno
					if(subOperation != null && subOperation != "") {
						
						if(subOperation.equals("incrementa_turno")) {
							tabelloneDB.incrementaTurno(tabelloneBean);
						}
						
						if(subOperation.equals("decrementa_turno")) {
							tabelloneDB.decrementaTurno(tabelloneBean);
						}
					}
					
					List<PostiDisponibiliBean> tabelloneBeans = tabelloneDB.getGiornoAperturaByIdCentroVaccinaleToday(tabelloneBean);
					
					if(!tabelloneBeans.isEmpty()) {
						request.setAttribute("tabelloneBeans", tabelloneBeans.get(0));
						address = "/WEB-INF/view/result_tabellone_turno_success.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
						
					}else {
						msgError = "Nessun utente a turno!";
					}
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("TabelloneServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
				
				request.setAttribute("msgError", msgError);
				address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
				return;
				
		}
        catch(Exception exc) {
        	new ExceptionManager("TabelloneServlet", "Errore generico Servlet TabelloneServlet; errore durante il recupero dei dati. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_tabellone_turno_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("TabelloneServlet", "Errore generico Servlet TabelloneServlet; errore durante il recupero dei dati -> errore forward. Eccezione lanciata: {0}", excForward);
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











