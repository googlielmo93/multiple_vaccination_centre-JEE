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

import it.googlielmo93.apsw.model.PrenotazioneBean;
import it.googlielmo93.apsw.model.SomministrazioneBean;
import it.googlielmo93.apsw.model.TurnoBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.PrenotazioneDB;
import it.googlielmo93.apsw.utility.SomministrazioneDB;
import it.googlielmo93.apsw.utility.TurnoDB;



/**
 * Servlet implementation class InserisciSomministrazione
 */
@WebServlet({"/InserisciSomministrazione", "/somministra_vaccino"})
public class InserisciSomministrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserisciSomministrazione() {
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
				String codicePrenotazione = "", tipoVaccino = "", codiceVaccino = "", lotto = ""; 
				int id_utente = -1;

				try {
					String id_utente_string =  request.getParameter("id_utente");
					if(id_utente_string != null)
						id_utente = Integer.parseInt(id_utente_string);
				}
				catch(NumberFormatException exc){
			    	new ExceptionManager("InserisciSomministrazione", "Errore durante il parseInt dell'attributo id_dipendente. Eccezione lanciata: {0}", exc);		            
			        address = "/WEB-INF/view/result_smnt_fail.jsp";
			        String msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
			    	request.setAttribute("msgError", msgError);
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				
				codicePrenotazione = request.getParameter("codice_prenotazione");
				tipoVaccino = request.getParameter("tipo");
				codiceVaccino = request.getParameter("codice_vaccino");
				lotto = request.getParameter("lotto");
				
				
				if(id_utente == -1 || codicePrenotazione.equals("") || codiceVaccino.equals("") || tipoVaccino.equals("") || lotto.equals("")) {
					address = "/WEB-INF/view/result_smnt_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
		            return;
				}
				
				
				try {
					// creo un bean di tipo TurnoBean con parametri di default
					turnoBean = new TurnoBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("InserisciSomministrazione", "Errore ExceptionManager Servlet InserisciSomministrazione; errore durante la creazione dell'oggetto turnoBean di default. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_smnt_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
				
				turnoBean.setCodicePrenotazione(codicePrenotazione);
				
				
				try {
					
					String msgError="";

					HttpSession session = request.getSession(false);
					if(session == null){
						msgError = "Impossibile recuperare i dati! Si prega di effettuare nuovamente il login o di informare chi di competenza";
						request.setAttribute("msgError", msgError);
						address = "/WEB-INF/view/result_smnt_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
					}
					
					PrenotazioneDB prenotazioneDB = new PrenotazioneDB();
					TurnoDB turnoDB = new TurnoDB();
					
					List<PrenotazioneBean> prntBeansRs = prenotazioneDB.getPrenotazioniByCodiceNoCompletatoStartDay(turnoBean);

					UserBean userBean = (UserBean) session.getAttribute("userBean");
					
					List<TurnoBean> turnoBeans = turnoDB.getTurnoByCodicePrenotazioneStartDay(turnoBean);
					
					// se c'e' un turno inserito significa che si e' registrato all'accettazione e che gli spetta
					if(userBean != null && !turnoBeans.isEmpty()) {
							
							SomministrazioneBean stmtBean = new SomministrazioneBean();
							SomministrazioneDB stmtDB = new SomministrazioneDB();

							stmtBean.setCodiceVaccino(codiceVaccino);
							List <SomministrazioneBean> isVacc = stmtDB.getSomministrazioneByCodiceVaccino(stmtBean);
							
							if(isVacc.isEmpty()) {
									stmtBean.setTipoVaccino(tipoVaccino);
									stmtBean.setLotto(lotto);
									stmtBean.setCodicePrenotazione(codicePrenotazione);
									stmtBean.setIdUser(id_utente);
									stmtBean.setIdMedico(userBean.getIndexUser());
									stmtBean.setIdCentroVaccinale(prntBeansRs.get(0).getIdCentroVaccinale());
									
									// inserisce la somministrazione
									if(stmtDB.insertSomministrazione(stmtBean)){
										
										// aggiorno lo stato della prenotazione in completato
										prntBeansRs.get(0).setStatoCompletato(1);
										prenotazioneDB.updateStatoCompletatoByCodice(prntBeansRs.get(0));
									
										// rimuove il turno dalla lista turno
										turnoDB.removeTurnoByCodicePrenotazione(turnoBeans.get(0));
										
										address = "/WEB-INF/view/result_smnt_success.jsp";
										RequestDispatcher dispatcher = request.getRequestDispatcher(address);
										dispatcher.forward(request, response);
										return;
									}
									
							}else {
								msgError = "Il codice vaccino inserito &egrave; gi&agrave; stato registrato in un'altra somministrazione!";
							}
							
					}else {		// se non c'e' un turno inserito significa che non e' si e' registrato all'accettazione o che non gli spetta
						msgError = "Il codice prenotazione esiste ma non &egrave; stato registrato all'accettazione! Si prega di informare chi di competenza";
					}
									

					request.setAttribute("msgError", msgError);
					address = "/WEB-INF/view/result_smnt_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
					
				}
				catch(Exception exc) {
		        	new ExceptionManager("InserisciSomministrazione", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "/WEB-INF/view/result_smnt_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("InserisciSomministrazione", "Errore generico Servlet InserisciSomministrazione; errore durante l'inserimento della somministrazione. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_smnt_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("InserisciSomministrazione", "Errore generico Servlet InserisciSomministrazione; errore durante l'inserimento della somministrazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











