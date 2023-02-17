<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.UserBean"%>
<%@ page import="it.googlielmo93.apsw.utility.DateUtility"%>
<%@ page import="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%
	UserBean bean = (UserBean) request.getAttribute("userBeanUtente");

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Recupero dati utente avvenuto con successo!\", ";
		

		result += "\"data\" : [ {";
    	if(bean != null){
		    result += "\"info_utente\" : {";
				result += "\"id_utente\" : \""+ bean.getIndexUser() + "\", ";
				result += "\"ruolo\" : \""+ bean.getRuolo() + "\", ";
				result += "\"nome\" : \"" + bean.getNome() + "\", ";
				result += "\"cognome\" : \""+ bean.getCognome() + "\", ";
				result += "\"email\" : \""+ bean.getEmail() + "\", ";
				result += "\"sesso\" : \""+ bean.getSesso() + "\", ";
				result += "\"codice_fiscale\" : \""+ bean.getCodiceFiscale() + "\", ";
				result += "\"data_nascita\" : \""+ bean.getDataNascita() + "\", ";
				result += "\"data_registrazione\" : \""+ bean.getDataRegistrazione() + "\", ";
				result += "\"regione\" : \""+ bean.getRegioneResidenza() + "\", ";
				result += "\"citta\" : \""+ bean.getProvinciaResidenza() + "\", ";
				result += "\"provincia\" : \""+ bean.getCittaResidenza() + "\" ";
			result += "}";
    	}

		result += "} ]";
    
	result += " }";
	
	response.getWriter().println(result);
%>