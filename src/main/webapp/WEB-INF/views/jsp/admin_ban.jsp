<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
	<body>
	
			<c:if test="${sessionScope.user.isAdmin == true}">
				<p>Въведете имейла на потребителя, който чийто достъп до сайта ще бъде забранен:</p>
				<form action="<cs:url value='/product/insert_product'/>" method="post" enctype="multipart/form-data">
					<label for="name">Имейл</label>
					<input type="email" name="productName" value="name" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
					<input type="submit" value="Въведи">
				</form>
			</c:if>
			<c:if test="${sessionScope.user.isAdmin == false}">
				<p>Само администраторите, влезли в сметката си, имат достъп до тази страница!</p>
			</c:if>
	
	</body>
</html>