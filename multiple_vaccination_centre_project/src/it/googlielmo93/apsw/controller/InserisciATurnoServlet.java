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


/**
 * Servlet implementation class InserisciATurnoServlet
 */
@WebServlet({"/InserisciATurnoServlet", "/inserisci_turno"})
public class InserisciATurnoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserisciATurnoServlet() {
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
		        	exc.setLogError("InserisciATurnoServlet", "Errore ExceptionManager Servlet InserisciATurnoServlet; errore durante la creazione dell'oggetto turnoBean di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}


				if(request.getParameter("codiceUtenteTurno") != null){
					codiceUtenteTurno = request.getParameter("codiceUtenteTurno");
					turnoBean.setCodicePrenotazione(codiceUtenteTurno);
				}else {
					address = "/WEB-INF/view/result_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
		            return;
				}

				
				try {
					
					HttpSession session = request.getSession(false);
					if(session == null){
						address = "/WEB-INF/view/result_turno_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					    return;
					}

					PrenotazioneDB prenotazioneDB = new PrenotazioneDB();
					TurnoDB turnoDB = new TurnoDB();
					String msgError="";
					
					List<PrenotazioneBean> prntBeansRs = prenotazioneDB.getPrenotazioniByCodiceNoCompletatoStartDay(turnoBean);
						
					List<InfoDipendenteApprovatoBean> dipBean = null;
					
					// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
					InfoDipendenteApprovatoBean dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
					
					InfoDipendenteApprovatoDB infoDipApprDB = new InfoDipendenteApprovatoDB();

					UserBean userBean = (UserBean) session.getAttribute("userBean");
					int idDipendente = -1;
					
					if(userBean != null){
						idDipendente = userBean.getIndexUser();
						dipendenteApprovatoBean.setIdUser(idDipendente);
					}else {
						address = "/WEB-INF/view/result_turno_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					    return;
					}
					
					dipBean = infoDipApprDB.getDipByIdUser(dipendenteApprovatoBean);

					
					if(!prntBeansRs.isEmpty() && !dipBean.isEmpty()) {	//se esiste la prenotazione ed � attiva con stato non completato
						try {
							
							if(dipBean.get(0).getIdCentroVaccinale() != prntBeansRs.get(0).getIdCentroVaccinale()) {
								msgError = "Il codice prenotazione non &egrave; associato al centro vaccinale dell'operatore!";
							}else {
							
								String dataPrenotazione = DateUtility.getFormattedStringByTimestamp(prntBeansRs.get(0).getDataPrenotazioneTSSQL(), true);
								String currentDate = DateUtility.getFormattedStringCurrentDay();
								// se la data di prenotazione e' uguale alla data corrente allora inserisce l'utente a turno
								if(dataPrenotazione.equals(currentDate)) {
									
									turnoBean.setIdCentroVaccinale(prntBeansRs.get(0).getIdCentroVaccinale());
									turnoBean.setDataInserimento(DateUtility.getCurrentTimestamp());
									
									List<TurnoBean> turnoBeans = turnoDB.getTurnoByCodicePrenotazioneStartDay(turnoBean);
									
									// se non c'e' un'altro turno
									if(turnoBeans.isEmpty()) {
										
										if(turnoDB.insertTurnoBean(turnoBean)) {
											
											request.setAttribute("turno_bean", turnoBean);
											address = "/WEB-INF/view/result_turno_success.jsp";
											RequestDispatcher dispatcher = request.getRequestDispatcher(address);
											dispatcher.forward(request, response);
											return;
										}
									}else {
										msgError = "Il codice prenotazione &egrave; gi&agrave; stato inserito in coda per la data odierna";
									}
								}else {
									msgError = "Il codice inserito non &egrave; valido, la data di prenotazione non corrisponde alla data corrente.<br> Il codice inserito corrisponde ad una prenotazione prevista per giorno "+ dataPrenotazione;
								}
								
							}
						
						}catch(ExceptionManager exc) {
							exc.setLogError("InserisciATurnoServlet", "Errore generico Servlet InserisciATurnoServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
							address = "/WEB-INF/view/result_turno_fail.jsp";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
						}
					}else {
						msgError = "Il codice inserito non appartiene a nessuna prenotazione attiva";
					}
					
					request.setAttribute("msgError", msgError);
					address = "/WEB-INF/view/result_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("InserisciATurnoServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_turno_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("InserisciATurnoServlet", "Errore generico Servlet InserisciATurnoServlet; errore durante l'inserimento della prenotazione. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_turno_fail.jsp";
        	RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("InserisciATurnoServlet", "Errore generico Servlet InserisciATurnoServlet; errore durante l'inserimento della prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











