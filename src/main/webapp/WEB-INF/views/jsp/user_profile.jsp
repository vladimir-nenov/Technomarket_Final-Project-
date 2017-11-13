<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value="/css/profile_styles.css" />" rel="stylesheet">
	<title>Техномаркет - потребителски профил</title>
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container">
			<div class="log-head" id="left-div">
				<h2>Твоето потребителско име</h2>
				<c:if test="${sessionScope.user != null}">
					<strong>Имейл:</strong> <span>"${ sessionScope.user.email }"</span>  
				</c:if>
				<c:if test="${sessionScope.user == null}">
					<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
				</c:if>
			</div>
			<hr id="center-line">	
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
	</body>
</html>