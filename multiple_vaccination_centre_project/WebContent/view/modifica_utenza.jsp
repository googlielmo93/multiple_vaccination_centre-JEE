<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Modifica Utenza"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Modifica Utenza"); %>

<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<%
	UserBean userBean = (UserBean) session.getAttribute("userBean");
	if(userBean == null || request.getRemoteUser() == null){
		String address = "/googlielmo93/view/login.jsp?msgError=areaProtetta";
		response.sendRedirect(address);
		return;
	}
%>


	<div class="container mt-3 mb-5">
			<h3 class="m-5 text-center">Modifica Dati di Accesso</h3>
			<h5 class="col-md-12 display-5 text-left alert alert-info p-5">
            	Per poter modificare le informazioni personali &egrave; necessario scaricare
            	( <a href="../documents/modulo_richiesta.pdf" target="_blank">scarica modulo</a> ), compilare il modulo e
            	inviarlo come allegato all'indirizzo di posta elettronica centrovaccini_modifica_utenza@info.it 
          	</h5>
          	
          	<h5 class="col-md-12 display-5 text-left alert alert-danger p-5">
            	Attenzione dopo la modifica verrai reinderizzato nella pagina di login e dovrai effettuare nuovamente l'accesso!
          	</h5>
			
			<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
				<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
				<%
					String msgError = request.getParameter("msgError");
				
					if (msgError != null && msgError.equals("userExistUserName")){
						out.println("<span>Attenzione l'username inserito &egrave; gi&agrave; in uso in un'altra utenza!</span>");
					}
					
					if (msgError != null && msgError.equals("userExistCF")){
						out.println("<span>Attenzione il codice fiscale inserito non appartiene a nessuna utenza attiva! Se ha problemi con l'accesso la preghiamo di contattarci!</span>");
					}
					
					if (msgError != null && msgError.equals("modifica_utenzaFail")){
						out.println("<span>Errore durante la modfica dell'utenza! Provi di nuovo, se il problema persiste la preghiamo di contattarci, grazie!</span>");
					}
					
					if (msgError != null && msgError.equals("logoutFail")){
						out.println("<span>Errore durante il Logout! Provi di nuovo, se il problema persiste la preghiamo di contattarci</span>");
					}

				%>
			</div>
				
			
		<form class="m-5" id="upload_utenza_form" role="form" action='/googlielmo93/upload_dati_accesso' method="post" novalidate>
		  
		  <div class="form-row my-5">
		    <div class="col-md-6 mb-3 " style="padding-right: 2%;">
		      <label for="userName">Username</label>
		      <div class="input-group">
		        <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">Az123_</span>
		        </div>
		        <input name="userName" type="text" class="form-control" id="userName"  placeholder="Username" value="${userBean.userName}"
		        	aria-describedby="inputGroupPrepend">
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
		        <input name="email" type="email" class="form-control" id="email" placeholder="Email" value="${userBean.email}"
		        	aria-describedby="inputGroupPrepend">
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
		        <input name="password" type="password" class="form-control" id="password" placeholder="Password"
		         	aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci la password
		        </div>
		      </div>
		    </div>
		   </div>
		   
			  <button type="submit" class="mt-5 btn btn-primary btn-lg btn-block">Modifica dati di accesso</button>
		  
		</form>
	</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>


