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
import it.googlielmo93.apsw.utility.ExceptionManager;
import it.googlielmo93.apsw.utility.UserDB;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/LoginServlet","/login", "/googlielmo93/area-riservata", "/area-riservata"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
			
			HttpSession session = null;
        	UserBean userBean = null;
        	String address = "./index.jsp";
        			
        	try {	// se e' stato appena creato fa il login automatico recuperando i dati per il login dal bean passato nella request
				userBean = (UserBean) request.getAttribute("userBean");
			}catch(Exception exc) {
            	new ExceptionManager("LoginServlet",  "Errore Exception Servlet LoginServlet -> recupero attributo userBean. Eccezione lanciata: {0}", exc );
			}
        	

            String userName = null;
            String password = null;
            
        	
        	if (userBean != null) {

        		userName = userBean.getUserName();
        		password = userBean.getPassword();
        		
			}else 
				if(request.getParameter("userName") != null &&
					request.getParameter("password") != null){
				userName = request.getParameter("userName");
				password = request.getParameter("password");
				
				//  Hash concatenzazione di stringhe userName e password
            	password = ((Integer)(userName+password).hashCode()).toString();
			}else {
				address = "./view/login.jsp?msgError=loginFail";
                response.sendRedirect(address);
                return;
			}
            
			// se gia' loggato in un'altra area protetta, fa il logout e pulisce la sessione prima di procedere con il login in un'area protetta differente
            if(request.getRemoteUser() != null) {
	            try {
	            	request.logout();
	            	session = request.getSession();
	            	
	            	if(session != null)
	            		session.invalidate();
	            	
	            } catch(ServletException exc) {
	            	new ExceptionManager("LoginServlet", "Errore ServletException Servlet LoginServlet;  -> operazione di logout. Eccezione lanciata: {0}", exc );
	            	address = "./view/login.jsp?msgError=logoutFail";
	                response.sendRedirect(address);
	                return;
	            } catch(IllegalStateException exc) {
	            	new ExceptionManager("LoginServlet", "Errore IllegalStateException Servlet LoginServlet;  -> operazione di invalidazione sessione. Eccezione lanciata: {0}", exc );
	            	address = "./view/login.jsp?msgError=logoutFail";
	                response.sendRedirect(address);
	                return;
	            }
	            
            }
            

            address = "./view/login.jsp?msgError=loginError";
    		RequestDispatcher dispatcher_error = request.getRequestDispatcher(address);
            try {
            	if(userName != null && !userName.equals("") && password!= null && !password.equals(""))
            		request.login(userName, password);
            	else {
            		request.logout();
	            	session = request.getSession(false);
	            	
	            	if(session != null)
	            		session.invalidate();
             		dispatcher_error.forward(request, response);
            	}
            } catch(ServletException exc) {
            	new ExceptionManager("LoginServlet",  "Errore ServletException Servlet LoginServlet;  -> operazione di login. Eccezione lanciata: {0}", exc );
         		dispatcher_error.forward(request, response);
                return;
            }

				//Classe per interagire con il DB per la tabella user
				UserDB userDB = new UserDB();
				
				//UserBean di default da usare per inviare i dati di ricerca del Bean
				try {
					userBean = new UserBean();
				}catch(ExceptionManager exc) {
		        	exc.setLogError("LoginServlet", "Errore ExceptionManager Servlet LoginServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
					address = "./view/login.jsp?msgError=areaProtetta";
		            response.sendRedirect(address);
		            return;
				}
				
				userBean.setUserName(userName);
				
				// Metodo che effettua la query al DB per recuperare le info dello user che si sta loggando come lista di UserBean
				List <UserBean> rs = userDB.getUserByUserName(userBean);
				
				// se non e' vuoto, allora userBean diventa il ref dello UserBean Object restituito dalla query e la inserisco nella sessione
				if(!rs.isEmpty()) {	
					userBean = rs.get(0);
					//creo una nuova sessione, se gia' esiste sovrascrive il bean userBean con il bean contenente i dati recuperati dalla query al DB
					session = request.getSession();
					session.setAttribute("userBean", userBean);
					
				}else {
		        	new ExceptionManager("LoginServlet", "Errore generico Servlet LoginServlet -> Nessun dato recuperato durante il Login.");
			        address = "./view/login.jsp?msgError=loginFail";
		            response.sendRedirect(address);
	                return;
				}
		       			
            
            // Redirect area protetta in base al ruolo
            if(request.isUserInRole("medico") || userBean.getRuolo().equals("medico") ) {
            	address = "./protected/area_medico/index.jsp";
            }else if(request.isUserInRole("asp") || userBean.getRuolo().equals("asp") ) {
            	address = "./protected/area_asp/index.jsp";
            }else if(request.isUserInRole("user") || userBean.getRuolo().equals("user") ){
            	address = "./protected/area_utente/index.jsp";
            }else if(request.isUserInRole("accettazione") || userBean.getRuolo().equals("accettazione") ){
            	address = "./protected/area_accettazione/index.jsp";
            }else {
            	address = "./view/login.jsp?msgError=loginFail";
            }
            
            response.sendRedirect(address);
            return;
			
        }
        catch(Exception exc) {
        	new ExceptionManager("LoginServlet",  "Errore generico Servlet RegistrazioneServlet; errore durante la creazione dell'oggetto user di default. Eccezione lanciata: {0}", exc);
	        String address = "./view/login.jsp?msgError=loginFail";
            response.sendRedirect(address);
            return;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
