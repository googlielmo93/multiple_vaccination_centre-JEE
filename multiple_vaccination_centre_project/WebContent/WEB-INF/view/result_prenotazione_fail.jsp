<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.PrenotazioneBean"%>

<%

	String result = "{ \"result\" : \"fail\",";

		result += "\"message\" : \"Attenzione! Impossibile procedere con la prenotazione, si prega di riprovare.\", ";
		result += "\"data\" : []";
    
	result += " }";
	
	response.getWriter().println(result);
%>