<%@ page language="java"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="<c:url value="/css/filtred_products_styles.css" />" rel="stylesheet">
		<script src="<c:url value="/js/product_script.js" />"></script>
		<title>Техномаркет - продукти</title>
	</head>
	<body class="log-head" >
		<jsp:include page="header.jsp" />
		
		<c:if test="${ errorPrice != null}">
		  <h4>Невалидни данни за търсене по цена</h4>
		</c:if>
		
		
		
<div class="log-head" id="common-container">
	<div id="search-menu-container">
		<ul>
			<li class="titles-menu"><span class="menu-word"><strong><center>> Подреди</center></strong></span></li>
			<li class="search-menu-function">
				<form action="<c:url value='/product/compareProduct?compare=${mark}'/> " method="get">
					<center>
						<select name  = "compare" class="select-field">
    		        	<option value = "price">По ценa</option>
    		       		<option value = "mark"  >По марка</option>
   	    	 	    	<option value = "type">Промоции</option>
        				</select>
        			</center><br>
       		 		<input type = "hidden" value = "${categoryName}" name = "categoryName">
        			<input type = "submit" class="product-menu-btn" id="compare-sub" value="OK">
        		</form>
			</li>
			<li id="second-menu-product" class="titles-menu"><span class="menu-word"><strong><center>> Цена</center></strong></span></li>
			<li class="search-menu-function">
				<form action="<c:url value='/product/productByPrice'/> " method="get">

					<%-- <div id="from"><span class="price-index-menu">от</span></div><input type="text" id="number" class="num-field" name="price1" min = "0" max="99999" required >
					<span class="price-index-menu">до</span><input type="text" id="number" class="num-field" name="price2" min="0" max="99999" required ><br>
					<input type = "hidden" value = "${categoryName}" name = "categoryName"> --%>

				<div id="from"><span class="price-index-menu">от</span></div><input type="text" id="number" class="num-field" name="price1" min = "0" max="99999" required >
				<span class="price-index-menu">до</span><input type="text" id="number" class="num-field" name="price2" min="0" max="99999" required ><br>
				     <input type = "hidden" value = "${categoryName}" name = "categoryName">


					<input type = "submit" class="product-menu-btn" id="price-sub" value="OK">
				</form>
			</li>
		</ul> 
	</div>
       <div id="product-container"> 
 		   		<cs:forEach items="${ sessionScope.basket }" var="basket">
					<c:set var="total" value="${(total + ((basket.key.price * basket.value)) - ((basket.key.price*basket.key.percentPromo)/100))}" />
   			 	</cs:forEach>
   			 	
   		 	<c:set var="endLoop" value="${0}"/>
   			<c:if test="${filtredProducts.size() <= 4}">
   				<c:set var="endLoop" value="${1}"/>
   			</c:if>	
   			<c:if test="${filtredProducts.size() % 4 == 0}">
   				<c:set var="endLoop" value="${filtredProducts.size() / 4}"/>
   			</c:if>
   			<c:if test="${filtredProducts.size() % 4 != 0}">
   				<c:set var="endLoop" value="${filtredProducts.size() / 4 + 1}"/>
   			</c:if>	
       		<c:set var="counter" value="${-4}"/>
       		<c:forEach var="i" begin="0" end="${endLoop}">
       			<c:set var="counter" value="${counter + 4}"/>
       			<ul class="product-ul">
       				<c:forEach items="${filtredProducts}" begin="${counter}" end="${counter + 3}" var="filtredProduct">
       					<li class="product-li">
       						<c:if test="${ filtredProduct.percentPromo > 0}">
						 		<img alt="promo-sticker" src="<c:url value='/img/stickers/promo.jpg'/>">
							</c:if>
							<c:if test="${ filtredProduct.percentPromo == 0}">
						 		<div class="push-pic"></div>
							</c:if>
							<c:if test="${ filtredProduct.isNewProduct == true}">
							 	<img alt="new-sticker" src="<c:url value='/img/stickers/new.jpg'/>">
							</c:if>
							<div class="pic-div">
       							<a class="btn-links" href="<c:url value='/info/infoForProduct?value=${filtredProduct.productId}'/>">
									<img src="<c:url value='/product/product_pic?value=${filtredProduct.productId}'/>" class="product-img" alt="product-image">
								</a>
							</div>
							<div class="text-div">
       							<spane>${filtredProduct.name}</span><br>
       						</div>	
       						<div class="number-div">
       							<span>Арт. Nº: ${filtredProduct.productNumber}</span>
       						</div>
       						<div class="price-div">
								<h5>${filtredProduct.price} лв.</h5>
							</div>	
       						<form action = "<c:url value='/buyController/buy'/>" method = "post">
							  <input type = "hidden" name = "value" value = "${ filtredProduct.productId }"/>
							  <input type = "submit" class="product-btn" value = "Купи сега"/>
							</form>	
							
       					</li>
       				</c:forEach>
       			</ul>
       		</c:forEach>
       
       <c:if test = "${filtredProducts.size() == 0}">
		      <div style = "border:1px solid red">
		     <h3>Няма намерен резутат от търсенето</h3>
		     </div>
		</c:if>
		</div>
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
       
       <!-- 
			<c:forEach items="${filtredProducts}" var="filtredProduct">
				<div id="product-box" style="border:1px solid black;">
					<div>
						<a class="btn-links" href="<c:url value='/info/infoForProduct?value=${filtredProduct.productId}'/>">
							<img src="<c:url value='/product/product_pic?value=${filtredProduct.productId}'/>" alt="product-image" width="120" height="auto">
						</a>
					 	<h5>${filtredProduct.name}</h5><br>
						<c:if test="${ filtredProduct.percentPromo > 0}">
						 	<img alt="promo-sticker" src="<c:url value='/img/stickers/promo.jpg'/>">
						</c:if>
						<c:if test="${ filtredProduct.isNewProduct == true}">
						 	<img alt="new-sticker" src="<c:url value='/img/stickers/new.jpg'/>">
						</c:if>
					 	<span>Арт. Nº: ${filtredProduct.productNumber}</span>
						<h5>${filtredProduct.price} лв.</h5>
					</div>
				</div>
				
				<form action = "<c:url value='/buyController/buy'/>" method = "post">
				  <input type = "hidden" name = "value" value = "${ filtredProduct.productId }"/>
				  <input type = "submit" value = "Купи сега"/>
				</form>		
			</c:forEach>
		</div>

		<c:if test = "${filtredProducts.size() == 0}">
		      <div style = "border:1px solid red">
		     <h3>Няма намерен резутат от търсенето</h3>
		     </div>
		</c:if>
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
 -->
