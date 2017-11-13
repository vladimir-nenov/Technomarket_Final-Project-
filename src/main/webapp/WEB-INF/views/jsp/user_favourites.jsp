<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/filtred_products_styles.css" />" rel="stylesheet">
	<title>Техномаркет - любими</title>
</head>
<body>
	<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container" style="margin-left: 200px;">
			  <c:if test="${sessionScope.user != null}">
   				 <c:if test="${product.size() == 0}">
	  				<h4>${sessionScope.user.firstName} намя добавени продукти в категория любими!</h4>
    			</c:if>
		
		
			<div id="product-container"> 
				<c:if test="${product != null && product.size() > 0}">


   			 	
   		 	<c:set var="endLoop" value="${0}"/>
   			<c:if test="${product.size() <= 4}">
   				<c:set var="endLoop" value="${1}"/>
   			</c:if>	
   			<c:if test="${product.size() % 4 == 0}">
   				<c:set var="endLoop" value="${product.size() / 4}"/>
   			</c:if>
   			<c:if test="${product.size() % 4 != 0}">
   				<c:set var="endLoop" value="${product.size() / 4 + 1}"/>
   			</c:if>	
       		<c:set var="counter" value="${-4}"/>
       		<c:forEach var="i" begin="0" end="${endLoop}">
       			<c:set var="counter" value="${counter + 4}"/>
       			<ul class="product-ul">
       				<c:forEach items="${product}" begin="${counter}" end="${counter + 3}" var="filtredProduct">
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
							<form action = "<c:url value='/favourite/removeFromFavorite'/>" method="post">
				      			<input type = "submit" class="product-btn"  value = "Премахни">
				      			<input type = "hidden" name = "value" value = "${ filtredProduct.productId }"/>
				    		</form>
       					</li>
       				</c:forEach>
       			</ul>
       		</c:forEach>
       	</c:if>
       <c:if test = "${filtredProducts.size() == 0}">
		      <div style = "border:1px solid red">
		     <h3>Няма намерен резутат от търсенето</h3>
		     </div>
		</c:if>
		</div>
		</c:if>
		</div>
		<div class="push"></div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>








<!-- 





  <c:if test="${sessionScope.user != null}">
    <c:if test="${product.size() == 0}">
	  <h4>${sessionScope.user.firstName} намя добавени продукти в категория любими!</h4>
    </c:if>
    <c:if test="${product != null && product.size() > 0}">
         <cs:forEach items="${ product }" var="favourite">
           <h4>Продукт* ${favourite.productNumber }</h4>
                <h4>Цена* ${favourite.price}</h4>
                <h4>Име* ${favourite.name}</h4>
                <h4>Номер на артикул* ${favourite.productNumber}</h4> 
                <h4>Гаранция* ${favourite.worranty}</h4>   
                <a class="btn-links" href="<c:url value='/info/infoForProduct?value=${favourite.productId}'/>">
							<img src="<c:url value='/product/product_pic?value=${favourite.productId}'/>" alt="product-image" width="120" height="auto">
				</a>
                <form action = "<c:url value='/favourite/removeFromFavorite'/>" method="post">
				      <input type = "submit" value = "Премахни продукта">
				      <input type = "hidden" name = "value" value = "${ favourite.productId }"/>
				    </form> 
   		 </cs:forEach>
     </c:if>
     </c:if>
       <c:if test="${sessionScope.user == null}">
				<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
			</c:if>
<jsp:include page="footer.jsp" />
</body>
</html> -->