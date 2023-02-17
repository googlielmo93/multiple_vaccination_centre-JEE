package it.googlielmo93.apsw.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.googlielmo93.apsw.model.UserBean;
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.UserDB;


/**
 * Servlet implementation class UploadDatiAccessoServlet
 */
@WebServlet({"/UploadDatiAccessoServlet", "/upload_dati_accesso"})
public class UploadDatiAccessoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDatiAccessoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String address = "./index.jsp";
		
		try {
			
				HttpSession session = request.getSession(false);
				if(session == null){
					address = "/googlielmo93/view/login.jsp?msgError=areaProtetta";
		        	response.sendRedirect(address);
		        	return;
				}

	        	UserBean userBean = null;
	        			
	        	try {
					userBean = (UserBean) session.getAttribute("userBean");
					
					if (userBean == null) {
		            	address = "/googlielmo93/view/login.jsp?msgError=areaProtetta";
		        		response.sendRedirect(address);
		        		return;
					}
					
					
				}catch(Exception exc) {
	            	new ExceptionManager("UploadDatiAccessoServlet",  "Errore Exception Servlet UploadDatiAccessoServlet -> recupero attributo userBean. Eccezione lanciata: {0}", exc );
	            	address = "/googlielmo93/view/login.jsp?msgError=areaProtetta";
	        		response.sendRedirect(address);
	        		return;
				}
	        	
					
				String emailMod = userBean.getEmail();
				if(request.getParameter("email") != null && !request.getParameter("email").equals(""))
					emailMod = request.getParameter("email");
				
				userBean.setEmail(emailMod);
				

				try {
					UserDB userDB = new UserDB();
					boolean usernameModificato = false;
					
					//verifico che l'username inserito nel form se diverso dall'username dell'utente loggato non sia gi� usato
					if( request.getParameter("userName") != null && !request.getParameter("userName").equals("")
						&& !request.getParameter("userName").equals(userBean.getUserName()) ) {

						userBean.setUserName(request.getParameter("userName"));
						
						//se non appartiene a nessuno essendo univoco a differenza del CF che deve essere accoppiato al ruolo (multiaccount)
						if(!userDB.isUserByUserName(userBean)){
							userBean.setUserName(request.getParameter("userName"));
							usernameModificato = true;
						}else {
							address = "./view/modifica_utenza.jsp?msgError=userExistUserName";
							RequestDispatcher dispatcher = request.getRequestDispatcher(address);
							dispatcher.forward(request, response);
							return;
						}
						
					}
					
					try {
						
						String passwordMod = userBean.getPassword();
						
						if(request.getParameter("password") != null && !request.getParameter("password").equals("")) {
							passwordMod = request.getParameter("password");
							userBean.setPasswordHashCode(passwordMod);
						}else {
							address = "./view/modifica_utenza.jsp?msgError=modifica_utenzaFail";
							response.sendRedirect(address);
							return;
						}
						
					}catch(ExceptionManager exc) {
						exc.setLogError("UploadDatiAccessoServlet", "Errore generico Servlet UploadDatiAccessoServlet; errore durante la creazione dell'inserimento dei parametri nell'oggetto UserBean; Nei log precedenti sono descritti i dettagli. Eccezione lanciata: {0}", exc);
						address = "./view/modifica_utenza.jsp?msgError=modifica_utenzaFail";
			            response.sendRedirect(address);
						return;
					}
					
					
					if(userDB.isUserByCFRole(userBean)) { 
							
						if(userDB.updateDatiAccessoByCFRuolo(userBean)) { // true inserito correttamente
							request.logout();
			            	session.invalidate();
							address = "./view/login.jsp";
							response.sendRedirect(address);
							return;

						}else {
							address = "./view/modifica_utenza.jsp?msgError=modifica_utenzaFail";
							response.sendRedirect(address);
							return;
						}
						
					}else {
						address = "./view/modifica_utenza.jsp?msgError=userExistCF";
						RequestDispatcher dispatcher = request.getRequestDispatcher(address);
						dispatcher.forward(request, response);
						return;
					}
				}
				catch(Exception exc) {
		        	new ExceptionManager("UploadDatiAccessoServlet", "Servlet UploadDatiAccessoServlet; Errore durante la verifica esistenza utente -> Operazioni DB. Eccezione lanciata: {0}", exc);
		        	address = "./view/modifica_utenza.jsp?msgError=modificaUtenzaFail";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
					return;
			    }
		}
        catch(Exception exc) {
        	new ExceptionManager("UploadDatiAccessoServlet", "Errore generico Servlet UploadDatiAccessoServlet; errore durante la modifica_utenza. Eccezione lanciata: {0}", exc);
			address = "./view/modifica_utenza.jsp?msgError=modifica_utenzaFail";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);

			try {
				dispatcher.forward(request, response);
			}catch(Exception excForward) {
	        	new ExceptionManager("UploadDatiAccessoServlet", "Errore generico Servlet UploadDatiAccessoServlet; errore durante la modifica_utenza -> errore forward. Eccezione lanciata: {0}", excForward);
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
