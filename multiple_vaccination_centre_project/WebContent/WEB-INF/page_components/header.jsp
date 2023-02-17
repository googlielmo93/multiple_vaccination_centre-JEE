<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import ="it.googlielmo93.apsw.model.UserBean"%>
<%@ page import ="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%
UserBean userBeanProfile = null;
try{
	userBeanProfile = (UserBean) session.getAttribute("userBean");
%>


<!DOCTYPE html>
<html>
	<head>
	
		<%
			String title = "Centro Vaccinazioni";
			if(request.getAttribute("title_page") != null)
				title = (String) request.getAttribute("title_page");
		%>
	
		<title><%= title %></title>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    
	    <link rel="icon" href="/googlielmo93/images/favicon/logo.ico">
		<link rel="stylesheet" href="/googlielmo93/Resources/bootstrap/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="/googlielmo93/Resources/fontawesome/css/all.css"/>
		<link rel="stylesheet" href="/googlielmo93/css/style.css"/>

	</head>
<body class="d-flex flex-column min-vh-100">

<!-- NAVBAR INCLUDE -->
<%@ include file = "/WEB-INF/page_components/navbar.jsp" %>

<!-- load spinner -->
<div class="spinner-border spinner-border-sm text-info spinnerCustom ml-2" role="status"  id="spinnerLoad" style="display:none;"></div>

<%
	
	if(userBeanProfile != null){
%>
	<!-- modale dati personali utente loggato -->				
	<div class="modal fade" id="infoUtenteModaleDatiPersonali" tabindex="-1" role="dialog" aria-labelledby="infoUtenteExtraTable" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="infoUtenteModaleTitle">Informazioni utente</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
		      		<div id="userBean_div">
						<ul class="list-group" style="text-align:left!important;">
	    					<li class="list-group-item">Nome: <%=userBeanProfile.getNome()%></li>
	    					<li class="list-group-item">Cognome:  <%=userBeanProfile.getCognome()%> </li>
	    					<li class="list-group-item">Email:  <%=userBeanProfile.getEmail()%> </li>
	    					<li class="list-group-item">Sesso:  <%=userBeanProfile.getSesso()%> </li>
	    					<li class="list-group-item">Codice Fiscale :  <%=userBeanProfile.getCodiceFiscale()%> </li>
	    					<%
	    						if(userBeanProfile.getRuolo().equals("user")){ 
	    					%>
		    					<li class="list-group-item">Data di nascita:  <%=userBeanProfile.getDataNascita()%> </li>
		    					<li class="list-group-item">Regione di residenza:  <%=userBeanProfile.getRegioneResidenza()%> </li>
		    					<li class="list-group-item">Provincia di residenza:  <%=userBeanProfile.getProvinciaResidenza()%> </li>
		    					<li class="list-group-item">Comune di residenza:  <%=userBeanProfile.getCittaResidenza()%> </li>
	    					<% } %>
		    			</ul> 
	    			</div>
	    		</div>
	    		<div class="modal-footer">
		    		<button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi Info</button>
		    		<button type="button" class="btn btn-secondary" id="modifica_dati_utente_loggato">Modifica</button>
		    	</div>
		    </div>
		</div>
	</div>

<%
	}

}catch(Exception exc) {
	new ExceptionManager("HeaderJSP", "Errore generico JSP HeaderJSP; errore durante il recupero delle info userBeanProfile dalla session per la modale info utente. Eccezione lanciata: {0}", exc);
	String address = "./error_generico.jsp";
	RequestDispatcher dispatcher = request.getRequestDispatcher(address);

	try {
		dispatcher.forward(request, response);
	}catch(Exception excForward) {
		new ExceptionManager("HeaderJSP", "Errore generico JSP HeaderJSP; errore durante il forward. Eccezione lanciata: {0}", excForward);
	}
}
%>
							
							