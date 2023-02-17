<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.PostiDisponibiliBean"%>
<%@ page import="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%@ page import="java.util.*"%>
<%
	@SuppressWarnings("unchecked")
	List<PostiDisponibiliBean> posti = (List<PostiDisponibiliBean>) request.getAttribute("postiDisponibili");
	String result = "{ \"posti\" : [";
	
    if(posti != null && (posti.size() > 0)){
    	
    		String dataFormatted = "";
    	
    		for(int i=0; i < posti.size()-1; i++) {
    			
    			try{
        			dataFormatted = posti.get(i).getFormattedDataPostiDisp();
        		} catch(ExceptionManager exc) {
        			dataFormatted = "";
    				exc.setLogError("posti_disponbili_jsp", "Errore ExceptionManager posti_disponbili_jsp -> getFormattedDataPostiDisp(). Eccezione lanciata: {0}", exc);
    			}
    			
   				result += "{";
   					result += "\"id_giorno_apertura\" : \"" + posti.get(i).getIdGiornoApertura() + "\", ";
	    			result += "\"id_centro_vaccinale\" : \"" + posti.get(i).getIndexCentroVaccinale() + "\", ";
	    			result += "\"posti_disponibili\" : \"" + posti.get(i).getPostiDisponibili() + "\", ";
	    			result += "\"data\" : \"" + dataFormatted + "\", ";
	    			result += "\"denominazione\" : \"" + posti.get(i).getDenominazione() + "\", ";
	       			result += "\"comune\" : \""+ posti.get(i).getComune() + "\", ";
	       			result += "\"provincia\" : \""+ posti.get(i).getProvincia() + "\", ";
	       			result += "\"regione\" : \""+ posti.get(i).getRegione() + "\", ";
	       			result += "\"indirizzo\" : \""+ posti.get(i).getIndirizzo() + "\"";
    			result += "} ,";
   			}
    		
    		try{
    			dataFormatted = posti.get(posti.size()-1).getFormattedDataPostiDisp();
    		} catch(ExceptionManager exc) {
    			dataFormatted = "";
				exc.setLogError("posti_disponbili_jsp", "Errore ExceptionManager posti_disponbili_jsp -> getFormattedDataPostiDisp(). Eccezione lanciata: {0}", exc);
			}
    		
    		
    		result += "{";
				result += "\"id_giorno_apertura\" : \"" + posti.get(posti.size()-1).getIdGiornoApertura() + "\", ";
				result += "\"id_centro_vaccinale\" : \"" + posti.get(posti.size()-1).getIndexCentroVaccinale() + "\", ";
				result += "\"posti_disponibili\" : \"" + posti.get(posti.size()-1).getPostiDisponibili() + "\", ";
				result += "\"data\" : \"" + dataFormatted + "\", ";
				result += "\"denominazione\" : \""+ posti.get(posti.size()-1).getDenominazione() + "\", ";
				result += "\"comune\" : \""+ posti.get(posti.size()-1).getComune() + "\", ";
				result += "\"provincia\" : \""+ posti.get(posti.size()-1).getProvincia() + "\", ";
				result += "\"regione\" : \""+ posti.get(posti.size()-1).getRegione() + "\", ";
				result += "\"indirizzo\" : \""+ posti.get(posti.size()-1).getIndirizzo() + "\"";
			result += "}";
   		}
    
	result += "] }";
	
	response.getWriter().println(result);
%>