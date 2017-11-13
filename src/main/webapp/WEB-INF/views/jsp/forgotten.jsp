<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Техномаркет - забравена парола</title>
	<link href="<c:url value="/css/common_styles.css" />" rel="stylesheet">
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container">
			<div id="left-div">
			<h2 class="loin-titles">Възстановяване на парола</h2>
        	<c:if test="${ emailError != null }">
				<div class="log-head">
					<p>Невалиден потребител</p>
				</div>
			</c:if>
			<form action="forgotten" method="post">
				<label id="first-label" for="field"><strong>Е-мейл</strong></label>
				<input id="field" type="email" name="email" maxlength="35" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
				<input id="btn-common" type="submit" value="Изпрати"><br>
			</form>
			</div>
			<hr id="center-line">
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
</body>
</html>