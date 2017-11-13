<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/user_order_styles.css" />" rel="stylesheet">
	<title>Техномаркет - преглед на поръчка</title>
</head>
	<body class="log-head">
		<jsp:include page="header.jsp" />

		<cs:if test="${sessionScope.user != null}">
			<cs:if test="${order!= null}">
	 			<div class="page-title">
	 				<h3>Номер на поръчка* ${order.orderId}</h3>
	 			</div>
	 			
	 			<div class="log-head" id="common-container">	
 		 	 	   	<table class="order-table">	  
	 					<tr>
	 						<th class="right-pad">Продукт</th>
	 						<th class="both-pad">Изображение</th>
	 						<th class="both-pad">Гаранция</th>
	 						<th class="left-pad">Цена</th>
	 					</tr>
	 				<cs:forEach items = "${products}" var = "product" >
       				<tr>
      					<td class="right-pad">${product.key.name}</td> 
            			<td class="both-pad">
            				<img src="<cs:url value='/product/product_pic?value=${product.key.productId}'/>" alt="product-image" width="120" height="auto" />
            			</td>
            			<td class="both-pad">${product.key.worranty}</td> 
            			<td class="left-pad">
            				<cs:if test="${product.key.percentPromo == 0}">
                				<span>${product.key.price} лв.</span>
                			</cs:if>
            				<cs:if test="${product.key.percentPromo != 0}">
                  				<span><del>${product.key.price}</del></span>
				  				<cs:set var="promoPrice" value="${0}"/>
        		  				<cs:set var="promoPrice" value="${promoPrice + product.key.price - (product.key.price * product.key.percentPromo/100)}" />
          		  				<span id="promo-price" style="color: #E60000;">${promoPrice} лв.</span>
               	 			</cs:if>
            			</td>
        			</tr>
        			</cs:forEach>
        			</table><br>			
  	 	   			<cs:set var="total" value="${0}"/>
  	 	   			<cs:forEach items = "${products}" var = "product" >
  	 	     			<c:set var="total" value="${ total + (product.key.price*product.value) - (((product.key.price * product.key.percentPromo)/100)*product.value) }" />
  	 	   			</cs:forEach>
  	 	   			<div id="total-price" style="margin-top: 30px">
  	 	   				<h3><span id="priceSum" style="color: #E60000;">Обща цена: ${total} лв.</span></h3>
  	 	   			</div>
  	 	   			</div>
  	 	   			</cs:if>
  	 	   			</cs:if>
  	 	   			<cs:if test="${sessionScope.user == null}">
						<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
					</cs:if>
		<div class="push" style="margin-top: 300px; margin-left: 200px;"></div>
		<jsp:include page="footer.jsp" />
	</body>
</html>
  	 	   