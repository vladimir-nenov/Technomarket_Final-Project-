<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Техномаркет - имейлът е изпратен</title>
	<link href="<c:url value="/css/common_styles.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp"/>
		<div class="log-head" id="common-container">
			<div id="left-div">
				<c:if test="${ systemProblem != null }">
					<h3>В момента системата не може да ви генерира нова парола, моля опитайте по-късно!</h3>
		  		</c:if>
				<c:if test="${ systemProblem == null }">
					<h3>Информацията е изпратена на имейла ви.</h3>
		  		</c:if>
			</div>
		<hr id="center-line">
		</div>	
		<div class="push"></div>
	<jsp:include page = "footer.jsp"/>
</body>
</html>