<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.PostiDisponibiliBean"%>

<%

	PostiDisponibiliBean bean = (PostiDisponibiliBean) request.getAttribute("tabelloneBeans");

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Dati recuperati con successo!\", ";
		

		result += "\"data\" : [ {";
    	if(bean != null){
    			result += "\"id_turno\" : \""+bean.getIdGiornoApertura()+"\", ";
    			result += "\"id_centro_vaccinale\" : \""+bean.getIndexCentroVaccinale()+"\", ";
	    		result += "\"data\" : \""+bean.getDataPostiDisponibili()+"\", ";
	    		result += "\"posti_disponibili\" : \""+bean.getPostiDisponibili()+"\", ";
	    		result += "\"numero_tabellone\" : \""+bean.getNumeroTabellone()+"\"";
    	}

		result += "} ]";
    
	result += " }";

	response.getWriter().println(result);
%>