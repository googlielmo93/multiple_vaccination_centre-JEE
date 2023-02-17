<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Area Utente"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Area Utente"); %>
<% request.setAttribute("ref_logo_navbar", "./"); %>


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
	            Area Utente
	          </h6>
	          <h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto my-5 display-5 m-3" >
	            In questa area &egrave; possibile effettuare la prenotazione della vaccinazione presso un centro vaccinale, gestire la prenotazione e accedere ai dati principali!
	          </h5>
	    </div>
	</div>
</div>
	
	<div class="d-flex justify-content-center justify-content-lg-between p-1 m-5 ">
	
		<div class="container text-center text-md-start mt-5">
		
			<!-- SEZIONE SOMMINISTRAZIONI -->
		    <div id="sezioneSomministrazione" class="mb-5" style="display:none!important;">
		    	<!-- Green Pass -->
		    	<div class="row mt-3">
		   		    <div class="d-inline-flex mb-1 pr-5 align-middle input-group-text text-wrap labelCodPrnt">
		   		    	<i class="fas fa-qrcode"></i>&nbsp;Green Pass
		   		    </div>
		   	    </div>
		   	    <% if(userBean != null){ %>
			    <div class="row text-wrap codPrntDiv" id="openInfoQr">
			    	<div class=" col-md-12 py-4 align-middle" style="margin-left: 12%!important;" id="codInfoQr">
			    		<input type="hidden" id="valueQrCode" value='nome:<%=userBean.getNome()%>+cognome:<%=userBean.getCognome()%>+CF:<%=userBean.getCodiceFiscale()%>'>
				    	<span class="centraverticale" id="qrcode_vaccinazione"></span>
			       		<i id="arrowInfoIconQr" class="fas fa-angle-down centraverticale" style="color: #17a2b8!important; margin-left: 20%!important;" aria-hidden="true"></i>
			        </div>
			    </div>
			    <% } %>
			    
		    	<!-- Somministrazioni -->
			    <div class="row mt-3 codInfoDiv sezioneSomministrazioni" id="divInfoQr" style="display:none!important;" >
				    <div class="col-md-3 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
			    		<div class="col-md-12">
				    		<i class="fas fa-info-circle"></i>&nbsp;Info Vaccino
				    		<br><span id="count_somministrati"></span> 
				    	</div>
				    </div>
				    
				    <!-- INFO SOMMINISTRAZIONI E RELATIVI CENTRI VACCINALI -->
		    		<div class="col-md-8 pb-5 ml-3" id="list_somministrazioni">
			    	</div>
			    	
				</div>
			</div>
		
		
			<!-- SEZIONE PRENOTAZIONI -->
			<div id="sezionePrenotazione" class="mb-5" style="display:none!important;">
			    <!-- Codice di prenotazione -->
			    <div class="row mt-3">
				    <div class="d-inline-flex mb-1 pr-5 align-middle input-group-text text-wrap labelCodPrnt">
				    	# Codice di prenotazione
				    </div>
			    </div>
			    <div class="row text-wrap codPrntDiv" id="openInfo">
			    	<div class=" col-md-12 mx-auto py-4 align-middle"  style="margin-left: 12.5%!important;" id="codInfo">
				    	<span class="centraverticale" id="codice_prenotazione_attivo"></span>
			       		<i id="arrowInfoIcon" class="fas fa-angle-down centraverticale" style="color: #17a2b8!important; margin-left: 20%!important;" aria-hidden="true"></i>
			        </div>
			    </div>
			    
			    <!-- Prenotazione -->
			    <div class="row mt-3 codInfoDiv" id="divInfoPrenotazione" style="display:none!important;" >
			    	<div class="col-md-3 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
			    		<div class="col-md-12">
				    		<i class="fas fa-info-circle"></i>&nbsp;Info Prenotazione
				    	</div>
				    </div>
				    
				    <!-- INFO PRENOTAZIONI E RELATIVI CENTRI VACCINALI -->
			   		<div class="col-md-8 pb-5 ml-3" id="list_prenotazioni">
			    	</div>
			    	
				</div>
			</div>
			
			
			<div id="sezioneIcon" class="mb-5" style="display:none!important;">
			
		     <div class="row mt-3" id="iconPrenotazione">
		      
		        <div class=" col-md-4 col-lg-4 col-xl-4 mx-auto mb-4"  id="iconFormPrenotazione" style="display:none!important;">
			      <span class="link_custom" id="inserisciPrenotazione">
		          	<img src="/googlielmo93/images/calendario.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Prenotazione">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Inserisci la prenotazione
		          	</h5>
		          </span>
		        </div>
			      
			    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4" id="iconArchivioPrenotazione" style="display:none!important;"> 
          		  <span class="link_custom" id="openInfoArchivio">
          		  	<img src="/googlielmo93/images/archivio.png" style="width:auto; max-height:200px;" class="rounded mx-auto d-block" alt="Archivio">
          		  	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
          		  		Archivio prenotazioni
          		  	</h5>
          		  </span>
         		</div> 
         		
			 </div>
			 
			</div>
			
			<div id="sezionePrenotazioneArchivio" class="mt-5" style="display:none!important;">
			    <!-- Archivio Prenotazione -->
			    <div class="row mt-3 codInfoDiv" id="divInfoPrenotazioneArchivio" style="display:none!important;" >
			    	<div class="col-md-3 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
			    		<div class="col-md-12">
				    		<i class="fas fa-info-circle"></i>&nbsp;Info Prenotazione Archiviata
				    	</div>
				    </div>
				    
				    <!-- INFO PRENOTAZIONI E RELATIVI CENTRI VACCINALI -->
			   		<div class="col-md-8 pb-5 ml-3" id="list_prenotazioni_archivio">
			    	</div>
			    	
				</div>
			</div>

	
			<!-- SEZIONE PRENOTAZIONI FORM -->
			<div id="sezioneFormPrenotazione" class="mb-5" style="display:none!important;">
			      
			      <!-- Info Prenotazione FORM -->
			    <div class="row mt-5 codInfoDiv" id="divPrenotazione" style="display:none!important;" >
			    
			    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
				    	<i class="far fa-check-square"></i>&nbsp;<span id="text-scelta-vaccinazione">Seleziona la regione</span>
				    </div>
				    
					<div class="container">
					
						<form class="m-5" id="prenotazione_form" role="form" action='' method="post" novalidate>
			
						  <div class="form-row mt-5 mb-5">
						  	
						  	<label for="regioneResidenza">Regione</label>
					    	<div class="col-md-12 mb-5">
						      <div class="input-group">
						      	<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="regionePrenotazione" name="regionePrenotazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una regione
						        </div>
						      </div>
						    </div>
						    
						    <label for="regioneResidenza" class="HideInputProvincia">Provincia</label>
					    	<div class="col-md-12 mb-5 HideInputProvincia">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="provinciaPrenotazione" name="provinciaPrenotazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una provincia
						        </div>
						      </div>
						    </div>
						    
						    <label for="regioneResidenza" class ="HideInputComune">Comune</label>
					    	<div class="col-md-12 mb-5 HideInputComune">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="comunePrenotazione" name="comunePrenotazione" aria-describedby="inputGroupPrepend">
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
						        <select class="custom-select form-control userElementInput" id="centroVaccinalePrenotazione" name="centroVaccinalePrenotazione" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un centro vaccinale
						        </div>
						      </div>
						    </div>
						    
						    
						    
						    <label for="DatePrenotazione" class="HideInputDateDisponibiliPrenotazione">Date disponibili</label>
						    <!-- fail insert -->
							<div id="div_fail_insert" style="display: none;">
							</div>
							
					    	<div class="col-md-12 mb-5 HideInputDateDisponibiliPrenotazione">
						      <div class="input-group">
						        <div  id="datePrenotazione" class="d-flex align-content-around flex-wrap">
						        
								</div>
						      </div>
						    </div>
			
					   </div>
					  
					</form>
					</div>
				</div>
			</div>
		
  		</div>
  		
</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>