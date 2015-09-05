<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Veicolo"
	import="java.util.*" 
	import="java.sql.*" %>

<%
	String targa = "", email = "", dati = "";
	char privilegi = 'U';
	String messaggio = "Sei sicuro di voler associare "+email+" al veicolo "+targa+"?";
	String benvenuto = "ASSOCIA VEICOLO -> UTENTE";
	
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

	if  (session.getAttribute("azione") == null || 
		(session.getAttribute("azione").toString().equalsIgnoreCase("Modifica") && session.getAttribute("veicoloUtenteSel") == null))
			response.sendRedirect("gestioneVeicoliUtente.jsp");

	if (session.getAttribute("azione").toString().equalsIgnoreCase("Modifica")) {
		dati = session.getAttribute("veicoloUtenteSel").toString();
		String[] items = dati.split(",");
		targa = items[0];
		email = items[1];
		
		messaggio = "Sei sicuro di voler confermare le modifiche?";
		benvenuto = "MODIFICA ASSOCIAZIONE";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gestione Veicoli Utente</title>
</head>
<body>
	<h1><%=benvenuto%></h1>
	<%=user%>
	<hr/>
	<form action="ModificaVeicoloUtenteServlet" method="POST" name="formFiltro">
		<table order="1" cellpadding="1" cellspacing="5">
			<tr>
				<td>Targa</td>
				<td><input type="text" name="targa" value="<%=targa%>" required></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email" value="<%=email%>" required></td>
			</tr>
		</table>

		<hr>
		<input type="submit" name="conferma" value="Conferma" onclick="return confirm('<%=messaggio%>')">
		<hr>
	</form>
	<a href="welcomeUser.jsp">Torna alla Home</a>
	<hr>
	<form action="LogoutServlet" method="POST">
		<input type="submit" name="Logout" value="Logout">
	</form>
	<hr>
</body>
</html>
