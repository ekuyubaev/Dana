<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление списком транспортов</h1>
    <h4>
        <a href="<c:url value='/adminController/addTransport' />">Добавить новый транспорт</a> 
    </h2>
    <br>
    <h2>Перечень транспортных средств</h1>

    <table>
	    <tr>
	        <th>Модель ТС</th>
	        <th>Номер ТС</th>
	        <th>Грузоподъемность</th>
	        <th>Холодильная</th>
	        <th>Примечание</th> 
	        <th>Действия</th>       
	    </tr>
	    <c:forEach items="${transports}" var="transport" varStatus="status">
	    <tr>
	        <td>${transport.model}</td>
	        <td>${transport.number}</td>
	        <td>${transport.capacity}</td>
	        <td><input type="checkbox"<c:if test="${transport.fridge}"> checked</c:if> disabled></td>
	        <td>${transport.notes}</td>
	        <td>
	            <a href="<c:url value='/adminController/editTransport?ID=${transport.ID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteTransport?ID=${transport.ID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />