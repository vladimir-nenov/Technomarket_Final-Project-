<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value="/css/insert_styles.css" />" rel="stylesheet">
	<title>Техномаркет - администраторски панел за добавяне на нов продукт</title>
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
			<div class="log-head" id="common-container">
				<div class="log-head" id="left-div">
									<h3>Добави нов продукт</h3>
		<c:if test="${sessionScope.user.isAdmin == true}">
		<cs:if test="${ added != null }">
			<div>
				<a href="<cs:url value='/info/infoForProduct?value=${productId}'/>">     
					<img id="admin-go" alt="go-button" src="<cs:url value='/img/buttons/admin-buttons/go.png'/>">
				</a>
				<p>Продуктът беше успешно добавен!<br>
				Моля преминете към страницата му, за да добавите количества към всеки магазин</p>
			</div>
		</cs:if>
		<cs:if test="${invalidProductData == true }">
			<p>Невалидни данни на продукт, моля въведете отново!</p>
		</cs:if>
		<cs:if test="${invalidDescription == true}">
			<p>Невалидно описание, моля въведете отново!</p>
		</cs:if>
			<form action="<cs:url value='/product/insert_product'/>" method="post" enctype="multipart/form-data">
				<label for="field-1" class="em-label"><strong>Име на продукт<span class="asterisk">*</span></strong></label>
				<input type="text" class="isert-field" id="field-1" name="productName" minlength="2" maxlength="35" required ><br>
				<label for="field-2" class="em-label"><strong>Марка<span class="asterisk">*</span></strong></label>
				<input type="text" class="isert-field" id="field-2" name="tradeMark" minlength="2" maxlength="35" required><br>
				<label for="field-3" class="em-label"><strong>Категория<span class="asterisk">*</span></strong></label>
				<input type="text" class="isert-field" id="field-3" name="categoryName" minlength = "2" maxlength="35" required><br>
				<label for="field-4" class="em-label"><strong>Цена<span class="asterisk">*</span></strong></label>
				<input type="number" class="isert-field" id="field-4" name="price" value="price" max  = "99999" min="1" step="any" required><br>
				<label for="field-5" class="em-label"><strong>Гаранция в месеци<span class="asterisk">*</span></strong></label>
				<input type="number" class="isert-field" id="field-5" name="warranty" value="warranty" max="99" min="0" pattern= "[0-9]" required><br>
				<label for="field-6" class="em-label"><strong>Промо<br> процент<span class="asterisk">*</span></strong></label>
				<input type="number" class="isert-field" id="field-6" name="promoPercent" value="promo" min="0" max="99" pattern= "[0-9]" required><br>
				<label for="field-7" id="label-7"><strong>Описание<span class="asterisk">*</span></strong></label>
				<textarea id="field-7" class="isert-field" name="description" rows="4" cols="50" required></textarea>
				<label for="field-8" id="label-8" class="em-label"><strong>Снимка на продукта<span class="asterisk">*</span></strong><sup><a href="#fn1" id="ref1">*</a></sup></label>
				<div if="file-block">
				<input type="file" id="field-8" name="image" value="image" accept=".jpg,.png,.gif,.bmp,.jpeg" required>
				<input type="submit" id="button-red" value="Добави">
				</div>
			</form>
			<div id="note">
				<sup id="fn1">1. Допустимите формати са: .jpg,.png,.gif,.bmp,.jpeg<a href="#ref1" title="Jump back to footnote 1 in the text.">↩</a></sup>
			</div>
		</c:if>
	<c:if test="${sessionScope.user.isAdmin == false}">
		<p>Само администраторите, влезли в сметката си, имат достъп до тази страница!</p>
	</c:if>
	</div>
	</div>
	<div class="push"></div>
	<jsp:include page="footer.jsp" />
	</body>
</html>