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
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<h3>Selezionare una funzionalita:</h3>
			<% if( userMode == 'A' ){ %>
				<fieldset style="width:250px; margin: 20px; ">  
		        	<legend>Operazioni Amministratore</legend>
					<a href="gestioneUtenti.jsp">GESTIONE UTENTI</a><br/>
					<a href="gestioneVeicoli.jsp">GESTIONE VEICOLI</a><br/>
					<a href="gestioneVeicoliUtente.jsp">ASSOCIA VEICOLI - UTENTE</a><br/>
					<a href="gestioneLimite.jsp">IMPOSTA ALLARMI VELOCITA'</a><br/>
				</fieldset>
			<% } %>

			<% if( userMode == 'U' || userMode == 'A'){ %>
				<fieldset style="width:250px; margin: 20px; ">  
		        	<legend>Operazioni Utente</legend>
					<a href="posizioneVeicoli.jsp">POSIZIONE VEICOLI</a><br/>
					<a href="sceltaVeicoloFurto.jsp">LIVE TRACKING FURTO</a><br/>
					<a href="sceltaStoricoFurto.jsp">STORICO FURTI</a><br/>
					<a href="sceltaVeicolo.jsp">VISUALIZZA ALLARMI</a><br/>
				</fieldset>
			<% } %>
		<hr/>
	</body>
</html>
