<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.InfoPrenotazioneBean"%>
<%@ page import="java.util.*"%>

<%
	@SuppressWarnings("unchecked")
	List<InfoPrenotazioneBean> prntBeans = (List<InfoPrenotazioneBean>) request.getAttribute("infoPrenotazioniActive");
	@SuppressWarnings("unchecked")
	List<InfoPrenotazioneBean> storicoBeans = (List<InfoPrenotazioneBean>) request.getAttribute("infoPrenotazioniNoActive");

	String result = "{ \"prenotazioni\" : [";
    if(prntBeans != null && (prntBeans.size() > 0)){
		
   		for(int i=0; i < prntBeans.size()-1; i++) {
  			result += "{";
  			
	   			result += "\"info_prenotazione\" : {";
	   			
	   				result += "\"codice_prenotazione\" : \""+ prntBeans.get(i).getCodicePrenotazione()+ "\", ";
		   			result += "\"data_prenotazione\" : \"" + prntBeans.get(i).getDataPrenotazioneDate() + "\", ";
		   			result += "\"data_inserimento\" : \""+ prntBeans.get(i).getDataInserimentoDate() + "\" ";
	   			result += "},";
	   			
	   			result += "\"info_centro_vaccinale\" : {";
	   			
		   			result += "\"id_centro_vaccinale\" : \"" + prntBeans.get(i).getIdCentroVaccinale() + "\", ";
		   			result += "\"denominazione\" : \"" + prntBeans.get(i).getDenominazione() + "\", ";
		   			result += "\"comune\" : \""+ prntBeans.get(i).getComune() + "\", ";
		   			result += "\"provincia\" : \""+ prntBeans.get(i).getProvincia() + "\", ";
		   			result += "\"regione\" : \""+ prntBeans.get(i).getRegione() + "\", ";
		   			result += "\"indirizzo\" : \""+ prntBeans.get(i).getIndirizzo() + "\"";
	   			
	   			result += "}";
   			
   			result += "} ,";
  		}
		
   			result += "{";
  			
	   			result += "\"info_prenotazione\" : {";
	   			
	   				result += "\"codice_prenotazione\" : \""+ prntBeans.get(prntBeans.size()-1).getCodicePrenotazione()+ "\", ";
		   			result += "\"data_prenotazione\" : \"" + prntBeans.get(prntBeans.size()-1).getDataPrenotazioneDate() + "\", ";
		   			result += "\"data_inserimento\" : \""+ prntBeans.get(prntBeans.size()-1).getDataInserimentoDate() + "\" ";
	   			result += "},";
	   			
	   			result += "\"info_centro_vaccinale\" : {";
	   			
		   			result += "\"id_centro_vaccinale\" : \"" + prntBeans.get(prntBeans.size()-1).getIdCentroVaccinale() + "\", ";
		   			result += "\"denominazione\" : \"" + prntBeans.get(prntBeans.size()-1).getDenominazione() + "\", ";
		   			result += "\"comune\" : \""+ prntBeans.get(prntBeans.size()-1).getComune() + "\", ";
		   			result += "\"provincia\" : \""+ prntBeans.get(prntBeans.size()-1).getProvincia() + "\", ";
		   			result += "\"regione\" : \""+ prntBeans.get(prntBeans.size()-1).getRegione() + "\", ";
		   			result += "\"indirizzo\" : \""+ prntBeans.get(prntBeans.size()-1).getIndirizzo() + "\"";
	   			
	   			result += "}";
			
			result += "}";
		
   	}
    
	result += "], \"storico\" : [";
	
    if(storicoBeans != null && (storicoBeans.size() > 0)){
		
   		for(int i=0; i < storicoBeans.size()-1; i++) {
  			result += "{";
  			
	   			result += "\"info_prenotazione\" : {";
	   			
	   				result += "\"codice_prenotazione\" : \""+ storicoBeans.get(i).getCodicePrenotazione()+ "\", ";
		   			result += "\"data_prenotazione\" : \"" + storicoBeans.get(i).getDataPrenotazioneDate() + "\", ";
		   			result += "\"data_inserimento\" : \""+ storicoBeans.get(i).getDataInserimentoDate() + "\" ";
	   			result += "},";
	   			
	   			result += "\"info_centro_vaccinale\" : {";
	   			
		   			result += "\"id_centro_vaccinale\" : \"" + storicoBeans.get(i).getIdCentroVaccinale() + "\", ";
		   			result += "\"denominazione\" : \"" + storicoBeans.get(i).getDenominazione() + "\", ";
		   			result += "\"comune\" : \""+ storicoBeans.get(i).getComune() + "\", ";
		   			result += "\"provincia\" : \""+ storicoBeans.get(i).getProvincia() + "\", ";
		   			result += "\"regione\" : \""+ storicoBeans.get(i).getRegione() + "\", ";
		   			result += "\"indirizzo\" : \""+ storicoBeans.get(i).getIndirizzo() + "\"";
	   			
	   			result += "}";
   			
   			result += "} ,";
  		}
		
   			result += "{";
  			
	   			result += "\"info_prenotazione\" : {";
	   			
	   				result += "\"codice_prenotazione\" : \""+ storicoBeans.get(storicoBeans.size()-1).getCodicePrenotazione()+ "\", ";
		   			result += "\"data_prenotazione\" : \"" + storicoBeans.get(storicoBeans.size()-1).getDataPrenotazioneDate() + "\", ";
		   			result += "\"data_inserimento\" : \""+ storicoBeans.get(storicoBeans.size()-1).getDataInserimentoDate() + "\" ";
	   			result += "},";
	   			
	   			result += "\"info_centro_vaccinale\" : {";
	   			
		   			result += "\"id_centro_vaccinale\" : \"" + storicoBeans.get(storicoBeans.size()-1).getIdCentroVaccinale() + "\", ";
		   			result += "\"denominazione\" : \"" + storicoBeans.get(storicoBeans.size()-1).getDenominazione() + "\", ";
		   			result += "\"comune\" : \""+ storicoBeans.get(storicoBeans.size()-1).getComune() + "\", ";
		   			result += "\"provincia\" : \""+ storicoBeans.get(storicoBeans.size()-1).getProvincia() + "\", ";
		   			result += "\"regione\" : \""+ storicoBeans.get(storicoBeans.size()-1).getRegione() + "\", ";
		   			result += "\"indirizzo\" : \""+ storicoBeans.get(storicoBeans.size()-1).getIndirizzo() + "\"";
	   			
	   			result += "}";
			
			result += "}";
		
   	}
    
	result += "] }";
	
	response.getWriter().println(result);
%>