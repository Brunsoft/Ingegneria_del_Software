<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Veicolo"
	import="java.util.*" 
	import="java.sql.*" %>

<%
	String targa = "", marca = "", modello = "";
	char privilegi = 'U';
	String messaggio = "Sei sicuro di voler inserire il nuovo veicolo?", disabled = "";
	String benvenuto = "INSERISCI VEICOLO";
	
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
		(session.getAttribute("azione").toString().equalsIgnoreCase("Modifica") && session.getAttribute("veicoloSel") == null))
			response.sendRedirect("gestioneVeicoli.jsp");

	if (session.getAttribute("azione").toString().equalsIgnoreCase("Modifica")) {
		targa = session.getAttribute("veicoloSel").toString();
		Veicolo veicoloSel = Veicolo.getVeicoloData(targa);
		marca = veicoloSel.getMarca();
		modello = veicoloSel.getModello();
		
		messaggio = "Sei sicuro di voler confermare le modifiche?";
		disabled = "disabled";
		benvenuto = "MODIFICA VEICOLO";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Gestione Veicoli</title>
	</head>
	<body>
		<h1><%=benvenuto%></h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="ModificaVeicoliServlet" method="POST" name="formFiltro">
			<table order="1" cellpadding="1" cellspacing="5">
				<td>Targa</td>
				<td><input type="text" name="targa" value="<%=targa%>" <%=disabled%> required></td>
				</tr>
				<tr>
					<td>Marca</td>
					<td><input type="text" name="marca" value="<%=marca%>" required></td>
				</tr>
				<tr>
					<td>Modello</td>
					<td><input type="text" name="modello" value="<%=modello%>" required></td>
				</tr>
			</table>
			<hr/>
			<input type="submit" name="conferma" value="Conferma" onclick="return confirm('<%=messaggio%>')">
			<hr/>
		</form>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	</body>
</html>
