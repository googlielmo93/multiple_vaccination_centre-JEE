<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Centro Vaccinale"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Centro Vaccinale"); %>
<% 
	String url_area_riservata = "./view/login.jsp";
	String ruolo = "";
	if(request.isUserInRole("medico")) {
		ruolo = "medico";
		url_area_riservata = "./protected/area_medico/index.jsp";
	}else if(request.isUserInRole("asp")) {
		ruolo = "asp";
		url_area_riservata = "./protected/area_asp/index.jsp";
	}else if(request.isUserInRole("user")){
		ruolo = "user";
		url_area_riservata = "./protected/area_utente/index.jsp";
	}else if(request.isUserInRole("accettazione")){
		ruolo = "accettazione";
		url_area_riservata = "./protected/area_accettazione/index.jsp";
	}
	
%>


<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<div class="container-flex text-center text-md-start" style="background-color: #66CDAA">
	<div id="alertMsg" style="display:none;" class="sticky-top text-center text-md-start mx-5 px-5 py-3 alert alert-danger" role="alert">
		<button type="button" id="closeAlertMsg" class="close" data-dismiss="alert">x</button>
		<%
			//MESSAGGI NELLA SOLA PAGINA PRINCIPALE
			String alertMsg = request.getParameter("msgError");
			if (alertMsg != null && alertMsg.equals("logoutFail")){
				out.println("<span>Errore durante il Logout! Provi di nuovo, se il problema persiste la preghiamo di contattarci</span>");
			}
		%>
	</div>

	<div class="row-flex m-5">
		<img src="./images/carousel.png" style="width:auto; max-height:400px; " class="rounded mx-auto d-block" alt="Immagine carosello">
		<div class="col-md-6 col-lg-6 col-xl-6 mx-auto text-center" style="color:white">
	          <h6 class=" text-uppercase fw-bold display-4 m-5 pb-2 border-bottom ">
	            Benvenuto
	          </h6>
	          <h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto display-5 m-3" >
	            Questa &egrave; la piattaforma per i servizi al cittadino relativi all'emergenza pandemica.
	            Qui potrai prenotare il tuo vaccino e fare la tua parte nella lotta contro il Covid19.<br>
	          </h5>
	          <h4 style="text-decoration: underline;" class="col-md-8 col-lg-8 col-xl-8 mx-auto fw-underline mt-2 display-5">Insieme possiamo farcela!</h4>
	    </div>
	</div>
	</div>
	<div class="d-flex justify-content-center justify-content-lg-between p-1 m-5 ">
    <div class="container text-center text-md-start mt-5">
      <div class="row mt-3">
      <%
      	if(ruolo == ""){
      %>
        <div class=" col-md-4 col-lg-4 col-xl-4 mx-auto mb-4">
	      <a class="link_custom" href="./protected/area_utente">
          	<img src="./images/siringa.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Prenotazione">
          	<h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto text-center text-uppercase fw-bold mt-4">
            	Prenota qui il tuo Vaccino
          	</h5>
          </a>
        </div>
        <%
      	}
        %>
        
        <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4">
          <a class="link_custom" href="<%=url_area_riservata%>">
            <img src="./images/area_riservata.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Area Privata">
            <h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto text-center text-uppercase fw-bold mt-4">
              Accedi all'area riservata
            </h5>
          </a>
        </div>
        
        <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mb-4">
          <a class="link_custom" href="./view/salaAttesa.jsp">
            <img src="./images/sala_attesa.png" style="width:auto; max-height:200px; " class="rounded mx-auto d-block" alt="Sala d'Attesa">
            <h5 class="col-md-8 col-lg-8 col-xl-8 mx-auto text-center text-uppercase fw-bold mt-4">
              Accedi alla sala d'attesa
            </h5>
          </a>
        </div>
      </div>
    </div>
  </div>
	
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>