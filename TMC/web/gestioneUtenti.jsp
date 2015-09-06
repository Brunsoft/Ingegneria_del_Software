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
		<title>Gestione utenti</title>
	</head>
	<body>
		<h1>GESTIONE UTENTI</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="GestioneUtentiServlet" method="POST" name="formFiltro">
			<% List<Utente> listaUtenti = Utente.getUsers(); 
			if ( !listaUtenti.isEmpty() ) { %>
				<h3>Utenti presenti nel DataBase:</h3>
				<table order="1" cellpadding="1" cellspacing="5">
					<thead>
						<tr>
							<th></th>
							<th>Nome</th>
							<th>Cognome</th>
							<th>Email</th>
							<th>Telefono</th>
							<th>Privilegi</th>
						</tr>
					</thead>
					<tbody>

						<% for (Utente u : listaUtenti) { %>

						<tr>
							<td><input type="radio" name="utenteSel" value="<%=u.getEmail()%>" required></td>
							<td><%=u.getNome()%></td>
							<td><%=u.getCognome()%></td>
							<td><%=u.getEmail()%></td>
							<td><%=u.getTelefono()%></td>
							<td><%=u.getPrivilegi()%></td>
						</tr>
			
						<% } %>
					</tbody>
				</table>
				<hr/>
				<input type="submit" name="azione" value="Modifica">
				<input type="submit" name="azione" value="Elimina" 
					onclick="return confirm('Sei sicuro di voler eliminare l\'utente selezionato?')">
			<% } else { %>
				<h1>Non son presenti Utenti!</h1>
			<% } %>
		</form>
		<hr/>
		<form action="GestioneUtentiServlet" method="POST" name="formFiltro">
			<input type="submit" name="azione" value="Nuovo Utente"> 
		</form>
		<hr/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	
	</body>
</html>
