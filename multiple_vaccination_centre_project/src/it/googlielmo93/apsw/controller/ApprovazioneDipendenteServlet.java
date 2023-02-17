package it.googlielmo93.apsw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoDipendenteApprovatoDB;


/**
 * Servlet implementation class ApprovazioneDipendenteServlet
 */
@WebServlet({"/ApprovazioneDipendenteServlet", "/list_approvazioni_pendenti", "/approva_dipendente"})
public class ApprovazioneDipendenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovazioneDipendenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
			
			InfoDipendenteApprovatoBean dipendenteApprovatoBean = null;
			String msgError = "";
			Integer id_centro_vaccinale = -1;
			Integer id_dipendente = -1;
			
			// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
			dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();

			String id_cntr_vacc_string =  request.getParameter("id_centro_vaccinale");
			if(id_cntr_vacc_string != null)
				id_centro_vaccinale = Integer.parseInt(id_cntr_vacc_string);

			String id_dipendente_string =  request.getParameter("id_dipendente");
			if(id_dipendente_string != null)
				id_dipendente = Integer.parseInt(id_dipendente_string);
			
			InfoDipendenteApprovatoDB infoDipApprDB = new InfoDipendenteApprovatoDB();

			String operation = request.getParameter("operation");
			
			
			//recupero la lista dei dipendenti in attesa di approvazione
			if (operation != null && id_centro_vaccinale != null && id_centro_vaccinale != -1 && operation.equals("listApprovazioniPendenti")) {

				dipendenteApprovatoBean.setIdCentroVaccinale(id_centro_vaccinale);
				
				List<InfoDipendenteApprovatoBean> rsDipApprovato = infoDipApprDB.getDipNoApprByIdCentroVaccinale(dipendenteApprovatoBean);
				
				if(!rsDipApprovato.isEmpty()) {
					request.setAttribute("dipendente_approvato_beans", rsDipApprovato);
					address = "/WEB-INF/view/result_approvazione_success.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
				}
				
			}
			else //approvazione dipendente pendente
			if (operation != null && id_dipendente != null && id_dipendente != -1 && operation.equals("approva_richiesta")) {

				dipendenteApprovatoBean.setIdUser(id_dipendente);
				
				
				if(infoDipApprDB.approvaByIdUser(dipendenteApprovatoBean)) {
					address = "/WEB-INF/view/result_approvazione_confermato_success.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
				}
			}
			
			address = "/WEB-INF/view/result_approvazione_fail.jsp";
			msgError = "Impossibile recuperare le informazioni! Non &egrave; presente nessuna richiesta di approvazione.";
        	request.setAttribute("msgError", msgError);
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			return;
				
		}
		catch(NumberFormatException exc){
	    	new ExceptionManager("ApprovazioneDipendenteServlet", "Errore durante il parseInt dell'attributo id_dipendente. Eccezione lanciata: {0}", exc);		            
	        address = "/WEB-INF/view/result_approvazione_fail.jsp";
	        String msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
	    	request.setAttribute("msgError", msgError);
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			
		}
        catch(Exception exc) {
        	new ExceptionManager("ApprovazioneDipendenteServlet", "Errore generico Servlet ApprovazioneDipendenteServlet; errore durante l'inserimento. Eccezione lanciata: {0}", exc);
        	String msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
        	request.setAttribute("msgError", msgError);
			address = "./view/login.jsp?msgError=areaProtetta";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("ApprovazioneDipendenteServlet", "Errore generico Servlet ApprovazioneDipendenteServlet; errore durante la prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











