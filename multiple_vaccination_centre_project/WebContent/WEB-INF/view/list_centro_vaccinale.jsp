<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.CentroVaccinaleBean"%>
<%@ page import="java.util.*"%>

<%
	@SuppressWarnings("unchecked")
	List<CentroVaccinaleBean> cntBeans = (List<CentroVaccinaleBean>) request.getAttribute("listCentroVaccinale");

	String result = "{ \"centri_vaccinali\" : [";
    if(cntBeans != null && (cntBeans.size() > 0)){
    	
   		for(int i=0; i < cntBeans.size()-1; i++) {
  			result += "{";
   			result += "\"id_centro_vaccinale\" : \"" + cntBeans.get(i).getIndexCentroVaccinale() + "\", ";
   			result += "\"denominazione\" : \"" + cntBeans.get(i).getDenominazione() + "\", ";
   			result += "\"comune\" : \""+ cntBeans.get(i).getComune() + "\", ";
   			result += "\"provincia\" : \""+ cntBeans.get(i).getProvincia() + "\", ";
   			result += "\"regione\" : \""+ cntBeans.get(i).getRegione() + "\", ";
   			result += "\"indirizzo\" : \""+ cntBeans.get(i).getIndirizzo() + "\"";
   			result += "} ,";
  			}
   		
   		result += "{";
		result += "\"id_centro_vaccinale\" : \"" + cntBeans.get(cntBeans.size()-1).getIndexCentroVaccinale() + "\", ";
		result += "\"denominazione\" : \""+ cntBeans.get(cntBeans.size()-1).getDenominazione() + "\", ";
		result += "\"comune\" : \""+ cntBeans.get(cntBeans.size()-1).getComune() + "\", ";
		result += "\"provincia\" : \""+ cntBeans.get(cntBeans.size()-1).getProvincia() + "\", ";
		result += "\"regione\" : \""+ cntBeans.get(cntBeans.size()-1).getRegione() + "\", ";
		result += "\"indirizzo\" : \""+ cntBeans.get(cntBeans.size()-1).getIndirizzo() + "\"";
		result += "}";
   	}
    
	result += "] }";
	
	response.getWriter().println(result);
%>