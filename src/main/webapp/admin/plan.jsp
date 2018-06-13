<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section class="plan">

<h3>Формирование плана поставок</h3>
<c:if test="${matrix == null}">
	<form action="findOrders" method="post">
		<label>Выбрать не выполненные заказы до даты:  <input type="date" name="ordersDate"></label>
		<input type="submit" value="Найти заказы">
	</form>
</c:if>
<c:if test="${matrix != null}">
	<p>Заполните таблицу расстояний между клиентами и между клиентом и складом.</p>
	<p>Вы можете заполнить только элементы выше главной диагонали, если расстояние одинаковое в обеих направлениях.</p>
	<form action="calcPlan" method="post">
		<input type="hidden" value="${date}" name="date">
		<label><input id="checkBox" type="checkbox" name="plainPlan"> Составить простой план</label>
		<table>
		<c:forEach var="row" items="${matrix}" varStatus="rownum" >
			<tr>
		    <c:forEach var="col" items="${row}"  varStatus="colnum">
		    	<td>
		    	<c:if test="${rownum.index>0 && colnum.index>0 && rownum.index != colnum.index}">
		    		<input type="text" name="cell${rownum.index}${colnum.index}" size="10">
		    	</c:if>
		    	<c:if test="${rownum.index==0 || colnum.index==0}">
			        <c:if test="${rownum.index==0 && colnum.index==1}">
			        	Склад
		        	</c:if>
		        	<c:if test="${rownum.index==1 && colnum.index==0}">
			        	Склад
		        	</c:if>
		        	<c:if test="${rownum.index>1}">
			        	${clients.get(rownum.index-2).clientName}
		        	</c:if>
		        	<c:if test="${colnum.index>1}">
			        	${clients.get(colnum.index-2).clientName}
		        	</c:if>
		        </c:if>
		        </td>
		    </c:forEach>
		    </tr>
		</c:forEach>
		</table>
		<br>
		<input type="submit" value="Сформировать план">
	</form>
</c:if>
</section>
<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />