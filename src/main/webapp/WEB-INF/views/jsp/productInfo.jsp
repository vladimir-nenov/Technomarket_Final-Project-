<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
	<title>Техномаркет - продукт</title>
	<script src="<c:url value="/js/product_script.js" />"></script>
	<link href="<c:url value="/css/product_styles.css" />" rel="stylesheet">
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />
			<div class="log-head" id="common-container">
				<div id="product-pic-and-title">
					<h3>${product.name}</h3>
					<h6>Арт. Nº: ${product.productNumber}</h6>
					<a target="_blank" href="<c:url value='/product/product_pic?value=${product.productId}'/>">
						<img id="picture-nail" src="<c:url value='/product/product_pic?value=${product.productId}'/>" alt="product-image">
					</a>
			<c:if test="${sessionScope.user.isAdmin == true}">
				<c:if test="${invalidPercent == false }">
					<p>Невалиден запис! Позволените проценти са от 0 до 99!</p>
				</c:if>
			<ul id="admin-buttons">
				<li class="admin-btn">
					<form id="search" action="<c:url value='/product/setPromo'/>" method="post">
						<input type="hidden" name="productId" id="product" value="${product.productId}">
						<input type="number" id="promo-box" name="promoPercent" size="40" min="0" max="99" value="0" onclick="return empty();" required>
						<input type="image" id="grant-promo" alt="set-promo" src="<c:url value='/img/buttons/admin-buttons/grant-promo.png'/>"><br>
					</form>
				</li>
				<li class="admin-btn" id="delete-btn">
					<a class="btn-links" href="<c:url value='/product/remove?value=${product.productId}'/>">
						<img src="<c:url value='/img/buttons/admin-buttons/delete.png'/>" alt="delete">
					</a>
				</li>
			</ul>	
			</c:if>
			</div>
		<div id="product-info">
			<h3>Марка:</h3> 
			<span>${product.tradeMark}</span>
		<div id="description-div">
			<h3>Описание:</h3><br>
			<span>${product.description}</span>
		</div>
		
		<c:if test="${sessionScope.user != null}">
			<c:if test="${isProductInFavourite == false}">
				<form action = "<c:url value='/favourite/addInFavorite'/>" method = "get">
					<input type = "hidden" name = "value" value = "${ product.productId }"/>
					<input type = "submit" class="btn-favourites" value = "Добави в любими"/>
				</form>
			</c:if>
			<c:if test="${isProductInFavourite == true}">
			<form action = "<c:url value='/favourite/removeFromFavorite'/>" method="post">
				<input type = "submit" class="btn-favourites" value = "Премахни от любими">
				<input type = "hidden" name = "value" value = "${ product.productId }"/>
			</form> 
			</c:if>
		</c:if>
	</div>
		
		
		<div id="buy-table">
			
	<table id="product-table">
		<tr>
			<td>
				<c:if test="${product.percentPromo > 0}">
					<img alt="promo-sticker" src="<c:url value='/img/stickers/promo.jpg'/>">
				</c:if>
				<c:if test="${product.isNewProduct == true}">
					<img alt="new-sticker" src="<c:url value='/img/stickers/new.jpg'/>">
				</c:if>
			</td>
			<c:if test="${product.percentPromo == 0}">
				<td><h1>Цена: ${product.price}</h1></td>
			</c:if>
			<c:if test="${product.percentPromo > 0}">
				<c:set var="promoPrice" value="${0}"/>
				<c:set var="promoPrice" value="${promoPrice + product.price - (product.price * product.percentPromo/100)}" />
				<td><h2>Стара цена: <del>${product.price}</del></h2></td>
				<td><h2>Промо цена: <span id="promo-price-red">${promoPrice}</span></h2></td>
			</c:if>
			<td></td>
			<td><center><div id="warranty"><span class="warranty-span">Гаранция: ${product.worranty} месеца</span></div></center></td>
			<td>
				<c:if test="${isProductInStock == true }">
					<form action = "<c:url value='/buyController/buy'/>" method = "post">
					  <input type = "hidden" name = "value" value = "${product.productId}"/>
					  <input type = "image" id="buy-button" alt="buy-button" src="<c:url value='/img/buttons/buy-buttons/buy_online.png'/>"/>
					</form>	
				</c:if>
				<c:if test="${isProductInStock == false }">	
					<a class="btn-links" href="<c:url value='/info/infoContacts'/>">
						<img src="<c:url value='/img/buttons/buy-buttons/request.png'/>" alt="request">
					</a>
					<div id="note-request">
					<span>Продуктът не е в наличност!</span><br>
					<span>Заявете го!</span>
					</div>
				</c:if>
				
		<!-- 	<button><a class="btn-links" href="<c:url value='/buyController/buy?value=${product.productId}'/>">Купи сега</a></button> -->
		</td>
		</tr>
		</table>
		</div>
				</div>
		

		<div id="stores">
		<c:if test="${isProductInStock == true}">
			<img alt="status-legend" src="<c:url value='/img/legends/legend.png'/>">
		</c:if>
		<c:if test="${isProductInStock == false}">
			<img alt="status-legend" src="<c:url value='/img/legends/no_product.png'/>">
		</c:if>
			<c:forEach items="${statusPerStore}" var="entry">
				<ul>
					<c:if test="${(sessionScope.user.isAdmin == false && entry.value != null) || (sessionScope.user.isAdmin == true) || (sessionScope.user == null && entry.value != null)}">
						<li>
						<c:if test="${entry.value != null }">
								<img alt="status" src="<c:url value='${entry.value}'/>">
						</c:if>
						<c:if test="${entry.value == null }">
								<div class="status-push"></div>
						</c:if>
						<div class="city-down"><span><strong>${entry.key.city}</strong>, ${entry.key.address}</span></div>
						

							<c:if test="${sessionScope.user.isAdmin == true}">
							<!-- 	<form action="/product/changeQuantityPerStore" method="post"">  -->
									<div class="change-div">
										<label for="quantity">Смени количество: </label>
									</div>
									<input type="hidden" name="productId" id="product" value="${product.productId}">
									<input type="hidden" name="storeId" id="store${entry.key.storeId}" value="${entry.key.storeId}">
									<input type="number" class="quantity" id="amount${entry.key.storeId}" name="quantity" min="0" max = "1000" onclick="return empty();" required>
									<input type="submit" id="ajax-submit" class="add-store" onclick="submitQuery(${entry.key.storeId})" value="Запиши">
							<!-- </form> --><br>
							</c:if>
						</li>
					</c:if>
				</ul>
        	    </c:forEach>		
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp" />
	</body>
</html>