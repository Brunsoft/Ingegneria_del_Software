<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Utente"
	import="java.util.*" 
	import="java.sql.*" %>
<%
	String user = "";
    char userMode = 'U';
    char minMode = 'A';
    if(session.getAttribute("currUserEmail") == null)
        response.sendRedirect("login.jsp");
    else {
        user = session.getAttribute("currUserEmail").toString();
        userMode = session.getAttribute("currUserMode").toString().charAt(0);

		if (minMode == 'A'){
			if(userMode != 'A')
				response.sendRedirect("accessoNegato.jsp");
		}else
        if (minMode == 'U'){
            if(userMode != 'A' && userMode != 'U')
				response.sendRedirect("accessoNegato.jsp");
		}
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Stampa Report</title>
	</head>
	<body>
		<h1>STAMPA REPORT PROGETTO</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="ReportServlet" method="POST" name="formFiltro">
			<h3>Premere sul pulsante qui in basso per stampare il Report del Progetto</h3>
			<input type="submit" name="azione" value="Stampa Report" 
				onclick="return confirm('Sei sicuro di voler stampare il report di sistema?')">

		</form>
		<hr/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	
	</body>
</html>
