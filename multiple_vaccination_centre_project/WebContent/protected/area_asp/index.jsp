<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Area Operatore Asp"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Area Operatore Asp"); %>
<% request.setAttribute("ref_logo_navbar", "./"); %>


<%@ page import ="it.googlielmo93.apsw.model.InfoDipendenteApprovatoBean"%>
<%@ page import ="it.googlielmo93.apsw.model.CentroVaccinaleBean"%>
<%@ page import ="it.googlielmo93.apsw.model.SomministrazioneBean"%>
<%@ page import ="it.googlielmo93.apsw.utility.*"%>
<%@ page import="java.util.*"%>

<%@ include file = "/WEB-INF/page_components/header.jsp" %>


<%
	UserBean userBean = (UserBean) session.getAttribute("userBean");
%>

<div class="container-flex text-center start" style="background-color: #66CDAA">
	<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
		<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
			<%
				//MESSAGGI NELLA SOLA PAGINA OP ASP
				String alertMsg = request.getParameter("msgError");
				if (alertMsg != null && alertMsg.equals("logoutFail")){
					out.println("<span>Errore durante il Logout! Prova di nuovo, se il problema persiste la preghiamo di contattarci, grazie</span>");
				}
				
			%>
	</div>
	

	<div class="row-flex m-5">
		<div class="col-md-8 mx-auto text-center" style="color:white">
	          <h6 class=" text-uppercase fw-bold display-4 m-5 pb-2 border-bottom">
	            Area Operatore Asp
	          </h6>
	          <h5 class="col-md-10 mx-auto my-5 display-5 m-3">
	            In questa area &egrave; possibile abilitare i profili dei medici e degli addetti all'accettazione, inserire le date di apertura per ogni centro vaccinale ed inserirne uno nuovo.
	          </h5>
	    </div>
	</div>
</div>
	
	<div class="d-flex justify-content-center justify-content-lg-between p-1 m-5 ">
	
		<div class="container text-center text-md-start mt-5">

			<div id="sezioneIcon" class="mb-5" style="display:none;">
			
		     <div class="row mt-3" id="iconAsp">
		      
		        <div class="col-md-4 mx-auto mb-4"  id="iconFormApprovazione">
			      <span class="link_custom" id="abilitaDipendenteIcon">
		          	<img src="/googlielmo93/images/verificato.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Abilita dipendente">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Abilita dipendente
		          	</h5>
		          </span>
		        </div>
		        
		        <div class="col-md-4 mx-auto mb-4"  id="iconFormDateApertura">
			      <span class="link_custom" id="dateAperturaIcon">
		          	<img src="/googlielmo93/images/calendario.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Date Apertura">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Date Apertura
		          	</h5>
		          </span>
		        </div>
		        
		        <div class="col-md-4 mx-auto mb-4"  id="iconFormCentroVaccinaleAdd">
			      <span class="link_custom" id="centroVaccinaleAddIcon">
		          	<img src="/googlielmo93/images/hospital.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Centro Vaccinale Add">
		          	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
		            	Aggiungi Centro Vaccinale
		          	</h5>
		          </span>
		        </div>

				<!--
			    <div class="col-md-3 mx-auto mb-4" id="iconScaricaDati"> 
          		  <span class="link_custom" id="scaricaDatiIcon">
          		  	<img src="/googlielmo93/images/xml-download.png" style="width:auto; max-height:200px;" class="rounded mx-auto d-block" alt="Scarica dati in XML">
          		  	<h5 class="col-md-12 mx-auto text-center text-uppercase fw-bold mt-4">
          		  		Scarica Dati Vaccinazione
          		  	</h5>
          		  </span>
         		</div> 
         		  -->
			 </div>
			 
		   </div>
		   
		   		  
		   
		    <div id="sezioneAspSuccessResponse" class="mt-5" style="display: none;">
			</div>
			
			<div id="sezioneAddCentroVaccinaleSuccessResponse" class="mt-5" style="display: none;">
			</div>
			
		   	<div id="sezioneApprovazioneSuccessResponse" class="mt-5" style="display: none;">
			</div>
				
			<div id="sezioneFormApprovazione" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>Per poter accedere ai servizi del portale &egrave; richiesta la conferma dell'utenza da parte di uno operatore Asp.</b>
	            	<br><br>Una volta compilato e inviato il form di seguito un operatore prender&agrave; in carico la sua richiesta, in caso di conferma al prossimo accesso
	            	potr&agrave; usare i servizi offerti dal portale.
	          	</h5>
	          	
				<div id="sezioneApprovazioneFailResponse" class="mt-5" style="display: none;">
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
			
				
			<div id="sezioneFormAsp" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>Per poter abilitare le richieste di approvazione pendenti, selezionare il centro vaccinale corrispondente.</b>
	          	</h5>
	          	
			      
			      <!-- Info Asp FORM -->
			    <div class="row mt-5 codInfoDiv" id="divAsp">
			    
			    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
				    	<i class="far fa-check-square"></i>&nbsp;<span id="text-approvazione_dipendente">Seleziona la regione</span>
				    </div>
				    
					<div class="container">
					
					
						<form class="m-5" id="asp_approvazione_form" role="form" action='' method="post" novalidate>
			
						  <div class="form-row mt-5 mb-5">
						  	
						  	<label for="regioneAsp">Regione</label>
					    	<div class="col-md-12 mb-5">
						      <div class="input-group">
						      	<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="regioneAsp" name="regioneAsp" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una regione
						        </div>
						      </div>
						    </div>
						    
						    <label for="provinciaAsp" class="HideInputProvincia">Provincia</label>
					    	<div class="col-md-12 mb-5 HideInputProvincia">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="provinciaAsp" name="provinciaAsp" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una provincia
						        </div>
						      </div>
						    </div>
						    
						    <label for="comuneAsp" class ="HideInputComune">Comune</label>
					    	<div class="col-md-12 mb-5 HideInputComune">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="comuneAsp" name="comuneAsp" aria-describedby="inputGroupPrepend">
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un comune
						        </div>
						      </div>
						    </div>
						    
						    <label for="CentroVaccinale" class="HideInputCentroVaccinale">Centro vaccinale</label>
					    	<div class="col-md-12 mb-5 HideInputCentroVaccinale">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-clinic-medical"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="centroVaccinaleAsp" name="centroVaccinaleAsp" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un centro vaccinale
						        </div>
						      </div>
						    </div>
						    
						    
						    <label for="RichiestePendenti" class="HideInputRichiestePendenti">Richieste Pendenti</label>
							
					    	<div class="col-md-12 mb-5 HideInputRichiestePendenti">
						      <div class="input-group">
						      
						      <div class="mb-4 mt-2 ml-5" id="div_fail_insert_approvazione_asp" style="font-size: 1.1rem!important;">
		    				  </div>
						      
						        <div  id="richiestePendenti" class="d-flex col-sm-12 align-content-around flex-wrap ml-4">
								</div>
								
						      </div>
						    </div>
						    
						    
						    <label for="ScaricaDati" style="display: none;" class="HideInputScaricaDati">Scarica Dati</label>
							
					    	<div class="col-md-12 mb-5 HideInputScaricaDati">
						      <div class="input-group">
						      
						      <div class="mb-4 mt-2 ml-5" id="div_fail_scarica_dati" style="font-size: 1.1rem!important;">
		    				  </div>
						      
						        <div  id="scaricaDati" class="d-flex col-sm-12 align-content-around flex-wrap ml-4">
								</div>
								
						      </div>
						    </div>
			
						   </div>
						  
						</form>
						
					</div>
					
				</div>
				
			</div>

	
			<div id="sezioneFormDateApertura" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>In questa sezione &egrave; possibile inserire le date di apertura e il numero di posti disponibili per centro vaccinale</b>
	          	</h5>
	          	
			      
			      <!-- Info DateApertura FORM -->
			    <div class="row mt-5 codInfoDiv" id="divDateApertura">
			    
			    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
				    	<i class="far fa-check-square"></i>&nbsp;<span id="text-date_apertura">Seleziona la regione</span>
				    </div>
				    
					<div class="container">
					
					
						<form class="m-5" id="date_apertura_form" role="form" action='' method="post" novalidate>
			
						  <div class="form-row mt-5 mb-5">
						  	
						  	<label for="regioneDateApertura">Regione</label>
					    	<div class="col-md-12 mb-5">
						      <div class="input-group">
						      	<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="regioneDateApertura" name="regioneDateApertura" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una regione
						        </div>
						      </div>
						    </div>
						    
						    <label for="provinciaDateApertura" class="HideInputProvincia">Provincia</label>
					    	<div class="col-md-12 mb-5 HideInputProvincia">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="provinciaDateApertura" name="provinciaDateApertura" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una provincia
						        </div>
						      </div>
						    </div>
						    
						    <label for="comuneDateApertura" class ="HideInputComune">Comune</label>
					    	<div class="col-md-12 mb-5 HideInputComune">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="comuneDateApertura" name="comuneDateApertura" aria-describedby="inputGroupPrepend">
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un comune
						        </div>
						      </div>
						    </div>
						    
						    <label for="CentroVaccinale" class="HideInputCentroVaccinale">Centro vaccinale</label>
					    	<div class="col-md-12 mb-5 HideInputCentroVaccinale">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-clinic-medical"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="centroVaccinaleDateApertura" name="centroVaccinaleDateApertura" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un centro vaccinale
						        </div>
						      </div>
						    </div>
						    
						    <label for="dataApertura" class="HideInputInfoApertura">Data apertura</label>
					    	<div class="col-md-12 mb-5 HideInputInfoApertura">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="far fa-calendar-alt"></i></span>
						        </div>
								<input name="dataApertura" type="date" class="form-control userElementInput" id="dataAperturaInsert"
									placeholder="Data di Apertura del centro vaccinale"
					        		aria-describedby="inputGroupPrepend" required>
						  
						        <div class="invalid-feedback">
						          Inserisci una data del centro vaccinale
						        </div>
						      </div>
						    </div>
						    
						    <label for="postiDisponibili" class="HideInputInfoApertura">Posti Disponibili</label>
					    	<div class="col-md-12 mb-5 HideInputInfoApertura">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-sort-numeric-up-alt"></i></span>
						        </div>
								<input name="postiDisponibiliInput" type="number" class="form-control userElementInput" id="postiDisponibiliInput"
									placeholder="Posti disponibili del centro vaccinale"
					        		aria-describedby="inputGroupPrepend" required>
						  
						        <div class="invalid-feedback">
						          Inserisci i posti disponibili del centro vaccinale
						        </div>
						      </div>
						    </div>
						    
					      	<div class="mb-4 mt-2 ml-5" id="div_msg_insert_data_apertura" style="font-size: 1.1rem!important;">
	    				  	</div>
						    
							<button type="submit"class="mt-5 btn btn-primary btn-lg btn-block" id="submitDataApertura" style="display:none;">Inserisci data apertura</button>

						   </div>
						  
						</form>
						
					</div>
					
				</div>
				
			</div>
	
			
			<div id="sezioneFormCentroVaccinaleAdd" class="mb-5" style="display: none;">
			
				<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
	            	<b>In questa sezione &egrave; possibile inserire un nuovo centro vaccinale e le sue informazioni base</b>
	          	</h5>
	          	
			      
			      <!-- Info CentroVaccinaleAdd FORM -->
			    <div class="row mt-5 codInfoDiv" id="divCentroVaccinaleAdd">
			    
			    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
				    	<i class="far fa-check-square"></i>&nbsp;<span id="text-centro_vaccinale_add">Seleziona la regione</span>
				    </div>
				    
					<div class="container">
					
					
						<form class="m-5" id="centro_vaccinale_add_form" role="form" action='' method="post" novalidate>
			
						  <div class="form-row mt-5 mb-5">
						  	
						  	<label for="regioneCentroVaccinaleAdd">Regione</label>
					    	<div class="col-md-12 mb-5">
						      <div class="input-group">
						      	<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="regioneCentroVaccinaleAdd" name="regioneCentroVaccinaleAdd" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una regione
						        </div>
						      </div>
						    </div>
						    
						    <label for="provinciaCentroVaccinaleAdd" class="HideInputProvincia">Provincia</label>
					    	<div class="col-md-12 mb-5 HideInputProvincia">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="provinciaCentroVaccinaleAdd" name="provinciaCentroVaccinaleAdd" aria-describedby="inputGroupPrepend" required>
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona una provincia
						        </div>
						      </div>
						    </div>
						    
						    <label for="comuneCentroVaccinaleAdd" class ="HideInputComune">Comune</label>
					    	<div class="col-md-12 mb-5 HideInputComune">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
						        </div>
						        <select class="custom-select form-control userElementInput" id="comuneCentroVaccinaleAdd" name="comuneCentroVaccinaleAdd" aria-describedby="inputGroupPrepend">
								</select>
						  
						        <div class="invalid-feedback">
						          Seleziona un comune
						        </div>
						      </div>
						    </div>
						    
						    <label for="CentroVaccinale" class="HideInputDenominazione">Denominazione Centro vaccinale</label>
					    	<div class="col-md-12 mb-5 HideInputDenominazione">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-sort-alpha-up"></i></span>
						        </div>
						        <input type="text" class="form-control userElementInput" id="denominazioneCentroVaccinaleAdd" 
						         	placeholder="Inserisci la denominazione del centro vaccinale"
						         	aria-describedby="inputGroupPrepend" required>
						  
						        <div class="invalid-feedback">
						         Inserisci denominazione del centro vaccinale
						        </div>
						      </div>
						    </div>
						    
						    <label for="dataApertura" class="HideInputIndirizzo">Indirizzo Centro Vaccinale</label>
					    	<div class="col-md-12 mb-5 HideInputIndirizzo">
						      <div class="input-group">
						      	<div class="input-group-prepend">
						          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-map-marked-alt"></i></span>
						        </div>
								<input name="dataNascita" type="text" class="form-control userElementInput" id="indirizzoCentroVaccinaleAdd"
									placeholder="Inserisci l'indirizzo del centro vaccinale"
					        		aria-describedby="inputGroupPrepend" required>
						  
						        <div class="invalid-feedback">
						          Inserisci l'indirizzo del centro vaccinale
						        </div>
						      </div>
						    </div>
						    
					      	<div class="mb-4 mt-2 ml-5" id="div_msg_insert_centro_vaccinale" style="font-size: 1.1rem!important;">
	    				  	</div>
						    
							<button type="submit"class="mt-5 btn btn-primary btn-lg btn-block" id="submitCentroVaccinaleAdd" style="display:none;">Inserisci il centro vaccinale</button>

						   </div>
						  
						</form>
						
					</div>
					
				</div>
				
			</div>
	
			
	
  		</div>
  		
</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>