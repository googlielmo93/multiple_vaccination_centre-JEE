
<%@ page import ="it.googlielmo93.apsw.utility.ExceptionManager"%>

<%
	try {
	  	request.logout();
	  	session.invalidate();
	} catch(ServletException exc) {
		new ExceptionManager("ErroreGenericoJSP", "Errore generico JSP ErroreGenericoJSP; errore durante il logout.  Eccezione lanciata: {0}", exc);
	}
%>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    
	    <link rel="icon" href="/googlielmo93/images/favicon/logo.ico">
		<link rel="stylesheet" href="/googlielmo93/Resources/bootstrap/css/bootstrap.min.css"/>
		<title>Errore Generico</title>
	</head>

	<body style="background-color:#f2f9ff">
		<img src="/googlielmo93/images/errore_generico.jpg" class="rounded mx-auto d-block" alt="404">
		<h1 class="text-center display-4">
			<a  href="/googlielmo93/view/login.jsp">Errore generico... Effettua nuovamente il login ></a>
		</h1>
	</body>
</html>