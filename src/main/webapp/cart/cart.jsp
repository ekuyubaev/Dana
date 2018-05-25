<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- begin middle column -->

<section class="cart">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<h1>Ваша корзина</h1>
  <c:choose>
      <c:when test="${emptyCart != null}">
          <p>Ваша корзина пуста.</p>
      </c:when>
      <c:otherwise>
        <table>
           <tr>
            <th>Количество</th>
            <th>Наименование</th>
            <th>Цена</th>
            <th>Стоимость</th>
            <th>Действия</th>
         </tr>
          <c:forEach var="item" items="${cart.items}">
            <tr class="cart_row">
              <td>
                <form action="<c:url value='/order/updateItem'/>" method="post">
                  <input type="hidden" name="productID" 
                         value="<c:out value='${item.product.productID}'/>">
                  <input type=text name="quantity" 
                         value="<c:out value='${item.quantity}'/>" id="quantity">
                  <input type="submit" value="Обновить">
                </form>                  
              </td>
              <td>${item.product.productName}</td>
              <td>${item.product.productPrice}</td>
              <td>${item.total}</td>
              <td>
                <form action="<c:url value='/order/removeItem'/>" method="post">
                  <input type="hidden" name="productID" 
                         value="<c:out value='${item.product.productID}'/>">
                  <input type="submit" value="Удалить">
                </form>                  
              </td>
            </tr>
          </c:forEach>
            <tr>
              <td colspan="5">
                <p><b>Чтобы изменить количество продукта</b>, введите новое количество и нажмите Обновить.</p>
                <p><b>Чтобы удалить продукт из корзины</b>, нажмите кнопку Удалить.</p>
              </td>
            </tr>
          </table>
      </c:otherwise>
  </c:choose>

<br>

<form action="<c:url value='/catalog'/>" method="get" id="float_left">
  <input type="submit" value="Продолжить покупку">
</form>
  
<c:if test="${emptyCart == null}">
    <!-- Connection is NOT SECURE.  For testing only. -->
    <form action="<c:url value='/order/checkUser'/>" method="post">
      <input type="submit" value="Оформить заказ">
    </form>
    <!-- Connection is SECURE.  Before you can use it, you need to configure 
    a secure connection on your system as described in chapter 15, comment
    out the previous form, and remove the comments from the following form. -->
    <!--
    <form action="${absolutePathSecure}/order/checkUser" method="post">
      <input type="submit" value="Checkout">
    </form>
    -->
</c:if>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />