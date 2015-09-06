<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.*"
	import="java.util.*" 
	import="java.sql.*" %>
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
		<title>Storico Furti</title>
	</head>
	<body>
		<h1>STORICO FURTI</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="TravelServlet" method="POST" name="formFiltro">

		<%	List<Furto> listaFurti = Furto.getStoricoFurtiUtente(user); 
			if (!listaFurti.isEmpty()){ %>
				<table order="1" cellpadding="1" cellspacing="5">
					<thead>
						<tr>
							<th></th>
							<th>Targa</th>
							<th>Data</th>
							<th>Ora</th>
						</tr>
					</thead>
					<tbody>
						<%	for (Furto f : listaFurti) { %>
							<tr>
								<td><input type="radio" name="furtoSel" value="<%=f.getId()%>" required></td>
								<td><%=f.getTarga()%></td>
								<td><%=f.getData()%></td>
								<td><%=f.getOra()%></td>
							</tr>
						<% } %>
					</tbody>
				</table>
				<hr/>
				<input type="submit" name="azione" value="Guarda Percorso">
			<% } else { %>
				<h1>Nessun e' presente alcun furto!</h1>
				<h2>I tuoi veicoli non sono mai stati rubati.</h2>
				<hr/>
			<% } %>
		</form>
		<hr/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	
	</body>
</html>
