<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Sala d'attesa"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Sala d'attesa"); %>
<% request.setAttribute("ref_logo_navbar", "./salaAttesa.jsp"); %>

<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<% 
	String opAsp = request.getParameter("opAsp");
%>


	<div class="container mt-3 mb-5">
		<h3 class="m-5 text-center">Sala d'attesa</h3>
			
		<div id="tabellone_turno">
			
		    <div id="sezioneTabelloneSuccessResponse" class="mt-5" style="display: none;">
			</div>
			
			<% 
				// se true stiamo accedendo alla jsp tramite l'operatore ASP
				if( opAsp == null || !opAsp.equals("true") ){ 
			%>
					
				<div id="sezioneFormTabellone" class="mb-5" style="display: none;">
				
					<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
		            	<b>Per sapere i posti disponibili e il numero turno selezionare il centro vaccinale</b>
		          	</h5>
		          	
				      
				      <!-- Info Tabellone FORM -->
				    <div class="row mt-5 codInfoDiv" id="divTabellone">
				    
				    	<div class="col-md-12 align-middle input-group-text text-wrap text-center labelCodPrntInfo">
					    	<i class="far fa-check-square"></i>&nbsp;<span id="text-scelta-approvazione">Seleziona la regione</span>
					    </div>
					    
						<div class="container">
						
						
							<form class="m-5" id="tabellone_approvazione_form" role="form" action='' method="post" novalidate>
				
							  <div class="form-row mt-5 mb-5">
							  	
							  	<label for="regioneTabellone">Regione</label>
						    	<div class="col-md-12 mb-5">
							      <div class="input-group">
							      	<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
							        </div>
							        <select class="custom-select form-control userElementInput" id="regioneTabellone" name="regioneTabellone" aria-describedby="inputGroupPrepend" required>
									</select>
							  
							        <div class="invalid-feedback">
							          Seleziona una regione
							        </div>
							      </div>
							    </div>
							    
							    <label for="provinciaTabellone" class="HideInputProvincia">Provincia</label>
						    	<div class="col-md-12 mb-5 HideInputProvincia">
							      <div class="input-group">
							      	<div class="input-group-prepend">
							          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
							        </div>
							        <select class="custom-select form-control userElementInput" id="provinciaTabellone" name="provinciaTabellone" aria-describedby="inputGroupPrepend" required>
									</select>
							  
							        <div class="invalid-feedback">
							          Seleziona una provincia
							        </div>
							      </div>
							    </div>
							    
							    <label for="comuneTabellone" class ="HideInputComune">Comune</label>
						    	<div class="col-md-12 mb-5 HideInputComune">
							      <div class="input-group">
							      	<div class="input-group-prepend">
							          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
							        </div>
							        <select class="custom-select form-control userElementInput" id="comuneTabellone" name="comuneTabellone" aria-describedby="inputGroupPrepend">
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
							        <select class="custom-select form-control userElementInput" id="centroVaccinaleTabellone" name="centroVaccinaleTabellone" aria-describedby="inputGroupPrepend" required>
									</select>
							  
							        <div class="invalid-feedback">
							          Seleziona un centro vaccinale
							        </div>
							      </div>
							    </div>
							   </div>
							  
							</form>
							
						</div>
						
					</div>
					
				</div>
				
			<% } %>
			
			<div id="infoSalaAttesa" class="my-5" style="display: none;">
				<div class="row my-5">
			    	<div class="col-md-6">
				      	<div  id="posti_disponibili" class="d-flex col-sm-12 py-5 text-align-center align-content-around flex-wrap codPrntDiv labelCodPrnt">
				      	</div>
				    </div>
				    <div class="col-md-6">
				    	<div  id="numero_turno" class="d-flex col-sm-12 py-5 text-align-center align-content-around flex-wrap codPrntDiv labelCodPrnt">
					    </div>
				    </div>
				</div>
				<%
				//visualizza i pulsanti soltanto se l'utente e' loggato ed ha ruolo accettazione
				if( opAsp != null && opAsp.equals("true") && request.isUserInRole("accettazione") ){ 
				%>
				<div class="row mt-5">
			    	<div class="d-flex col-sm-12 my-5 align-content-around flex-wrap labelCodPrnt">

				    	<div class="mx-auto">
					    	<div  id="plus_numero_turno" class="float-left mr-5 labelCodPrnt">
					    		<i class="fas fa-plus-circle"></i>
						    </div>
						    <div  id="minus_numero_turno" class="float-right ml-5 labelCodPrnt">
						    	<i class="fas fa-minus-circle"></i>
						    </div>
						</div>
				    </div>
				</div>
				<% } %>
			</div>

  		</div>

	</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>


