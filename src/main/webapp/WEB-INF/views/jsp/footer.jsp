<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<cs:url value="/css/footer.css" />" rel="stylesheet">
	</head>
<body>
		<div id="main-container">
		<div id="ul-container">
			<div class="mini-containers">
			<ul id="first-ul">
				<li class="footer-li"><strong class="titles">Техномаркет</strong></li>
				<li> <a class="footer-a" href="<cs:url value='/header/stores'/>">Магазини</a> </li>
				<li> <a class="footer-a" href="<cs:url value='/info/infoContacts'/>">Контакти</a> </li>
			</ul>
			</div>
			<div class="mini-containers">
			<ul id="second-ul">
				<li class="footer-li"><strong class="titles">Лесно пазаруване</strong></li>      
				<li> <a class="footer-a" href="<cs:url value='/info/infoForShoppingCon'/>">Успловия за пазаруване</a> </li>
				<li> <a class="footer-a" href="<cs:url value='/info/infoForDelivery'/>">Бърза и безплатна доставка до дома Ви</a> </li>
				<!-- <li> <a href="<cs:url value='/info/infoForOnlinePay'/>/">Сигурно Online разплащане</a> </li>
				<li> <a href="<cs:url value='/info/infoForTBICredit'/>">Условия за TBI Bank кредитиране</a> </li>
				<li> <a href="<cs:url value='/info/infoForUniCredit'/>">Условия за UniCredit кредитиране</a> </li> -->
			</ul>
			</div>
			<div class="mini-containers">
			<ul id="third-ul">
				<li class="footer-li"><strong class="titles">Контакти</strong></li>
				<li class="footer-li"><strong>Национален телефон на клиента</strong></li>
				<li class="footer-li"><strong id="phone">0700 10 800</strong></li>
				<li class="footer-li">на цената на един градски разговор</li>
			</ul>
			</div>
			</div>
		</div>
	</body>
</html>