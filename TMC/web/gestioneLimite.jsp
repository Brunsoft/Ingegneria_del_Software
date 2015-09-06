<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Veicolo"
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
<title>Gestione limiti</title>
</head>
<body>
	<h1>GESTIONE LIMITI DI VELOCITA</h1>
	<%=user%>
	<hr/>
	<h3>Veicoli presenti nel DataBase:</h3>
	<form action="GestioneLimitiServlet" method="POST" name="formFiltro">
		<table order="1" cellpadding="1" cellspacing="5">
			<thead>
				<tr>
					<th></th>
					<th>Targa</th>
					<th>Marca</th>
					<th>Modello</th>
					<th>Limite</th>
				</tr>
			</thead>
			<tbody>

				<% List<Veicolo> listaVeicoli = Veicolo.getVeicoli(); %>
				<% for (Veicolo v : listaVeicoli) { %>

				<tr>
					<td><input type="radio" name="carLimSel" value="<%=v.getTarga()%>"></td>
					<td><%=v.getTarga()%></td>
					<td><%=v.getMarca()%></td>
					<td><%=v.getModello()%></td>
					<td><%=v.getLimite()%> Km/h</td>
				</tr>
				
				<% } %>
			</tbody>
		</table>
		<hr>
		<input type="submit" name="azione" value="Modifica">
		<input type="submit" name="azione" value="Azzera" 
				onclick="return confirm('Azzerando il limite tutti gli utenti legati a questo veicolo non riceveranno più notifiche sul superamento del limite di velocita?')">
	</form>
	<br/>
	<form action="LogoutServlet" method="POST">
		<input type="submit" name="Logout" value="Logout">
	</form>
	<br/>
	<a href="welcomeUser.jsp">Home</a>
	
</body>
</html>
