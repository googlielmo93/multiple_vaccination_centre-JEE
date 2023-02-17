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

import it.googlielmo93.apsw.model.CentroVaccinaleBean;
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.CentroVaccinaleDB;


/**
 * Servlet implementation class CentroVaccinaleAddServlet
 */
@WebServlet({"/CentroVaccinaleAddServlet", "/centro_vaccinale_add"})
public class CentroVaccinaleAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CentroVaccinaleAddServlet() {
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
			
				HttpSession session = request.getSession(false);
				if(session == null){
					new ExceptionManager("CentroVaccinaleAddServlet", "Errore durante il recupero della sessione. Eccezione lanciata: {0}");
					address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				    return;
				}
				
				CentroVaccinaleBean centroVaccinaleBean = null;
				
				try {
						// creo un bean di tipo CentroVaccinaleBean con parametri di default
						centroVaccinaleBean = new CentroVaccinaleBean();
				}
				catch(ExceptionManager exc) {
			        	exc.setLogError("CentroVaccinaleAddServlet", "Errore ExceptionManager Servlet CentroVaccinaleAddServlet; errore durante la creazione dell'oggetto centroVaccinaleBean di default. Eccezione lanciata: {0}", exc);
			        	address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
				}

				
				UserBean userBean = (UserBean) session.getAttribute("userBean");
				
				//se esiste lo userBean e se inoltre e' un operatore asp a richiedere questa servlet
				if(userBean == null || !userBean.getRuolo().equals("asp")){
		        	new ExceptionManager("CentroVaccinaleAddServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
					address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				    return;
				}
				
				try {
						String regione = request.getParameter("regione");
						centroVaccinaleBean.setRegione(regione);
						
						String provincia = request.getParameter("provincia");
						centroVaccinaleBean.setProvincia(provincia);
						
						String comune = request.getParameter("comune");
						centroVaccinaleBean.setComune(comune);
						
						String denominazione = request.getParameter("denominazione");
						centroVaccinaleBean.setDenominazione(denominazione);
						
						String indirizzo = request.getParameter("indirizzo");
						centroVaccinaleBean.setIndirizzo(indirizzo);

	
						
						CentroVaccinaleDB centroVaccinaleDB = new CentroVaccinaleDB();
						
						List<CentroVaccinaleBean> centroVaccinaleRS = centroVaccinaleDB.getCentroVaccinaleByAllData(centroVaccinaleBean);
						
						//se e' vuota significa che non e' ancora esistente un centro vaccinale con questi dati
						if(centroVaccinaleRS.isEmpty()) {
						
							//inserisce il centro vaccinale
							if(centroVaccinaleDB.insertCentroVaccinale(centroVaccinaleBean)) {
								address = "/WEB-INF/view/result_centro_vaccinale_add_success.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
								return;
							}else {
								msgError = "Attenzione, non &egrave; stato possibile inserire il centro vaccinale!";
							}
						
						}else {
							msgError = "Attenzione, centro vaccinale gi&agrave; esistente!";
						}
						
					}
					catch(Exception exc) {
			        	new ExceptionManager("CentroVaccinaleAddServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
			        	address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
				    }
				
				request.setAttribute("msgError", msgError);
				address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
				return;
				
		}
        catch(Exception exc) {
        	new ExceptionManager("CentroVaccinaleAddServlet", "Errore generico Servlet CentroVaccinaleAddServlet; errore durante il recupero dei dati. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_centro_vaccinale_add_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("CentroVaccinaleAddServlet", "Errore generico Servlet CentroVaccinaleAddServlet; errore durante il recupero dei dati -> errore forward. Eccezione lanciata: {0}", excForward);
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











