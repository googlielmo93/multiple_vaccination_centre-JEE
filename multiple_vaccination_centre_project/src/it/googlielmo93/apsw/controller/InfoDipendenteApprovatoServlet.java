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

import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.InfoDipendenteApprovatoDB;


/**
 * Servlet implementation class InfoDipendenteApprovatoServlet
 */
@WebServlet({"/InfoDipendenteApprovatoServlet", "/info_dipendente_approvato", "/richiesta_approvazione"})
public class InfoDipendenteApprovatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoDipendenteApprovatoServlet() {
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
			String msgError = "";
			int idDipendente = -1;
			Integer id_centro_vaccinale = -1;
			
			HttpSession session = request.getSession(false);
			if(session == null){
				address = "/WEB-INF/view/result_approvazione_fail.jsp";
				msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
            	request.setAttribute("msgError", msgError);
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
			    return;
			}
			
			
        	// creo un bean di tipo InfoDipendenteApprovatoBean con parametri di default
			InfoDipendenteApprovatoBean dipendenteApprovatoBean = new InfoDipendenteApprovatoBean();
			
			userBean = (UserBean) session.getAttribute("userBean");
			
			if(userBean != null){
				idDipendente = userBean.getIndexUser();
				dipendenteApprovatoBean.setIdUser(idDipendente);
			}else {
				address = "/WEB-INF/view/result_approvazione_fail.jsp";
				msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
            	request.setAttribute("msgError", msgError);
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
			    return;
			}
			
			
			InfoDipendenteApprovatoDB infoDipApprDB = new InfoDipendenteApprovatoDB();
			
			List<InfoDipendenteApprovatoBean> dipendenteApprovatoBeansRs = infoDipApprDB.getDipByIdUser(dipendenteApprovatoBean);
			String operation = request.getParameter("operation");
			
			//se non e' vuota allora vuol dire che e' gia' stato approvato o in fase di approvazione
			if(!dipendenteApprovatoBeansRs.isEmpty()) {
				// in base al flag inserito nella risposta, nel js distingue tra richiesta di approvazione pendente e approvata
				request.setAttribute("dipendente_approvato_beans", dipendenteApprovatoBeansRs);
				address = "/WEB-INF/view/result_approvazione_success.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
				return;
				
			}else {
				// se non trova nessuna relazione e inoltre l'operazione e' di inserimento procede con l'inserimento
				String id_cntr_vacc_string =  request.getParameter("id_centro_vaccinale");
				
				if(id_cntr_vacc_string != null)
					id_centro_vaccinale = Integer.parseInt(id_cntr_vacc_string);

				if (operation != null && id_centro_vaccinale != null && id_centro_vaccinale != -1 && operation.equals("richiesta_approvazione")) {
					
					dipendenteApprovatoBean.setIdUser(idDipendente);
					dipendenteApprovatoBean.setIdCentroVaccinale(id_centro_vaccinale);
					dipendenteApprovatoBean.setFlagApprovato(0);
					
					if(infoDipApprDB.insertInfoDipendenteApprovatoBean(dipendenteApprovatoBean)) {
						dipendenteApprovatoBeansRs.clear();
						dipendenteApprovatoBeansRs = infoDipApprDB.getDipByIdUser(dipendenteApprovatoBean);
						request.setAttribute("dipendente_approvato_beans", dipendenteApprovatoBeansRs);
						address = "/WEB-INF/view/result_approvazione_success.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
					}
				}
				
			}
			
			address = "/WEB-INF/view/result_approvazione_fail.jsp";
			msgError = "Impossibile recuperare le informazioni! Non &egrave; presente nessuna richiesta di approvazione.";
        	request.setAttribute("msgError", msgError);
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			return;
			
		
		}catch(NumberFormatException exc){
	    	new ExceptionManager("InfoDipendenteApprovatoServlet", "Errore durante il parseInt dell'attributo idDipendente. Eccezione lanciata: {0}", exc);		            
	        address = "/WEB-INF/view/result_approvazione_fail.jsp";
	        String msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
	    	request.setAttribute("msgError", msgError);
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			return;
		}
        catch(Exception exc) {
        	new ExceptionManager("InfoDipendenteApprovatoServlet", "Errore generico Servlet InfoDipendenteApprovatoServlet; errore durante la prenotazione. Eccezione lanciata: {0}", exc);
        	String msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
        	request.setAttribute("msgError", msgError);
			address = "./view/result_approvazione_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("InfoDipendenteApprovatoServlet", "Errore generico Servlet InfoDipendenteApprovatoServlet; errore durante la prenotazione -> errore forward. Eccezione lanciata: {0}", excForward);
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











