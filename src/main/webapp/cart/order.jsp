<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- begin middle column -->

<section class="cart">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Ваш заказ</h1>

<table>
  <tr>
    <th>Дата</th>
    <td colspan=3>${order.orderDateDefaultFormat}</td>
  </tr>
  <tr>
      <th class="top">Клиент </th>
    <td colspan=3>${client.clientAdressHTMLFormat}</td>
  </tr>
  <tr>
      <td colspan="4"><hr></td>
  </tr>
  <tr>
      <th>Количество</th>
      <th>Описание</th>
      <th>Скидка</th>
      <th>Стоимость</th>
  </tr>

  <c:forEach var="item" items="${order.lineItems}">
  <tr>
    <td>${item.quantity}</td>
    <td>${item.product.productName}</td>
    <td>${item.discountAmount}</td>
    <td>${item.totalCurrencyFormat}</td>
  </tr>
  </c:forEach>

  <tr>
    <th>Итого:</th>
    <td></td>
    <td></td>
    <td>${order.orderTotalCurrencyFormat}</td>
  </tr>
</table>
<br>
<form action="<c:url value='/order/displayUser' />" method="post" id="float_left">
     <input type="submit" value="Edit Address">
</form>

<form action="<c:url value='/order/completeOrder' />" method="post">
     <input type="submit" value="Оформить">
</form>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />