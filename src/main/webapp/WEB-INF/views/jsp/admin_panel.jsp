<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value='/css/admin_panel.css' />" rel="stylesheet">
	<title>Техномаркет - администраторски панел</title>
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container">
			<div class="log-head" id="left-div">
				<h2>Администраторски инструменти</h2>
				<c:if test="${sessionScope.user.isAdmin == true}">
					<button class="adm-btn"><a class="adm-a-btn" href="<c:url value='/info/infoAdminInsertProduct'/>"><strong>Нов продукт</strong></a></button>			
					<button class="adm-btn"><a class="adm-a-btn" href="<c:url value='/admin/adminCreateAdmin'/>"><strong>Нов админ</strong></a></button>
					<button class="adm-btn"><a class="adm-a-btn" href="<c:url value='/info/infoAdminOrders'/>"><strong>Поръчки</strong></a></button>
				</c:if>
				<c:if test="${sessionScope.user == null}">
					<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
				</c:if>
			</div>
		</div>
		<c:if test="${sessionScope.user.isAdmin == false}">
			<p>Само администраторите, влезли в сметката си, имат достъп до тази страница!</p>
		</c:if>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
	</body>
</html>