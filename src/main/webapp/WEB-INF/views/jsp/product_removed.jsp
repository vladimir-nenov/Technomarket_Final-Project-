<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Техномаркет - премахнат продукт</title>
	<link href="<c:url value="/css/common_styles.css" />" rel="stylesheet">
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
			<div class="log-head" id="common-container">
			<div id="left-div">
			<c:if test="${sessionScope.user.isAdmin == false}">
				<h3>Само админите, влезли в сметката си, имат достъп до тази страница!</h3>
			</c:if>
			<c:if test="${sessionScope.user.isAdmin == true}">
				<h3>Продуктът беше усешно премахнат!</h3>
			</c:if>
			</div>
			<hr id="center-line">
			</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
	</body>	
</html>