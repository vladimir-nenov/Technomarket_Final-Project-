<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/user_menu.css" />" rel="stylesheet">
</head>
<body>
	<div>
	<c:if test="${sessionScope.user != null}">
		<ul id="menu-head-user">
			<li id="head-li"><stong><span id="glyphicons-parents"></span>Профил</stong></li>
			<li class="menu-button-user"><a class="user-menu-link" href="<cs:url value='/info/infoUserProfile'/>"> Моят профил</a></li>
			<li class="menu-button-user"><a class="user-menu-link" href="<cs:url value='/info/infoUserOrders'/>">> Моите поръчки</a></li>
			<li class="menu-button-user"><a class="user-menu-link" href="<cs:url value='/favourite/infoUserFavourites'/>">> Любими продукти</a></li>
				<c:if test="${session.user.isAdmin == true}">
					<li class="menu-button-user"><a class="user-menu-link" href="<cs:url value='/info/infoAdminPanel'/>">> Админ панел</a></li>
				</c:if>
			<li class="menu-button-user"><a class="user-menu-link" href="logout">> Изход</a></li>
		</ul>
	</c:if>
	<c:if test="${sessionScope.user == null}">
	</c:if>
	</div>
</body>
</html>