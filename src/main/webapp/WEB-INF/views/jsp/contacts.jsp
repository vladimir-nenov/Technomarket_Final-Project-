<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head class="log-head">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/contacts_styles.css" />" rel="stylesheet">
	<title>Техномаркет - контакти</title>
	<script src="<c:url value="/js/contacts_script.js" />"></script>
</head>
<body class="log-head">
<jsp:include page="header.jsp"></jsp:include>

	<div class="log-head" id="common-container">
		<div class="log-head" id="left-div">
			<c:if test="${emptyEmail == true}">
				<p>Не сте въвели имейл! Моля променете полето преди да изпратите имейл!</p>
			</c:if>			
			<c:if test="${emptyMessage == true}">
				<p>Невалидно съобщение! Моля променете полето преди да изпратите имейл!</p>
			</c:if>	
			<c:if test="${invalidEmail == true}">
				<p>Невалиден имейл! Моля променете полето преди да изпратите имейл!</p>
			</c:if>	
			<c:if test="${invalidPhone == true}">
				<p>Невалиден телефонен номер! Моля променете полето преди да изпратите имейл!</p>
			</c:if>
			<c:if test="${emailSend == true}">
				<p>Съобщението е въведено спешно!</p>
			</c:if>	
			
			<h2>Изпратете ни съобщение</h2>

 			<form action="<c:url value="/info/contactsEmail" />" method  = "post">
    		<label for="field-1"><strong>Име и Фамилия<span class="asteriks">*</span></strong></label>
    		<input id="field-1" type="text" name="names" maxlength="35" required><br>
    		<label for="field-2"><strong>Имайл<span class="asteriks">*</span></strong></label>
    		<input id="field-2" type="email" name="email" maxlength="35" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
   			<label for="field-3"><strong>Телефон<span class="asteriks">*</span></strong></label>
    		<input id="field-3" type="number" name="phone" maxlength="10" minlength="10" pattern="^[0-9]*$" required><br>
    		<label if="label-4" for="field-4"><strong>Съобщение<span class="asteriks">*</span></strong></label>
    		<textarea id="field-4" rows="4" cols="50" required></textarea>
   			<input id="button-red" type = "submit" value = "Изпрати">
   			</form>
		</div>
   		<hr id="center-line">
		<div class="log-head" id="right-div">	
		<h3>Отдел електронна търговия</h3>

		<h1><strong class="color-tm">0700 10 800</strong></h1>

		<p><small>на цената на един градски разговор</small></p>

		<p><strong>Е-мейл:</strong> <a class="contact-link" href="mailto:online@technomarket.bg">online@technomarket.bg</a></p>

		<p><small class="color-gray">Моля, за предявяване на рекламация за уреди закупени<br>
		On-Line моля използвайте: <a class="contact-link" href="mailto:onlinereklamacii@technomarket.bg">onlinereklamacii@technomarket.bg</a></small></p>

		<h3>Отдел Корпоративни клиенти</h3>
		<address><strong>Е-мейл:</strong> <a class="contact-link" href="mailto:corporate@technomarket.bg">corporate@technomarket.bg</a><br />
		<strong><span class="title" style="height: 25px;">Адрес:</span></strong> гр.София п.к. 1138 бул.Цариградско шосе 361</address>

		<h3>Централен офис</h3>

		<address><strong><span class="title" style="height: 25px;">Адрес:</span></strong> гр.София п.к. 1138 бул.&nbsp;Цариградско шосе 361<br />
		<strong>Е-мейл:</strong> <a class="contact-link" href="mailto:info@technomarket.bg">info@technomarket.bg</a><br />
		Тел.: 02&nbsp;9421 101<br />
		Тел.: 02 9421 105</address>
		</div>
	</div>
	<div class="push"></div>
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>