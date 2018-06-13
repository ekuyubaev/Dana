<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Учет клиентов</h1>
    <br>
    <h2>Список клиентов</h1>
    <table>
	    <tr>
	        <th>ФИО клиента</th>
	        <th>Дата рождения</th>
	        <th>Почта</th>
	        <th>Телефон</th>
	        <th>Адрес</th>
	        <th>Логин</th>     
	    </tr>
	    <c:forEach items="${clients}" var="client" varStatus="status">
	    <tr>
	        <td>${client.clientName}</td>
	        <td>${client.clientBirthDate}</td>
	        <td>${client.clientMail}</td>
	        <td>${client.clientPhone}</td>
	        <td>${client.clientAdress}</td>
	        <td>${client.clientLogin}</td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />