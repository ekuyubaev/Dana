<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление продукцией</h1>
    <h4>
        <a href="<c:url value='/adminController/addProduct' />">Добавить новый продукт</a> 
    </h2>
    <br>
    <h2>Перечень продукции</h1>

    <table>
	    <tr>
	        <th>Наименование продукции</th>
	        <th>Категория</th>
	        <th>Количество</th>
	        <th>Ед. измерения</th>
	        <th>Стоимость</th>
	        <th>Примечание</th>
	        <th>Действия</th>        
	    </tr>
	    <c:forEach items="${products}" var="product" varStatus="status">
	    <tr>
	        <td>${product.productName}</td>
	        <td>
				<c:forEach var="category" items="${categories}">
					<c:if test="${category.categoryID == product.productCategoryID}">
						${category.categoryName}
					</c:if>
				</c:forEach>
			</td>
	        <td>${product.productQuantity}</td>
	        <td>
	        	<c:forEach var="measure" items="${measures}">
					<c:if test="${measure.measureID == product.productMeasureID}">
						${measure.measureName}
					</c:if>
				</c:forEach>
			</td>
	        <td>${product.priceCurrencyFormat}</td>
	        <td>${product.productNote}</td>
	        <td>
	            <a href="<c:url value='/adminController/editProduct?productID=${product.productID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteProduct?productID=${product.productID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />