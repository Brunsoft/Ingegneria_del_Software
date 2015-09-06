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
		<title>Storico Furti</title>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	</head>
	<body>	
		<h1>TRAGITTO PERCORSO DAL LADRO</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
		<div id="map-canvas" style="height:400px; width:100%;"></div>
		<hr/>
		<a href="sceltaStoricoFurto.jsp">STORICO FURTI</a><br/>
		<a href="welcomeUser.jsp">HOMEPAGE</a>
		<!--p><%=session.getAttribute("messaggio")%></p-->
		<script>
			var icon = new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/micons/cabs.png");
			var map;
			var markLAT, markLNG, indice, idFurto, targa, oraP;
			var timer = 0;
			var distT = 0;
			var ora1 = "";
			var ora2 = "";
			var flightPlanCoordinates = [];
			var bounds = new google.maps.LatLngBounds();
			var flightPath;
			var marker;
			//var el = document.getElementById("outputDiv");
			//el.innerHTML = '';
	
		// ===================== RICAVO DALLA SERVLET TUTTE LE COORDINATE DELL'ULTIMO FURTO FINO AD ORA =====================
			function initialize() {

				$.get("TravelServlet", {idFurto:"<%=session.getAttribute("furtoSel").toString()%>"}, function(responseJson) {    
        			//var $ul = $("<ul>").appendTo($("#coordinatesDiv")); 
        			$.each(responseJson, function(index, item) {
            			//$("<li>").text("Lat: "+item.lat+" Lng: "+item.lng+" "+item.ora).appendTo($ul);
						markLAT = item.lat;
						markLNG = item.lng;
						ora1 = item.ora;
						if(index == 0)
							oraP = item.ora;
						flightPlanCoordinates[index] = new google.maps.LatLng(item.lat,item.lng);
						indice = index;
        			});

					map = new google.maps.Map(document.getElementById('map-canvas'), {
						zoom: 15,
						center: new google.maps.LatLng(markLAT, markLNG),
						mapTypeId: google.maps.MapTypeId.TERRAIN
					});

					addMarker(markLAT, markLNG);

    				flightPath = new google.maps.Polyline({
						path: flightPlanCoordinates,
						geodesic: true,
						strokeColor: '#FF0000',
						strokeOpacity: 1.0,
						strokeWeight: 2
				  	});
					//alert(flightPlanCoordinates.length);
					flightPath.setMap(map);
				});
			}
			google.maps.event.addDomListener(window, 'load', initialize);

		
		// ========================================== AGGIUNGO MARKER SULL'ULTIMO PUNTO ====================================
			function addMarker(lat, lng){
				var pt = new google.maps.LatLng(lat, lng);
				marker = new google.maps.Marker({
    				position: pt,
    				map: map,
					icon: icon,
					animation: google.maps.Animation.DROP,
					
    				title: 'Posizione attuale LADRO!'
  				});
				//map.panTo( new google.maps.LatLng( lat, lon ) );
			}
		</script>
	</body>
</html>
