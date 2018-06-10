<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление скидками</h1>
    <h4>
        <a href="<c:url value='/adminController/addDiscount' />">Добавить новую скидку</a> 
    </h2>
    <br>
    <h2>Перечень скидок</h1>

    <table>
	    <tr>
	        <th>Описание</th>
	        <th>Размер</th>
	        <th>Начало</th>
	        <th>Окончание</th>
	        <th>Продукт</th>
	        <th>Действия</th>        
	    </tr>
	    <c:forEach items="${discounts}" var="discount" varStatus="status">
	    <tr>
	        <td>${discount.discountName}</td>
	        <td>${discount.discountAmount}</td>
	        <td>
	        	<fmt:formatDate value="${discount.discountStart}" pattern="dd.MM.yyyy" />
	        </td>
	        <td>
	        	<fmt:formatDate value="${discount.discountEnd}" pattern="dd.MM.yyyy" />
	        </td>
	        <td>
	        	<c:forEach var="product" items="${products}">
					<c:if test="${product.productID == discount.productID}">
						${product.productName}
					</c:if>
				</c:forEach>
	        </td>
	        <td>
	            <a href="<c:url value='/adminController/editDiscount?discountID=${discount.discountID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteDiscount?discountID=${discount.discountID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />