<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
		<title>Техномаркет - магазини</title>
		<link href="<cs:url value="/css/city_styles.css" />" rel="stylesheet">
</head>
<body class="log-head">
<jsp:include page="header.jsp" />

		<div class="log-head" id="common-container">
		<c:if test="${ stores != null}">
			<c:forEach items="${stores}" var="store">
				<div id="store-info">
				<ul id="store-ul">
					<li class="store-li"><strong>Адрес:</strong> ${store.address}</li>
					<li class="store-li"><strong>Тел. номер:</strong> ${store.phoneNumber}</li>
					<li class="store-li"><strong>Имейл:</strong> ${store.email}</li>
					<li class="store-li"><strong>Работно време:</strong> ${store.workingTime}</li>
					<li class="store-li"><strong>GPS:</strong> ${store.gps}</li>
					<li class="store-li">
						<form action = "<c:url value='/store/showInTheMap'/>" method = "get">
				  			<input type = "hidden" name = "value" value = "${store.storeId}"/>
				  			<input type = "submit" class="store-map-btn" value = "На карта"/>
						</form>
					</li>
				</ul>
				</div>	
				<div id="store-pic">
					<a target="_blank" href="<c:url value='/store/store_pic?value=${store.storeId}'/>">
						<img id="picture-nail-store" src="<c:url value='/store/store_pic?value=${store.storeId}'/>" alt="product-image">
					</a>
				</div>		
			</c:forEach>
		</c:if>
</div>
<div class="push"></div>
<jsp:include page="footer.jsp" />
</body>
</html>