<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Utente"
	import="java.util.*" 
	import="java.sql.*" %>

<%
	String email = "", nome = "", cognome = "", telefono="";
	char privilegi = 'U';
	String messaggio = "Sei sicuro di voler inserire il nuovo utente?", disabled = "";
	String benvenuto = "INSERISCI UTENTE";
	
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
		(session.getAttribute("azione").toString().equalsIgnoreCase("Modifica") && session.getAttribute("utenteSel") == null))
			response.sendRedirect("gestioneUtenti.jsp");

	if (session.getAttribute("azione").toString().equalsIgnoreCase("Modifica")) {
		email = session.getAttribute("utenteSel").toString();
		Utente utenteSel = Utente.getUserData(email);
		nome = utenteSel.getNome();
		cognome = utenteSel.getCognome();
		privilegi = utenteSel.getPrivilegi();
	    telefono = utenteSel.getTelefono();
		messaggio = "Sei sicuro di voler confermare le modifiche?";
		disabled = "disabled";
		benvenuto = "MODIFICA UTENTE";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gestione utenti</title>
</head>
<body>
	<h1><%=benvenuto%></h1>
	<%=user%>
	<hr/>
	<form action="ModificaUtentiServlet" method="POST" name="formFiltro">
		<table order="1" cellpadding="1" cellspacing="5">
			<td>Email</td>
			<td><input type="email" name="email" value="<%=email%>" <%=disabled%> required></td>
			</tr>
			<tr>
				<td>Nome</td>
				<td><input type="text" name="nome" value="<%=nome%>" required></td>
			</tr>
			<tr>
				<td>Cognome</td>
				<td><input type="text" name="cognome" value="<%=cognome%>" required></td>
			</tr>
			<tr>
				<td>Telefono</td>
				<td><input type="text" name="telefono" value="<%=telefono%>" required></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" required></td>
			</tr>
			<tr>
				<td>Conferma Password</td>
				<td><input type="password" name="passwordR" required></td>
			</tr>
			<tr>
				<td>Privilegi (A - Admin, U - Utente)</td>
				<td><input type="text" name="privilegi" value="<%=privilegi%>" required></td>
				<td></td>
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
