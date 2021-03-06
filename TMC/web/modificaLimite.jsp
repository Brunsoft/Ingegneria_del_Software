<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Veicolo"
	import="java.util.*" 
	import="java.sql.*" %>

<%
	String targa = "";
	int limite = 0;
	char privilegi = 'U';	
	String messaggio = "Sei sicuro di voler confermare le modifiche?";
	String disabled = "disabled";

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
		(session.getAttribute("azione").toString().equalsIgnoreCase("Modifica") && session.getAttribute("carLimSel") == null))
			response.sendRedirect("gestioneLimite.jsp");

	if (session.getAttribute("azione").toString().equalsIgnoreCase("Modifica")) {
		targa = session.getAttribute("carLimSel").toString();
		Veicolo veicoloSel = Veicolo.getVeicoloData(targa);
		limite = veicoloSel.getLimite();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Gestione Veicoli</title>
	</head>
	<body>
		<h1>REIMPOSTA LIMITE VELOCITA'</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="ModificaLimitiServlet" method="POST" name="formFiltro">
			<table order="1" cellpadding="1" cellspacing="5">
				<tr>
					<td>Targa</td>
					<td><input type="text" name="targa" value="<%=targa%>" <%=disabled%> required></td>
				</tr>
				<tr>
					<td>Limite</td>
					<td><input type="number" name="limite" value="<%=limite%>" required></td>
				</tr>
			</table>
			<hr/>
			<input type="submit" name="conferma" value="Conferma" onclick="return confirm('<%=messaggio%>')">
			<hr/>
		</form>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	</body>
</html>
