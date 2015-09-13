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
		<title>Live Tracking Furto</title>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	</head>
	<body>	
		<h1>LIVE TRACKING FURTO</h1>
		<div>
			<%=user%>
			<form action="LogoutServlet" method="POST" style="float:right">
				<input type="submit" name="Logout" value="Logout">
			</form>
		</div>
		<hr/>
        <div>
		    <div id="map-canvas" style="height:400px; width:60%; float:left;"></div>
            <div id="video-canvas" style="height:400px; width:40%; float:left;">
                <iframe width="100%" height="400" src="https://www.youtube.com/embed/gbPXeiY-Ng0?fs=1&autoplay=1&loop=1" frameborder="0" allowfullscreen></iframe>
            </div>
        </div>
        <hr/>
		</br>
		<a href="sceltaVeicoloFurto.jsp">LISTA FURTI ATTIVI</a><br/>
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

		// ======================================= SPOSTO IL MARKER SULL'ULTIMO PUNTO ======================================
			function moveMarker(lat, lon) {
				marker.setPosition( new google.maps.LatLng( lat, lon ) );
				if (marker.getAnimation() == null)
					marker.setAnimation(google.maps.Animation.BOUNCE);
				map.panTo( new google.maps.LatLng( lat, lon ) );
			};
		
		// ========================================== AGGIORNO I DATI OGNI 1000ms ==========================================
			var auto_refresh = setInterval(
				function (){
					var origin = new google.maps.LatLng(markLAT, markLNG);						// Salvo posizione precedente 
					ora2 = ora1;
					$.get("UpdateServlet", {idFurto:"<%=session.getAttribute("furtoSel").toString()%>"}, function(responseJson){
						//var $ul = $("<ul>").appendTo($("#updateDiv")); 
						
						$.each(responseJson, function(index, item) {
						
							if(markLAT != item.lat && markLNG != item.lng){
								timer = 0;
								indice = indice + 1;
								//$("<li>").text("Lat: "+item.lat+" Lng: "+item.lng+" "+indice).appendTo($ul);
								markLAT = item.lat;
								markLNG = item.lng;
								ora1 = item.ora;
								/*if( item.id != idFurto ){
									window.location.reload();
								} 
								else{ */
									flightPlanCoordinates[indice] = new google.maps.LatLng(item.lat,item.lng);
									flightPath = new google.maps.Polyline({
										path: flightPlanCoordinates,
										geodesic: true,
										strokeColor: '#FF0000',
										strokeOpacity: 1.0,
										strokeWeight: 2
								  	});
									flightPath.setMap(map);
									moveMarker(markLAT,markLNG);

								
									var destination = new google.maps.LatLng(markLAT, markLNG);		// Salvo posizione corrente
									var service = new google.maps.DistanceMatrixService();
									//var geocoder = new google.maps.Geocoder;
					
						//=========================== CALCOLO TEMPO IMPEGATO ===================================

							/*		var token = ora1.split(":");
				  					var ora1Sec = (Number(token[0]) * 360) + (Number(token[1]) * 60) + Number(token[2]);
									//el.innerHTML = "<p>Tempo 1(s): "+ ora1Sec +"</p>";					
					
									var token = ora2.split(":");
				  					var ora2Sec = (Number(token[0]) * 360) + (Number(token[1]) * 60) + Number(token[2]);
									//el.innerHTML += "<p>Tempo 2(s): "+ ora2Sec +"</p>";	

									var nDiff;
									if (ora1Sec > ora2Sec) {
										nDiff = (ora1Sec - ora2Sec) / 360;
								  	} else {
										nDiff = (ora2Sec - ora1Sec) / 360;
								  	}

						//============================== CALCOLO DISTANZA PERCORSA ==================================
									var dist;
									service.getDistanceMatrix(
										{
											origins: [origin],
											destinations: [destination],
											travelMode: google.maps.TravelMode.DRIVING
										}, 
										function(response, status) {
											var originList = response.originAddresses;
					  						var destinationList = response.destinationAddresses;

											var results = response.rows[0].elements;
											//geocoder.geocode({'address': originList[0]});
							
											//geocoder.geocode({'address': destinationList[0]});
											//el.innerHTML += "<p>"+originList[0] + "<br></br> to <br></br>"+ 
											//destinationList[0] +"<br></br> "+ results[0].distance.value+"</p>";

											dist = results[0].distance.value / 1000;
											distT += dist;
											var velocita = dist / nDiff;
											var token = destinationList[0].split(",");
											el.innerHTML = 	"<table>"+
																"<tr><td> Targa: </td><td>"				+ targa +"</td></tr>"+
																"<tr><td> Ora Partenza: </td><td>"		+ oraP +"</td></tr>"+
																"<tr><td> Ultimo Agg.: </td><td>"		+ ora1 +"</td></tr>"+
																"<tr><td> Distanza Totale: </td><td>"	+ (Math.floor(distT * 100) / 100) +" Km</td></tr>"+
																"<tr><td> Velocita: </td><td>"			+ (Math.floor(velocita * 100) / 100) +" Km/h</td></tr>"+
																"<tr><td> Via: </td><td>"				+ token[0] +"</td></tr>"+
																"<tr><td> Provincia: </td><td>"			+ (token[2].split(" "))[2] +"</td></tr>"+
															"</table>";
										}
									); 
      							}*/
							}
							else{
								if( timer >= 10 ){
									marker.setAnimation(null);
									timer = 0;
								}
								else									
									timer++;
							}
						});
					});
				},
			1000); // refresh every 1000 milliseconds */
		</script>
	</body>
</html>
