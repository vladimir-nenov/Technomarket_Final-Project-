<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="Description" content="Техномаркет най-голямата верига за бяла, черна и офис техника в България. Купи лесно и бързо онлайн с безплатна доставка. Удължаване на гаранцията с програма Гаранция плюс.">
	<link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css" />">
	<style>
		.slides {
			display:none;
		}
		#index{
			z-index:-1;
			margin-top: 150px;
			magin-top: -10%;
			text-align: center;
			margin-botton: 0px;
			padding: 0px;
			position: relative;
			top: -200px;
		}
		body, div, img {
			margin: 0px;
			padding: 0px;
		}
	</style>
	<title>Технoмаркет - онлайн магазин</title>
</head>
	<body>
		<jsp:include page="header.jsp" />
		<div id="index">
			<div class="w3-content w3-section" style="max-width:1550px; margin-top:-200px; position: relative">
					<a class="btn-links" href="<c:url value='/header/apple'/>">
						<img class="slides" alt="slide1" src="<c:url value='/img/banners/apple.jpg'/>" style="width:100%">
					</a>
					<a class="btn-links" href="<c:url value='/header/home'/>">
						<img class="slides" alt="slide2" src="<c:url value='/img/banners/home.jpg'/>" style="width:100%">
  					</a>
  					<a class="btn-links" href="<c:url value='/header/stores'/>">
  						<img class="slides" alt="slide3" src="<c:url value='/img/banners/stores.jpg'/>" style="width:100%">
					</a>
			</div>
		</div>
		<script>
			var myIndex = 0;
			carousel();

			function carousel() {
			var i;
			var x = document.getElementsByClassName("slides");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";  
			}
			myIndex++;
			if (myIndex > x.length) {myIndex = 1}    
				x[myIndex-1].style.display = "block";  
				setTimeout(carousel, 6000); // Change image every 6 seconds
			}
		</script>
		<jsp:include page="footer.jsp" />
	</body>
</html>