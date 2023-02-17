<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%@ page import="java.util.*"%>

<%
	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Dipendente abilitato con successo!\", ";
		result += "\"data\" : []";
		
	result += " }";
	
	response.getWriter().println(result);
%>