<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section class="plan">

<h3>Оптимальный план поставок</h3>
	<table>
		<tr>
			<th>№ п\п</th>
			<th>Клиент</th>
			<th>Заказ</th>
		</tr>
		<c:forEach var="client" items="${clients}" varStatus="status1" >
			<tr>
				<td>${status1.index}</td>
				<td>${client.clientName}</td>
				<td>
				    <c:forEach var="order" items="${orders}"  varStatus="status2">
						<c:if test="${client.clientId == order.client.clientId}">[${order.orderDate}],</c:if> 
				    </c:forEach>
			    </td>
		    </tr>
		</c:forEach>
	</table>
</section>
<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />