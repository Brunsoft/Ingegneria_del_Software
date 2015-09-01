<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<html>  
	<head>  
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
		<title>Login Application</title>  
	</head>  
	<body>  
		<form action="LoginServlet" method="POST">  
		    <fieldset style="width:300px; margin: 20px auto; ">  
		        <legend> Login to TMC </legend>  
		        <table>  
		            <tr>  
		                <td>Username</td>  
		                <td><input type="text" name="username" required="required" /></td>  
		            </tr>  
		            <tr>  
		                <td>Password</td>  
		                <td><input type="password" name="userpass" required="required" /></td>  
		            </tr>
		            <tr>  
		                <td><input type="submit" value="Login" name="tmcLogin"/></td> 
						<td>
							<% String error = (String)request.getAttribute("dataStringa"); 
							   try{
									if ( error.equals("Password o Username errate") )
										out.println(error);
								}catch(Exception e){} %>
						</td> 
		            </tr>  
		        </table>  
		    </fieldset>  
		</form>  
	</body>  
</html>
