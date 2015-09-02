<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR"/>
		<title>New Account</title>
	</head>
	<body>
		<fieldset style="width:230px; margin: 20px auto; ">  
	       <legend> Create New Account </legend>
			<form action="LoginServlet" method="POST">  
			<table>
				<tr><td>Nome </td><td><input type="text" name="nomeN" required="required" style="width: 150px;"/></td></tr>
				<tr><td>Cognome </td><td><input type="text" name="cognN" required="required" style="width: 150px;"/></td></tr>
				<tr><td>Password </td><td><input type="password" name="pdwN" required="required" style="width: 150px;"/></td></tr>
				<tr><td>eMail </td><td><input type="text" name="mailN" required="required" style="width: 150px;"/></td></tr>
				<tr><td>Telefono </td><td><input type="text" name="telN" required="required" style="width: 150px;"/></td></tr>
			</table>
			<br/>
			<input type="submit" value="Iscrivi" name="tmcNewUser" style="width: 110px;"/>
			</form>

			<form action="LoginServlet" method="POST"> 
				<input type="submit" value="Indietro" name="tmcAction" style="width: 110px;"/>
				<% 
					String error = (String)request.getAttribute("dataStringa"); 
					try{
						if ( !error.equals("") )
							out.println("<p>"+error+"</p>");
					}catch(Exception e){} 
				%>
			</form>   
		</fieldset>
	</body>
</html>
