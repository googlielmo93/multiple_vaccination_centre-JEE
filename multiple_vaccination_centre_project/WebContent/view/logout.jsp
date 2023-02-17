<!-- HEADER INCLUDE -->
<% request.setAttribute("title_page", "Logout"); %>
<!-- NAVBAR ATTRIBUTE -->
<% request.setAttribute("view_page", "Logout"); %>

<%@ include file = "/WEB-INF/page_components/header.jsp" %>

<%
UserBean userBean = null;
try {
	userBean = (UserBean) session.getAttribute("userBean");
}catch(Exception exc) {
	new ExceptionManager("LogoutJSP", "Errore generico JSP LogoutJSP; errore durante il recupero del userBean dalla request. Eccezione lanciata: {0}", exc);
}
%>

	<div class="container mt-5">
		<div class="m-5 text-center">
		
			<h3>A presto ${userBean.nome} ${userBean.cognome}!</h3>
			
			<%
			
			try {
            	request.logout();
            	session.invalidate();
            	out.println("<h4>Logout avvenuto con successo!</h4>");
            } catch(ServletException exc) {
            	out.println("<h3>Errore durante il logout!</h3>");
        		out.println("<h4>Provi di nuovo, se il problema persiste la preghiamo di contattarci</h4>");
        		new ExceptionManager("LogoutJSP", "Errore generico JSP LogoutJSP; errore durante il logout. Eccezione lanciata: {0}", exc);
            }
			
			%>
		</div>
		
		<h1 class="text-center display-4"><a  href="/googlielmo93/">Torna alla HomePage ></a></h1>
		
	</div>
<!-- FOOTER INCLUDE -->
<%@ include file = "/WEB-INF/page_components/footer.jsp" %>