<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_cabinet.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section  id="admin">
<h2>Заказ № ${order.orderID}</h2>

<table>
	<tr>
	    <th>Дата заказа</th>
	    <th>Адрес доставки</th>
	    <th>Телефон клиента</th>
	    <th>Подтвержден</th>
	    <th>Выполнен</th>
	    <th>Дата выполнения</th>
	    <th>Отменен</th>       
	</tr>
	<tr>
	    <td>${order.orderDateDefaultFormat}</td>
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
	    <th>Ед. измерения</th>
	    <th>Упаковка</th>
	    <th>Стоимость</th>
	</tr>

	 <c:forEach var="item" items="${order.lineItems}">
		 <tr>
		   <td>${item.product.productName}</td>
		   <td>${item.quantity}</td> 
		   <td>
		   		<c:forEach var="measure" items="${measures}">
					<c:if test="${measure.measureID == item.product.productMeasureID}">
						${measure.measureName}
					</c:if>
				</c:forEach>
		   </td>
		   <td>
		   		<c:forEach var="p" items="${packages}">
					<c:if test="${p.ID == item.product.productPackagingID}">
						${p.name}
					</c:if>
				</c:forEach>
		   </td>
		   <td>${item.totalCurrencyFormat}</td>
		 </tr>
	 </c:forEach>

	<tr>
	  <th>Итого:</th>
	  <td></td>
	  <td></td>
	  <td></td>
	  <td>${order.sumCurrencyFormat}</td>
	</tr>
</table>

<c:if test="${order.confirmed && !order.cancelled}">
<form action="printContract" method="post">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Договор на поставку">
</form>
</c:if>

<c:if test="${!order.confirmed && !order.cancelled && !order.processed}"> 
<form action="cancelOrder" method="post">
	<input type="hidden" name="orderID" value="${order.orderID}">
     <input type="submit" value="Отменить">
</form>
</c:if>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />