<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.InfoSomministrazioneBean"%>
<%@ page import="java.util.*"%>

<%
	Integer countSmnt = (Integer) request.getAttribute("countSmntRimanenti");
	@SuppressWarnings("unchecked")
	List<InfoSomministrazioneBean> smntBeans = (List<InfoSomministrazioneBean>) request.getAttribute("listSomministrazioni");

	String result = "{ \"somministrazioni\" : [";
    if(smntBeans != null && (smntBeans.size() > 0)){
		
   		for(int i=0; i < smntBeans.size()-1; i++) {
  			result += "{";

	   			result += "\"info_somministrazione\" : {";
	   			
		   			result += "\"tipo_vaccino\" : \"" + smntBeans.get(i).getTipoVaccino() + "\", ";
		   			result += "\"codice_vaccino\" : \"" + smntBeans.get(i).getCodiceVaccino() + "\", ";
		   			result += "\"lotto\" : \""+ smntBeans.get(i).getLotto() + "\", ";
		   			result += "\"data_somministrazione\" : \""+ smntBeans.get(i).getDataSomministrazione() + "\", ";
		   			result += "\"codice_prenotazione\" : \""+ smntBeans.get(i).getCodicePrenotazione()+ "\", ";
		   			result += "\"id_medico\" : \""+ smntBeans.get(i).getIdMedico() + "\"";
	   			
	   			result += "},";
	   			
	   			result += "\"info_centro_vaccinale\" : {";
	   			
		   			result += "\"id_centro_vaccinale\" : \"" + smntBeans.get(i).getIdCentroVaccinale() + "\", ";
		   			result += "\"denominazione\" : \"" + smntBeans.get(i).getDenominazione() + "\", ";
		   			result += "\"comune\" : \""+ smntBeans.get(i).getComune() + "\", ";
		   			result += "\"provincia\" : \""+ smntBeans.get(i).getProvincia() + "\", ";
		   			result += "\"regione\" : \""+ smntBeans.get(i).getRegione() + "\", ";
		   			result += "\"indirizzo\" : \""+ smntBeans.get(i).getIndirizzo() + "\"";
	   			
	   			result += "}";
   			
   			result += "} ,";
  		}
		
		result += "{";

			result += "\"info_somministrazione\" : {";
			
   			result += "\"tipo_vaccino\" : \"" + smntBeans.get(smntBeans.size()-1).getTipoVaccino() + "\", ";
   			result += "\"codice_vaccino\" : \"" + smntBeans.get(smntBeans.size()-1).getCodiceVaccino() + "\", ";
   			result += "\"lotto\" : \""+ smntBeans.get(smntBeans.size()-1).getLotto() + "\", ";
   			result += "\"data_somministrazione\" : \""+ smntBeans.get(smntBeans.size()-1).getDataSomministrazione() + "\", ";
   			result += "\"codice_prenotazione\" : \""+ smntBeans.get(smntBeans.size()-1).getCodicePrenotazione()+ "\", ";
   			result += "\"id_medico\" : \""+ smntBeans.get(smntBeans.size()-1).getIdMedico() + "\"";
			
			result += "},";
			
			result += "\"info_centro_vaccinale\" : {";
			
   			result += "\"id_centro_vaccinale\" : \"" + smntBeans.get(smntBeans.size()-1).getIdCentroVaccinale() + "\", ";
   			result += "\"denominazione\" : \"" + smntBeans.get(smntBeans.size()-1).getDenominazione() + "\", ";
   			result += "\"comune\" : \""+ smntBeans.get(smntBeans.size()-1).getComune() + "\", ";
   			result += "\"provincia\" : \""+ smntBeans.get(smntBeans.size()-1).getProvincia() + "\", ";
   			result += "\"regione\" : \""+ smntBeans.get(smntBeans.size()-1).getRegione() + "\", ";
   			result += "\"indirizzo\" : \""+ smntBeans.get(smntBeans.size()-1).getIndirizzo() + "\"";
			
			result += "}";
		
		result += "}";
		
   	}
    
	result += "], \"totale_somministrazioni_rimanenti\" : \"";
	if(countSmnt != null)
		result += countSmnt;
	else
		result += "0";
	
	result  += "\" }";
	
	response.getWriter().println(result);
%>