<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.InfoTurnoBean"%>
<%@ page import="it.googlielmo93.apsw.utility.DateUtility"%>
<%@ page import="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%@ page import="java.util.*"%>

<%
	@SuppressWarnings("unchecked")
	List <InfoTurnoBean> beans = (List<InfoTurnoBean>) request.getAttribute("listInfoTurnoBeans");



String result = "{ \"result\" : \"success\",";
	
	result += "\"message\" : \"Lista utenti a turno!\", ";
	result += "\"data\" : [ ";
	

	
	if(beans != null && (beans.size() > 0)){
		for(int i=0; i < beans.size(); i++) {
		                        
		  result += "{";
		                        
			result += "\"lista_utenti\" : ";
	
			result += "{";
				
				result += "\"info_prenotazione\" : {";
					result += "\"codice_prenotazione\" : \""+ beans.get(i).getCodicePrenotazione()+ "\", ";
					result += "\"stato_completato\" : \""+ beans.get(i).getStato_completato()+ "\", ";
					String dataPrenotazione = "--/--/--";
					try{
						dataPrenotazione = DateUtility.getFormattedStringByTimestamp((beans.get(i).getDataPrenotazione()), true);
					}catch(Exception exc) {
			        	new ExceptionManager("result_list_turno_success.jsp", "Errore generico JSP result_list_turno_success.jsp; errore durante il recupero della data di prenotazione. Eccezione lanciata: {0}", exc);
			        }
					
					result += "\"data_prenotazione\" : \""+ dataPrenotazione + "\" ";
					
				result += "},";
				
				result += "\"info_utente\" : {";
					result += "\"id_utente\" : \""+ beans.get(i).getIdUser() + "\", ";
					result += "\"nome\" : \"" + beans.get(i).getNome() + "\", ";
					result += "\"cognome\" : \""+ beans.get(i).getCognome() + "\", ";
					result += "\"email\" : \""+ beans.get(i).getEmail() + "\", ";
					result += "\"sesso\" : \""+ beans.get(i).getSesso() + "\", ";
					result += "\"codice_fiscale\" : \""+ beans.get(i).getCodiceFiscale() + "\", ";
					String dataNascita = "--/--/--";
					try{
						dataNascita = DateUtility.getFormattedStringByTimestamp((beans.get(i).getDataNascita()), true);
					}catch(Exception exc) {
			        	new ExceptionManager("result_list_turno_success.jsp", "Errore generico JSP result_list_turno_success.jsp; errore durante il recupero della data di nascita. Eccezione lanciata: {0}", exc);
			        }
					result += "\"data_nascita\" : \""+ dataNascita + "\", ";
					result += "\"data_registrazione\" : \""+ beans.get(i).getDataRegistrazione() + "\", ";
					result += "\"regione\" : \""+ beans.get(i).getRegione() + "\", ";
					result += "\"citta\" : \""+ beans.get(i).getProvincia() + "\", ";
					result += "\"provincia\" : \""+ beans.get(i).getComune() + "\" ";
				result += "}";
				
			result += "}, ";
	
	
			result += "\"info_centro_vaccinale\" : ";
			
			result += "{";
			   			
	   			result += "\"id_centro_vaccinale\" : \"" + beans.get(i).getIdCentroVaccinale() + "\", ";
	   			result += "\"denominazione\" : \"" + beans.get(i).getDenominazione() + "\", ";
	   			result += "\"regione\" : \""+ beans.get(i).getRegione() + "\", ";
	   			result += "\"provincia\" : \""+ beans.get(i).getProvincia() + "\", ";
	   			result += "\"comune\" : \""+ beans.get(i).getComune() + "\", ";
	   			result += "\"indirizzo\" : \""+ beans.get(i).getIndirizzo() + "\"";
	   			
				result += "}";
			
			if((i+1) == beans.size())
				result += "}";
			else
				result += "}, ";
			
		  }
	}
		
	result += " ] }";

	
	response.getWriter().println(result);
%>