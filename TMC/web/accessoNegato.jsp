<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String home = "login.jsp";
	String mail = ""+session.getAttribute("currUserEmail");
	if (session.getAttribute("currUserEmail") != null)
		home = "welcomeUser.jsp";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Accesso negato</title>
	</head>
	<body>
		<h1>Accesso negato</h1>
		<h2>Non hai i privilegi per visualizzare questa pagina</h2>
		<hr/>
		<a href="<%=home%>">HOMEPAGE</a>
	</body>
</html>
