package it.googlielmo93.apsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.googlielmo93.apsw.model.CentroVaccinaleBean;
import it.googlielmo93.apsw.model.PostiDisponibiliBean;
import it.googlielmo93.apsw.utility.CentroVaccinaleDB;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.PostiDisponibiliDB;

/**
 * Servlet implementation class CentroVaccinaleServlet
 */
@WebServlet({"/CentroVaccinaleServlet", "/centroVaccinale"})
public class CentroVaccinaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CentroVaccinaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
			String operation = "";
			
			try {
				operation = request.getParameter("operation");
			}catch(Exception exc) {
            	new ExceptionManager("CentroVaccinaleServlet",  "Errore Exception Servlet CentroVaccinaleServlet -> recupero attributo operation. Eccezione lanciata: {0}", exc );
			}
			
			
			switch(operation) {
				case "listCentroVaccinale":
					String regione = request.getParameter("regione");
					String provincia = request.getParameter("provincia");
					String comune = request.getParameter("comune");
					
					CentroVaccinaleBean cntrVaccBean = new CentroVaccinaleBean();
					cntrVaccBean.setRegione(regione);
					cntrVaccBean.setProvincia(provincia);
					if(comune != null)
						cntrVaccBean.setComune(comune);
					
					CentroVaccinaleDB cntrVaccDB = new CentroVaccinaleDB();
					
					List <CentroVaccinaleBean> rs_cntrVacc = new ArrayList<CentroVaccinaleBean>();
					if(comune != null)
						rs_cntrVacc = cntrVaccDB.getCentroVaccinaleByRegProvComune(cntrVaccBean);
					else
						rs_cntrVacc = cntrVaccDB.getCentroVaccinaleByRegProv(cntrVaccBean);
					
					request.setAttribute("listCentroVaccinale", rs_cntrVacc);
					address = "/WEB-INF/view/list_centro_vaccinale.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					
					try {
						dispatcher.forward(request, response);
						return;
					}catch(Exception excForward) {
			        	new ExceptionManager("CentroVaccinaleServlet", "Errore generico Servlet CentroVaccinaleServlet; errore durante il forward per il json -> errore forward. Eccezione lanciata: {0}", excForward);
					}
					
				break;
				case "listDateCentroVaccinale":
					Integer id_cntr = -1;
					
					try{
						id_cntr = Integer.parseInt(request.getParameter("id_centro_vaccinale"));
					}catch(NumberFormatException exc){
			        	new ExceptionManager("CentroVaccinaleServlet", "Errore durante il parseInt dell'attributo id_centro_vaccinale. Eccezione lanciata: {0}", exc);		            
			        	address = "/WEB-INF/view/posti_disponibili_centro_vaccinale.jsp";
						RequestDispatcher dispatcherError = request.getRequestDispatcher(address);
						dispatcherError.forward(request, response);
					}
					
					if(id_cntr != -1) {
						CentroVaccinaleBean cntrVaccBeanDate = new CentroVaccinaleBean();
						cntrVaccBeanDate.setIndexCentroVaccinale(id_cntr);
						
						PostiDisponibiliDB pstDispDB = new PostiDisponibiliDB();
						
						List<PostiDisponibiliBean> posti = pstDispDB.postiDisponibiliByIdCentroVaccinale(cntrVaccBeanDate);
						request.setAttribute("postiDisponibili", posti);
					}
					
					address = "/WEB-INF/view/posti_disponibili_centro_vaccinale.jsp";
					RequestDispatcher dispatcherCntrDate = request.getRequestDispatcher(address);
					
					try {
						dispatcherCntrDate.forward(request, response);
						return;
					}catch(Exception excForward) {
			        	new ExceptionManager("CentroVaccinaleServlet", "Errore generico Servlet CentroVaccinaleServlet; errore durante il forward per il json con date -> errore forward. Eccezione lanciata: {0}", excForward);
					}
					
				break;
			}
			
			
			
		}
	    catch(Exception exc) {
	    	new ExceptionManager("CentroVaccinaleServlet", "Errore generico Servlet CentroVaccinaleServlet. Eccezione lanciata: {0}", exc);
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
	
			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("CentroVaccinaleServlet", "Errore generico Servlet CentroVaccinaleServlet; errore forward try catch esterno. Eccezione lanciata: {0}", excForward);
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
