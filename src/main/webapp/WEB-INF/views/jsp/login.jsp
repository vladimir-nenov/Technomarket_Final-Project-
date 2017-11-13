<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<c:url value="/css/login_styles.css" />" rel="stylesheet">
		<script type="text/javascript" src="<cs:url value='/js/common_scripts.js'/>"></script>
		<title>Техномаркет - вход</title>
	</head>
	<body class="log-head">
	<jsp:include page="header.jsp" />
	
	<div class="log-head" id="common-container">
	<div class="log-head" id="left-div">
	  <h2 class="login-titles">Вход в сайта</h2>	
	  <c:if test="${logInPls == true}">
	    <p>Моля влезте в своя профил!</p>
	  </c:if>
		<c:if test="${ invalidUser != null}">
			<div>
			<p>Невалидно удостоверение за автентикация.</p>
			</div>
		</c:if>
		<form action="<cs:url value='/login'/>" method="post">
			<label id="first-label" .class="lagel-log" for="email"><strong>Имейл</strong></label>
			<input id="field-1" class="txt-log" id="email" type="email" name="username" maxlength="35" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
			<label  id="second-label" .class="lagel-log" for="password" ><strong>Парола</strong></label>
			<input id="field-2" class="txt-log" id="password" type="password" minlength = "8" maxlength="35" name="password" required><br>
			<div id="rem-forg">
			<label for="checkbox"><strong>Запомни ме</strong></label>
			<input id="checkbox" type = "checkbox" name = "remember"> 
				<a id="forgotten" href="<cs:url value='../forgotten'/>">Забравена парола?</a>
			</div>
			<input id="btn-log" type="submit" value="Вход"><br>
		</form>
	</div>
	<hr id="center-line">
	<div class="log-head" id="right-div">	
		<h2 class="login-titles">Нямаш профил?</h2>
		<button id="btn-reg"><a id="btn-reg-a" href="<cs:url value='/register'/>"><strong>Регистрирай се</strong></a></button>
	</div>	
	<div id="fix">
	</div>
	</div>
	<div class="push"></div>
	<div id="foot">
		<jsp:include page="footer.jsp" />
	</div>
	</body>
</html>