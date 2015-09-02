<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR"/>
		<title>Welcome Administrator <%=session.getAttribute("name")%></title>
	</head>
	<body>
		<form action="LoginServlet" method="post">  
		    <fieldset style="width:300px; margin: 20px auto; ">  
		        <legend> Welcome Administrator </legend>  
			    <p>Scegliere la funzione che si vuole svolgere:<br/>
				</p>
				<table>
					<tr>
						<td>
							 <fieldset style="width:150px; height: 80px; margin: 0 auto; ">  
		       					<legend> Gestione Utenti </legend>
								<input type="submit" value="Crea Nuovo Account" name="tmcAction" style="width: 150px;"/>
								<input type="submit" value="Modifica Account" name="tmcAction" style="width: 150px;"/>
								<input type="submit" value="Eliminazione Account" name="tmcAction" style="width: 150px;"/>
							</fieldset>
						</td>
						<td>
							 <fieldset style="width:150px; height: 80px; margin: 0 auto; ">  
		       					<legend> Gestione Furti </legend>
								<input type="submit" value="Tracking Veicolo" name="tmcAction" style="width: 150px;"/>
							</fieldset>
						</td>
					</tr>
				</table>
				<input type="submit" value="Logout" name="tmcLogin" style="width: 150px;"/>
		    </fieldset>  
		</form>   
	</body>
</html>
