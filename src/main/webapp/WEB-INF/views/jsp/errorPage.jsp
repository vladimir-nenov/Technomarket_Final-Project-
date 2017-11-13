<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/error_styles.css" />" rel="stylesheet">
	<title>Техномаркет - грешка</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
   		<div id="container-error"><img id="error-img" src="<cs:url value='/img/errors/error.jpg'/>" alt="contacts"></div>
   				<div class="push"></div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>