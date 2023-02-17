<%@ page contentType="application/json; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import ="it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean"%>
<%@ page import="it.googlielmo93.apsw.utility.DateUtility"%>
<%@ page import="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%@ page import="java.util.*"%>

<%
	@SuppressWarnings("unchecked")
	List <InfoDipendenteApprovatoBean> beans = (List<InfoDipendenteApprovatoBean>) request.getAttribute("dipendente_approvato_beans");



String result = "{ \"result\" : \"success\",";
	
	result += "\"message\" : \"Relazione tra dipendente e centro vaccinale esistente\", ";
	result += "\"data\" : [ ";
	

	
	if(beans != null && (beans.size() > 0)){
		for(int i=0; i < beans.size(); i++) {
		                        
		  result += "{";
		                        
			result += "\"info_dipendente_approvato\" : ";
	
			result += "{";
				
				result += "\"info_relazione_dipendenza\" : {";
						result += "\"id_dipendente_approvato\" : \""+ beans.get(i).getIdDipendenteApprovatoCentroVaccinale()+ "\"";
					result += "},";
				
				result += "\"info_dipendente\" : {";
					result += "\"id_dipendente\" : \""+ beans.get(i).getIdUser() + "\", ";
					result += "\"ruolo\" : \""+ beans.get(i).getRuolo() + "\", ";
					result += "\"nome\" : \"" + beans.get(i).getNome() + "\", ";
					result += "\"cognome\" : \""+ beans.get(i).getCognome() + "\", ";
					result += "\"email\" : \""+ beans.get(i).getEmail() + "\", ";
					result += "\"sesso\" : \""+ beans.get(i).getSesso() + "\", ";
					result += "\"codice_fiscale\" : \""+ beans.get(i).getCodiceFiscale() + "\", ";
					String dataNascita = "--/--/--";
					try{
						dataNascita = DateUtility.getFormattedStringByTimestamp((beans.get(i).getDataNascita()), true);
					}catch(Exception exc) {
			        	new ExceptionManager("result_approvazione_success.jsp", "Errore generico JSP result_approvazione_success.jsp; errore durante il recupero della data di nascita. Eccezione lanciata: {0}", exc);
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
	   			
			result += "}, ";
			
			int utente_abilitato = i;
			if(beans != null && (beans.size() > i)){
				utente_abilitato = beans.get(i).getFlagApprovato();
			}
			result += "\"utente_abilitato\" : \"" + utente_abilitato + "\"";
			
			if((i+1) == beans.size())
				result += "}";
			else
				result += "}, ";
			
		  }
	}
		
	result += " ] }";

	
	response.getWriter().println(result);
%>