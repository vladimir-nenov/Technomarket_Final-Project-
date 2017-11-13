<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Техномаркет - създай админ</title>
	<link href="<c:url value="/css/common_styles.css" />" rel="stylesheet">
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container">
			<div id="left-div">
        	<c:if test="${create != null}">
         		<p>Новият админ е създаден</p>
        	</c:if>
         	<c:if test="${invalidEmail == true }">
         		<p>Невалиден имей адрес, моля въведете го отново!</p>
         	</c:if>
			<c:if test="${sessionScope.user.isAdmin == true}">
			
				<h2>Създаване на нов админ</h2>
				<p>Въведете имейла на потребителя, който ще получи администраторки права:</p>
				<form action="<cs:url value='/admin/createAdmin'/>" method="post" enctype="multipart/form-data">
					<label id="first-label" for="field"><strong>Имейл</strong></label>
					<input id="field" type="email" name="email" maxlength="35" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
					<input id="btn-common" type="submit" value="Въведи">
				</form>
			</c:if>
			<c:if test="${sessionScope.user.isAdmin == false}">
				<p>Само администраторите, влезли в сметката си, имат достъп до тази страница!</p>
			</c:if>
			</div>
			<hr id="center-line">
			</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
	</body>
</html>