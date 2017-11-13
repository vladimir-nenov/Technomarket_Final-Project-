<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp" />
<p>Сигурността при въвеждане и пренос на картовите данни се осигурява чрез използване на SSL протокол за криптиране на връзката между нашия сървър и платежната страница на обслужващата ни банка<br />
<br />
-Автентичността на вашата карта се проверява чрез въвеждането на код за сигурност (CVV2)<br />
-В допълнение, за идентифицирането ви като картодържател, платежният сървър за електронна търговия на обслужващата ни банка поддържа схемите за автентикация на международните картови организации - Veryfied by VISA и MasterCard SecureCode, в случай, че сте регистрирани да ги използвате.<br />
<br />
Банковите карти с които можете да направите on-lineразплащане са: MasterCard, MasterCard Electronic, Maestro, Visa, Visa Electron, V PAY<br />
Моля, обърнете внимание, че вашата V PAY или MasterCard Electronic карта ще се приеме само, ако е регистрирана за участие в схемите Verified by Visa или MasterCard SecureCode.<br />
Моля проверете също така, дали вашата карта е отворена от вашият издател за транзакции в Интернет&rdquo;.<br />
Максималната сума за транзакция е 10 000 лева.<br />
<br />
Данните, които клиента трябва да попълни за плащане с карта са:<br />
-Данни за картата (номер, дата на валидност, 3-цифрен код за сигурност, име на картодържателя, адрес)<br />
-Данни за автентикация (За идентифицирането ви като картодържател, платежният ни сървър за електронна търговия на обслужващата ни банка поддържа схемите за автентикация на международните картови организации - Veryfied by VISA и MasterCard SecureCode.&nbsp; В случай, че сте регистрирани от вашата банка Издател в тези схеми за сигурност, на екрана ви ще се появи страница където трябва да въведете вашата автентикационна парола.<br />
<br />
<strong>ВАЖНО:</strong>Техномаркет <strong>НЕ </strong>събира картови данни и като търговец нямаме достъп до тях, а само обслужващата ни банка</p>

<p>Транзакционната валута (тази която се въвежда в платежната страница при покупка) е Български лев.&nbsp;</p>

<p><strong><em>Възможно е забавяне при обработката на Вашите поръчки платени с банкова карта издадена от банки регистрирани извън страната, поради проверки извършвани съвместно с банката картоиздател &nbsp;относно достоверността на плащанията и с цел по-висока Ваша сигурност. В такива случай Ви молим за малко търпение. Веднага щом получим положителен отговор , поръчката Ви ще бъде потвърдена за доставка и изпълнена от служителите на нашата компания.</em></strong></p>
<jsp:include page="footer.jsp" />
</body>
</html>