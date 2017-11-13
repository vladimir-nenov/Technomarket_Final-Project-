<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<cs:url value="/css/admin_order_styles.css" />" rel="stylesheet">
	<title>Техномаркет - клиентски поръчки</title>
</head>
	<body>
    <jsp:include page="header.jsp" />
		<c:if test="${sessionScope.user.isAdmin == true}">
      		<c:if test = "${orders.size() == 0 }">
           		<div class="page-title">
              		<h3>Няма направени поръчки!</h3>
           		</div >
       		</c:if>
 

  		<c:if test="${orders != null}">
  			<c:if test = "${orders.size() > 0 }">
  				<div class="page-title">
  					<h3>Поръчки</h3>
  				</div>
  			</c:if>
  		</c:if>
  		
  		<div class="log-head" id="common-container">
	 		<table class="order-table">	  
	 			<tr>
	 				<th class="right-pad">Дата</th>
	 				<th class="both-pad">Статус</th>
	 				<th class="both-pad">Клиент</th>
	 				<th class="left-pad">Телефон</th>
	 				<th class="left-pad">Потвърди/Откажи</th>
	 				<th class="left-pad">Платена</th>
	 			</tr>
	 			<c:forEach items="${orders}" var="order">
       			<tr>
       				<td class="right-pad">${order.time}</td>
       				<td class="both-pad">${order.isConfirmed == false ? "Непотвърдена" : "Потвърдена"}</td>
       				<td class="both-pad">${order.userNames}</td>
       				<td class="both-pad">${order.userPhoneNumber}</td>
       				<td class="both-pad">                  
                      	<c:if test="${order.isConfirmed == false}">
                      		<form action = "<cs:url value='/admin/confirmed'/>" method="post">
                      			<input type = "submit" class="submit-btn" value = "Потвърди">
                      			<input type="hidden" name="value" value = "${order.orderId}">
                       		</form>
                  		</c:if>
                  		<c:if test="${order.isConfirmed == true}">
                           <form action = "<cs:url value='/admin/removeOrder'/>" method="post">
                        	   <input type = "submit" class="submit-btn" value = "Отхвърли">
                        	   <input type="hidden" name="value" value = "${order.orderId}">
                           </form>
                       </c:if> 
       				</td>
       				<td class="left-pad">
       					<c:if test="${order.isConfirmed == false}">
                        	<input type = "submit" class="submit-btn-none" value = "Заплащане">
                  		</c:if>
       					<c:if test="${order.isConfirmed == true}">
                        	 <form action = "<cs:url value='/admin/setPaid'/>" method="post">
                        		 <input type = "submit" class="submit-btn" value = "Заплащане">
                       			 <input type="hidden" name="value" value = "${order.orderId}">
                       		 </form>
                     	</c:if>
       				</td>
       			</tr>
       		</c:forEach>
       	</table>
 	</div>
 	</c:if>
		<c:if test="${sessionScope.user.isAdmin == false}">
			<p>Само администраторите, влезли в сметката си, имат достъп до тази страница!</p>
		</c:if>
			<div class="push"></div>
		    <jsp:include page="footer.jsp" />
	</body>
</html>