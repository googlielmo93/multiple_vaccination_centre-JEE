<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.PrenotazioneBean"%>

<%
	PrenotazioneBean bean = (PrenotazioneBean) request.getAttribute("info_insert_prenotazione");

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Prenotazione inserita con successo!\", ";
		
    	if(bean != null){
    		result += "\"data\" : [ {";
	    		result += "\"codice_prenotazione\" : \""+bean.getCodicePrenotazione()+"\", ";
	    		result += "\"data_prenotazione\" : \""+bean.getDataPrenotazione()+"\"";
    		result += "} ]";
    	}
    
	result += " }";
	
	response.getWriter().println(result);
%>