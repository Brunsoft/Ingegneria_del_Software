<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%
    String user = "";
    char userMode = 'U';
    char minMode = 'U';
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
		<title>Area riservata - Utente</title>
	</head>
	<body>
		<% if( userMode == 'A' ){ %>
			<h1>BENVENUTO AMMINISTRATORE</h1>
		<% }else{ %>
			<h1>BENVENUTO UTENTE</h1>
		<% } %>
		<%=user%>
		<hr/>
		<h3>Selezionare una funzionalita:</h3>

			<% if( userMode == 'A' ){ %>
				<ul>
					<li><a href="gestioneUtenti.jsp">Gestione Utenti</a></li>
					<li><a href="gestioneVeicoli.jsp">Gestione Veicoli</a></li>
					<li><a href="gestioneVeicoliUtente.jsp">Associa Veicoli-Utente</a></li>
					<li><a href="gestioneLimite.jsp">Imposta Allarmi Velocita</a></li>
				</ul>
			<% } %>

			<% if( userMode == 'U' || userMode == 'A'){ %>
				<ul>
					<li><a href="track.jsp">Posizione Veicoli</a></li>
					<li><a href="sceltaVeicoloFurto.jsp">Live Tracking Furto</a></li>
					<li>Storico Furti</li>
				</ul>
			<% } %>

		<hr/>
		<form action="LogoutServlet" method="POST">
			<input type="submit" name="Logout" value="Logout">
		</form>
	</body>
</html>
