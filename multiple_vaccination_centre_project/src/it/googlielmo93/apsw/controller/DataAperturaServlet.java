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
import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.DateUtility;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.PostiDisponibiliDB;


/**
 * Servlet implementation class DataAperturaServlet
 */
@WebServlet({"/DataAperturaServlet", "/data_apertura"})
public class DataAperturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataAperturaServlet() {
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
					new ExceptionManager("DataAperturaServlet", "Errore durante il recupero della sessione. Eccezione lanciata: {0}");
					address = "/WEB-INF/view/result_data_apertura_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				    return;
				}
				
				PostiDisponibiliBean dataAperturaBean = null;
				int id_centro_vaccinale = -1;
				int posti_disponibili = -1;
				
				
				try {
						// creo un bean di tipo PostiDisponibiliBean con parametri di default
						dataAperturaBean = new PostiDisponibiliBean();
				}
				catch(ExceptionManager exc) {
			        	exc.setLogError("DataAperturaServlet", "Errore ExceptionManager Servlet DataAperturaServlet; errore durante la creazione dell'oggetto dataAperturaBean di default. Eccezione lanciata: {0}", exc);
			        	address = "/WEB-INF/view/result_data_apertura_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
				}

				
				UserBean userBean = (UserBean) session.getAttribute("userBean");
				
				//se esiste lo userBean e se inoltre e' un operatore asp a richiedere questa servlet
				if(userBean == null || !userBean.getRuolo().equals("asp")){
		        	new ExceptionManager("DataAperturaServlet", "Errore durante il recupero dei dati -> recupera dati userBean. Eccezione lanciata: {0}");
					address = "/WEB-INF/view/result_data_apertura_fail.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				    return;
				}
				
				try {
						String id_centro_vaccinale_string = request.getParameter("id_centro_vaccinale");
						if(id_centro_vaccinale_string != null)
							id_centro_vaccinale = Integer.parseInt(id_centro_vaccinale_string);
						
						dataAperturaBean.setIndexCentroVaccinale(id_centro_vaccinale);
						
						String posti_disponibili_string = request.getParameter("posti_disponibili");
						if(posti_disponibili_string != null)
							posti_disponibili = Integer.parseInt(posti_disponibili_string);
						
						dataAperturaBean.setPostiDisponibili(posti_disponibili);
					}
					catch(NumberFormatException exc){
				    	new ExceptionManager("DataAperturaServlet", "Errore durante il parseInt dell'attributo id_centro_vaccinale. Eccezione lanciata: {0}", exc);		            
				        address = "/WEB-INF/view/result_data_apertura_fail.jsp";
				        msgError = "Impossibile procedere con il recupero delle informazioni! Se il problema sussiste la preghiamo di contattarci.";
				    	request.setAttribute("msgError", msgError);
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
					}
	
						
				try {	
						String data_apertura = request.getParameter("data_apertura");
						
						dataAperturaBean.setDataPostiDisponibili(DateUtility.getTimestampByString(data_apertura));
						
						PostiDisponibiliDB dataAperturaDB = new PostiDisponibiliDB();
						
						List<PostiDisponibiliBean> dataAperturaRs = dataAperturaDB.getGiornoAperturaByIdCentroVaccinaleByDay(dataAperturaBean);
						
						//se � vuota significa che non e' ancora esistente una data per questo centro vaccinale per questa data specifica
						if(dataAperturaRs.isEmpty()) {
						
							//inserisce la data apertura
							if(dataAperturaDB.insertPostiDisponibiliBean(dataAperturaBean)) {
								address = "/WEB-INF/view/result_data_apertura_success.jsp";
								RequestDispatcher dispatcher = request.getRequestDispatcher(address);
								dispatcher.forward(request, response);
								return;
							}else {

								
								msgError = "Attenzione, non � stato possibile inserire la data apertura!";
							}
						
						}else {

							
							msgError = "Attenzione, data apertura gi&agrave; inserita per questo centro vaccinale!";
						}
						
					}
					catch(Exception exc) {
			        	new ExceptionManager("DataAperturaServlet", "Errore durante il recupero dei dati -> Operazioni DB. Eccezione lanciata: {0}", exc);
			        	address = "/WEB-INF/view/result_data_apertura_fail.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
				    }
				
				request.setAttribute("msgError", msgError);
				address = "/WEB-INF/view/result_data_apertura_fail.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
				return;
				
		}
        catch(Exception exc) {
        	new ExceptionManager("DataAperturaServlet", "Errore generico Servlet DataAperturaServlet; errore durante il recupero dei dati. Eccezione lanciata: {0}", exc);
        	address = "/WEB-INF/view/result_data_apertura_fail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("DataAperturaServlet", "Errore generico Servlet DataAperturaServlet; errore durante il recupero dei dati -> errore forward. Eccezione lanciata: {0}", excForward);
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











