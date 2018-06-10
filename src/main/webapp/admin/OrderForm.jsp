<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section  id="admin">
<h2>Заказ № ${order.orderID}</h2>

<table>
	<tr>
	    <th>Дата заказа</th>
	    <th>Клиент</th>
	    <th>Адрес доставки</th>
	    <th>Телефон клиента</th>
	    <th>Подтвержден</th>
	    <th>Выполнен</th>
	    <th>Дата выполнения</th>
	    <th>Отменен</th>       
	</tr>
	<tr>
	    <td>${order.orderDate}</td>
	    <td>${order.client.clientName}</td>
	    <td>${order.client.clientAdress}</td>
	    <td>${order.client.clientPhone}</td>
	    <td><input type="checkbox"<c:if test="${order.confirmed}"> checked</c:if> disabled></td>
	    <td><input type="checkbox"<c:if test="${order.processed}"> checked</c:if> disabled></td>
	    <td>${order.processedDate}</td>
	    <td><input type="checkbox"<c:if test="${order.cancelled}"> checked</c:if> disabled></td>
	</tr>
</table>
<br>
<h3>Содержимое заказа</h3>
<table>
	<tr>
	    <th>Описание</th>
	    <th>Количество</th>
	    <th>Стоимость</th>
	</tr>

	 <c:forEach var="item" items="${order.lineItems}">
		 <tr>
		   <td>${item.product.productName}</td>
		   <td>${item.quantity}</td> 
		   <td>${item.totalCurrencyFormat}</td>
		 </tr>
	 </c:forEach>

	<tr>
	  <th>Итого:</th>
	  <td></td>
	  <td>${order.orderTotalCurrencyFormat}</td>
	</tr>
</table>

<c:if test="${!order.confirmed && !order.cancelled && !order.processed}"> 
<form action="confirmOrder" method="post" id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Подтвердить">
</form>
<form action="cancelOrder" method="post"  id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Отменить">
</form>
</c:if>
<c:if test="${order.confirmed && !order.processed}"> 
<form action="completeOrder" method="post" id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Выполнен">
</form>
<form action="cancelOrder" method="post"  id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Отменить">
</form>
</c:if>

<form action="nakladnaia" method="post"  id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
    <input type="submit" value="Товарная накладная">
</form>

<form action="akt" method="post"  id="float_left">
	<input type="hidden" name="orderID" value="${order.orderID}">
    <input type="submit" value="Акт выполненных работ">
</form>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />