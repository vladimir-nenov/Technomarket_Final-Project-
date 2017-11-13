<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="<c:url value="/js/basket_scripts.js" />"></script>
	<link href="<c:url value="/css/basket_styles.css" />" rel="stylesheet">
	<title>Техномаркет - кошница</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>           
          <div class="page-title">
          	<h2>Кошница</h2>
          </div>
          
		<div id="error">
          <c:if test="${sessionScope.basket.size() == 0}">
              <h4>Няма продукти във вашата кошница!</h4>
          </c:if>
          <c:if test="${productNames != null}">
          	<c:if test="${productNames.size() == 1 }">
          		<span><strong>Следният продукт не е в достатъчна наличност, моля намалете я:</strong></span>
          	</c:if>
          	<c:if test="${productNames.size() > 1 }">
          		<span><strong>Следните продукти не са в достатъчна наличност, моля намалете я:</strong></span>
          	</c:if>	
          		<c:forEach items="${productNames}" var="name" varStatus="loop">
          			<span>${name}</span>
          		</c:forEach>
          </c:if>
          </div>
          
          <div class="log-head" id="common-container">
          <c:if test="${sessionScope.basket.isEmpty() == false}">
          	<table class="order-table">	  
	 					<tr>
	 						<th class="right-pad">Продукт</th>
	 						<th class="both-pad">Марка</th>
	 						<th class="both-pad">Изображение</th>
	 						<th class="both-pad">Цена</th>
	 						<th class="both-pad">Отстъпка</th>
	 						<th class="both-pad">Гаранция</th>
	 						<th class="both-pad">Количество</th>
	 						<th class="left-pad">Премахни</th>
	 					</tr>
	 					<c:forEach items="${sessionScope.basket}" var="entry">
	 						<tr>
      							<td class="right-pad"><strong>${entry.key.name}</strong></td> 
	 							<td class="both-pad">${entry.key.tradeMark}</td>
	 							<td class="both-pad">
	 								<a class="btn-links" href="<c:url value='/info/infoForProduct?value=${entry.key.productId}'/>">
										<img src="<c:url value='/product/product_pic?value=${entry.key.productId}'/>" alt="product-image" width="120px" height="auto">
									</a>
	 							</td>
	 							<td class="both-pad">
	 								<c:if test="${entry.key.percentPromo == 0}">
                   						<span><strong>${entry.key.price} лв.</strong></span>
                    				</c:if>
                   					<c:set var="counting" value="${entry.key.price}"/>
                    				<c:if test="${entry.key.percentPromo > 0}">
										<c:set var="counting" value="${(counting + ((entry.key.price * pro.value)) - ((entry.key.price*entry.key.percentPromo)/100))}" />
                   						<strong>
                   							<span><del>${entry.key.price}</del></span>
                   							<span class="pomo-price">${counting} лв.</span>
                   						</strong>
                   					</c:if>
	 							</td>
	 							<td class="both-pad"><span class="pomo-price">${entry.key.percentPromo}%</span></td>
	 							<td class="both-pad">${entry.key.worranty}</td>
	 							<td class="both-pad"><input type="number" class="quantiy" id="product${entry.key.productId}" min="0" value="${entry.value}" onchange="handleBasket(${entry.key.productId});" onbeforeunload ="return false;"></td>
	 							<td class="left-pad">
	 								<form id="remove${entry.key.productId}" action = "<c:url value='/buyController/removeProduct?value=${entry.key.productId}'/>" method="post">
				     			 		<input type = "submit" class="submit-btn" value = "Премахни">
				    				</form>
	 							</td>
	 						</c:forEach>	
		        		</tr>
        			</table><br>
        			
 			</div>	
 			<div id="btn-container">
 					<div class="basket-btn">
        				<button class="btn-bask">
        					<a class="btn-links" href="<cs:url value='/buyController/makeOrder'/>">Поръчай</a>
        				</button>
        			</div>	
        			<div class="basket-btn" id="btn-more">
        				<button class="btn-bask">
							<a class="btn-links" href="<c:url value='/header/goIndex'/>">Добави още продукти</a>
						</button>
					</div>
        	   </c:if> 
           <div class="basket-btn" id="basket-sum">
           		<c:set var="total" value="${0}"/>
 		   		<cs:forEach items="${ sessionScope.basket }" var="basket">
					<c:set var="total" value="${(total + ((basket.key.price * basket.value)) - ((basket.key.price*basket.key.percentPromo)/100))}" />
   			 	</cs:forEach>
   			 	<c:if test="${total != 0}">
 			  		<h4><span id="priceSum">Обща цена: ${total} лв.</span></h4>
 			 	</c:if>	
 			</div>
 			
 			
 		</div>	
 		<div class="push"></div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>