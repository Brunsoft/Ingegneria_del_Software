<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="it.univr.is.observer.persistenza.*"
	import="java.util.*" import="java.sql.*"%>
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
		<title>Pagina di Modifica</title>
	</head>
	<body>
		<h1><%=session.getAttribute("messaggio")%></h1>
		<%=user%>
		<hr/>
		<a href="gestioneUtenti.jsp">Torna a GESTIONE UTENTI</a>
		<br/>
		<a href="gestioneVeicoli.jsp">Torna a GESTIONE VEICOLI</a>
		<br/>
		<a href="gestioneVeicoliUtente.jsp">Torna a ASSOCIA VEICOLI-UTENTE</a>
		<br/>
		<a href="gestioneLimite.jsp">Torna a GESTIONE LIMITI</a>
		<br/>
		<br/>
		<a href="welcomeUser.jsp">Torna alla HOME</a>
		<hr/>
		<form action="LogoutServlet" method="POST">
			<input type="submit" name="Logout" value="Logout">
		</form>
	</body>
</html>
