<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/common_stores_styles.css" />" rel="stylesheet">
	<title>Техномаркет - магазини</title>
</head>
<body class="log-head">
<jsp:include page="header.jsp" />
<div class="log-head" id="common-container">
<div id="map-move">
	<jsp:include page="maping.jsp" />
</div>	
	<div id="store-contaner">
		<ul id="stored-id">
		<c:forEach items="${ allNameOfStores }" var="allNameOfStores">
			<li class="indiv-store-li">
				<strong>Магазини във </strong><span class="asterisk">*</span> <a href = "<cs:url value='/header/cities?value=${allNameOfStores}'/>" class="store-link-a"><strong>${allNameOfStores}</strong></a>
			</li>
		</c:forEach>
 		</ul>
	</div>
	</div>
	<div class="push"></div>
<jsp:include page="footer.jsp" />
</body>
</html>