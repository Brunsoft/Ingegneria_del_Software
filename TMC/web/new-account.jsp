<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR"/>
		<title>New Account></title>
	</head>
	<body>
		<form action="LoginServlet" method="post">  
		    <fieldset style="width:450px; margin: 20px auto; ">  
		        <legend> Create New Account </legend>  
			    <p>Compila i campi qui sotto per inserire un nuovo Utente:<br/>
				</p>
				<ul>			
				    <li><input type="text" name="username" style="width: 150px;"/></li>
				    <li><input type="text" name="surname" style="width: 150px;"/></li>
				    <li><input type="password" name="newpassword" style="width: 150px;"/></li>
					<li><input type="text" name="mail" style="width: 150px;"/></li>
					<li><input type="text" name="tel" style="width: 150px;"/></li>
					<li><input type="submit" value="Iscrivi" name="tmcAction" style="width: 150px;"/></li>
				</ul>
		    </fieldset>  
		</form>   
	</body>
</html>
