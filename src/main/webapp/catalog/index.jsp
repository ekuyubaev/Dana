<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_catalog.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section>
    <h1>Каталог продукции</h1>

    <table>
	    <tr>
	        <th>Наименование</th>
	        <th>Единица измерения</th>
	        <th>Количество</th>
	        <th>Стоимость</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${products}" var="product" varStatus="status">
	    <tr>
	        <td>${product.productName}</td>
	        <td>${product.productMeasureID}</td>
	        <td>${product.productQuantity}</td>
	        <td>${product.productPrice}</td>
	        <td>
	        	<a href="<c:url value='/order/addItem?productID=${product.productID}&categoryID=${product.productCategoryID}'/>">
                    Добавить</a>
            </td>
        </form>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->

<jsp:include page="/includes/column_right_news.jsp" flush="true" />
<jsp:include page="/includes/footer.jsp" />