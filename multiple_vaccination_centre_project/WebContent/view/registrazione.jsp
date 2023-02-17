<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Registrazione"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Registrazione"); %>

<%@ include file = "../WEB-INF/page_components/header.jsp" %>

<%
try {
	UserBean userBean = (UserBean) request.getAttribute("userBean");

%>

	<div class="container mt-3 mb-5">
			<h3 class="m-5 text-center">Registrazione</h3>
			<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
				<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
				<%
					String msgError = request.getParameter("msgError");
				
					if (msgError != null && msgError.equals("userExistUserName")){
						out.println("<span>Attenzione l'username inserita &egrave; gi&agrave; in uso in un'altra utenza!</span>");
					}
					
					if (msgError != null && msgError.equals("userExistCF")){
						out.println("<span>Attenzione il codice fiscale inserito &egrave; gi&agrave; in uso in un'altra utenza! Se ha problemi con l'accesso la preghiamo di contattarci!</span>");
					}
					
					if (msgError != null && msgError.equals("registrazioneFail")){
						out.println("<span>Errore durante la Registrazione! Provi di nuovo, se il problema persiste la preghiamo di contattarci, grazie!</span>");
					}
					
					if (msgError != null && msgError.equals("logoutFail")){
						out.println("<span>Errore durante il Logout! Provi di nuovo, se il problema persiste la preghiamo di contattarci</span>");
					}
					
					if(request.getRemoteUser() != null){ //se e' gia' loggato allora mostra il messaggio di avviso
						out.println("<span>Attenzione! Procedendo verr&agrave; automaticamente disconnesso e verr&agrave; effettuato l'accesso con la nuova utenza.</span>");
					}
				%>
			</div>
				
			
		<form class="m-5" id="registration_form" role="form" action='/googlielmo93/registrazione' method="post" novalidate>

		  <div class="form-row mt-5 mb-5 borderGroup">
		    <div class="col-md-12 mb-3">
		      <label for="Ruolo">Ruolo</label>
		      <div class="input-group col-md-12 col-lg-12 col-xl-12 mx-auto mt-3">
			  	<div class="btn-group col-md-12 col-lg-12 col-xl-12 mx-auto" id="select_role" data-toggle="buttons">
			  	
				   <label class="btn btn-default blue">
				     <input type="radio" class="ruolo_toggle" name="ruolo_toggle" value="user" id="user"
				     	<% if(userBean == null || (userBean != null && userBean.getRuolo().equals("user"))){ %>
				      		checked
				      	<% } %>
				      > Utente
				   </label>
				   <label class="btn btn-default blue">
				     <input type="radio" class="ruolo_toggle" name="ruolo_toggle" value="medico" id="medico"
					    <% if(userBean != null && userBean.getRuolo().equals("medico")){ %>
				      		checked
				      	<% } %>
				     > Medico
				   </label>
				   <label class="btn btn-default blue">
				     <input type="radio" class="ruolo_toggle" name="ruolo_toggle" value="asp" id="asp"
					    <% if(userBean != null && userBean.getRuolo().equals("asp")){ %>
				      		checked
				      	<% } %>
				     > Operatore ASP
				   </label>
				   <label class="btn btn-default blue">
				     <input type="radio" class="ruolo_toggle" name="ruolo_toggle" value="accettazione" id="accettazione"
					    <% if(userBean != null && userBean.getRuolo().equals("medico")){ %>
				      		checked
				      	<% } %>
				     > Accettazione
				   </label>
				</div>
			</div>
		    </div>
		  </div>
		  
		<% 
			//mi serve per poter discriminare in base al ruolo nel caso di utente loggato in JS
			if(userBean != null){ 
		%>
      		<input type="hidden" id="ruoloBean" value="${userBean.ruolo}"></input>
      	<% } %>

      		<input type="hidden" id="beanReqExist" <%if(userBean != null && request.getRemoteUser() == null){ %>value="true"<% }else{  %> value="false" <% } %> ></input>
      		
      	
		  
		  <div class="form-row mb-5">
		    <div class="col-md-6 mb-3 " style="padding-right: 2%;">
		      <label for="userName">Username</label>
		      <div class="input-group">
		        <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">Az123_</span>
		        </div>
		        <input name="userName" type="text" class="form-control readOnlyClass" id="userName"  placeholder="Username" value="${userBean.userName}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci l'username
		        </div>
		      </div>
		    </div>
		    
		    <div class="col-md-6 mb-3 " style="padding-left: 2%;">
		      <label for="userName">Email</label>
		      <div class="input-group">
		        <div class="input-group-prepend">
			        <div class=" emailAsUserNameDiv emailAsUserName">
				    	<span id="emailAsUserNameClose" class="emailAsUserName text-center">X</span>
				    	<span class=" btn btn-info emailAsUserName" id="emailAsUserNameEvent">Clicca per usare l'email anche come username</span>
				    </div>
		          <span class="input-group-text" id="inputGroupPrepend">@</span>
		        </div>
		        <input name="email" type="email" class="form-control readOnlyClass" id="email" placeholder="Email" value="${userBean.email}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci un'email valida
		        </div>
		      </div>
		    </div>
		  
		    <div class="col-md-6 mb-3 " style="padding-right: 2%;">
		      <label for="password">Password</label>
		      <div class="input-group">
		      	<div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">*****</span>
		        </div>
		        <input name="password" type="password" class="form-control readOnlyClass" id="password" placeholder="Password"
		         	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci la password
		        </div>
		      </div>
		    </div>
		    
		    <div class="col-md-6 mb-3 " style="padding-left: 2%;">
		      <label for="codiceFiscale">Codice Fiscale</label>
		      <div class="input-group">
		      	<div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">AZ123</span>
		        </div>
		        <input name="codiceFiscale" type="text" class="form-control readOnlyClass" id="codiceFiscale" placeholder="Codice Fiscale"
		        	value="${userBean.codiceFiscale}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci il codice fiscale
		        </div>
		      </div>
		    </div>
		  
		    <div class="col-md-6 mb-3 " style="padding-right: 2%;">
		      <label for="nome">Nome</label>
		      <div class="input-group">
		      	<div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">Abc</span>
		        </div>
		        <input name="nome" type="text" class="form-control readOnlyClass" id="nome" placeholder="Nome"
		        	value="${userBean.nome}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci il tuo nome
		        </div>
		      </div>
		    </div>
		    
		    <div class="col-md-6 mb-3 " style="padding-left: 2%;">
		      <label for="cognome">Cognome</label>
		      <div class="input-group">
		      	<div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">Abc</span>
		        </div>
		        <input name="cognome" type="text" class="form-control readOnlyClass" id="cognome" placeholder="Cognome"
		        	value="${userBean.cognome}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci il tuo cognome
		        </div>
		      </div>
		    </div>
		 </div>
		 
		 <% if(userBean != null){ %>
		 	<input type="hidden" id="sessoResBean" value="${userBean.getSesso()}"></input>
		 <% } %>
		  
		  <div class="form-row mb-5 mt-5 userGroupInput">
		    <div class="col-md-6 mb-3 " style="padding-right: 2%;">
		      <label for="nome">Sesso</label>
		      <div class="input-group col-md-12 col-lg-12 col-xl-12 mx-auto">
			  	<div class="btn-group col-md-12 col-lg-12 col-xl-12 mx-auto" id="select_sesso" data-toggle="buttons">
				   <label class="col-md-4 btn btn-default blue">
				     <input type="radio" class="sesso_toggle readOnlyClass" name="sesso_toggle" id="M" value="M"> Maschile
				   </label>
				   <label class="col-md-4 btn btn-default blue">
				     <input type="radio" class="sesso_toggle readOnlyClass" name="sesso_toggle" id="F" value="F"> Femminile
				   </label>
				   <label class="col-md-4 btn btn-default blue">
				     <input type="radio" class="sesso_toggle readOnlyClass" name="sesso_toggle" id="N/D" value="N/D" checked> Preferisco non rispondere
				   </label>
				</div>
			   </div>
		  	 </div>
		  	 
		  	 <div class="col-md-6 mb-3 " style="padding-left: 2%;">
		      <label for="dataNascita">Data di Nascita</label>
		      <div class="input-group">
		      	<div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend"><i class="far fa-calendar-alt"></i></span>
		        </div>
		        <input name="dataNascita" type="date" class="form-control userElementInput readOnlyClass" id="dataNascita" placeholder="Data di Nascita"
		        	value="${userBean.getDataNascitaEN()}"
		        	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci la tua data di nascita
		        </div>
		      </div>
		    </div>
		    
		   </div>
		   
		   <div class="form-row mb-3 userGroupInput">
		    <div class="col-md-12 mb-3">
		    
			   <div class="col-md-12 mb-5">
			      <label for="regioneResidenza">Regione di residenza</label>
			      <div class="input-group">
			      	<div class="input-group-prepend">
			          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
			        </div>
			        
			       <% if(userBean != null ){ %><input type="hidden" id="regioneResBean" value="${userBean.getRegioneResidenza()}"></input> <% } %>
			        
			        <select class="custom-select form-control userElementInput readOnlyClass" id="regioneResidenza" name="regioneResidenza" aria-describedby="inputGroupPrepend" required>
					</select>
					
			        <div class="invalid-feedback">
			          Seleziona la tua regione di residenza
			        </div>
			      </div>
			    </div>
			   
			   <div class="col-md-12 my-5 showResidenza" id="provinciaReg" style="display: none;">
			      <label for="provinciaResidenza">Provincia di residenza</label>
			      <div class="input-group">
			      	<div class="input-group-prepend">
			          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
			        </div>
			        
			         <% if(userBean != null ){ %><input type="hidden" id="provinciaResBean" value="${userBean.getProvinciaResidenza()}"></input> <% } %>
			        <select class="custom-select form-control userElementInput readOnlyClass" id="provinciaResidenza" name="provinciaResidenza" aria-describedby="inputGroupPrepend" required>
					</select>
					
			        <div class="invalid-feedback">
			          Seleziona la tua provincia di residenza
			        </div>
			      </div>
			    </div>
			    
			    <div class="col-md-12 my-5 showResidenza" id="cittaReg" style="display: none;">
			      <label for="cittaResidenza">Citt&agrave; di residenza</label>
			      <div class="input-group">
			      	<div class="input-group-prepend">
			          <span class="input-group-text" id="inputGroupPrepend"><i class="fas fa-city"></i></span>
			        </div>
			        
			         <% if(userBean != null ){ %><input type="hidden" id="cittaResBean" value="${userBean.getCittaResidenza()}"></input> <% } %>
			        <select class="custom-select form-control userElementInput readOnlyClass" id="cittaResidenza" name="cittaResidenza" aria-describedby="inputGroupPrepend" required>
					</select>
					
			        <div class="invalid-feedback">
			          Seleziona la tua citt&agrave; di residenza
			        </div>
			      </div>
			    </div>
			   </div>
			   
		   </div>

			  <button type="submit" class="mt-5 btn btn-primary btn-lg btn-block">Registrati</button>
		  
		</form>
	</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "../WEB-INF/page_components/footer.jsp" %>


<%

}catch(Exception exc) {
	new ExceptionManager("RegistrazioneJSP", "Errore generico JSP RegistrazioneJSP. Eccezione lanciata: {0}", exc);
	String address = "../error_generico.jsp";
	RequestDispatcher dispatcher = request.getRequestDispatcher(address);

	try {
		dispatcher.forward(request, response);
	}catch(Exception excForward) {
		new ExceptionManager("RegistrazioneJSP", "Errore generico JSP RegistrazioneJSP; errore durante il forward. Eccezione lanciata: {0}", excForward);
	}
}
%>


