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
import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.TurnoBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoDipendenteApprovatoDB;
import it.googlielmo93.apsw.utility.PrenotazioneDB;
import it.googlielmo93.apsw.utility.TurnoDB;
import it.googlielmo93.apsw.utility.UserDB;


/**
 * Servlet implementation class InfoUtenteByCodPrenotazioneServlet
 */
@WebServlet({"/InfoUtenteByCodPrenotazioneServlet", "/recupera_info_utente_by_cod"})
public class InfoUtenteByCodPrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoUtenteByCodPrenotazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
			
				TurnoBean turnoBean = null;
				String codiceUtenteTurno = "";
				
				
				try {
					// creo un bean di tipo TurnoBean con parametri di default
					turnoBean = new TurnoBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("InfoUtenteByCodPrenotazioneServlet", "Errore ExceptionManager Servlet InfoUtenteByCodPrenotazioneServlet; errore durante la creazione dell'oggetto turnoBean di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_info_utente_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}


				if(request.getParameter("codiceUtente") != null){
					codiceUtenteTurno = request.getParameter("codiceUtente");
					turnoBean.setCodicePrenotazione(codiceUtenteTurno);
				}else {
					address = "/WEB-INF/view/result_info_utente_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
		            return;
				}

				
				try {
					
					PrenotazioneDB prenotazioneDB = new PrenotazioneDB();
					TurnoDB turnoDB = new TurnoDB();
					String msgError="";
					
					List<PrenotazioneBean> prntBeansRs = prenotazioneDB.getPrenotazioniByCodiceNoCompletatoStartDay(turnoBean);
						
					HttpSession session = request.getSession(false);
					if(session == null){
						address = "/WEB-INF/view/result_info_utente_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					    return;
					}
					
					List<InfoDipendenteApprovatoBean> dipBean = null;
					
					// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
					InfoDipendenteApprovatoBean dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
					
					InfoDipendenteApprovatoDB infoDipApprDB = new InfoDipendenteApprovatoDB();

					// recupero l'id dell'utenza del medico dal bean salvato nella sessione che corrispondera'
					// all'idDipendente nella tabella dipendente_approvato da cui posso recuperare l'id del centro vaccinale
					// in cui puo' esercitare perche' confermato da un operatore ASP
					UserBean userBean = (UserBean) session.getAttribute("userBean");
					int idDipendente = -1;
					
					if(userBean != null){
						idDipendente = userBean.getIndexUser();
						dipendenteApprovatoBean.setIdUser(idDipendente);
					}else {
						address = "/WEB-INF/view/result_info_utente_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					    return;
					}
					
					dipBean = infoDipApprDB.getDipByIdUser(dipendenteApprovatoBean);

					
					if(!prntBeansRs.isEmpty() && !dipBean.isEmpty()) {	//se esiste la prenotazione ed � attiva con stato non completato
						try {
							
							if(dipBean.get(0).getIdCentroVaccinale() != prntBeansRs.get(0).getIdCentroVaccinale()) {
								msgError = "Il codice prenotazione non &egrave; associato al centro vaccinale del medico";
							}else {
							
								String dataPrenotazione = DateUtility.getFormattedStringByTimestamp(prntBeansRs.get(0).getDataPrenotazioneTSSQL(), true);
								String currentDate = DateUtility.getFormattedStringCurrentDay();
								
								// se la data di prenotazione e' uguale alla data corrente allora inserisce l'utente a turno
								if(dataPrenotazione.equals(currentDate)) {
									
									turnoBean.setIdCentroVaccinale(prntBeansRs.get(0).getIdCentroVaccinale());
									
									List<TurnoBean> turnoBeans = turnoDB.getTurnoByCodicePrenotazioneStartDay(turnoBean);
									
									// se c'e' un turno inserito significa che si e' registrato all'accettazione e che gli spetta
									if(!turnoBeans.isEmpty()) {
										//recupero i dati dell'utente
										UserDB userDB = new UserDB();
										List<UserBean> userBeans = userDB.getUserByIdUser(prntBeansRs.get(0));
										
										if(!userBeans.isEmpty()) {
											request.setAttribute("userBeanUtente", userBeans.get(0));
											address = "/WEB-INF/view/result_info_utente_success.jsp";
											RequestDispatcher dispatcher = request.getRequestDispatcher(address);
											dispatcher.forward(request, response);
											return;
										}
											
									}else {		// se non c'e' un turno inserito significa che non e' si e' registrato all'accettazione o che non gli spetta
										msgError = "Il codice prenotazione esiste ma non &egrave; stato registrato all'accettazione! Si prega di informare chi di competenza";
									}
								}else {
									msgError = "Il codice inserito non &egrave; valido, la data di prenotazione non corrisponde alla data corrente.<br> Il codice inserito corrisponde ad una prenotazione prevista per giorno "+ dataPrenotazione;
								}
								
							}
						
						}catch(ExceptionManager exc) {
							exc.setLogError("InfoUtenteByCodPrenotazioneServlet", "Errore generico Servlet InfoUtenteByCodPrenotazioneServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
							address = "/WEB-INF/view/result_info_utente_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						}
					}else {
						msgError = "Il codice inserito non appartiene a nessuna prenotazione attiva";
					}
					
					request.setAttribute("msgError", msgError);
					address = "/WEB-INF/view/result_info_utente_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("InfoUtenteByCodPrenotazioneServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_info_utente_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("InfoUtenteByCodPrenotazioneServlet", "Errore generico Servlet InfoUtenteByCodPrenotazioneServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_info_utente_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("InfoUtenteByCodPrenotazioneServlet", "Errore generico Servlet InfoUtenteByCodPrenotazioneServlet; errore durante l'inserimento della prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











