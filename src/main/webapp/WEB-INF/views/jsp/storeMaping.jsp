<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cs" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Техномаркет - магазин</title>
	<link href="<cs:url value="/css/common_stores_styles.css" />" rel="stylesheet">
</head>
<body onload="getCityMap(${longitude}, ${latitude});">
<jsp:include page="header.jsp" />
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDkHN_gdiuaWXmHeLB8Fpe_pBc840VRgIk&callback=map"
        type="text/javascript"></script>
</head>
		<div class="log-head" id="common-container">
<div id="map" style="float: left; width: 1050px; height: 500px;"></div>

<script type="text/javascript">
 function getCityMap(longitude,latitude){ 

    var locations = [
        ['ТЕХНОМАРКЕТ', longitude, latitude, 1]
    ];
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: new google.maps.LatLng(longitude, latitude),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    var infowindow = new google.maps.InfoWindow();
    var marker, i;
    for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map
        });
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infowindow.setContent(locations[i][0]);
                infowindow.open(map, marker);
            }
        })(marker, i));
    }
 }
</script>
 </div>
 <div class="push"></div>
<jsp:include page="footer.jsp" />
</body>
</html>