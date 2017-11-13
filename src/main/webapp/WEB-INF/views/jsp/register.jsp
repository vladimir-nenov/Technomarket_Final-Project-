<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="log-head">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="<c:url value="/js/common_scripts.js" />"></script>
	<link href="<c:url value="/css/register_styles.css" />" rel="stylesheet">	
	<title>Техномаркет - регистрация</title>
</head>
<body class="log-head">
	<jsp:include page="header.jsp" />
		<div class="log-head" id="common-container">
			<div class="log-head" id="left-div">
				<h2>Регистрация</h2>
				<c:if test="${dataError != null }">
					<div>
						<p>Данните за регистрация не са валидни!</p>
					</div>
				</c:if>
				<c:if test="${ date != null }">
					<div>
						<p>Данните за регистрация не са валидни!</p>
					</div>
				</c:if>
				<c:if test="${ passError != null }">
					<div>
						<p>Паролите не съвпадат!</p>
					</div>
				</c:if>
				<c:if test="${ emailError != null }">
					<div>
						<p>Имейл адреса вече съществува!</p>
					</div>
				</c:if>
				<c:if test="${ submitError != null }">
					<div>
						<p>Трябва да приемете условията за да продължите!</p>
					</div>
				</c:if>
				<c:if test="${ invlidPassError != null }">
					<div>
						<p>Паролата трябва да съдържа поне 8 символа от тип букви и цифри едновременно като кирилицата не е разрешена!</p>
					</div>
				</c:if>
				<c:if test="${ invlidMail != null }">
					<div>
						<p>Невалиден имейл!</p>
					</div>
				</c:if>
				<form action="register" method="post">
					<label for="field-1" class="field-label"><strong>Име<span class="asterisk">*</span></strong></label>
					<input id="field-1" type="text" name="firstName" maxlength="35" required><br>
					<label for="field-2" class="field-label"><strong>Фамилия<span class="asterisk">*</span></strong></label>
					<input id="field-2" type="text" name="lastName" maxlength="35" required><br>
					<label for="field-3" class="field-label"><strong>Имейл<span class="asterisk">*</span></strong></label>
					<input id="field-3" type="email" name="email" maxlength="35" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required><br>
					<label for="field-4" class="field-label"><strong>Парола<span class="asterisk">*</span></strong></label>
					<input id="field-4" type="password" name="password" maxlength="35" required></input><br>
					<label for="field-5" class="field-label"><strong>Парола(отново)<span class="asterisk">*</span></strong></label>
					<input id="field-5" type="password" maxlength="35" name="password1" required></input><br>
					<label class="field-label" for="date-1"><strong>Датa на раждане<span class="asterisk">*</span></strong></label>
					<input id="date-1" type="date" name="bday" min="18" max="2017-11-03" required></input><br>
					<label for="radio-box" class="field-label"><strong>Пол<span class="asterisk">*</span></strong></label>
						<input id="radio-1" type="radio" name="gender" checked="checked" value="male">
						<label class="radio-label" for="radio-1">Мъж</label> 
						<input id="radio-2" type="radio" name="gender" value="female">
						<label for="radio-2">Жена</label><br>
					<input class="checkboxes" id="checkbox-1" type="checkbox" name="abonat" value="1"><label for="checkbox-1">Искам да получавам информация за <span id="text">промоционални и нови продукти.</span></label><br> 
					<input class="checkboxes" id="checkbox-2" type="checkbox" name="submit" value="1"><label for="checkbox-2">Приемам условията за ползване.</label><br> 
					<input id="button-red" type="submit" value="Регистация">
				</form>
		
		<!-- Max date to be always today -->
		<script type="text/javascript">
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1;
		var yyyy = today.getFullYear();
		 if(dd < 10){
		        dd='0' + dd
		    } 
		 if(mm < 10){
		        mm='0' + mm
		    } 
		today = yyyy + '-' + mm + '-' + dd;
		document.getElementById("date-1").setAttribute("max", today);
		</script>
		<!-- Max date to be always today -->
			</div>
			<hr id="center-line">
			<div class="log-head" id="right-div">		
				<h2>Имаш профил?</h2>
				<button id="btn-login"><a id="btn-login-a" href="<c:url value='/login'/>"><strong>Вход в сайта</strong></a></button>
				</div>	
			</div>
			<div class="push"></div>
		<jsp:include page="footer.jsp" />
</body>
</html>