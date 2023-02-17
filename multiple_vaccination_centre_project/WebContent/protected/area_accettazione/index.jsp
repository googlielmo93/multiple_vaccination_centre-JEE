<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Area Accettazione"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Area Accettazione"); %>
<% request.setAttribute("ref_logo_navbar", "./"); %>


<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<%@ page import ="it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean"%>
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
				//MESSAGGI NELLA SOLA PAGINA ACCETTAZIONE
				String alertMsg = request.getParameter("msgError");
				if (alertMsg != null && alertMsg.equals("logoutFail")){
					out.println("<span>Errore durante il Logout! Prova di nuovo, se il problema persiste la preghiamo di contattarci, grazie</span>");
				}
				
			%>
	</div>
	

	<div class="row-flex m-5">
		<div class="col-md-8 mx-auto text-center" style="color:white">
	          <h6 class=" text-uppercase fw-bold display-4 m-5 pb-2 border-bottom">
	            Area Accettazione
	          </h6>
	          <h5 class="col-md-10 mx-auto my-5 display-5 m-3">
	            In questa area &egrave; possibile confermare la presenza degli utenti in sala dopo il riconoscimento tramite il codice di prenotazione, far scorrere il turno in sala e inserire un nuovo utente non prenotato con un codice temporaneo.
	          </h5>
	    </div>
	</div>
</div>
	
	<div class="d-flex justify-content-center justify-content-lg-between p-1 m-5 ">
	
		<div class="container text-center text-md-start mt-5">

			<div id="sezioneIcon" class="mb-5" style="display: none!important;">
			
		     <div class="row mt-3" id="iconApprovazione">
		      
		        <div class=" col-md-4 col-lg-4 col-xl-4 mx-auto mb-4"  id="iconFormMettiATurno">
			      <span class="link_custom" id="inserisciTurno">
		          	<img src="/googlielmo93/images/clipboard.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Metti a Turno">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Metti a turno
		          	</h5>
		          </span>
		        </div>
		        <!-- 
		        <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4" id="iconNuovoUtente"> 
          		  <span class="link_custom" id="nuovoUtente">
          		  	<img src="/googlielmo93/images/nuova_prenotazione.png" style="width:auto; max-height:200px;" class="rounded mx-auto d-block" alt="Nuovo Utente">
          		  	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
          		  		Inserisci Nuova Prenotazione
          		  	</h5>
          		  </span>
         		</div> 
         		 -->
			      
			    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4" id="iconTurnoSala"> 
          		  <span class="link_custom" id="turnoSalaAsp">
          		  	<img src="/googlielmo93/images/sala_attesa.png" style="width:auto; max-height:200px;" class="rounded mx-auto d-block" alt="Turno Sala">
          		  	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
          		  		Turno Sala
          		  	</h5>
          		  </span>
         		</div> 
			 </div>
			 
		   </div>
		  
		   
		   <div id="sezioneApprovazioneSuccessResponse" class="mt-5" style="display: none;">
			</div>
				
			<div id="sezioneFormApprovazione" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>Per poter accedere ai servizi del portale &egrave; richiesta la conferma dell'utenza da parte di uno operatore Asp.</b>
	            	<br><br>Una volta compilato e inviato il form di seguito un operatore prender&agrave; in carico la sua richiesta, in caso di conferma al prossimo accesso
	            	potr&agrave; usare i servizi offerti dal portale.
	          	</h5>
	          	
				  
			
				<div class="mt-4" id="sezioneApprovazioneFailResponse" style="font-size: 1.1rem!important;">
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
		    
			
			<div id="sezioneMettiATurno" class="mt-5" style="display: none;">
				<!-- Codice di prenotazione -->
			    <div class="row mt-3">
				    <div class="d-inline-flex mb-1 pr-5 align-middle input-group-text text-wrap labelCodPrnt">
				    	# Inserire il codice di prenotazione
				    </div>
			    </div>
			    
			    <div class="row text-wrap codPrntDiv" id="mettiATurnoDiv">
			    	<div class=" col-md-12 mx-auto py-4 align-middle">
					    <div class="col-md-12 mb-3" class="centraverticale" id="codice_prenotazione_utente_turno">
					      <label for="mettiATurnoLabel" class="mb-3" style="font-size: 1.5rem!important; float:left">Inserisci a turno con codice prenotazione</label>
					      <div class="input-group mt-5 col-md-12 mx-auto">
					      	<div class="input-group-prepend">
					          <span class="input-group-text" id="inputGroupPrepend">xxx-xxx</span>
					        </div>
					        
					        <input name="inputUtenteTurnoCod" type="text" class="form-control" id="codiceUtenteTurno" placeholder="Inserisci un codice prenotazione valido"
					        	aria-describedby="inputGroupPrepend" required>
					        <i id="arrowCodiceUtenteTurno" class="fas fa-angle-right centraverticale" style="color: #17a2b8!important; margin-left: 20%!important;" aria-hidden="true"></i>
					        	
					        <div class="invalid-feedback mt-4" style="font-size: 1.1rem!important;">
					          Inserisci un codice prenotazione valido per aggiungere l'utente a turno
					        </div>
					        
					      </div>
					      
					        
					        <div class="mt-4" id="div_fail_insert_turno" style="font-size: 1.1rem!important;">
					        </div>
					    </div>
			        </div>
			    </div>
				
			</div>
			
			
			<div id="sezioneMettiATurnoSuccessResponse" class="mt-5" style="display: none;">
			</div>
				

  		</div>
  		
</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>