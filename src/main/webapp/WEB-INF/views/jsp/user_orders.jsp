<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<c:url value="/css/user_order_styles.css" />" rel="stylesheet">
	<title>Техномаркет - потребителски поръчки</title>
</head>
<body>
<jsp:include page="header.jsp" />


	  <c:if test="${sessionScope.user != null}">
      	<c:if test = "${orders.size() == 0 }">
           <div class="page-title">
              <h3>"${user.firstName}" няма направени поръчки!</h3>
           </div >
       	</c:if>
 

  		<c:if test="${orders != null}">
  		<c:if test = "${orders.size() > 0 }">
  			<div class="page-title">
  				<h3>Всички поръчки на ${user.firstName}</h3>
  			</div>
  		</c:if>
  		<div class="log-head" id="common-container">
	 	<table class="order-table">	  
	 		<tr>
	 			<th class="right-pad">Номер</th>
	 			<th class="both-pad">Дата</th>
	 			<th class="both-pad">Статус</th>
	 			<th class="left-pad">Погледни</th>
	 		</tr>
	 		<c:forEach items="${orders}" var="userOrders">
       		<tr>
      			<td class="right-pad">${userOrders.orderId}</td> 
            	<td class="both-pad">${userOrders.time}</td>
            	<td class="both-pad">${userOrders.isConfirmed == false ? "Непотвърдена" : "Потвърдена"}</td> 
            	<td class="left-pad" align="right">
            	<form id="submit-td" action = "<cs:url value='/info/infoFoCurrentOrder'/>" method="post">
                	<input type = "submit" class="submit-btn" value = ">>Виж поръчката">
                	<input type="hidden" name="value" value = "${userOrders.orderId}">
            	</form>
            	</td>
        	</tr>
        	</c:forEach>
        </table><br>			
  		</c:if>
  	</c:if>
  	<c:if test="${sessionScope.user == null}">
		<p>Само потребителите, влезли в сметката си, имат достъп до тази страница!</p>
	</c:if>
	</div>
	<div class="push"></div>
<jsp:include page="footer.jsp" />
</body>
</html>