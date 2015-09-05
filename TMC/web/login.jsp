<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    String user = ""+session.getAttribute("currUserEmail");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>TMC - HomePage</title>
	</head>
	<body>
		<h1>Track My Car</h1>
		Sistema per localizzazione e recupero veicolo in caso di furto.
		mail: <%=user%>
		<br/>
		<h2>LOGIN</h2>
		<form action="LoginServlet" method="POST">
			<table>
				<tr>
					<td>Email:</td>
					<td><input type="email" name="user" required placeholder="Indirizzo email"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="pwd" required placeholder="Password"></td>
				</tr>
				<tr>
					<td><input type="submit" value="Invia"></td>
				</tr>
			</table>
		</form>
	</body>
</html>
