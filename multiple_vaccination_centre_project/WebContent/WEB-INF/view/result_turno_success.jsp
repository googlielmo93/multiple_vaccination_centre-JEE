<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.TurnoBean"%>

<%
	TurnoBean bean = (TurnoBean) request.getAttribute("turno_bean");

	String result = "{ \"result\" : \"success\",";

		result += "\"message\" : \"Inserimento in coda avvenuto con successo!\", ";
		

		result += "\"data\" : [ {";
    	if(bean != null){
    			result += "\"id_turno\" : \""+bean.getIdTurno()+"\", ";
    			result += "\"id_centro_vaccinale\" : \""+bean.getIdCentroVaccinale()+"\", ";
	    		result += "\"codice_prenotazione\" : \""+bean.getCodicePrenotazione()+"\", ";
	    		result += "\"data_inserimento\" : \""+bean.getDataInserimento()+"\"";
    	}

		result += "} ]";
    
	result += " }";
	
	response.getWriter().println(result);
%>