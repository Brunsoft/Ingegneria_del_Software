<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
	import="univr.is.tmc.entity.*"
	import="java.util.*" 
	import="java.sql.*" %>
<%
	String user = "";
	String targa = "";
    char userMode = 'U';
    char minMode = 'U';
    if(session.getAttribute("currUserEmail") == null)
        response.sendRedirect("login.jsp");
    else {
		targa = session.getAttribute("veicoloSel").toString();
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
		<title>Visualizza Allarmi</title>
	</head>
	<body>
		<h1>ALLARMI ATTIVATI</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
	
		<%	List<Allarme> listaAllarmi = Allarme.getAllarmi(targa); 
		if (!listaAllarmi.isEmpty()){ %>
			<table order="1" cellpadding="1" cellspacing="5">
				<thead>
					<tr>
						<th>Data</th>
						<th>Ora</th>
						<th>Limite</th>
						<th>Velocita</th>
					</tr>
				</thead>
				<tbody>
					<%	for (Allarme a : listaAllarmi) { %>
						<tr>
							<td><%=a.getData()%></td>
							<td><%=a.getOra()%></td>
							<td><%=(a.getVelocita() - a.getDiffVelocita())%> Km/h</td>
							<td><%=a.getVelocita()%> Km/h</td>
						</tr>
					<% } %>
				</tbody>
			</table>
			<hr/>
			<input type="submit" name="azione" value="Vedi Allarmi">
		<% } else { %>
			<h1>Nessun allarme attivato su questo veicolo!</h1>
		<% } %>
		<hr/>
		<a href="sceltaVeicolo.jsp">ELENCO VEICOLI</a><br/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
	</body>
</html>
