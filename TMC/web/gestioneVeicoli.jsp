<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.Veicolo"
	import="java.util.*" 
	import="java.sql.*" %>
<%
	String user = ""+session.getAttribute("currUserEmail");
    char userMode = 'U';
    char minMode = 'A';
    if(user.equals("null"))
        response.sendRedirect("login.jsp");
    else {
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
		<h1>GESTIONE VEICOLI</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<form action="GestioneVeicoliServlet" method="POST" name="formFiltro">
			<% List<Veicolo> listaVeicoli = Veicolo.getVeicoli(); 
			if ( !listaVeicoli.isEmpty() ){ %>
				<h3>Veicoli presenti nel DataBase:</h3>
				<table order="1" cellpadding="1" cellspacing="5">
					<thead>
						<tr>
							<th></th>
							<th>Targa</th>
							<th>Marca</th>
							<th>Modello</th>
						</tr>
					</thead>
					<tbody>
						<% for (Veicolo v : listaVeicoli) { %>

						<tr>
							<td><input type="radio" name="veicoloSel" value="<%=v.getTarga()%>" required></td>
							<td><%=v.getTarga()%></td>
							<td><%=v.getMarca()%></td>
							<td><%=v.getModello()%></td>
						</tr>
				
						<% } %>
					</tbody>
				</table>
				<hr/>
				<input type="submit" name="azione" value="Modifica">
				<input type="submit" name="azione" value="Elimina" 
						onclick="return confirm('Sei sicuro di voler eliminare il veicolo selezionato?')">
			<% } else { %>
				<h1>Non son presenti Veicoli!</h1>
			<% } %>
		</form>
		<hr/>
		<form action="GestioneVeicoliServlet" method="POST" name="formFiltro">
			<input type="submit" name="azione" value="Nuovo Veicolo"> 
		</form>
		<hr/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	
	</body>
</html>
