<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR"/>
		<title>Welcome Regular <%=session.getAttribute("name")%></title>
	</head>
	<body>
		<form action="LoginServlet" method="post">  
		    <fieldset style="width:450px; margin: 20px auto; ">  
		        <legend> Welcome Regular </legend>  
				    <h>L'account ha ottenuto i privilegi di user.<br/>
						Scegliere dall'elenco sottostante la funzione che si vuole svolgere:<br/>
					</h>
				<ul>			
				    <li><a href='newaccount.jsp'>Crea Nuovo Account.</a></li>
				    <li><a href='updateaccount.jsp'>Modifica Account.</a></li>
				    <li><a href='deleteaccount.jsp'>Eliminazione Account.</a></li>
				    <li><a href='index.jsp'>Logout.</a></li>
				</ul>
		    </fieldset>  
		</form>   
	</body>
</html>
