<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Area Medico"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Area Medico"); %>

<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<%@ page import ="it.googlielmo93.apsw.model.PrenotazioneBean"%>
<%@ page import ="it.googlielmo93.apsw.model.CentroVaccinaleBean"%>
<%@ page import ="it.googlielmo93.apsw.model.SomministrazioneBean"%>
<%@ page import ="it.googlielmo93.apsw.utility.*"%>
<%@ page import="java.util.*"%>

<%
	UserBean userBean = (UserBean) session.getAttribute("userBean");
%>

<div class="container-flex text-center start" style="background-color: #66CDAA">
	<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
		<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
			<%
				//MESSAGGI NELLA SOLA PAGINA USER
				String alertMsg = request.getParameter("msgError");
				if (alertMsg != null && alertMsg.equals("logoutFail")){
					out.println("<span>Errore durante il Logout! Prova di nuovo, se il problema persiste la preghiamo di contattarci, grazie</span>");
				}
				
			%>
	</div>
	

	<div class="row-flex m-5">
		<div class="col-md-6 col-lg-6 col-xl-6 mx-auto text-center" style="color:white">
	          <h6 class=" text-uppercase fw-bold display-4 m-5 pb-2 border-bottom ">
	            Area Medico
	          </h6>
	          <h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto my-5 display-5 m-3" >
	            In questa area &egrave; possibile recuperare l'elenco dei  un centro vaccinale, gestire la prenotazione e accedere ai dati principali!
	          </h5>
	    </div>
	</div>
</div>

	<div class="d-flex justify-content-center justify-content-lg-between p-1 m-5 ">
	
		<div class="container text-center text-md-start mt-5">

			<div id="sezioneIcon" class="mb-5" style="display: none!important;">
			
		     <div class="row mt-3" id="iconMedico">
		      
		        <div class=" col-md-4 col-lg-4 col-xl-4 mx-auto mb-4"  id="iconFormSomministrazione">
			      <span class="link_custom" id="somministraDipendenteIcon">
		          	<img src="/googlielmo93/images/verificato.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Abilita dipendente">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Trova con codice prenotazione
		          	</h5>
		          </span>
		        </div>

			    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4" id="iconlistaUtentiTurno"> 
          		  <span class="link_custom" id="listaUtentiTurnoIcon">
          		  	<img src="/googlielmo93/images/clipboard.png" style="width:auto; max-height:200px;" class="rounded mx-auto d-block" alt="Scarica dati in XML">
          		  	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
          		  		Lista utenti a turno
          		  	</h5>
          		  </span>
         		</div> 
			 </div>
			 
		   </div>
		   
		    
			
			<div id="sezioneTrovaCod" class="mt-5" style="display: none;">
				<!-- Codice di prenotazione -->
			    <div class="row mt-3">
				    <div class="d-inline-flex mb-1 pr-5 align-middle input-group-text text-wrap labelCodPrnt">
				    	# Inserire il codice di prenotazione
				    </div>
			    </div>
			    
			    <div class="row text-wrap codPrntDiv" id="trovaCodDiv">
			    	<div class=" col-md-12 mx-auto py-4 align-middle">
					    <div class="col-md-12 mb-3" class="centraverticale" id="codice_prenotazione_utente_turno_smnt">
					    	<label for="trovaCodLabel" class="mb-3" style="font-size: 1.5rem!important; float:left">Recupera i dati utente con il codice prenotazione</label>
						      <div class="input-group mt-5 col-md-12 mx-auto">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend">xxx-xxx</span>
						        </div>
						        
						        <input name="inputUtenteTrovaCod" type="text" class="form-control" id="codiceUtenteTrovaCod" placeholder="Inserisci un codice prenotazione valido"
						        	aria-describedby="inputGroupPrepend" required>
						        <i id="arrowCodiceUtenteTrovaCod" class="fas fa-angle-right centraverticale" style="color: #17a2b8!important; margin-left: 20%!important; cursor:pointer!important;" aria-hidden="true"></i>
						        	
						        <div class="invalid-feedback mt-4" style="font-size: 1.1rem!important;">
						          Inserisci un codice prenotazione valido per trovare l'utente oppure scarica la lista degli utenti a turno cliccando su "Lista utenti a turno"
						        </div>
						        
						      </div>
					      
						    <div class="mb-4 mt-2 ml-5" id="div_fail_insert_lista_utenti" style="font-size: 1.1rem!important;">
				  			</div>
					      
					    </div>
			        </div>
			    </div>
				
			</div>
		   
		   <!-- LISTA UTENTI-->
		   <div id="sezioneTrovaCodSuccessResponse" class="mt-5" style="display: none;">
			    <label for="Utenti Trovati" class="HiddenListTurno">Lista Utenti</label>
			    <br>
			    <div class="mb-4 mt-2 ml-5" id="div_fail_smnt_lista_utenti" style="font-size: 1.1rem!important;">
			    </div>
				<div class="row mt-5 HiddenListTurno">
			    	<div class="col-md-12 mb-5">
				      <div class="input-group">
				  		
				        <div  id="listaUtenti" class="d-flex col-sm-12 align-content-around flex-wrap ml-4">
						</div>
						
				      </div>
				    </div>
			    </div>
			</div>
			

			
			<!-- SEZIONE RICHIESTA APPROVAZIONE -->
		   	<div id="sezioneApprovazioneSuccessResponse" class="mt-5" style="display: none;">
			</div>
			
				
			<div id="sezioneFormApprovazione" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>Per poter accedere ai servizi del portale &egrave; richiesta la conferma dell'utenza da parte di uno operatore Medico.</b>
	            	<br><br>Una volta compilato e inviato il form di seguito un operatore prender&agrave; in carico la sua richiesta, in caso di conferma al prossimo accesso
	            	potr&agrave; usare i servizi offerti dal portale.
	          	</h5>
	          	
	          	
				<div class="mt-4" id="sezioneApprovazioneFailResponse" style="display: none; font-size: 1.1rem!important;">
			    </div>
	          	
			      
			      <!-- Info Approvazione FORM -->
			    <div class="row mt-5 codInfoDiv" id="divApprovazione" >
			    
			    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
				    	<i class="far fa-check-square"></i>&nbsp;<span id="text-scelta-approvazione">Seleziona la regione</span>
				    </div>
				    
					<div class="container">
					
					
						<form class="m-5" id="approvazione_form" role="form" action='' method="post" novalidate>
			
						  <div class="form-row mt-5 mb-5">
						  	
						  	<label for="regioneApprovazione">Regione</label>
					    	<div class="col-md-12 mb-5">
						      <div class="input-group">
						      	<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="regioneApprovazione" name="regioneApprovazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una regione
						        </div>
						      </div>
						    </div>
						    
						    <label for="provinciaApprovazione" class="HideInputProvincia">Provincia</label>
					    	<div class="col-md-12 mb-5 HideInputProvincia">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="provinciaApprovazione" name="provinciaApprovazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una provincia
						        </div>
						      </div>
						    </div>
						    
						    <label for="comuneApprovazione" class ="HideInputComune">Comune</label>
					    	<div class="col-md-12 mb-5 HideInputComune">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="comuneApprovazione" name="comuneApprovazione" aria-describedby="inputGroupPrepend">
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un comune (opzionale)
						        </div>
						      </div>
						    </div>
						    
						    <label for="CentroVaccinale" class="HideInputCentroVaccinale">Centro vaccinale</label>
					    	<div class="col-md-12 mb-5 HideInputCentroVaccinale">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-clinic-medical"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="centroVaccinaleApprovazione" name="centroVaccinaleApprovazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un centro vaccinale
						        </div>
						      </div>
						    </div>
			
						   </div>
						   
						   <button type="submit"class="mt-5 btn btn-primary btn-lg btn-block" id="submitRichiestaApprovazione">Richiesta Approvazione</button>
						  
						</form>
						
					</div>
					
				</div>
				
			</div>

  		</div>
  		
</div>
	
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>