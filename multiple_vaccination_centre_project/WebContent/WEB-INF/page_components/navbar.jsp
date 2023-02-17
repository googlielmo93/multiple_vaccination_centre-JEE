<!-- NAVBAR -->
<nav class="navbar navbar-light bg-light">


<%
  	String logo_ref = (String) request.getAttribute("ref_logo_navbar");
	if(logo_ref == null)
		logo_ref = "/googlielmo93/";
 %>


  	<div class="px-0 mx-0">
	  	<a class="navbar-brand px-0 mx-0" href= "/googlielmo93/" >
	    	<img src="/googlielmo93/images/logo.png" width="30" height="30"
	    	class="d-inline-block align-top px-0" alt="Logo Centro Vaccinale"/><span class="px-0 mr-0 ml-1">Centro Vaccinale</span>
	    </a>
	  
	    <%
	    	String view_page = (String) request.getAttribute("view_page");
	    
			if(view_page != null && view_page != "Centro Vaccinale"){
		%>	
				<a class="navbar-brand px-0 mx-0" style="color: rgba(0,0,0,.5);" href="<%=logo_ref%>">
					<span style="color: rgba(0,0,0,.5);" class="px-0 mx-0"> &gt;</span>
					<%= view_page %>
				</a>
		<%
			}else{
				view_page = "";
			}
		%>
	</div>
  	<%
		if(!view_page.equals("Login") && !view_page.equals("Logout")){
	%>
	  	<button class="navbar-toggler" type="button" data-toggle="collapse"
	    data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
    <%
		}
	%>
</nav>


<div class="pos-f-t navbar-light bg-light">
  <div class="collapse" id="navbarToggleExternalContent">
    <div class="mr-5 float-right mt-3
    <%
		if(request.getRemoteUser() == null && !view_page.equals("Login") && !view_page.equals("Logout")){
	%>
		 pb-5
	<%  } %>
	">
		
      	<%
		if(request.getRemoteUser() == null && !view_page.equals("Login") && !view_page.equals("Logout")){
		%>
		    <a class="link_login fw-bold" href="/googlielmo93/view/login.jsp">Accedi all'area riservata</a>
		    <%
			if(!view_page.equals("Registrazione")){
			%>
		    	<span class="mx-3 fw-bold">o</span>
				<a class="mr-3 link_login fw-bold" href="/googlielmo93/view/registrazione.jsp">Registrati</a>
			<%
			}
		}else if(request.getRemoteUser() != null && !view_page.equals("Login") && !view_page.equals("Logout")){
		%>
			<ul style="list-style: none;" class="pb-3">
			  <li style="list-style-type: none;" class="mb-3">
			  	<a class="mr-3 link_login fw-bold" href="/googlielmo93/view/logout.jsp">Logout</a>
			  </li>
			  <li style="list-style-type: none;">			  
			  	<span class="mr-3 link_login fw-bold" style="cursor: pointer;" data-toggle="modal" data-target="#infoUtenteModaleDatiPersonali">Dati Personali</span>
			  </li>
			</ul>
		<%
		}
	  	%>
    </div>
  </div>
    
</div>

