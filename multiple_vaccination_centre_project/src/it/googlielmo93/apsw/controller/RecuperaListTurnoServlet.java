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
import it.googlielmo93.apsw.model.InfoTurnoBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoDipendenteApprovatoDB;
import it.googlielmo93.apsw.utility.InfoTurnoDB;


/**
 * Servlet implementation class RecuperaListTurnoServlet
 */
@WebServlet({"/RecuperaListTurnoServlet", "/recupera_list_turno"})
public class RecuperaListTurnoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaListTurnoServlet() {
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
				
				InfoTurnoBean infoTurnoBean = null;
				int id_centro_vaccinale = -1;
				int id_utente = -1;
				
				
				try {
					// creo un bean di tipo InfoTurnoBean con parametri di default
					infoTurnoBean = new InfoTurnoBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("RecuperaListTurnoServlet", "Errore ExceptionManager Servlet RecuperaListTurnoServlet; errore durante la creazione dell'oggetto infoTurnoBean di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_list_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				
				String operation = request.getParameter("operation");
				
				if(operation != null) {
					
					// recupero tutti gli utenti messi a turno tramite il centro vaccinale
					if(operation.equals("list_turno_by_id_centro_vaccinale")) {
						if(request.getParameter("id_centro_vaccinale") != null){
							
							try {
								String id_centro_vaccinale_string = request.getParameter("id_centro_vaccinale");
								if(id_centro_vaccinale_string != null)
									id_centro_vaccinale = Integer.parseInt(id_centro_vaccinale_string);
							}
							catch(NumberFormatException exc){
						    	new ExceptionManager("RecuperaListTurnoServlet", "Errore durante il parseInt dell'attributo id_dipendente. Eccezione lanciata: {0}", exc);		            
						        address = "/WEB-INF/view/result_list_turno_fail.jsp";
						        msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
						    	request.setAttribute("msgError", msgError);
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
							}
							
							infoTurnoBean.setIdCentroVaccinale(id_centro_vaccinale);
							
						}else {
							address = "/WEB-INF/view/result_list_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
				            return;
						}
					}
					
					// recupero tutti gli utenti messi a turno tramite l'id centro vaccinale recuperato dallo userBean dell'utente loggato
					if(operation.equals("list_turno_by_id_utente")) {

						List<InfoDipendenteApprovatoBean> dipBean = null;
						
						// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
						InfoDipendenteApprovatoBean dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
						
						InfoDipendenteApprovatoDB infoTurnoDB = new InfoDipendenteApprovatoDB();

						HttpSession session = request.getSession(false);

						if(session == null){
							new ExceptionManager("RecuperaListTurnoServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
							address = "/WEB-INF/view/result_list_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						    return;
						}
						
						UserBean userBean = (UserBean) session.getAttribute("userBean");
						
						if(userBean != null){
							id_utente = userBean.getIndexUser();
							dipendenteApprovatoBean.setIdUser(id_utente);
						}else {
				        	new ExceptionManager("RecuperaListTurnoServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
							address = "/WEB-INF/view/result_list_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						    return;
						}
						
						dipBean = infoTurnoDB.getDipByIdUser(dipendenteApprovatoBean);
						
						id_centro_vaccinale = dipBean.get(0).getIdCentroVaccinale();
						
						infoTurnoBean.setIdCentroVaccinale(id_centro_vaccinale);
					}
					
				}else {
					address = "/WEB-INF/view/result_list_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
		            return;
				}

				
				try {
					
					InfoTurnoDB infoTurnoDB = new InfoTurnoDB();
					
					List<InfoTurnoBean> infoTurnoBeans = infoTurnoDB.getInfoTurnoByIdCentroVaccinale(infoTurnoBean);

					
					if(!infoTurnoBeans.isEmpty()) {	//se esiste la prenotazione ed � attiva con stato non completato
						
						request.setAttribute("listInfoTurnoBeans", infoTurnoBeans);
						address = "/WEB-INF/view/result_list_turno_success.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
						
					}else {
						msgError = "Nessun utente a turno!";
					}
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("RecuperaListTurnoServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_list_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
				
				request.setAttribute("msgError", msgError);
				address = "/WEB-INF/view/result_list_turno_fail.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
				return;
				
		}
        catch(Exception exc) {
        	new ExceptionManager("RecuperaListTurnoServlet", "Errore generico Servlet RecuperaListTurnoServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_list_turno_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("RecuperaListTurnoServlet", "Errore generico Servlet RecuperaListTurnoServlet; errore durante l'inserimento della prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











