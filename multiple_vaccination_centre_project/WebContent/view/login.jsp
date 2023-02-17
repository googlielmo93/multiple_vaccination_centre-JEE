<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Login"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Login"); %>
<%@ include file = "/WEB-INF/page_components/header.jsp" %>

	<div class="container mt-3 mb-5">
			<h3 class="m-5 text-center">Autenticazione</h3>
			<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
				<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
				<%
					String msgError = request.getParameter("msgError");
					if (msgError != null && msgError.equals("loginError")){
						out.println("<span>Username o Password errate! Provi di nuovo</span></span>");
					}
					
					if (msgError != null && msgError.equals("accessDenied")){
						out.println("<span>Accesso ad area riservata negato! Si prega di inserire le credenziali corrette.<br>Attenzione! Procedendo verr&agrave; automaticamente disconnesso prima di poter accedere all'area riservata richiesta.</span>");
					}
	
					if (msgError != null && msgError.equals("loginFail")){
						out.println("<span>Errore durante il Login! Provi di nuovo, se il problema persiste la preghiamo di contattarci, grazie!</span>");
					}
					
					if (msgError != null && msgError.equals("logoutFail")){
						out.println("<span>Errore durante il Logout! Provi di nuovo, se il problema persiste la preghiamo di contattarci</span>");
					}
					
					if (msgError != null && msgError.equals("areaProtetta")){
						out.println("<span>Accesso ad area riservata! Si prega di effettuare l'accesso!</span>");
					}
					if (msgError != null && msgError.equals("reLogin")){
						out.println("<span>Modificati i dati di accesso! Si prega di effettuare nuovamente il login.</span>");
					}
				%>
			
			</div>
			
		<form class="m-5" id="login_form" role="form" action='/googlielmo93/area-riservata' method="post" novalidate>
		  <div class="form-row">
		    <div class="col-md-12 mb-3">
		      <label for="userName">Username</label>
		      <div class="input-group">
		        <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">Az123_</span>
		        </div>
		        <input name="userName" type="text" class="form-control" id="userName" placeholder="Username" aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci la tua username
		        </div>
		      </div>
		    </div>
		  </div>
		  
		  <div class="form-row">
		    <div class="col-md-12 mb-3">
		      <label for="password">Password</label>
		      <div class="input-group">
		      <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend">*****</span>
		        </div>
		        <input name="password" type="password" class="form-control" id="password" placeholder="Password" aria-describedby="inputGroupPrepend" required>
		        <div class="invalid-feedback">
		          Inserisci la password
		        </div>
		      </div>
		    </div>
		  </div>
		  <button type="submit" class="mt-5 btn btn-primary btn-lg btn-block">Accedi</button>
		  
		</form>
		
	   <div class="d-flex justify-content-center justify-content-lg-between p-1 m-1 ">
	    <div class="container text-center text-md-start mt-1">
	      <div class="row">
	        <div class="col-md-10 col-lg-10 col-xl-10 mx-auto">
			  <a href="/googlielmo93/view/registrazione.jsp" >
		        <h5 class="mx-auto text-center">
	            	Non sei ancora registrato? Clicca per registrarti
	          	</h5>
	          </a>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>


