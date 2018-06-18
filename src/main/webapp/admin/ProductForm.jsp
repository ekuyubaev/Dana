<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${product != null}">
            <form action="updateProduct" method="post">
        </c:if>
        <c:if test="${product == null}">
            <form action="insertProduct" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${product != null}">
                        Редактирование продукта
                    </c:if>
                    <c:if test="${product == null}">
                        Добавление нового продукта
                    </c:if>
                </h2>
            </caption>
                <c:if test="${product != null}">
                    <input type="hidden" name="productID" value="<c:out value='${product.productID}' />" />
                </c:if>           
            <tr>
                <th>Наименование: </th>
                <td>
                    <input type="text" name="productName" size="45"
                            value="<c:out value='${product.productName}' />"/>
                </td>
            </tr>
            <tr>
                <th>Категория: </th>
                <td>
                    <select name="productCategoryID">
					    <c:forEach var="category" items="${categories}">
				    		<c:if test="${category.categoryID == product.productCategoryID}">
				    			<option value="${category.categoryID}" selected>${category.categoryName}</option>
				    		</c:if>
				    		<c:if test="${category.categoryID != product.productCategoryID}">
				    			<option value="${category.categoryID}">${category.categoryName}</option>
				    		</c:if>
					    </c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <th>Количество: </th>
                <td>
                    <input type="text" name="productQuantity" size="45"
                            value="<c:out value='${product.productQuantity}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Единица измерения: </th>
                <td>
                    <select name="productMeasureID">
					    <c:forEach var="measure" items="${measures}">
				    		<c:if test="${measure.measureID == product.productMeasureID}">
				    			<option value="${measure.measureID}" selected>${measure.measureName}</option>
				    		</c:if>
				    		<c:if test="${measure.measureID != product.productMeasureID}">
				    			<option value="${measure.measureID}">${measure.measureName}</option>
				    		</c:if>
					    </c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <th>Упаковка: </th>
                <td>
                    <select name="productPackagingID">
					    <c:forEach var="p" items="${packages}">
				    		<c:if test="${p.ID == product.productPackagingID}">
				    			<option value="${p.ID}" selected>${p.name}</option>
				    		</c:if>
				    		<c:if test="${p.ID != product.productPackagingID}">
				    			<option value="${p.ID}">${p.name}</option>
				    		</c:if>
					    </c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <th>Стоимость: </th>
                <td>
                    <input type="text" name="productPrice" size="45"
                            value="<c:out value='${product.productPrice}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Примечание: </th>
                <td>
                    <input type="text" name="productNote" size="45"
                            value="<c:out value='${product.productNote}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Сохранить" />
                </td>
            </tr>
        </table>
        </form>
</div>
</section>   

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />