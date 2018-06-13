<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_cabinet.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Мои заказы</h1>
    <form action="orders" method="get">
    	<table>
    	<tr>
    		<td>
			    <select name="orderFilter">
			    	<option value="1"<c:if test="${orderFilter == 1}"> selected</c:if>>Не подтвержденные</option>
			    	<option value="2"<c:if test="${orderFilter == 2}"> selected</c:if>>В обработке</option>
			    	<option value="3"<c:if test="${orderFilter == 3}"> selected</c:if>>Выполненные</option>
			    	<option value="4"<c:if test="${orderFilter == 4}"> selected</c:if>>Все</option>
			    </select>
		    </td>
		    <td>
		    	<input type="submit" value="Показать">	
		    </td>
	    </tr>
	    </table>
    </form>
    <br>
    <h2>Перечень заказов</h2>
    <table>
	    <tr>
	        <th>Дата заказа</th>
	        <th>Сумма</th>
	        <th>В обработке</th>
	        <th>Выполнен</th>
	        <th>Дата выполнения</th>
	        <th>Отменен</th> 
	        <th>Действия</th>       
	    </tr>
	    <c:forEach items="${orders}" var="order" varStatus="status">
	    <tr>
	        <td>${order.orderDate}</td>
	        <td>${order.sumCurrencyFormat}</td>
	        <td><input type="checkbox"<c:if test="${order.confirmed}"> checked</c:if> disabled></td>
	        <td><input type="checkbox"<c:if test="${order.processed}"> checked</c:if> disabled></td>
	        <td>${order.processedDate}</td>
	        <td><input type="checkbox"<c:if test="${order.cancelled}"> checked</c:if> disabled></td>
	        <td>
	            <a href="<c:url value='/userController/displayClientOrder?orderID=${order.orderID}' />">Просмотреть</a>
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />