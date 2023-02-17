<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	String msgError = (String) request.getAttribute("msgError");
	if(msgError == null)
		msgError = "";

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \""+msgError+"\", ";
		result += "\"data\" : []";
    
	result += " }";
	
	response.getWriter().println(result);
%>