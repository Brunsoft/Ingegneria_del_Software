<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%
    String user = "";
    char userMode = 'U';
    char minMode = 'U';
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
		<title>Area riservata - Utente</title>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	</head>
	<body>	
		<h1>POSIZIONE VEICOLI</h1>
		<%=user%>
		<hr/>
		<div id="updateDiv"></div>
		<hr/>
		<form action="LogoutServlet" method="POST">
			<input type="submit" name="Logout" value="Logout">
		</form>
		</br>
		<a href="welcomeUser.jsp">Home</a>
		<p><%=session.getAttribute("messaggio")%></p>
		<script>
			var icon = new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/micons/cabs.png");
			var map;
			var markLAT, markLNG, indice, idFurto, targa, oraP;
			var bounds = new google.maps.LatLngBounds();
			var marker;
	
		// ===================== RICAVO DALLA SERVLET TUTTE LE COORDINATE DI TUTTI I VEICOLI DELL'UTENTE CORRENTE =====================
			function initialize() {
				
				$.get("MyCarsPositionServlet", {email:"<%=session.getAttribute("currUserEmail").toString()%>"}, function(responseJson){

			// ========== SE SON PRESENTI DEI VEICOLI CREO LA MAPPA E LI VISUALIZZO ==========

					if(responseJson.length > 0){
						var myLatLng = {lat: 45.412538, lng: 10.990101};
						$("<div id=\"map-canvas\" style=\"height:400px; width:100%;\"><h1>Non possiedi alcun veicolo!</h1></div>").appendTo($("#updateDiv"));
						map = new google.maps.Map(document.getElementById('map-canvas'), {
							zoom: 8,
							center: myLatLng
						});
		    			$.each(responseJson, function(index, item) {
							markLAT = item.lat;
							markLNG = item.lng;
							targa = item.targa;
							//alert( item.lat+' '+item.lng+' '+item.email );
						
							addMarker(markLAT, markLNG, targa);	
							indice = index;
		    			});
					}else{
						$("<h1>Non possiedi alcun veicolo!</h1>").appendTo($("#updateDiv")); 
					}
				});
			}
			google.maps.event.addDomListener(window, 'load', initialize);

		
		// ========================================== AGGIUNGO MARKER SULL'ULTIMO PUNTO ====================================
			function addMarker(lat, lng, targa){
				var pt = new google.maps.LatLng(lat, lng);
				marker = new google.maps.Marker({
    				position: pt,
    				map: map,
					icon: icon,
					animation: google.maps.Animation.DROP,
    				title: targa
  				});
				marker.setMap(map);
				//map.panTo( new google.maps.LatLng( lat, lon ) );
			}			
		</script>
	</body>
</html>
