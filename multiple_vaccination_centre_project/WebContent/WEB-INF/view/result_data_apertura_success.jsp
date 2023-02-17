<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Inserimento data apertura avvenuto con successo!\", ";
		

		result += "\"data\" : []";
    
	result += " }";
	
	response.getWriter().println(result);
%>