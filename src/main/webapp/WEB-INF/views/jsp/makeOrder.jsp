<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/order_styles.css" />" rel="stylesheet">
	<title>Техномаркет - поръчка</title>
</head>
<body class="log-head">
<jsp:include page="header.jsp" />
	<c:if test="${sessionScope.user != null}">
	<div class="log-head" id="common-container">
		<h2 id="page-header">Преглед на поръчката</h2>
    	<div class="head-div">
        	<img src="<cs:url value='/img/order_numeration/one.png'/>" alt="first-heading" id="first-heading">
        	<div class="title-div">
        		<h3 class="heading-title">Вашата поръчка все още не е потвърдена</h3>
        	</div>
    	</div>
		<div class="info-div">
				<div class="data-div">
					<span><strong class="dt">Дата:</strong></span><span class="data-span" id="order-date">${ date }</span><br>
				</div>
				<div class="data-div">
					<span><strong class="dt">Статус:</strong></span><span class="data-span">Непотвърдена поръчка</span><br>
				</div>
				<div class="data-div">
					<span><strong class="dt">Е-майл:</strong></span><span class="data-span"><a id="email" href>${ sessionScope.user.email }</a></span>
				</div>
		</div>
            
		<div class="head-div">
			<img src="<cs:url value='/img/order_numeration/two.png'/>" alt="second-heading" id="second-heading">
			<div class="title-div">
				<h3 class="heading-title">Детайли на поръчката</h3>
			</div>
		</div>
		<div class="info-div">
		<c:if test="${sessionScope.basket.size() == 1 }">
			<div class="products-head">
				<h3>Продукт:</h3>
			</div>
		</c:if>
		<c:if test="${sessionScope.basket.size() > 1 }">
			<div class="products-head">
				<h3>Продукти:</h3>
			</div>	
		</c:if>
		<cs:forEach items="${ sessionScope.basket }" var="basket"> 
			<c:if test="${basket.key.percentPromo == 0 }">
				<div class="data-div">
					<span class="individual-product"><strong>${basket.value} X ${basket.key.name} - <span> цена: ${basket.key.price}</span> лв.</strong></span>
				</div>
			</c:if>
			<c:if test="${basket.key.percentPromo > 0 }">
				<c:set var="promoPrice" value="${0}"/>
				<c:set var="promoPrice" value="${promoPrice + basket.key.price - (basket.key.price * basket.key.percentPromo/100)}" />
				<div class="data-div">
					<span class="individual-product"><strong>${basket.value} x ${basket.key.name} - <span> цена: <del>${basket.key.price}</del> <span class="promo-price">${promoPrice}</span> лв.</span></strong></span>
				</div>
			</c:if>
		</cs:forEach>
			<div>
				<div class="data-div">
					<span><strong>Доставка - <span id="shipping">Безплатна</span></strong></span>
				</div>	
				<div class="products-head">
					<h3>Общо за плащане: ${price} лв.</h4>
				</h3>
			</div>
		</div>
		<div class="head-div">
			<div class="img-div">
				<img src="<cs:url value='/img/order_numeration/three.png'/>" alt="third-heading" id="third-heading">
				<div class="title-div">
					<h3 class="heading-title">Адрес за доставка</h3>
				</div>	
			</div>
		</div>
		<div class="info-div">
		<div id="address-form">
     	<form action="<cs:url value='/buyController/addOrder'/>" method="post">
				<label for="names" class="field-label"><strong>Име и Фамилия<span class="asterisk">*</strong></label>
				<input type="text" id="names" class="top-field" name="firstAndLastName" maxlength="35" required><br>
				<label for="phone" class="field-label"><strong>Мобилен Телефон<span class="asterisk">*</span></strong></label>
				<input type="text" class="top-field" id="phone" name="telNumber" maxlength="10" minlength="10" pattern="^[0-9]*$" required><br>
				<label for="city" class="field-label"><strong>Град<span class="asterisk">*</span></strong></label>
				<input type="text" class="top-field" id="city" name="town" maxlength="30" required></input>
				<label for="zip" class="row-label"><strong>Пощенски код<span class="asterisk">*</span></strong></label>
				<input type="number" id="zip" name="postCode" min = "1000"  max="9999" pattern= "[0-9]" required></input><br>
				<label for="street" class="field-label"><strong>Улица/Квартал<span class="asterisk">*</span></strong></label>
				<input type="text" class="top-field" id="street" name="street" maxlength="60" required><br>
				<label class="field-label"><strong>Други</strong></label>
				<label for="number" class="row-label"><strong>Номер</strong></label>
				<input type="number" class="small-number-input" id="number" name="number" min = "1" max = "999" required>
				<label for="block" class="row-label"><strong>Блок</strong></label>
				<input type="text" class="small-number-input" id="block" name="block" maxlength="35">  
                <label for="entrance" class="row-label"><strong>Вход</strong></label>
                <input type="text" class="small-number-input" id="entrance" name="entrace" maxlength="10"> 
                <label for="floor" class="row-label"><strong>Етаж</strong></label>
                <input type="number" class="small-number-input" id="floor" name="floor" max="99" min = "0"> 
                <label for="aprtment" class="row-label"><strong>Апартамент</strong></label>
                <input type="number" class="small-number-input" id="apartment" name="apartment" max="999"><br>
               	<label for="notes" class="field-label"><strong>Уточнения за адрес</strong></label>
                <input type="text" class="top-field" id="notes" name="notes" maxlength="60"><br>
                <label for="payment" class="field-label"><strong>Начин на плащане*</strong></label>
                <select name = "payment" id="payment">
                    <option value = "buy-shipping">При получаване</option>
                    <option value="bankCard">Банкова карта</option>
                </select>
				<input type="hidden" name="price" value = "${price}">
				<input type="submit" id="order-submit-btn" value="Изпрати">
			</form>
			</div>
     	</div>
     	</c:if>
     	<c:if test="${sessionScope.user == null}">
				<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
		</c:if>
	</div>
	<div class="push"></div>
    <jsp:include page="footer.jsp" />
</body>
</html>